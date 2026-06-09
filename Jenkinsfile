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

                def devicesRaw = sh(
                        script: """
                    grep '<test ' src/test/resources/testng.xml | sed 's/.*name="//;s/".*//' | while IFS= read -r name; do
                        udid=\$(grep -A20 "name=\\"\${name}\\"" src/test/resources/testng.xml | grep 'name="udid"' | sed 's/.*value="//;s/".*//' | head -1)
                        platform=\$(grep -A5 "name=\\"\${name}\\"" src/test/resources/testng.xml | grep 'name="platformName"' | sed 's/.*value="//;s/".*//' | head -1)
                        echo "\${name}|\${udid}|\${platform}"
                    done | grep '|'
                """,
                        returnStdout: true
                ).trim()

                def deviceMap = [:]
                def platformGroups = [:]
                if (devicesRaw) {
                    devicesRaw.split('\n').each { line ->
                        def parts = line.split('\\|')
                        if (parts.size() == 3) {
                            def name = parts[0].trim()
                            def udid = parts[1].trim()
                            def platform = parts[2].trim()
                            deviceMap[name] = udid
                            if (!platformGroups[platform]) platformGroups[platform] = []
                            platformGroups[platform] << name
                        }
                    }
                }

                def devices = ['ALL']
                platformGroups.keySet().sort().each { platform ->
                    devices << platform
                }
                platformGroups.keySet().sort().each { platform ->
                    devices.addAll(platformGroups[platform])
                }

                env.DEVICE_MAP = deviceMap.collect { k, v -> "${k}||${v}" }.join('\n')
                env.PLATFORM_MAP = platformGroups.collect { plat, names -> "${plat}||${names.join(',')}" }.join('\n')

                def tagsRaw = sh(
                        script: "grep -roh '@[a-zA-Z0-9_-]*' src/test/resources/ --include='*.feature' | sort -u",
                        returnStdout: true
                ).trim()

                def tags = tagsRaw ? tagsRaw.split('\n').toList() : ['@test']

                properties([
                        parameters([
                                choice(
                                        name: 'TARGET_DEVICE',
                                        choices: devices,
                                        description: 'Choose target device'
                                ),
                                choice(
                                        name: 'TAGS',
                                        choices: tags,
                                        description: 'Choose cucumber tag'
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

                } else if (params.TARGET_DEVICE == 'Android' || params.TARGET_DEVICE == 'iOS') {
                    env.TARGET_DEVICE_NAME = params.TARGET_DEVICE
                    env.TARGET_UDID = 'ALL'
                    env.TARGET_PLATFORM = params.TARGET_DEVICE

                } else {
                    def map = [:]
                    env.DEVICE_MAP.split('\n').each { entry ->
                        def idx = entry.indexOf('||')
                        map[entry.substring(0, idx)] = entry.substring(idx + 2)
                    }
                    env.TARGET_DEVICE_NAME = params.TARGET_DEVICE
                    env.TARGET_UDID = map[params.TARGET_DEVICE]
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
                string(credentialsId: 'APPIUM_URL', variable: 'APPIUM_URL'),
                string(credentialsId: 'TELEGRAM_BOT_TOKEN', variable: 'TELEGRAM_BOT_TOKEN'),
                string(credentialsId: 'TELEGRAM_CHAT_ID', variable: 'TELEGRAM_CHAT_ID')
            ]) {

                sh """
                echo "DB_URL=$DB_URL" > .env
                echo "DB_USER=$DB_USER" >> .env
                echo "DB_PASSWORD=$DB_PASSWORD" >> .env
                echo "APPIUM_URL=$APPIUM_URL" >> .env
                echo "TARGET_UDID=${env.TARGET_UDID}" >> .env
                echo "TARGET_DEVICE_NAME=${env.TARGET_DEVICE_NAME}" >> .env
                echo "TARGET_PLATFORM=${env.TARGET_PLATFORM ?: 'ALL'}" >> .env
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
            catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                sh '''
                docker compose up --build \
                  --remove-orphans \
                  --abort-on-container-exit \
                  --exit-code-from test-runner
                '''
            }
        }
    }

    stage('Publish Allure Report') {
        steps {
            catchError(buildResult: 'FAILURE', stageResult: 'UNSTABLE') {
                allure(
                    includeProperties: false,
                    jdk: '',
                    results: [[path: 'allure-results']]
                )
            }
        }
    }
}

post {

    always {

        archiveArtifacts artifacts: 'logs/**', allowEmptyArchive: true

        sh '''
        docker compose down --remove-orphans
        '''
    }

    success {
        echo 'Automation pipeline executed successfully!'

        withCredentials({
            string(credentialsId: 'TELEGRAM_BOT_TOKEN', variable: 'TELEGRAM_BOT_TOKEN')
            string(credentialsId: 'TELEGRAM_CHAT_ID', variable: 'TELEGRAM_CHAT_ID')
        }) {
            sh """
            curl -s -X POST "https://api.telegram.org/bot${TELEGRAM_BOT_TOKEN}/sendMessage" \
            -d chat_id="${TELEGRAM_CHAT_ID}" \
            -d text="
                ✅ Automation Success
                
                Project:${JOB_NAME}
                Build Number:${BUILD_NUMBER}
                Tags:${params.TAGS}
                Device:${env.TARGET_DEVICE_NAME}
                
                Report: ${BUILD_URL}allure
            """
        }
    }

    failure {
        echo 'Automation pipeline failed!'

        withCredentials({
            string(credentialsId: 'TELEGRAM_BOT_TOKEN', variable: 'TELEGRAM_BOT_TOKEN')
            string(credentialsId: 'TELEGRAM_CHAT_ID', variable: 'TELEGRAM_CHAT_ID')
        }) {
            sh """
            curl -s -X POST "https://api.telegram.org/bot${TELEGRAM_BOT_TOKEN}/sendMessage" \
            -d chat_id="${TELEGRAM_CHAT_ID}" \
            -d text="
                ❌ Automation Failed
                Project:${JOB_NAME}
                Build Number:${BUILD_NUMBER}
                Tags:${params.TAGS}
                Device:${env.TARGET_DEVICE_NAME}
                
                Report: ${BUILD_URL}allure
            """
        }
    }

    unstable {
        echo 'Automation pipeline is unstable!'
    }
}

}
