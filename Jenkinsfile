// git凭证id
def git_auth = "e536df57-70be-4cab-ae06-448a0c1db793"
// git拉取地址
def git_url = "git@gitee.com:pacee/xc-edu-service.git"
// 镜像版本
def tag = "latest"
// Harbor ip地址
def harbor_url = "192.168.56.132:85"
// Harbor 项目名称
def harbor_project = "xc-edu"
// Harbor 凭证
def harbor_auth = "52478843-63ff-459f-bbf4-c59c852e2521"

node {
    // 获取参数projectNames
    def projectNames = "${project_name}".split(",")
    // 获取服务器名称
    def servers = "${publish_server}".split(",")

    stage('拉取代码') {
        checkout([$class: 'GitSCM', branches: [[name: '*/${branch}']],
                  doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [],
                  userRemoteConfigs: [[credentialsId: "${git_auth}", url: "${git_url}"]]])
    }
    stage('代码审查') {
        // 循环扫描每个微服务
        for(int i=0;i < projectNames.length;i++){
            // xc-govern-center@11111
            def projectInfo = projectNames[i]
            // xc-govern-center
            def currentProjectName = "${projectInfo}".split("@")[0]
            // 11111
            def currentProjectPort = "${projectInfo}".split("@")[1]

            // 引入SonarQube Scanner工具，这里是我们在Jenkins全局工具中配置的名称
            def scannerHome = tool 'sonar-scanner'
            // 引入Sonar服务器环境，这里是我们Jenkins系统配置中Sonar的名称
            withSonarQubeEnv('sonar'){
                sh """
                    cd ${currentProjectName}
                    ${scannerHome}/bin/sonar-scanner
                 """
            }
        }
    }
    stage('编译打包父工程'){
        sh """
        mvn clean install
        """
    }
    stage('编译打包微服务，并使用docker插件制作镜像') {
        // 循环扫描每个微服务
        for(int i=0;i < projectNames.length;i++){
            // xc-govern-center@11111
            def projectInfo = projectNames[i]
            // xc-govern-center
            def currentProjectName = "${projectInfo}".split("@")[0]
            // 11111
            def currentProjectPort = "${projectInfo}".split("@")[1]

            sh "mvn -f ${currentProjectName} clean package dockerfile:build"
        }
    }
    stage('推送镜像到Harbor上') {
        // 循环扫描每个微服务
        for(int i=0;i < projectNames.length;i++){
            // xc-govern-center@11111
            def projectInfo = projectNames[i]
            // xc-govern-center
            def currentProjectName = "${projectInfo}".split("@")[0]
            // 11111
            def currentProjectPort = "${projectInfo}".split("@")[1]
            // 定义镜像名称
            def imageName = "${currentProjectName}:${tag}"

            // 为镜像打标签
            sh "docker tag ${imageName} ${harbor_url}/${harbor_project}/${imageName}"

            // 推送到Harbor上
            withCredentials([usernamePassword(credentialsId: "${harbor_auth}", passwordVariable: 'password', usernameVariable: 'username')]) {
                // 推送前先登录
                sh "docker login -u ${username} -p ${password} ${harbor_url}"

                // 上传镜像
                sh "docker push ${harbor_url}/${harbor_project}/${imageName}"
            }
        }
    }
    stage('删除Jenkins服务器中的镜像') {
        // 循环扫描每个微服务
        for(int i=0;i < projectNames.length;i++){
            // xc-govern-center@11111
            def projectInfo = projectNames[i]
            // xc-govern-center
            def currentProjectName = "${projectInfo}".split("@")[0]
            // 11111
            def currentProjectPort = "${projectInfo}".split("@")[1]

            // 定义镜像名称
            def imageName = "${currentProjectName}:${tag}"

            def tagName = "${harbor_url}/${harbor_project}/${imageName}"

            // 删除镜像
            sh "docker rmi ${currentProjectName}"
            // 删除标签镜像
            sh "docker rmi ${tagName}"
        }
    }
    stage('远程调用生产服务器拉取镜像') {
        // 循环扫描每个微服务
        for(int i=0;i < projectNames.length;i++){
            // xc-govern-center@11111
            def projectInfo = projectNames[i]
            // xc-govern-center
            def currentProjectName = "${projectInfo}".split("@")[0]
            // 11111
            def currentProjectPort = "${projectInfo}".split("@")[1]

            // 循环服务器
            for(int j=0;j < servers.length;j++){
                // 服务器获取
                def currentServer = servers[j]

                // 设置注册中心使用的配置文件
                def activeProfile = "--spring.profiles.active="
                // 判断服务器
                if(currentServer=="prod_server"){
                    activeProfile = activeProfile + "eureka01"
                }else if(currentServer=="prod_server_slave"){
                    activeProfile = activeProfile + "eureka02"
                }

                sshPublisher(publishers: [
                    sshPublisherDesc(
                        configName: "${currentServer}",
                        transfers:[
                            sshTransfer(
                                cleanRemote:false,
                                execCommand:"/opt/jenkins_shell/deployCluster.sh $harbor_url $harbor_project $currentProjectName $tag $currentProjectPort $activeProfile"
                            )
                        ]
                    )
                ])
            }
        }
    }

}