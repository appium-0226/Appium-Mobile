pipeline {
agent any

environment {
    PATH = "/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin"
}

stages {

    stage('Checkout Code') {
        steps {
            checkout scm
        }
    }

    stage('Update Jenkins Parameters') {
        steps {
            script {

                def xml = new XmlParser().parse('src/test/resources/testng.xml')

                def devices = ['ALL']

                xml.test.each { test ->

                    def testName = test.@name

                    def udid = test.parameter.find {
                        it.@name == 'udid'
                    }?.@value

                    devices.add("${testName}|${udid}")
                }

                def tagsRaw = sh(
                    script: "grep -roh '@[a-zA-Z0-9_-]*' src/test/resources/ --include='*.feature' | sort -u",
                    returnStdout: true
                ).trim()

                def tags = tagsRaw ? tagsRaw.split('\n').toList() : ['@test']

                properties([
                    parameters([

                        choice(
                            name: "TARGET_DEVICE",
                            choices: devices,
                            description: "Choose target device"
                        ),

                        choice(
                            name: "TAGS",
                            choices: tags,
                            description: "Choose cucumber tags"
                        )
                    ])
                ])
            }
        }
    }

    stage('Extract Device Information') {
        steps {
            script {

                if (params.TARGET_DEVICE == 'ALL') {

                    env.TARGET_DEVICE_NAME = 'ALL'
                    env.TARGET_UDID = 'ALL'

                } else {

                    def parts = params.TARGET_DEVICE.split('\\|')

                    env.TARGET_DEVICE_NAME = parts[0]
                    env.TARGET_UDID = parts[1]
                }

                echo "TARGET_DEVICE_NAME=${env.TARGET_DEVICE_NAME}"
                echo "TARGET_UDID=${env.TARGET_UDID}"
            }
        }
    }

    stage('Inject Environment Variables') {
        steps {
            withCredentials([
                string(credentialsId: 'DB_URL', variable: 'DB_URL'),
                string(credentialsId: 'DB_USER', variable: 'DB_USER'),
                string(credentialsId: 'DB_PASSWORD', variable: 'DB_PASSWORD'),
                string(credentialsId: 'APPIUM_URL', variable: 'APPIUM_URL')
            ]) {

                sh """
                echo "DB_URL=$DB_URL" > .env
                echo "DB_USER=$DB_USER" >> .env
                echo "DB_PASSWORD=$DB_PASSWORD" >> .env
                echo "APPIUM_URL=$APPIUM_URL" >> .env
                echo "TARGET_UDID=${env.TARGET_UDID}" >> .env
                echo "TARGET_DEVICE_NAME=${env.TARGET_DEVICE_NAME}" >> .env
                echo "TAGS=${params.TAGS}" >> .env
                """
            }
        }
    }

    stage('Verify Docker') {
        steps {
            sh '''
            which docker
            docker --version
            docker compose version
            '''
        }
    }

    stage('Print Build Parameters') {
        steps {
            sh """
            echo "TARGET_DEVICE_NAME=${env.TARGET_DEVICE_NAME}"
            echo "TARGET_UDID=${env.TARGET_UDID}"
            echo "TAGS=${params.TAGS}"
            """
        }
    }

    stage('Clean Previous Test Artifacts') {
        steps {
            sh '''
            rm -rf allure-results/*
            rm -rf logs/*
            '''
        }
    }

    stage('Run Automated Tests') {
        steps {
            sh '''
            docker compose up --build \
              --remove-orphans \
              --abort-on-container-exit \
              --exit-code-from test-runner
            '''
        }
    }

    stage('Publish Allure Report') {
        steps {
            allure(
                includeProperties: false,
                jdk: '',
                results: [[path: 'allure-results']]
            )
        }
    }
}

post {

    always {

        archiveArtifacts artifacts: 'logs/**', allowEmptyArchive: true

        sh '''
        docker compose down -v --remove-orphans
        '''
    }

    success {
        echo 'Automation pipeline executed successfully!'
    }

    failure {
        echo 'Automation pipeline failed!'
    }

    unstable {
        echo 'Automation pipeline is unstable!'
    }
}

}
