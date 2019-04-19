pipeline {
  agent {
    docker {
      image 'maven:3-alpine'
      args '-v /root/.m2:/root/.m2'
    }
  }
  
  stages {
    stage('Build') {
      steps {
        sh 'mvn -B -DskipTests clean package -P prod'
        //archiveArtifacts artifacts: '**/target/*.war', fingerprint: true
      }
    }
    stage('Deliver') {
      steps {
        //sh 'bash ./jenkins/scripts/deliver.sh'
        sh 'cp -f $(pwd)/target/*.war /home/lyae/dev/docker_lyae_web/app/app.war'
      }
    }
    stage('Deploy') {
      steps {
        //sh 'bash ./jenkins/scripts/deliver.sh'
		dir('/home/lyae/dev/docker_lyae_web') {
			echo 'hi deploy'
	        sh 'ls -l app'
        }
      }
    }
    
  }
}