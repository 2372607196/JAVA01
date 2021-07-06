## 第八章 Java混合编程

### 第一节 Java调用Java程序(RMI)

#### Java 混合编程

- 众多编程语言，各有各的特点和应用范围
  - https://www.tiobe.com/tiobe-index/
- 现实世界存在很多应用程序
  - 由不同编程语言实现
  - 分布式部署的
- Java混合编程
  - Java程序和其他应用程序进行通讯和数据交互
  - Java和Java/C/JS/Python/Web Service/命令行的混合编程

#### RMI

- 单虚拟机JVM上的程序运行
  - 启动一个main程序，然后重复以下的2个步骤
    - new出一个对象
    - 调用对象的某一个方法
- 多虚拟机JVM的程序运行
  - 启动多个main程序，这些程序可以部署在多个机器上/虚拟机上
  - 多个进程可通过网络互相**传递消息**进行协作
  - 进程通过RMI可调用另一个机器的Java的函数

![62401642875](D:\学习资料\java\Mooc笔记\进阶第八章第一节.png)

![62401648679](D:\学习资料\java\Mooc笔记\进阶第八章第一节2.png)

- RMI的参数和返回值

  - (**自动化**)传递远程对象(实现Remote接口)

    - 当一个远程对象的引用从一个JVM传递到另一个JVM，该远程对象的发送者和接受者将持有**同一个实体对象的引用**。这个引用并非是一个内存位置，而是由网络地址和该远程对象的唯一标识符构成的。

      ### 两个JVM拥有同一个对象###

    - (**自动化**)传递可序列化对象(实现Serializable接口)

      - JVM中的一个对象经过序列化后的字节，通过网络，其副本传递到另一个JVM中，并重新还原成一个Java对象。

        ###每个JVM拥有自己的对象###

### 第二节 Java调用C程序(JNI)

#### JNI

- Java和C互操作
  - JNI，Java Native Interface
  - Java和本地C代码进行互操作
    - Java调用C程序完成一些需要快速计算的功能(**常见，重点**)
    - C调用Java程序(基于反射的方法)

![62401731466](D:\学习资料\java\Mooc笔记\进阶第八章第二节.png)

- 在Java类中声明一个本地方法
- 调用javac.exe编译，得到HelloNative.class
- 调用javah.exe得到包含该方法(Java_HelloNative_greeting)的头文件HelloNative.h
- 实现.c文件(对应HelloNative.h)
- 将.c和.h文件，整合为共享库(DLL)文件
- 在Java类中，加载相应的共享库文件

### 第三节 Java调用JavaScript程序(Nashorn)

#### JavaScript

- JavaScript语言，又称JS语言
  - 1995年由Netscape网景公司发布
  - 脚本语言(解释型语言)
    - 便于快速变更，可以修改运行时的程序行为
    - 支持程序用户的定制化
  - 可用于Web前端和后端开发(全栈)
  - JS基本教程，http://www.w3school.com.cn/b.asp

### Java调用JS

- 脚本引擎，ScriptEngine
  - Nashorn，JDK8自带的JS解释器(JDK6/7是Rhino解释器)
    - ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn")
  - 主要方法
    - eval，执行一段js脚本，eval(String str),eval(Reader reader)
    - put,设置一个变量
    - get,获取一个变量
    - createBindings,创建一个Bindings
    - setBindings,设置脚本变量使用的范围

### 第四节 Java调用Python程序(Jython)

#### Jython

- Jython(曾用名JPython)
  - Jython是Python语言在Java平台的实现
  - Jython是在JVM上实现的Python，由Java编写
  - Jython将Pythton源码编译成JVM字节码，由JVM执行对应的字节码，因此能很好的与JVM集成。**Jython并不是Java和Python的连接器**
- 关键类
  - PythonInterpreter
    - exec 执行语句
    - set 设置变量值
    - get 获取变量值
    - execfile 执行一个python文件
  - PyObject
  - PyFunction

### 第五节 Java调用Web Service

#### Web Service

- Web Service
  - 由万维网联盟(W3C,World Wide Web Consortium)提出
  - 消除语言差异、平台差异、协议差异和数据结构差异，成为不同构件模型和异构系统之间的集成技术
  - Web Service是为实现跨网络操作而设计的软件系统，提供了相关的操作接口，其他应用可以使用SOAP消息，以预先指定的方式来与Web Service进行交互

![62402286019](D:\学习资料\java\Mooc笔记\进阶第八章第五节.png)

#### Java 调用 Web Service

- Java提供wsimport 工具

  - %JAVA_HOME%\bin目录下

  - 根据wsdl文档，自动产生客户端中间代码

  - wsimport -keep -verbose

    http://ws.webxml.com.cn/WebServices/MobileCodeWS.asmx?WSDL

    ![62409113304](D:\学习资料\java\Mooc笔记\进阶第八章第五节2.png)

- Java 调用Web Service

  - 调用wsimport所产生客户端中间代码
  - 提供相应参数
  - 获取返回结果

- Java 调用 Web Service其他方法

  - Axis/Axis2 (axis.apache.org)
  - 采用URLConnection访问Web Service
  - 采用HttpClient访问Web Service

### 第六节 Java调用命令行

#### Runtime

- Java提供Runtime类
  - exec以一个独立进程执行命令command，并返回Process句柄
  - 当独立进程起动后，需要处理该进程的输出流/错物流
    - 当调用Process.getInputStream 可以获取进程的输出流
    - 调用Process.getErrorStream 可以获取进程的错误输出流
  - 调用Process.waitFor 等待目标进程的终止(当前进程阻塞)