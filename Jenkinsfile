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

        // stage('Test') {
        //     steps {
        //         bat 'mvn test'
        //     }
        // }

        stage('SonarQube Analysis') {
    		steps {
        		withSonarQubeEnv('SonarQube') {
            		bat '''
            		mvn clean verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar ^
           			 -Dsonar.projectKey=spring-crud ^
            		-Dsonar.projectName=spring-project
           			 '''
       		 }
    		}
		}

        //stage('Quality Gate') {
          //  steps {
            //    timeout(time: 2, unit: 'MINUTES') {
              //      waitForQualityGate abortPipeline: true
                //}
            //}
        //}
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