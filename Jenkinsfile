pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "galaizar/my-app:latest"
        K8S_DIR      = "k8s"
        SONAR_TOKEN  = credentials('sonar-token')
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build + SonarQube') {
            steps {
                withSonarQubeEnv('sonarqube') {
                    sh """
                      mvn -B clean verify sonar:sonar \
                        -Dsonar.projectKey=my-app \
                        -Dsonar.token=${SONAR_TOKEN}
                    """
                }
            }
        }

        stage('Build & Push Docker') {
            steps {
                sh """
                  docker build -t ${DOCKER_IMAGE} .
                  docker push ${DOCKER_IMAGE}
                """
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh """
                  kubectl apply -f ${K8S_DIR}/mysql-secret.yaml
                  kubectl apply -f ${K8S_DIR}/mysql-pvc.yaml
                  kubectl apply -f ${K8S_DIR}/mysql-deployment.yaml
                  kubectl apply -f ${K8S_DIR}/app-deployment.yaml
                  kubectl get pods
                """
            }
        }
    }

    post {
        always {
            echo "Pipeline terminée (Maven + SonarQube + Docker + Kubernetes)."
        }
    }
}
