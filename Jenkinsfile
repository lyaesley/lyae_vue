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
        sh 'mvn -B -DskipTests clean package'
        //archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
        sh 'cp -f $(pwd)/target/*.jar /home/lyae/dev/app/app.jar'
      }
    }
    stage('Deliver') {
      steps {
        //sh 'bash ./jenkins/scripts/deliver.sh'
        sh 'cp -f $(pwd)/target/*.jar /home/lyae/dev/app/app.jar'
      }
    }
  }
}