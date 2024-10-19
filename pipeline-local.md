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
                credentialsId: '3e583b36-d939-4350-b782-cc1ea1891449'
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

                    // Build Docker image with the specified environment profile
                    sh """
                    sudo docker build --build-arg SPRING_PROFILES_ACTIVE=${profile} -t payment:${profile} .
                    """

                    // Optional: Push to Docker registry (adjust based on your setup)
                    // sh "docker tag payment:${profile} your-docker-repo/cam:${profile}"
                    // sh "docker push your-docker-repo/payment:${profile}"
                }
            }
        }

    stage('Deploy') {
            steps {
                script {
                    def profile = params.ENVIRONMENT
                    echo "Deploying Docker container for profile: ${profile}"

                    // Stop and remove any running container for the environment
                    sh """
                    if [ \$(sudo docker ps -q -f name=payment_${profile}) ]; then
                        sudo docker stop payment_${profile}
                        sudo docker rm payment_${profile}
                    fi
                    """

                    // Remove the old Docker image for the specified profile
                    sh """
                    if [ \$(sudo docker images -q payment:${profile}) ]; then
                        sudo docker rmi payment:${profile}
                    fi
                    """

                    // Run the new Docker container for the specified environment
                    sh """
                    sudo docker run -d --name payment_${profile} -p 8088:8088 payment:${profile}
                    """
                }
            }
        }
    }
  
    post {
        success {
            echo 'Build was successful!'
        }
        failure {
            echo 'Dev Build failed.'
        }
    }
}