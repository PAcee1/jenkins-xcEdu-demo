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
   stage('代码审查') {
       // 引入SonarQube Scanner工具，这里是我们在Jenkins全局工具中配置的名称
       def scannerHome = tool 'sonar-scanner'
       // 引入Sonar服务器环境，这里是我们Jenkins系统配置中Sonar的名称
       withSonarQubeEnv('sonar'){
           sh """
                cd ${project_name}
                ${scannerHome}/bin/sonar-scanner
             """
       }
  }
}