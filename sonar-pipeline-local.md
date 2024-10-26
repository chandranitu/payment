pipeline {
     agent any
    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/chandranitu/payment.git',
                credentialsId: '84465c96-9185-4d6b-8f76-5d44899e7755'
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
                    ssh -tt '${remotePassword}' ssh -o StrictHostKeyChecking=no chandra@10.10.10.60 << 'EOF'
                    cd ~/app/
                    sh """
                    sonar-scanner -Dsonar.projectKey=payment123 
                                   -Dsonar.projectName="payment123" 
                                   -Dsonar.projectVersion=1.0 
                                   -Dsonar.sources=src 
                                   -Dsonar.java.binaries=target 
                                   -Dsonar.host.url=http://localhost:9000 
                                   -Dsonar.login=admin 
                                   -Dsonar.password=Mko09ijn@123  # Preferably use a token
                                   -Dsonar.token=sqp_3be93e740a027aa1ec564fd051b31d421410010a 
                                   -Dsonar.scm.provider=svn 
                                   -Dsonar.exclusions=node_modules/**,src/environments/**,**/*.spec.ts,dist/**,**/docs/**,**/*.js,e2e/**,coverage/**,TLH-distributions/**,src/bsb-theme/css/** 
                                   -Dsonar.ts.tslint.configPath=tslint.json 
                                   -Dsonar.typescript.lcov.reportPaths=coverage/lcov.info
                    """
                }
            }
        }

}
}

