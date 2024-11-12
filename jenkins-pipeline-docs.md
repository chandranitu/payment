mvn javadoc:javadoc

javadoc -d docs -sourcepath src -subpackages .


pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/chandranitu/payment.git', credentialsId: '39d395ea-2683-4d98-9044-5295be7b0cb2'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean install && mvn javadoc:javadoc'
            }
        }
    }
}

