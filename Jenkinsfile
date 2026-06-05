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
                def xmlContent = readFile('src/test/resources/testng.xml')
                def deviceMathcer = xmlContent =~ /<test name="([^"]+)"/
                def devices = ['ALL']
                deviceMathcer.each{ match ->
                devices.add(match[1])
                }
                def tagsSet = [] as Set
                def featureFiles = findFiles(glob: 'src/test/resources/**/*.feature')
                for (file in featureFiles){
                    def content = readFile(file.path)
                    def tagMatcher = content =~ /@[\w-]+/
                    tagMatcher.each{tag -> 
                        tagsSet.add(tag)
                    }
                }    
                def tags = tagsSet.toList()
                properties([
                    parameters([
                        choice(
                            name: "TAGS",
                            choices: tags,
                            description: "Choose a Tag"
                        ),
                        choice(
                            name: "DEVICE_NAME",
                            choices: devices,
                            description: "Choose a Device"
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
