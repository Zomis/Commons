node {
    properties([
        disableConcurrentBuilds(),
        parameters([
            booleanParam(defaultValue: false, description: 'Clean', name: 'Clean'),
            string(defaultValue: '', description: 'Release version', name: 'ReleaseVersion'),
            string(defaultValue: '', description: 'Next version', name: 'NextVersion')
        ])
    ])

    if (params.Clean) {
        step([$class: 'WsCleanup'])
    }

    checkout scm
    stage('Build') {
        sh 'mvn compile'
    }

    stage('Upload') {
        sh 'mvn deploy'
    }

    if (params.ReleaseVersion != '' && params.NextVersion != '') {
        stage('Release') {
            sh "git checkout $env.BRANCH_NAME"
            sh "mvn --batch-mode -Dtag=$params.ReleaseVersion release:prepare release:perform -DreleaseVersion=$params.ReleaseVersion -DdevelopmentVersion=$params.NextVersion"
        }
    }

}
