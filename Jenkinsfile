pipeline {
    agent any
    stages {
        stage('Build Order') {
            steps {
                build job: 'tech-emporium.order', wait: true
            }
        }
        stage('Build Redis') {
            steps {
                build job: 'tech-emporium.redis', wait: true
            }
        }
        stage('Build') { 
            steps {
                sh 'mvn clean package'
            }
        }      
        stage('Build Image') {
            steps {
                script {
                    order = docker.build("luccahiratsuca/order:${env.BUILD_ID}", "-f Dockerfile .")
                }
            }
        }
        stage('Push Image') {
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'dockerhub') {
                        order.push("${env.BUILD_ID}")
                        order.push("latest")
                    }
                }
            }
        }
        stage('Deploy on k8s') {
            steps {
                withCredentials([ string(credentialsId: 'minikube_credentials', variable: 'api_token') ]) {
                    sh 'kubectl --token $api_token --server https://host.docker.internal:60504  --insecure-skip-tls-verify=true apply -f ./k8s/deployment.yaml '
                    sh 'kubectl --token $api_token --server https://host.docker.internal:60504  --insecure-skip-tls-verify=true apply -f ./k8s/service.yaml '
                    sh 'kubectl --token $api_token --server https://host.docker.internal:60504  --insecure-skip-tls-verify=true apply -f ./k8s/configmap.yaml '
                }
            }
        }
    }
}