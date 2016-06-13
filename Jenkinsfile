stage 'Snapshot'
node {
    checkout scm
    sh 'cd Custommap'
    sh 'mvn compile'
    // dir('target') {stash name: 'war', includes: 'x.war'}
}

stage 'Release', concurrency: 1
input parameters: [new StringParameterDefinition('release_version', '0.0.0'), 
	new StringParameterDefinition('next_version', '0.0.0-SNAPSHOT')]
node {
    checkout scm
    sh "echo Release version ${release_version}"
    sh "echo Snapshot version ${release_version}"
    // sh 'mvn clean release'
}
