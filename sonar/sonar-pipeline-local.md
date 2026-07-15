pipeline {
    agent any  

    stages {        
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/chandranitu/payment.git',
                    credentialsId: '62bb5cec-3fa1-4e16-ab34-6898bc36329e'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

     stage('SonarQube Analysis') {
    steps {
        withSonarQubeEnv('SonarQube') {
            sh '''
                mvn sonar:sonar \
                  -Dsonar.host.url=http://sonarqube:9000 \
                  -Dsonar.token=squ_28ecba2ce6b216253fae2e43a91400833bb1574b \
                  -Dsonar.projectKey=my-app \
                  -Dsonar.projectName=my-app
            '''
        }
    }
}

        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

    }

}



