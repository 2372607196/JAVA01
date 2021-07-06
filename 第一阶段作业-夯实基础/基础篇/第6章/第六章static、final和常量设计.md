## 第六章static、final和常量设计

### 第一节 static

- Java中的static关键字（可作用在）

  -变量

  -方法

  -类

  -匿名方法块

- 静态变量，类共有成员

  -static变量只依赖于类存在

  -所有的对象实例，关于static变量的值都共享存储在一个共同的空间（栈）

- static 方法

  -在静态方法中，只能使用静态变量，不能使用非静态变量

  -静态方法禁止引用非静态方法

- static块

  -只在类第一次被加载时调用

  -换句话说，在程序运行期间，这段代码只运行一次

  -执行顺序：static块>匿名块>构造函数

### 第二节 单例模式

- 限定某一个类在整个程序运行过程中，只能保留一个实例对象在内存空间中

- 单例模式是GoF的23种设计模式中经典的一种，属于创建型模式类型

- 单例模式：保证一个类有且只有一个对象

  -采用static来共享对象实例

  -采用private构造函数，防止外界new操作

### 第三节 final

- Java的final关键字同样可以用来修饰

  -类

  -方法

  -字段

- final的类，不能被继承

- 父类中如果有final的方法，子类中不能改写此方法

- final的变量，不能再次赋值

  -如果是基本型别的变量，不能修改其值

  -如果是对象实例，那么不能修改其指针（但是可以修改对象内部的值）

### 第四节 常量设计和常量池

#### 1.常量设计

- 常量：一种不会修改的变量

  -Java没有constant关键字

  -不能修改，final

  -不会修改/只读/只要一份，static

  -方便访问public

- Java中的常量

  -public static final

  -建议变量名字全大写，以连字符相连，如UPPER_BOUND

- **一种特殊的常量：接口内定义的变量默认是常量**

  -实现接口，就需要遵循接口的定义，就是尊重契约精神。所以接口的变量就是以常量的形式存在

#### 2.常量池

- Java为很多基本类型的包装类/字符串都建立常量池

- 常量池：相同的值只存储一份，节省内存，共享访问

- 基本类型的包装类

  -Boolean，Byte,Short,Integer,Long,Character,   (Float,Double **没有常量池**)

  -Boolean: true,false

  -Byte:-128~127,Character:0~127

  -Short,Int,Long:-128~127

  -Float,Double:没有缓存（常量池）

- Java为常量字符串都建立常量池缓存机制

  ```java
  public class StringConstantTest{
      public static void main(String[] args){
          String s1 = "abc";
          String s2 = "abc";
          String s3 = "ab"+"c";
          String s4 = "a"+"b"+"c";
          System.out.println(s1==s2);//true
          System.out.println(s1==s3);//true
          System.out.println(s1==s4);//true
      }
  }
  ```

- 基本类型的包装类和字符串有两种创建方式

  - 常量式（字面量）赋值创建，放在栈内存(将被常量化)
    - Interger a = 10;
    - String b = "abc";
  - new对象进行创建，放在堆内存（不会常量化）
    - Integer c = new Integer(10);
    - String d =new String("abc");

- 这两种创建方式导致创建的对象存放的位置不同

- 栈内存读取速度快，但容量小

- 堆内存读取速度慢，但容量大

```java
public class BoxClassTest {
	public static void main(String[] args)
	{
		int i1 = 10;
		Integer i2 = 10;                // 自动装箱
		System.out.println(i1 == i2);   //true
		// 自动拆箱  基本类型和包装类进行比较，包装类自动拆箱
		
		Integer i3 = new Integer(10);
		System.out.println(i1 == i3);  //true
		// 自动拆箱  基本类型和包装类进行比较，包装类自动拆箱
		
		System.out.println(i2 == i3); //false
		// 两个对象比较，比较其地址。 
		// i2是常量，放在栈内存常量池中，i3是new出对象，放在堆内存中
		
		Integer i4 = new Integer(5);
		Integer i5 = new Integer(5);
		System.out.println(i1 == (i4+i5));   //true
		System.out.println(i2 == (i4+i5));   //true
		System.out.println(i3 == (i4+i5));   //true
		// i4+i5 操作将会使得i4,i5自动拆箱为基本类型并运算得到10. 
		// 基础类型10和对象比较, 将会使对象自动拆箱，做基本类型比较
		
		Integer i6 = i4 + i5;  // +操作使得i4,i5自动拆箱，得到10，因此i6 == i2.
		System.out.println(i1 == i6);  //true
		System.out.println(i2 == i6);  //true
		System.out.println(i3 == i6);  //false
	}	
}
```

- Integer类
  - 基本类型和包装类比较，将对包装类自动拆箱
  - 对象比较，比较地址
  - 加法+会自动拆箱
- String类
  - 常量赋值(堆内存)和new创建（栈内存）不是同一个对象
  - 编译器只会优化确定的字符串，并缓存

### 第五节 不可变对象和字符串

#### 1.不可变对象

- 不可变对象
  - 一旦创建，这个对象（状态/值）不能被更改了
  - 其内在的成员变量的值就不能修改了
  - 八个基本型别的包装类
  - String，BigInteger和BigDecimal等
- 可变对象
  - 普通对象
-  如何创建不可变对象
  - immutable对象是不可改变的，有改变，青clone/new一个对象进行修改
  - 所有的属性都是final和private的
  - 不提供setter方法
  - 类是final的，或者所有的方法都是final
  - 类中包含mutable对象，那么返回拷贝需要深度clone
- 不可变对象优点
  - 只读，线程安全
  - 并发读，提高性能
  - 可以重复使用
- 缺点
  - 制造垃圾，浪费空间
    - 对不可变对象进行修改时，会新开辟空间，旧对象则被搁置，直到垃圾回收

#### 2.Java字符串

- 字符串是Java使用最多的类，是一种典型的不可变对象
- String定义有两种
  - String a = "abc";//常量赋值，栈分配内存
  - String b = new String（“abc”);//new对象，堆分配内存
- 字符串内容比较：equals方法
- 是否指向同一个对象：指针比较==
- Java常量池
  - 保存在编译期间就已经确定的数据
  - 是一块特殊的内存
  - 相同的**常量字符串**只存储一份，节省内存，共享访问
- 字符串的加法
- String a = "abc";
- a = a + "def";//由于String不可修改，效率差，这样会开辟一个新的空间
- 使用StringBuffer/StringBuilder类的append方法进行修改
- StringBuffer/StringBuilder的对象都是可变对象
- StringBuffer（同步，线程安全，修改快速），StringBuilder（不同步，线程不安全，修改更快）

