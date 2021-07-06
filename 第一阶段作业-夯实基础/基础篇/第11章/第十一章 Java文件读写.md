## 第十一章 Java文件读写

### 第一节 文件系统及Java文件基本操作

#### 文件概述

- 文件系统是有OS（操作系统）管理的
- 文件系统和Java进程是平行的，是两套系统
- 文件系统是由文件夹和文件递归组合而成
- 文件目录分隔符
  - Linux/Unix 用/隔开
  - Windows用\隔开，涉及到转义，在程序中需用/或\\\代替
    - 常见的字符转义\r 等价于换行
    - \r等价于回车
    - \t 等价于tab键
- 文件包括文件里面的内容和文件基本属性
- 文件基本属性：名称、大小、扩展名、修改时间等

#### Java文件类File

- java.io.File是文件和目录的重要类（JDK6及以前是唯一）
  - 目录也使用File类进行表示
- File类与OS无关，但会受到OS的权限限制
- 常用方法
  - createNewFile,delete,exists,getAbsolutePath,getName,
  - getParent,getPath,isDirectory,isFile,length,listFiles,mkdir,mkdirs
- 注意：File不涉及到具体的文件内容，只涉及属性

~~~java
psvm(String[] args){
    //创建目录
    File d =new File("c:/temp/a/b/c");
    if(!d.exists()){//exists 判断File对象是否存在
        d.mkdirs();//mkdir 创建单级目录 mkdirs连续创建目录
    }
    d.isDIrectory;//是否是目录
    //创建文件
    File f = new File("C:/temp/abc.txt");
    if(!f.exists()){
        try{
            f.createNewFile();//创建abc.txt
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    //输出文件相关属性
    System.out.prinln(f.isFile());//是否是文件
    System.out.prinln(f.getName());//获取文件名字
    System.out.prinln(f.getParent());//获取上一层目录路径
    System.out.prinln(f.getPath());//获取这个文件的全路径
    System.out.prinln(f.length());//获取这个文件的大小
    System.out.prinln(f.lastModified());//返回文件最后一次修改时间
    //遍历d目录下所有的文件信息
    File[] fs = d.listFiles();//列出d目录下的所有的子文件，不包括子目录下的文件
    for(File f1:fs){
        System.out.println(f1.getPath());
    }
    f.delete();//删除此文件
    d.delete();//删除目录
}
~~~

#### Java NIO

- Java 7提出的NIO包，提出新的文件系统类

  - Path，Files，DirectoryStream，FileVisitor,FileSystem
  - 是java.io.File的有益补充
    - 文件复制和移动
    - 文件相对路径
    - 递归遍历目录
    - 递归删除目录
    - .....

  ~~~java
  //Path 和 java.io.File 基本类似
  //获取path方法一，c:/temp/abc.txt
  Path path = FileSystem.getDefault().getPath("c:/temp","abc.txt");
  //获取path方法二，用File的toPath（）方法获得Path对象
  File file = new File("c:/temp/abc.txt");
  Path pathOther = file.toPath();
  // 0,说明这两个path是相等的
  System.out.println(path.compareTo(pathOther));
  //获取path方法三
  Path path3 = Paths.get("c:/temp","abc.txt");
  //合并两个Path
  Path path4 = Paths.get("c:/temp");
  path4.resolve("abc.txt");
  ~~~

  ​

  ~~~java
  Path from = Paths.get("c:/temp","abc.txt");
  //移动C://temp/abc.txt到C://temp/test/def.txt.如目标文件已存在，就替换
  Path to = from.getParent().resolve("test/def.txt");
  try{
      //文件的大小bytes
      System.out.println(Files.size(from));
      //调用文件移动方法，如果目标文件已经存在，就替换
      Files.move(from,to,StandardCopyOption.REPLACE_EXISTING);
  }
  catch(IOException e){
      System.err.println("移动文件错误"+e.getMessage());
  }
  //创建一个带有过滤器，过滤文件名以java.txt结尾的文件
  DirectoryStream<Path> pathsFilter = Files.newDirectoryStream(path,"*.{java,txt}");
  for(Path p :pathsFilter){
      System.out.println(p.getFilename());
  }
  catch(IOException e){
      e.printStackTrace();
  }
  ~~~

  ​

### 第二节 Java io 包概述

- 文件系统和Java是两套系统
- Java读写文件，只能以（数据）流的形式进行读写
- 文件和Java是两套平行的系统。由于文件可能很大，Java不能一口气将文件都加载进内存（需要大量的内存），只能以流的形式分批读取。
- java.io包中
  - 节点类：直接对文件进行读写
  - 包装类
    - 转化类：字节/字符/数据类型的转化类
    - 装饰类：装饰节点类
- 字节：byte,8bit，最基础的基础
  - 1 Byte(字节) = 8 Bits(位)，意思是1Byte是8个0/1位，即00000000至11111111
- 字符：a,10000,我
- 数据类型：3，5.25，abcdef
- 文件是以字节保存，因此程序将变量保存到文件需要转化
- 节点类：直接操作文件类
  - InputStream：数据从文件读取到Java里
  - OutputStream(字节):数据从Java输出到文件里
    - FileInputStream，FileOutputStream
  - Reader,Writer(字符)
    - FileReader,FileWriter
    - 字节类以Stream为结尾，字符类以er为结尾
- 转换类：字符到字节之间的转化
  - InputStreamReader：文件读取时字节，转化为Java能理解的字符
  - OutputStreamWriter：Java将字符转化为字节输入到文件中
- 装饰类：装饰节点类
  - DataInputStream,DataOutputStream:封装数据流
  - BufferedInputStream,BufferOutputStream:缓存字节流
  - BufferedReader,BufferedWriter：缓存字符流
  - ![62212400622](D:\学习资料\java\Mooc笔记\第十一章第二节.png)

### 第三节 文本文件读写

- 文件类型
  - 一般文本文件（若干行字符构成的文件），如txt等
  - 一般二进制文件，如数据文件dat，照片文件等
  - 带特殊格式的文本文件，如xml等
  - 带特殊格式二进制文件，如doc，ppt都有自己的格式

- 文件是数据的一个容器（口袋）

- 文件可以存放大量的数据

- 文件很大，注定Java只能以流形式依次处理

- 从Java角度理解
  - 输出：数据从Java到文件中，写操作
  - 输入：数据从文件到Java中，读操作

- 文本文件读写
  - 输出文本字符到文件中，写操作
  - 从文件中读取文本字符串，读操作

- 写文件

  - 先创建文件，写入数据，关闭文件

  - FileOutputStream：往文件写字节

  - OutputStreamWriter：字节和字符转化

  - BufferedWriter：写缓冲区类，加速写操作

    - writer
    - newLine

  - try-resource语句，自动关闭资源

    - 一个文件被程序打开了，就处于锁定状态了，防止其他程序访问。如果程序最后退出，没有关闭文件资源，将导致其他程序不能访问文件。

  - 关闭最外层的数据流，将会把其上所有的数据流关闭

    ~~~java
    public static void writeFile1(){
        FileOutputStream fos = null;//节点类，负责写字节
        OutputStreamWriter osw = null;//转化类，负责字符到字节转化
        BufferedWriter bw = null;//类饰类，负责写字符到缓存区
        try{
            fos = new FileOutputStream("c:/temp/abc.txt");
            ows = new OutputStreamWriter(fos,"UTF-8");
            bw = new BufferedWriter(osw);
            bw.write("我们是");
            bw.nextLine();//换行
            bw.write("Ecnuers.");
            bw.nextLine();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                bw.close();//关闭最后一个类，会将所有的底层流都关闭
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
    public static void writeFile2(){
        //try-resource 语句，自动关闭资源
        try(BufferedWriter bu = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("c:/temp/adc.txt"))));//try括号里的语句，try运行完自动释放
        {
            bw.write("我们是");
            bw.newLine();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    ~~~

    ​

- 读文件

  - 先打开文件，逐行读入数据，关闭文件

  - FileInputStream,InputStreamWriter,BufferedReader

  - BufferReader

    - readLine //读一行

  - try-resource 语句，自动关闭资源

  - 关闭最外层的数据流，将会把其上所有的数据流关闭

    ~~~java
    br = new BufferedReader(isr);
    String line;
    while((line = br.readLine())!=null)//每次读取一行
    {
        System.out.println(line);
    }
    ~~~

    ​

### 第四节 二进制文件读写

- 二进制文件

  - 狭义上说，采用字节编码，非字符编码的文件
  - 广义上说，一切文件都是二进制文件
  - 用记事本等无法打开/阅读

- 二进制文件读写

  - 输出数据到文件中
  - 从文件中读取数据

- 写文件

  - 先创建文件，写入数据，关闭文件
  - FileOutputStream，BufferedOutputStream，DataOutputStream
  - DataOutputStream
    - flush
    - write/writeBoolean/writeByte/writeChars/writeDouble/writeInt/WriteUTF/..

  ~~~java
  FileOutputStream fos = null;
  DataOutputStream dos = null;
  BufferedOutputStream bos = null;
  try{
      fos = new FileOutputStream("c:/temp/def.dat");//节点类，负责写字节
      bos = new BufferOutputStream(fos);//装饰类，负责写字节数据到缓冲区
      dos = new DataOutputStream(bos);//转化类，负责数据类型到字节转化
      dos.writeUTF("a");
      dos.writeInt(20);
  }
  ~~~

- 读文件

  - 先打开文件，读入数据，关闭文件
  - FileInputStream，BufferedInputStream，DataInputStream
  - DataInputStream
    - read/readBoolean/readChar/readDouble/readFloat/readFloat/readInt/readUTF/..

### 第五节 Zip文件读写

#### Java zip 包

- 压缩包：zip，rar，gz,....
- Java zip包支持Zip和Gzip包的压缩和解压
- zip文件操作类：java.util.zip包中
  - java.io.InputStream,java.io.OutputStream的子类
  - ZipInputStream，ZipOutputStream压缩文件输入/输出流
  - ZipEntry压缩项

#### 压缩

- 单个/多个压缩
  - 打开输出zip文件
  - 添加一个ZipEntry
  - 打开一个输入文件，读数据，向ZipEntry写数据，关闭输入文件
  - 重复以上两个步骤，写入多个文件到zip文件中
  - 关闭zip文件

~~~java
import java.io.File;
public static void main(String args[])throws Exception{
    File file = new File("c:/temp/abc.txt");//定义要压缩的文件
    File zipFile = new File("c:/temp/single2.zip");//定义压缩文件名称
    InputStream input = new FileInputStream(file);//定义文件的输入流
    ZipOutputStream zipOut = null;//声明压缩流对象
    ZipOut = new ZipOutputStream(new FileOutputStream(zipFile));
    zipOut.putNextEntry(new ZipEntry(file.getName()));//设置ZipEntry对象
    zipOut.setComment("single file zip");//设置注释
    //单个压缩过程
    int temp = 0;
    while((temp=input.read())!=-1){//读取内容
        zipOut.write(temp);//压缩输出
    }//InputStream的read方法可以从文件中读取一个或者多个字节到Java中。读到文件末尾时，将返回-1
    input.close();//关闭输入流
    zipOut.close();//关闭输出流
    //多个压缩过程
    int temp = 0;
    if(file.isDirectory()){//判断是否是文件夹
        File lists[] = file.listFiles();//列出全部子文件
        for(int i =0;i<lists.length;i++){
            input = new FileInputStream(lists[i]);//定义文件的输入流
            zipOut.putNextEntry(new ZipEntry(file.getname()+File.separayor+lists[i].getName()));//设置ZipEntry对象
            System.out.println("正在压缩"+lists[i].getName());
            while((temp=input.read()!=-1)){//读取内容
                zipOut.write(temp);//压缩输出
            }
            input.close();//关闭输入流
        }
    }
    zipOut.close();//关闭输出流
}
~~~

#### 解压

- 单个/多个解压
  - 打开输入的zip文件
  - 获取下一个ZipEntry
  - 新建一个目标文件，从ZipEntry读取数据，向目标文件写入数据，关闭目标文件
  - 重复以上两个步骤，从zip包中读取数据到多个目标文件
  - 关闭zip文件

~~~java
psvm(String args[])throws Exception{
    //待解压文件，需要从zip文件打开输入流，读取数据到java中
    File zipFile = new File("c:/temp/single.zip");//定义压缩文件名称
    ZipInputStream input = null;//定义压缩输入流
    input = new ZipInputStream(new FileInputStream(zipFile));//实例化ZIpInputSream
    ZipEntry entry = input.getNextEntry();//得到一个压缩实体
    System.out.println("压缩实体名称："+entry.getName());//获取压缩包中文件名字
    //新建目标文件，需要从目标文件打开输出流，数据从java流入
    File outFile = new File("c:/temp/"+entry.getName());
    OutputStream out = new FileOutputStream(outFile);//实例化文件输出流
    int temp = 0；
        while((temp=input.read())!=-1){
            out.write(temp);
        }
    input.close();//关闭输入流
    out.close();//关闭输出流
    //遍历压缩包中的文件
    while((entry=zipInput.getNextEntry())!=null){//得到一个压缩实体
        System.out.println("解压缩"+entry.getName()+"文件")；
        outFile = new File("c:/temp/"+entry.getName());//定义输出的文件路径
        if(!outFile.getParentFile().exists()){//如果输出文件夹不存在
            outFile.getParentFile().mkdirs();
            //创建文件夹，如果这里的有多级文件夹不存在，请使用mkdirs（）
            //如果只是单纯的一级文件夹，使用mkdir()
        }
        if(!outFile.exists()){//判断输出文件是否存在
            if(entry.isDirectory()){
                outFile.mkdirs();
            }
            else
            {
                outFile.createFile();//创建文件
            }
        }
        if(!entry.isDirectory()){
            input = ZipFile.getInputStream(entry);//得到每一个实体的输入流
            out = new FileOutputStream(outFile);//实例化文件输出流
            int temp = 0;
            while((temp=input.read())!=-1){
                out.write(temp);
            }
            input.close();//关闭输入流
            out.close();//关闭输出流
        }
    }
}
~~~

