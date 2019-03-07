/*
 * Copyright (c) 2019-present Sonatype, Inc. All rights reserved.
 * Includes the third-party code listed at http://links.sonatype.com/products/nexus/attributions.
 * "Sonatype" is a trademark of Sonatype, Inc.
 */

@Library(['docker-nexus-platform-cli-pipeline-library','jenkins-shared']) _

pipeline {
  agent {
    label 'ubuntu-zion'
  }
  environment {
    VERSION = cliBuild.getVersion()
    COMMIT_ID = cliBuild.getCommitId()
  }
  triggers {
    // every 4 minutes monday - friday
    pollSCM(cliBuild.isCIBuild() ? 'H */4 * * 1-5' : '')
  }
  stages {
    stage('Preparation') {
      steps {
        cliPreparation()
        circleCiInstall()
      }
    }
    stage('Validate ORB') {
      steps {
        circleCiValidateOrb()
      }
    }
    stage('Push') {
      steps {
        circleCiPush()
      }
    }
  }
}
