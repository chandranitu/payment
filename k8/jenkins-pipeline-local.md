pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/chandranitu/payment.git',
                    credentialsId: '308b351d-d54e-4be5-b786-0e52762f3cf5'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Docker Build') {
            steps {
                sh '''
                docker build -t payment:latest .
                '''
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                docker stop payment || true
                docker rm payment || true

                docker run -d \
                    --name payment \
                    -p 8088:8088 \
                    payment:latest
                '''
            }
        }
    }
}
