pipeline {
    agent any

    environment {
        // ECR repository URI
        ECR_REGISTRY = "885657313466.dkr.ecr.ap-south-1.amazonaws.com/rhoboardcontainerrepo"
        VERSION = "1.0.${BUILD_NUMBER}"

        // Use workspace for Maven local repo
        MAVEN_OPTS = "-Dmaven.repo.local=$WORKSPACE/.m2/repository"
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out source code...'
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo 'Building Spring Boot application with Maven JDK 21...'
                sh """
                docker run --rm \
                    -v $WORKSPACE:/workspace \
                    -w /workspace \
                    maven:3.9-eclipse-temurin-21-alpine \
                    mvn clean package -Dmaven.repo.local=/workspace/.m2/repository
                """
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Building Docker image...'
                script {
                    def image = "${env.ECR_REGISTRY}:${env.VERSION}"
                    sh "docker build -t ${image} ."
                }
            }
        }

        stage('Push to ECR') {
            steps {
                echo 'Pushing Docker image to ECR...'
                script {
                    def image = "${env.ECR_REGISTRY}:${env.VERSION}"
                    sh """
                        aws ecr get-login-password --region ap-south-1 | docker login --username AWS --password-stdin ${env.ECR_REGISTRY}
                        docker push ${image}
                    """
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                echo 'Deploying to Kubernetes...'
                script {
                    sh """
                    set -exu

                    aws eks update-kubeconfig --name first-cluster --region ap-south-1 --kubeconfig /tmp/kubeconfig
                    export KUBECONFIG=/tmp/kubeconfig
                    echo "Using kubeconfig: \$KUBECONFIG"
                    kubectl version --client
                    kubectl cluster-info
                    kubectl apply -f k8s/deployment.yaml
                    kubectl apply -f k8s/service.yaml
                    kubectl set image deployment/rhoboard-deployment rhoboard-container=${ECR_REGISTRY}:${VERSION} --record
                    kubectl rollout status deployment/rhoboard-deployment
                """


                }
            }
        }
    }

    post {
        always {
            echo 'Cleaning up workspace...'
            cleanWs()
        }
    }
}
