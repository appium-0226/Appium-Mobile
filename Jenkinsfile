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

    stage('Update Jenkins Parameters'){
        steps{
            script{
                def devicesRaw = sh(
                    script: "grep -o '<test name=\"[^\"]*\"' src/test/resources/testng.xml | sed 's/<test name=\"//g; s/\"//g'",
                    returnStdout: true
                ).trim()
                def devices = ['ALL']
                if (devicesRaw) {
                    devices.addAll(devicesRaw.split('\n').toList())
                }

                def tagsRaw = sh(
                    script: "grep -roh '@[a-zA-Z0-9_-]*' src/test/resources/ --include='*.feature' | sort -u",
                    returnStdout: true
                ).trim()
                def tags = tagsRaw ? tagsRaw.split('\n').toList() : ['@test']

                properties([
                    parameters([
                        choice(
                            name: "TAGS",
                            choices: tags,
                            description: "Choose a Cucumber Tag to run"
                        ),
                        choice(
                            name: "DEVICE_NAME",
                            choices: devices,
                            description: "Choose a Device (ALL = parallel)"
                        )
                    ])
                ])
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

                sh '''
                echo "DB_URL=$DB_URL" > .env
                echo "DB_USER=$DB_USER" >> .env
                echo "DB_PASSWORD=$DB_PASSWORD" >> .env
                echo "APPIUM_URL=$APPIUM_URL" >> .env
                '''
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
        steps{
            sh '''
            echo "Tags: ${TAGS}"
            echo "Device Name: ${DEVICE_NAME}"
            '''
        }
    }

    stage('Run Automated Tests') {
        steps {
            sh 'rm -rf allure-results/*'
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
