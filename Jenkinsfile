pipeline {
  agent any

  stages {
    stage('Build library') {
      steps {
        sh "./gradlew clean jar --debug"
      }
    }

    stage("Publish service contract") {
      steps {
        sh "./gradlew publish"
      }
    }
  }
}
