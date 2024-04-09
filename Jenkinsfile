pipeline {
    agent any

    stages {

        stage('Unit Tests') {
            steps {
                // Run your unit tests here
                sh 'mvn clean test'
            }
        }

        stage('Maven Package') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t api-best-practice-demo -f docker/Dockerfile .'
            }
        }
    }
}