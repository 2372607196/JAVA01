## 第一章 Maven

### 第一节 构建工具

#### 传统方法

- 将jar文件添加到Java Build Path
  - 右键项目，Properties->Libraries标签页->Add External JARs,添加

#### Java构建工具

- 构建工具功能
  - 自动帮程序员甄别和下载第三方库(jar)
  - 完成整个项目编译(调用javac.exe)
  - 完成整个项目单元测试流程(调用Junit工具)
  - 完成项目打包(jar/war等格式，调用jar.exe)
- 当前主要的Java构建工具
  - Maven,Gradle,Ivy,Buildr,Ant等
- IDEA设置(拓展)
  - settings是当前的设置
  - Other Settings是每次创建都会设置好的

![62251651792](D:\学习资料\java\Mooc笔记\十三章第一节.png)

## 第二节Maven概念和实战

- Maven仓库的基本概念

  - 对于Maven来说，仓库只分为两类：本地仓库和远程仓库

  - 远程仓库分为三种:中央仓库，私服，其他公共库

  - 中央仓库是默认配置下，Maven下载jar包的地方

  - 对于仓库路径的修改，可以通过maven配置文件conf目录下settings.xml来指定仓库路径

    ~~~java
    <!-- 设置到指定目录中，路径的斜杠不要写反 -->
        <settings>
        <localRepository>D:/m2/repositry<localRepository>
        </settings>
    ~~~

- Maven环境下构建多模块项目

  - 模块 maven_parent -- 基模块，就是常说的parent (pom)
  - 模块 maven_dao -- 数据库的访问层，例如jdbc操作(jar)
  - 模块 maven_service -- 项目的业务逻辑层(jar)
  - 模块 maven_controller -- 用来接受请求，响应数据(war)

#### POM(Project Object Model)

- XML格式
- 包含了项目信息、依赖信息、构建信息
- 构建信息(artifact)
  - groupId:组织
  - artifact: 产品名称
  - version：版本

#### Maven respository(仓库)

- 本地仓库(本地用户的.m2文件夹)
- 远程仓库
  - 中央仓库
  - 阿里云仓库
  - 谷歌仓库

#### Maven项目的目录结构

- 基本目录结构
  - src
    - main
      - java/存放java文件
      - resources/ 存放程序资源文件
    - test/
      - java/存放测试程序
      - resources/存放测试程序资源文件
  - pom.xml

#### 创建Maven项目

- groupId:组织名
- artifactId:作品（项目)名