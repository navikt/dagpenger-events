pipeline {
  agent any

  #triggers {
  #  pollSCM('H * * * 1-5')
  #}

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

    stage("Publish") {
      when {
        branch "master"
      }

      steps {
        withCredentials([usernamePassword(credentialsId: 'repo.adeo.no', usernameVariable: 'REPO_CREDENTIAL_USR', passwordVariable: 'REPO_CREDENTIAL_PSW')]) {
          sh "git status"
          sh "./gradlew -PmavenUser=${env.REPO_CREDENTIAL_USR} -PmavenPassword=${env.REPO_CREDENTIAL_PSW} publish"
        }
      }
    }
  }
}
