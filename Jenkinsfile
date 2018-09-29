pipeline {
  agent any

  stages {
    stage('Install dependencies') {
      steps {
        sh "./gradlew assemble"
      }
    }

    stage('Build') {
      steps {
        sh "./gradlew check"
      }
    }

    stage('Package') {
      steps {
        sh "./gradlew check"
        sh "git status"
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
