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
        sh "./gradlew publish"
      }
    }
  }
}
