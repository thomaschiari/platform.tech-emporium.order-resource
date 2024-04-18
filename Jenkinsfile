pipeline {
    agent any
    stages {
        stage('Build Order') {
            steps {
                build job: 'tech-emporium.order', wait: true
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
    }
}