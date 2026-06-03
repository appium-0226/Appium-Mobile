pipeline {
agent any

```
environment {
    PATH = "/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin"
}

stages {

    stage('Checkout Code') {
        steps {
            checkout scm
        }
    }

    stage('Inject Environment Variables') {
        steps {
            withCredentials([
                string(credentialsId: 'DB_URL', variable: 'DB_URL'),
                string(credentialsId: 'DB_USER', variable: 'DB_USER'),
                string(credentialsId: 'DB_PASSWORD', variable: 'DB_PASSWORD')
            ]) {

                sh '''
                echo "DB_URL=$DB_URL" > .env
                echo "DB_USER=$DB_USER" >> .env
                echo "DB_PASSWORD=$DB_PASSWORD" >> .env
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

    stage('Run Automated Tests') {
        steps {
            sh '''
            docker compose up --build --remove-orphans --exit-code-from test-runner
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
        sh '''
        docker compose down -v --remove-orphans
        '''
    }
}
```

}
