#!groovy
pipeline {
    agent any
	/*
	tools {
        // Use Java 8 for the build
        jdk 'Java81'
    }
*/
    stages {
		  
		  stage("Clone project") {
            steps {
		    	git branch: 'main', url: 'https://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@github.com/factorylatam/pe-gob-sunarp-app-solicitud.git'
		    }
	      }
		  
		  stage('Build') {
			 steps {
			   // sh ("export JAVA_HOME=`/usr/libexec/java_home -v 1.8`")
				sh ("./gradlew clean build")
				// junit allowEmptyResults: true, testResults: '**/test-results/*.xml'
				// step( [ $class: 'JacocoPublisher' ] )
			 }
		}
	/*
        stage('Deploy to Staging') {
            steps {
                // Despliegue a un entorno de staging
                sh ("docker build -t mi-aplicacion .")
            }
        }

        stage('Deploy to Production') {
            steps {
                // Despliegue a un entorno de staging
                sh ("docker run --name app-test -d -p 8082:8080 mi-aplicacion")
            }
        }
		*/
		// junit skipPublishingChecks: true, testResults: 'test-results.xml'

        stage('Test') {
			 steps {
				sh script: "ls build/*", returnStdout: true
				sh ("./gradlew jacocoTestReport")
				sh script: "ls build/*", returnStdout: true
				junit allowEmptyResults: true, testResults: '**/reports/jacoco/testCodeCoverageReport/*.xml'
				step( [ $class: 'JacocoPublisher' ] )
			 }
		}
		
		stage('SonarQube analysis') {
			 steps {
				withSonarQubeEnv('SonarQube') { // Will pick the global server connection you have configured
				  sh './gradlew sonar'
				}
		    }
		 }
		  

    }
    
}
