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
      
      git branch: 'main', credentialsId: 'GIT_TOKEN', url: 'https://github.com/ustundagsemih/spring-app'
      container('maven') {
        stage('Building maven project') {
          sh '''
          mvn clean package -DskipTests
          '''
        }
      }
    

      container('kaniko') {
        stage('Building and pushing docker image to docker hub') {
          sh '''
            /kaniko/executor --context `pwd` --destination ustundagsemih/spring-application:${BUILD_ID} --destination ustundagsemih/spring-application:latest
          '''
        }
      }
    

  }
}

podTemplate(yaml: '''
    apiVersion: v1
    kind: Pod
    spec:
      serviceAccount: jenkins
      containers:
      - name: helm
        image: alpine/helm
        command:
        - sleep
        args:
        - 99d
      restartPolicy: Never
''') {
  node(POD_LABEL) {
      container('helm') {
        stage('Adding helm repository') {
          sh 'helm repo add my-custom-repo https://ustundagsemih.github.io/helm-charts/'
        }
      }

      container('helm') {
        stage('Deploying to beta environment') {
          sh 'helm repo update'
          sh 'helm upgrade --install my-app-beta my-custom-repo/sample --namespace beta --create-namespace --set image.tag=${BUILD_ID} --set "ingress.hosts[0].host=app-beta.ustundagsemih.com,ingress.hosts[0].paths[0].path=/",ingress.hosts[0].paths[0].pathType=ImplementationSpecific'
        }
      }
    
    
    def userInput = input(id: 'Proceed', message: 'Promote build?', , parameters: [[$class: 'BooleanParameterDefinition', defaultValue: true, description: '', name: 'Please confirm you agree with this']])
    
    if(userInput == true) {
        container('helm') {
        stage('Deploying to production') {
          sh 'helm repo update'
          sh 'helm upgrade --install my-app-prod my-custom-repo/sample --namespace prod --create-namespace'
        }
      }
    }
    else {
        sh 'echo Deploy failed'
    }
  }
}



