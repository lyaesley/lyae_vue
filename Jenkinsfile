pipeline {
  agent {
    docker {
      image 'maven:3-alpine'
      args '-v /root/.m2:/root/.m2'
    }
  } //end agent
  stages {
  
    stage('Build') {
      steps {
        sh 'mvn -B -DskipTests clean package -P prod'
        //archiveArtifacts artifacts: '**/target/*.war', fingerprint: true
      }
    } //end Build
    
    stage('Deliver') {
      steps {
        //sh 'bash ./jenkins/scripts/deliver.sh'
        sh 'cp -f $(pwd)/target/*.war /home/lyae/dev/docker_lyae_web/app/app.war'
      }
    } //end Deliver
    
    stage('Deploy') {
    agent {docker}
      steps {
      	sh 'ls -l'
      	sh 'pwd'
      	sh 'docker ps'
      	sh 'docker stop lyae-was'
        //sh 'bash ./jenkins/scripts/deliver.sh'
        //sh 'docker stop lyae-was'
		//sh 'ls -l /home/lyae/dev/docker_lyae_web'
		//sh 'docker build --tag lyae/was:1.0 -f Dockerfile-was /home/lyae/dev/docker_lyae_web'
      }
    } //end Deploy
    
  } //end stages
} //end pipeline