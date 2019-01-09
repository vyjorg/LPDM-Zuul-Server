pipeline {
    agent any
    tools {
        maven 'Apache Maven 3.5.2'
    }
    stages{
        stage('Checkout') {
            steps {
                git 'https://github.com/vyjorg/LPDM-Zuul-Server'
            }
        }
        stage('Tests') {
            steps {
                sh 'mvn clean test'
            }
            post {
                always {
                    junit 'target/surefire-reports/**/*.xml'
                }
                failure {
                    error 'The tests failed'
                }
            }
        }
        stage('Push to DockerHub') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Deploy') {
            steps {
                sh 'docker stop LPDM-ZuulMS || true && docker rm LPDM-ZuulMS || true'
                sh 'docker pull vyjorg/lpdm-zuul:latest'
                sh 'docker run -d --name LPDM-ZuulMS -p 28090:28090 --restart always --memory-swappiness=0  vyjorg/lpdm-zuul:latest'
            }
        }
    }
}