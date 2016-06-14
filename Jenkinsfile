node {
    stage 'Snapshot'
    checkout scm
    sh 'cd Custommap && mvn compile'

    stage name: 'Release', concurrency: 1
    def buildParams = input parameters: [[$class: 'StringParameterDefinition', name: 'release_version', defaultValue: '0.0.0'], 
	[$class: 'StringParameterDefinition', name: 'next_version', defaultValue: '0.0.0-SNAPSHOT']]
    def releaseVersion = buildParams['release_version']
    def nextVersion = buildParams['next_version']
    sh "echo Release version ${releaseVersion}"
    sh "echo Snapshot version ${nextVersion}"
    sh "cd Custommap && mvn --batch-mode -Dtag=${releaseVersion} release:perform -DreleaseVersion=${releaseVersion} -DdevelopmentVersion=${nextVersion}"
    // sh 'mvn clean release'
}
