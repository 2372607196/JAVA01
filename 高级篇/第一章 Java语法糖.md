## 第一章 Java语法糖

### 第一节 语法糖和环境设置

#### 语法糖

- 语法糖：Syntactic sugar / Syntax sugar
  - JDK 2(1.1~1.4)是普及版
  - JDK5/6/7/8/11是长期稳定版
  - JDK9/10/12是短期版本

### 第二节 for-each和枚举

#### for/for-each

- for vs for-each
  - for-each 从JDK5.0开始引入
  - for-each 语法更简洁
  - for-each 避免越界错误
  - **for 可以删除元素，for-each 不可以删除元素/替换元素**
  - **for-each 遍历的时候，是不知道当前元素的具体位置索引**
  - **for-each 只能正向遍历，不能反向遍历**
  - **for-each 不能同时遍历2个集合**
  - for 和 for-each性能接近

### 第三节 不定项参数和静态导入

#### 不定项参数

- 普通函数的形参列表是固定个数/类型/顺序

- JDK5提供了不定项参数(可变参数)功能

  - 类型后面加了3个点，如int.../double.../String.../
  - 可变参数，本质上是一个数组

  ~~~java
  print();
  print("aaa");
  print("aaa","bbb");
  print("aaa","bbb","ccc");
  public static void print(String... args){
      System.out.println(args.length);
      for(String arg:args){
          System.out.println(arg);
      }
  }
  ~~~

- 不定项参数(可变参数)

  - 一个方法只能有**一个不定项参数**，且必须位于参数列表的**最后**
  - 重载的优先级规则1：**固定参数的方法，比可变参数优先级更高**
  - 重载的优先级规则2：**调用语句，同时与两个带可变参数的方法匹配，则报错**

#### 静态导入

- import导入程序所需要的类
- import static 导入一个类的**静态方法和静态变量**(JDK5引入)
  - 少使用*通配符，不滥用，最好具体到静态变量或方法
  - 静态方法名具有明确特征，如有重名，需要补充类名

### 第四节  自动拆箱和装箱、多异常并列、数值类型赋值优化

#### 自动装箱和拆箱

- 自动装箱和拆箱

  - 从JDK5.0开始引入，简化基本类型和对象转换的写法

  - 基本类型：boolean/byte/char/int/short/long/float/double

  - 对象：Boolean/Byte/Character/Integer/Short/Long/Float/Double

    - 装箱：基本类型的值被封装为一个包装类对象

    - 拆箱：一个包装类对象被拆开并获取相应的值

      ~~~java
      Integer obj1 = 5; //自动装箱
      Integer obj2 = Integer.valueOf(5);

      int a1 = obj1; //自动拆箱
      int a2 = obj1.intValue();

      ArryList<Integer> list = new ArrayList<>();
      list.add(1);
      list.add(Integer.valueOf(2));

      int a3 = list.get(1);
      int a4 = list.get(1).intValue();
      ~~~

- 自动装箱和拆箱的注意事项

  - 装箱和拆箱是编译器的工作，在class中已经添加转化。虚拟机没有自动装箱和拆箱的语句
  - ==：基本类型是内容相同，对象是指针是否相同(内存同一个区域)
  - **基本类型没有空值，对象有null,可能出发NullPointerException。**
  - **当一个基础数据类型与封装类进行==、+、-、*、/运算时，会将封装类进行拆箱，对基础数据类型进行运算。**
  - **谨慎使用多个非同类的数值类对象进行运算**

#### 多异常并列

- 多个异常并列在一个catch中

  - 从JDK7.0开始引入简化写法

  - 多个异常之间**不能有(直接/间接)继承关系，如果有，则报错**

    ~~~java
    try{
        test();
    }catch(IOException | SQLException ex){
        //从JDK7开始，支持一个catch写多个异常
        //异常处理
    }
    ~~~

#### 整数类型用二进制数赋值

- Java7的新语法：整数类型用二进制数赋值

  - 避免二进制计算

  - byte/short/int/long

    ~~~java
    int a1 = 0b101;
    long a2 = 0b111L;
    byte a3 = (byte)0b110;
    short a4 = (short)0b110;
    final int[] s1 = {0b001,0b010,0b101};
    ~~~

#### 数字中的下划线

- Java 7的新语法：在数值字面量中使用下划线
  - 增加数字的可读性和纠错功能
  - short/int/long/float/double
  - 下划线只能出现在数字中间，前后必须有数字
  - 允许在二/八/十/十六进制的数字中使用

### 第五节 接口方法

#### 接口的默认方法

- Java最初的设计中，接口的放大都是**没有实现的、公开的**
- Java8推出接口的默认方法/静态方法(**都带实现的**),为Lambda表达式提供支持

