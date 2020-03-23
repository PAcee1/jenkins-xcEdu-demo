// git凭证id
def git_auth = "e536df57-70be-4cab-ae06-448a0c1db793"
// git拉取地址
def git_url = "git@github.com:PAcee1/jenkins-xcEdu-demo.git"

node {

   stage('拉取代码') {
      checkout([$class: 'GitSCM', branches: [[name: '*/${branch}']],
       doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [],
        userRemoteConfigs: [[credentialsId: "${git_auth}", url: "${git_url}"]]])
   }
}
