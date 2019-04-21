node {
	docker.image('maven:3-alpine').inside('-v /root/.m2:/root/.m2' ) {
		stage('CheckOut') {
			checkout scm		
		}
	    stage('Build') {
		        sh 'mvn -B -DskipTests clean package -P prod'
		        //archiveArtifacts artifacts: '**/target/*.war', fingerprint: true
    	}
    }
	    stage('Deliver') {
	        //sh 'bash ./jenkins/scripts/deliver.sh'
	        sh 'cp -f $(pwd)/target/*.war /home/lyae/dev/docker_lyae_web/app/app.war'
	    }
	    stage('Stop') {
	    	//sh 'docker stop lyae-was'
	    	sh 'docker rm -f lyae-was'
	    }
	    stage('Deploy') {
		    dir("/home/lyae/dev/docker_lyae_web") {
			    sh "pwd"
			    sh 'ls -l app'
			    sh 'docker build --tag lyae/was:1.0 -f Dockerfile-was . '
			    sh 'docker-compose up -d'
			}
	    }
	    stage('RM image') {
	    	sh 'docker rmi $(docker images -f "dangling=true" -q)'
    	}
}