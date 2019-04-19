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
    	sh 'docker stop lyae-was'
		
	    dir("/home/lyae/dev/docker_lyae_web") {
		    sh "pwd"
		    sh 'ls -l'
		    //sh 'docker build --tag lyae/was:1.0 -f Dockerfile-was . '
		}
    }
}