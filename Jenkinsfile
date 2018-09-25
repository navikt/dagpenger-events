pipeline {
  agent any

  stages {
    stage('Test library') {
      steps {
        sh "./gradlew test --info"
      }
    }

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
