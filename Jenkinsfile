podTemplate(yaml: '''
    apiVersion: v1
    kind: Pod
    spec:
      containers:
      - name: maven
        image: maven:3.8.6-jdk-11-slim
        command:
        - sleep
        args:
        - 99d
      - name: kaniko
        image: gcr.io/kaniko-project/executor:debug
        command:
        - sleep
        args:
        - 9999999
        volumeMounts:
        - name: kaniko-secret
          mountPath: /kaniko/.docker
      restartPolicy: Never
      volumes:
      - name: kaniko-secret
        secret:
            secretName: dockercred
            items:
            - key: .dockerconfigjson
              path: config.json
''') {
  node(POD_LABEL) {
    stage('Get a Maven project') {
      
      git branch: 'main', credentialsId: 'GIT_TOKEN', url: 'https://github.com/ustundagsemih/spring-app'
      container('maven') {
        stage('Build a Maven project') {
          sh '''
          echo pwd
          '''
        }
      }
    }

    stage('Build Java Image') {
      container('kaniko') {
        stage('Build a Go project') {
          sh '''
            /kaniko/executor --context `pwd` --destination bibinwilson/hello-kaniko:1.0
          '''
        }
      }
    }

  }
}