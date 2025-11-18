pipeline {
    agent any
    tools {
        maven 'Maven3'
        jdk 'JDK21'
    }
    stages {
        stage('Nettoyage') {
            steps {
                echo '🚀 Nettoyage du projet...'
                sh 'mvn clean -Denforcer.skip=true'
            }
        }
        stage('Dépendances') {
            steps {
                echo '📦 Téléchargement des dépendances...'
                sh 'mvn dependency:resolve -Denforcer.skip=true'
            }
        }
        stage('Compilation') {
            steps {
                echo '🔨 Compilation du code...'
                sh 'mvn compile -Denforcer.skip=true'
            }
        }
        stage('Livrable') {
            steps {
                echo '📦 Construction du JAR...'
                sh 'mvn package -Denforcer.skip=true -DskipTests'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }
    post {
        always {
            echo '🏁 Pipeline terminé!'
        }
        success {
            echo '🎉 SUCCÈS : Construction réussie!'
        }
        failure {
            echo '❌ ÉCHEC : Construction échouée!'
        }
    }
}
