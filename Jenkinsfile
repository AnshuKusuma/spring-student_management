pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                echo 'Source code is being checked out from GitHub.'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean install'
            }
        }

    }

    post {
        success {
            echo 'Build completed successfully.'
        }

        failure {
            echo 'Build failed.'
        }
    }
}