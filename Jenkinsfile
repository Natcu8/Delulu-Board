// This Jenkinsfile defines the CI/CD pipeline 

pipeline {
    // The agent is the environment where the pipeline will run.
    // We use a Docker agent with a Maven image to ensure the correct build tools are available.
    agent {
        docker {
            image 'maven:3.9.11-eclipse-temurin-17-noble'
            args '-v /root/.m2:/root/.m2' // Caching Maven dependencies
        }
    }

    environment {
        // You MUST replace this with your ECR repository URI
        // Example: '123456789012.dkr.ecr.us-east-1.amazonaws.com/your-repo-name'
        ECR_REGISTRY = "885657313466.dkr.ecr.ap-south-1.amazonaws.com/rhoboardcontainerrepo"

        // Your Spring Boot application name
        DOCKER_IMAGE_NAME = "Rhoboard"

        // This creates a unique version number for each build
        VERSION = "1.0.${BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                // The 'checkout scm' step is automatically handled by the Git plugin
                // It pulls the latest code from the repository on every trigger.
                echo 'Checking out source code...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Building the Spring Boot application with Maven...'
                sh 'mvn clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Building Docker image...'
                script {
                    def image = "${env.ECR_REGISTRY}/${env.DOCKER_IMAGE_NAME}:${env.VERSION}"
                    // The Dockerfile in your repository root will be used
                    docker.build(image)
                }
            }
        }

        stage('Push to ECR') {
            steps {
                echo 'Pushing Docker image to ECR...'
                script {
                    def image = "${env.ECR_REGISTRY}/${env.DOCKER_IMAGE_NAME}:${env.VERSION}"
                    // This command uses the credentials you will set up in Jenkins
                    docker.withRegistry("https://${env.ECR_REGISTRY}", 'aws-ecr-credentials') {
                        docker.image(image).push()
                    }
                }
            }
        }
    }
}
