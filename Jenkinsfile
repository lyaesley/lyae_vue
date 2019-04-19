node {
    stage('Build') {
    	docker.image('maven:3-alpine').inside {
	        sh 'mvn -B -DskipTests clean package -P prod'
	        //archiveArtifacts artifacts: '**/target/*.war', fingerprint: true
    	}
    }
    stage('Deliver') {
        //sh 'bash ./jenkins/scripts/deliver.sh'
        sh 'cp -f $(pwd)/target/*.war /home/lyae/dev/docker_lyae_web/app/app.war'
    }
    stage('Deploy') {
    	echo 'deploy'
    }
}