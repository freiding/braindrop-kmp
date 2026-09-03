// Requires: a Multibranch Pipeline job pointed at this GitHub repo with branch
// discovery covering all branches + a GitHub webhook trigger. See docs/ci-cd.md
// for the required Jenkins credential IDs and one-time setup steps.
pipeline {
    agent any // swap for a specific label if this Jenkins has a dedicated Android build pool

    triggers {
        githubPush()
    }

    options {
        disableConcurrentBuilds()
    }

    stages {
        stage('Lint') {
            steps {
                sh './gradlew --no-daemon ktlintCheck detekt'
            }
        }

        stage('Unit Tests') {
            steps {
                // Unqualified task names: Gradle runs them in every subproject that
                // declares them and skips the rest — no need to list every module.
                sh './gradlew --no-daemon testAndroidHostTest testDebugUnitTest'
            }
        }

        stage('Debug Build') {
            steps {
                sh './gradlew --no-daemon :app:assembleDebug'
            }
        }

        stage('Release Bundle') {
            when {
                anyOf { branch 'staging'; branch 'main' }
            }
            steps {
                // play.resolutionStrategy is AUTO, so the version code is resolved via the
                // Play Developer API and baked into the bundle at build time, not just at
                // publish time — :app:bundleRelease transitively depends on
                // :app:processReleaseVersionCodes, which needs the service account credential.
                withCredentials([
                    file(credentialsId: 'android-release-keystore', variable: 'KEYSTORE_PATH'),
                    string(credentialsId: 'android-keystore-password', variable: 'KEYSTORE_PASSWORD'),
                    string(credentialsId: 'android-key-alias', variable: 'KEY_ALIAS'),
                    string(credentialsId: 'android-key-password', variable: 'KEY_PASSWORD'),
                    file(credentialsId: 'play-service-account-json', variable: 'PLAY_SERVICE_ACCOUNT_JSON'),
                ]) {
                    sh './gradlew --no-daemon :app:bundleRelease'
                }
            }
        }

        stage('Publish Internal Testing') {
            when {
                branch 'staging'
            }
            steps {
                withCredentials([
                    file(credentialsId: 'play-service-account-json', variable: 'PLAY_SERVICE_ACCOUNT_JSON'),
                ]) {
                    sh './gradlew --no-daemon :app:publishReleaseBundle -PplayTrack=internal'
                }
            }
        }

        stage('Publish Closed Testing') {
            when {
                branch 'main'
            }
            steps {
                withCredentials([
                    file(credentialsId: 'play-service-account-json', variable: 'PLAY_SERVICE_ACCOUNT_JSON'),
                ]) {
                    // TODO: replace with the real closed-testing track alias from Play Console
                    // (Release > Testing > Closed testing > your track > "Track name").
                    sh './gradlew --no-daemon :app:publishReleaseBundle -PplayTrack=closed-testing'
                }
            }
        }
    }

    post {
        always {
            junit testResults: '**/build/test-results/**/*.xml', allowEmptyResults: true
        }
    }
}