~~~java
public interface NewAnimal{
    public default void move()
    {
        System.out.println("I can move");
    }
}
~~~

-  接口的默认方法
  - 以**default**关键字标注，其他的定义和普通函数一样
  - 规则1：默认方法**不能重写**Object中的方法
  - 规则2：实现类可以**继承/重写**父接口的默认方法
  - 规则3：接口可以**继承/重写**父接口的默认方法
  - 规则4：当父类和父接口都有(同名同参数)默认方法，**子类继承父类的默认方法**，这样可以兼容JDK7及以前的代码
  - 规则5：子类实现了2个接口(均有同名同参数的默认方法)，**那么编译失败，必须在子类中重写这个default方法**

#### 接口的静态方法

- Java 8接口的静态方法**(带实现的)**

  - 该静态方法属于本接口的，**不属于子类/子接口**
  - 子类(子接口)没有继承该静态方法，只能通过**所在的接口名**来调用、

  ~~~java
  public interface StaticAnimal{
      public static void move()
      {
          System.out.println("I can move");
      }
  }
  public interface StaticLandAnimal extends StaticAnimal{
      //也继承不到StaticAnimal的move方法
  }
  public class StaticSwan implements StaticAnimal{
      public static void main(String[] args){
          StaticAnimal.move();
          StaticLandAnimal.move();//error
          new StaticSwan().move();//error
      }
  }
  ~~~

#### 接口的私有方法

- Java 9接口的私有方法(**带实现的**)
  - 解决多个默认方法/静态方法的内容重复问题
  - 私有方法属于本接口，只在本接口内使用，**不属于子类/子接口**
  - 子类(子接口)没有继承该私有方法，也无法调用
  - 静态私有方法可以被静态/默认方法调用，非静态私有方法被默认方法调用

#### 接口 vs 抽象类

- 相同点(截止至Java12以前，接口和抽象类对比)
  - 都是抽象的，都不能被实例化，即不能被new
  - 都可以有实现方法
  - 都可以不需要继承者实现所有的方法

### 第六节 try-with-source和Resource Bundle文件加载

#### try-with-resource

- JDK7提供try-with-resource
  - 资源要求定义在try中。若已经在外面定义，则需要一个本地变量
- JDK9不再要求定义临时变量，可以直接使用外部资源变量

~~~java
FIleInputStream fis = ....;
try(fis)
{
    ......
}
catch(Exception ex)
{
    ......
}
~~~

- try-with-resource原理
  - 资源对象必须实现**AutoCloseable接口**，即实现**close方法**

#### ResourceBundle文件加载z

- Java8及以前，ResourceBundle默认以ISO-8859-1方式加载Properties文件
  - **需要利用native2ascii工具(JDK自带)对文件进行转义**
- JDK9及以后，ResourBundle默认以UTF-8方式加载Properties
  - **JDK9及以后，已经删除native2ascii工具**
  - **新的Properties文件可以直接以UTF-8保存**
  - 已利用nativ2ascii工具转化后的文件，不受影响。即ResourBundle若解析文件不是有效的UFTF-8,则以ISO-8859-1方式加载

### 第七节 var类和switch

#### var类型

- Java以前一直是一种强类型的程序语言

- Java10推出var：局部变量推断

  - 避免信息冗余
  - 对齐了变量名
  - **更容易阅读**
  - **本质上还是强类型语言，编译器负责推断类型，并写入字节码文件。因此推断后不呢能更改**

  ~~~java
  var b1 = 6;
  var b2 = 0.25;
  var b3 = "avc";
  var b4 = new URL("htttps://");
  //b3 = 5; //error
  ~~~

- var的限制

  - **可以用在局部变量上，非类成员变量**
  - **可以用在for/for-each循环中**
  - **声明必须初始化**
  - **不能用在方法(形式)参数和返回类型**
  - **大面积滥用会使代码整体阅读性变差**
  - **var只在编译时起作用，没有在字节码中引入新的内容，也没有专门的JVM指令处理var**

#### Switch

- switch,多分支选择语句

  - 支持的类型：byte/Byte,short/Short,int/Integer,char/Character,String(7.0),Enum枚举(5.0),**不支持long/float/double**

- switch,多分支选择语句

  - 多分支合并，采用->直接连接判定条件和动作**(JDK12支持***)

    ~~~java
    switch(month)
    {
            case"JAn","MAr" ->result = 31;
            case"Apr" -> reuslt=30;
            case"Feb" ->result = 28;
            default -> result = -1;
    }
    ~~~

  - switch直接在表达式赋值(**JDK12支持**)

    ~~~java
    int num = 1;
    int days = switch(num){
            case 1,3,5 ->31;
            case 4,6,9 ->30;
        default ->{
            int result = 28;
            break result;//代码块中break返回结果
        }
    }
    ~~~

    ​