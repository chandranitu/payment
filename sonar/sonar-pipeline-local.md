pipeline {
     agent any
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/chandranitu/payment.git',
                credentialsId: '39d395ea-2683-4d98-9044-5295be7b0cb2'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('Run SonarScanner') {
            steps {
                script {
                    def remotePassword = 'Mko09ijn' 
                    sh """
                    sshpass -p '${remotePassword}' ssh -o StrictHostKeyChecking=no chandra@192.168.68.128 << 'EOF'
                    echo '${remotePassword}' | sudo -S bash -c 'cd /var/lib/jenkins/workspace/sonar && \\  
                    cp /home/chandra/workspace-24/payment/sonar-project.properties /var/lib/jenkins/workspace/sonar && \\
                    /home/chandra/sonar-scanner/bin/sonar-scanner -Dproject.settings=sonar-project.properties  '
                    """
                }
            }
        }

}
}

