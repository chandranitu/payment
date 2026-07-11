mvn javadoc:javadoc
javadoc -d docs -sourcepath src -subpackages .


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

        stage('Generate Javadocs') {
            steps {
                sh 'mvn javadoc:javadoc'
            }
        }
    }
}


docker cp jen-mvn:/var/jenkins_home/workspace/test/target/site/apidocs ./apidocs
