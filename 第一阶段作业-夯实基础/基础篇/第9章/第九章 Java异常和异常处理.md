## 第九章 Java异常和异常处理

### 第一节 Java异常分类

- 异常：程序不正常的行为或者状态

  - int a = 5/0;
  - 数组越界访问
  - 读取文件，结果该文件不存在

- 异常处理

  - 程序返回到安全状态

  - 允许用户保存结果，并以适当方式关闭程序

    ![62176404919](D:\学习资料\java\Mooc笔记\第九章第一节.png)

    - Throwable:所有错误的祖先

    - Error：系统内部错误或者资源耗尽。不管。

    - Exception:程序有关的异常。重点关注

      - RuntimeException:程序自身的错误

        - 5/0,空指针，数组越界...

      - 非RuntimeException：外界相关的错误

        - IOException


        - 打开一个不存在文件
        - 加载一个不存在的类...

### 第二节 Java异常处理

- 异常处理

  - 允许用户及时保存结果
  - 抓住异常，分析异常内容
  - 控制程序返回到安全状态

- 异常结构

  -try..catch(catch可以有多个，下同)

  -try..catch...finally

  -try...finally

- catch内部再次发生异常，也不影响finally的正常运行。finally肯定最后会执行的

- 进入catch块后，并不会返回到try发生异常的位置，也不会执行后续的catch块，一个异常只能进入一个catch块

- catch块的异常匹配是从上而下进行匹配的

- 所以一般是将小的异常写在前面，而一些大(宽泛)的异常则写在末尾

- 小异常，一般是具体的异常子类，如ArithmeticException；大异常，一般是泛指一种错误类别的异常父类，如Exception

- try-catch-finally每个模块里面也会发生异常，所以也可以在内部继续写一个完整的try结构

- 方法存在可能异常的语句，但不处理，那么可以使用throws来声明异常

- 调用带有throws异常(checked exception)的方法，要么处理这些异常，或者再次向外throws，直到main函数为止

- 一个方法被覆盖，覆盖它的方法必须抛出相同的异常，或者异常的子类

- 如果父类的方法抛出多个异常，那么重写的子类方法必须抛出那些异常的子集，也就是不能抛出新的异常

### 第三节 自定义异常

- 自定义异常，需要继承Exception类或其他子类
  - 继承自Exception，就变成Checked Exception
  - 继承自RuntimeException，就变成Unchecked Exception
- 自定义重点在构造函数
  - 调用父类Exception的message构造函数
  - 可以自定义自己的成员变量
- 在程序中采用throw主动抛出异常
- 除法可能会引发ArithmeticException.这个异常是属于RuntimeException,即Unchecked Exception,所以编译器不会辅助检查
- 对于CheckedException，编译器要求调用者处理，要么采用try-catch-finally,要么采用throws声明异常