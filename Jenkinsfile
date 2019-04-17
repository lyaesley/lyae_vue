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
        echo 'pwd'
        echo '$(pwd)'
        echo "$(pwd)"
      }
    }
    stage('Deliver') {
      steps {
        sh 'bash ./jenkins/scripts/deliver.sh'
      }
    }
  }
}