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
                sh 'docker build -t payment:latest .'
            }
        }

        stage('Load Image to Kind') {
            steps {
                sh 'kind load docker-image payment:latest --name payment-cluster'
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh '''
                kubectl apply -f deployment.yaml
                kubectl apply -f service.yaml
                '''
            }
        }
    }
}
