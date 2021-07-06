## 第七章 package 、import、classpath

### 第一节 package和import

- 包名package name 尽量唯一
- 域名是唯一的，因此常用域名做包名
- 域名逆序：cn.edu.ecnu,范围通常从大到小
- 类的完整名字：包名+类名，cn.edu.ecnu.PackageExample
- 包名：和目录层次一样，cn\edu\ecnu\PackageExample.java
- 但是包具体放在什么位置不重要，编译和运行的时候再指定
- **“*”代表这个目录下所有的文件，但不包括子文件和子文件夹内的文件**
- 添加同名类时，可以使用类的全名进行使用
  - b.Man m = new b.Man();

### 第二节 jar文件导出和导入

- .java文件最终会被编译成.class文件（二进制），并被分发到其他机器上使用
- jar文件实际上是一组class文件的压缩包
- jar文件优势
  - jar文件可以包括多个class，比多层目录更加简洁实用
  - jar文件只包括class,而没有包含java文件，在保护源文件知识版权方面，能够可以起到更好的作用
  - 将多个class文件压缩成jar文件（只有一个文件），可以规定给一个版本号，更容易进行版本控制

### 第三节 package和import——命令行

- java -classpath .;c:\temp cn.com.test.Man
- 第一部分：java，执行命令，是java.exe的缩写
- 第二部分 -classpath 固定格式参数，可以简写成-cp
- 第三部分：是一个（Windows分号，Linux/Mac冒号连接起来的）字符串。按分隔符隔开，得到一个个子路径。当运行cn.com.test.Man类的过程中，如果需要用到其他的类，就会分裂第三部分的字符串，得到多个子路径，然后依次在每个路径下，再去寻找相应类（全称，包名以点隔开对应到目录）
- 第四部分：主执行类的全程（含包名）
- 编译和运行规则
  - 编译一个类，需要java文件的全路径，包括扩展名
  - 运行一个类，需写类名全称（非文件路径），无须写扩展名
  - 编译类的时候，需要给出这个类所依赖的类（包括依赖的类再次依赖的所有其他类）的所在路径
  - 运行类的时候，需要给出这个类，以及被依赖类的路径总和
  - classpath参数也可以包含jar包。如果路径内有空格，请将classpath参数整体加双引号。
  - java -classpath ".;c:\test.jar;c:\temp;c:\a bc" cn.com.test.Man

### 第四节 Java访问权限

![62152045169](D:\学习资料\java\Mooc笔记\1621520451690.png)

