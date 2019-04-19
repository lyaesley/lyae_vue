node {
    stage('Build') {
    	docker.image('maven:3-alpine').inside('-v /root/.m2:/root/.m2' ) {
	        sh 'mvn -B -DskipTests clean package -P prod'
	        //archiveArtifacts artifacts: '**/target/*.war', fingerprint: true
    	}
    }
    stage('Deliver') {
        //sh 'bash ./jenkins/scripts/deliver.sh'
        sh 'cp -f $(pwd)/target/*.war /home/lyae/dev/docker_lyae_web/app/app.war'
    }
    stage('Deploy') {
    	docker.image('lyae/was:1.0').inside('-p 9090:9090'){
    		sh 'java -jar /app.war'
    	}
    }
}