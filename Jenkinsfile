pipeline {
  agent any

  stages {
    stage('Build library') {
      steps {
        sh "./gradlew jar"
      }
    }

    stage("Publish service contract") {
      steps {
        withCredentials([usernamePassword(credentialsId: 'repo.adeo.no', usernameVariable: 'REPO_CREDENTIAL_USR', passwordVariable: 'REPO_CREDENTIAL_PSW')]) {
          sh "./gradlew -PmavenUser=${env.REPO_CREDENTIAL_USR} -PmavenPassword=${env.REPO_CREDENTIAL_PSW} publish"
        }
      }
    }
  }
}
