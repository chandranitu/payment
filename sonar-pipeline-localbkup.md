pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                script {
                    def sshKeyId = '2bcde7d0-b45e-41e3-8f95-e5776cc18d47' 
                sshagent([sshKeyId]) {
                        sh """
                        ssh -tt -o StrictHostKeyChecking=no chandra@192.168.1.9 << 'EOF'
                        cd ~/app 
                        echo "Cleaning up previous checkout..."
                        rm -rf ~/app/*
                        echo "Cloning the repository..."
                        git clone -b main https://github.com/chandranitu/payment.git .
                        echo "Checkout completed. Current directory content:"
                        ls -la
                        EOF
                        """
                    }
            }
        }
        }

        stage('Build') {
            steps {
                script {
                    def sshKeyId = '2bcde7d0-b45e-41e3-8f95-e5776cc18d47' // Use Jenkins credentials in a real setup

                    // Use the sshagent to handle SSH key
                    sshagent([sshKeyId]) {
                        sh """
                        ssh -tt -o StrictHostKeyChecking=no chandra@192.168.1.9 << 'EOF'
                        cd ~/app/payment || exit
                        echo "Building the project..."
                        mvn clean install
                        EOF
                        """
                    }
                }
            }
        }
        
        stage('SonarQube Analysis') {
            steps {
                script {
                    def sshKeyId = '2bcde7d0-b45e-41e3-8f95-e5776cc18d47' // Replace with your Jenkins credential ID

                    sshagent([sshKeyId]) {
                        sh """
                        ssh -tt -o StrictHostKeyChecking=no chandra@192.168.1.9 << 'EOF'
                        cd ~/app/payment || exit
                        echo "Running SonarScanner..."
                        sonar-scanner -Dsonar.projectKey=payment123 
                                      -Dsonar.projectName="payment123" 
                                      -Dsonar.projectVersion=1.0 
                                      -Dsonar.sources=src 
                                      -Dsonar.java.binaries=target 
                                      -Dsonar.host.url=http://localhost:9000 
                                      -Dsonar.login=admin 
                                      -Dsonar.password=Mko09ijn@123   // Consider using a token instead of password
                                      -Dsonar.token=sqp_3be93e740a027aa1ec564fd051b31d421410010a 
                                      -Dsonar.scm.provider=svn 
                                      -Dsonar.exclusions=node_modules/**,src/environments/**,**/*.spec.ts,dist/**,**/docs/**,**/*.js,e2e/**,coverage/**,TLH-distributions/**,src/bsb-theme/css/** 
                                      -Dsonar.ts.tslint.configPath=tslint.json 
                                      -Dsonar.typescript.lcov.reportPaths=coverage/lcov.info
                        EOF
                        """
                    }
                }
            }
        }
    }
}
