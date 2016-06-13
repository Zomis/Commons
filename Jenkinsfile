stage 'Snapshot'
node {
    checkout scm
    sh 'cd Custommap && mvn compile'
    // dir('target') {stash name: 'war', includes: 'x.war'}
}

stage name: 'Release', concurrency: 1
input parameters: [[$class: 'StringParameterDefinition', name: 'release_version', defaultValue: '0.0.0'], 
	[$class: 'StringParameterDefinition', name: 'next_version', defaultValue: '0.0.0-SNAPSHOT']]
node {
    checkout scm
    sh "echo Release version ${release_version}"
    sh "echo Snapshot version ${release_version}"
    // sh 'mvn clean release'
}
