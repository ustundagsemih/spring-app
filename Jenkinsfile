podTemplate(containers: [
    containerTemplate(
        name: 'maven', 
        image: 'maven:3.8.6-jdk-11-slim'
        )
  ]) {

    node(POD_LABEL) {
        stage('Build maven project') {
            container('maven') {
                stage('Build app') {
                    sh '''
                    echo "Hello! I am executing shell"
                    '''
                }
            }
        }

    }
}c