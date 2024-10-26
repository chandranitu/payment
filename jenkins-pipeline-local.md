pipeline {
    agent any


    parameters {
        choice(
            name: 'ENVIRONMENT',
            choices: ['dev', 'qa'],
            description: 'Choose the environment for the build'
        )
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/chandranitu/payment.git',
                credentialsId: '39d395ea-2683-4d98-9044-5295be7b0cb2'
            }
        }
     
        
        stage('Build') {
            steps {
                script {
                    // Set profile based on Jenkins parameter
                    def profile = params.ENVIRONMENT
                    echo "Building with profile: ${profile}"

                    // Build the Spring Boot application with the selected profile
                    sh "mvn clean install -Dspring.profiles.active=${profile}"
                }
            }
        }
    
stage('Docker Build & Push') {
    steps {
        script {
            def profile = params.ENVIRONMENT
            echo "Building Docker image for profile: ${profile}"

            def remotePassword = 'Mko09ijn' // Use credentialsId for sensitive data in real setups                    

            sh """
            sshpass -p '${remotePassword}' ssh -o StrictHostKeyChecking=no chandra@192.168.1.9 << 'EOF'
            echo '${remotePassword}' | sudo -S bash -c 'cd /var/lib/jenkins/workspace/spring && \
             # Remove existing image if it exists
                if [ \$(sudo docker images -q payment:${profile}) ]; then
                    sudo docker rmi payment:${profile}
                fi
            docker build --no-cache --build-arg SPRING_PROFILES_ACTIVE=${profile} -t payment:${profile} .'
           
            """
        }
    }
}
stage('Deploy') {
            steps {
                script {
                    def profile = params.ENVIRONMENT
                    echo "Deploying Docker container for profile: ${profile}"

                    def remotePassword = 'Mko09ijn' 
           
            sh """
            sshpass -p '${remotePassword}' ssh -o StrictHostKeyChecking=no chandra@192.168.1.9 << 'EOF'
            echo '${remotePassword}' | sudo -S bash -c '
                # Stop and remove existing container if it exists
                if [ \$(sudo docker ps -q -f name=payment_${profile}) ]; then
                    sudo docker stop payment_${profile}
                    sudo docker rm payment_${profile}
                fi

               
                # Run the new container
                sudo docker run -d --name payment_${profile} -p 8088:8088 payment:${profile}
            '
            
            """
                }
            }
        } 
   
}
}