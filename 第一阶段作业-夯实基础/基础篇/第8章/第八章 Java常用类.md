## 第八章 Java常用类

### 第一节 Java类库概述

- Java类库文档
  - https://docs.oracle.com/javase/8/docs/api/
- 这些文档原先是程序中的注释。利用JavaDoc技术，将这些注释抽取出来，组织形式的以JHTML为表现形式的API(Application Programming Interface,应用编程接口)文档。

![62166524709](D:\学习资料\java\Mooc笔记\第八章.png)

### 第二类 数字相关类

- Java 数字类
  - 整数 Short,Int,Long
  - 浮点数 Float,Double
  - 大数类 BigInteger(大整数)，BigDecimal（大浮点数）
    - 在Java类库中，整数类和浮点数类所表示的数字都有界限范围，如Int是-2147483648至2147483647.而大数类没有限制，可以表示无穷大的数字
  - 随机数类Random
  - 工具类Math
- java.math包

![62166563285](D:\学习资料\java\Mooc笔记\第八章第二节.png)

- BigInteger不支持用+号进行加法，而是采用add函数。后续其他操作也是才用函数而不是符号进行的

- BIgDecimal尽量用字符串进行赋值

- Random 随机数

  - nextInt()返回一个随机 int
  - nextInt(int a)返回一个[0,a)之间的随机int
  - nextDouble()返回一个[0.0,1.0]之间的double
  - ints方法批量返回随机数数组

- Math.random()返回一个[0.0,1.0]之间double

  - 采用Random类随机生成在int范围内的随机数

    ~~~ java
    Random rd = new Random();
    rd.nextInt();
    rd.nextint(100);//0--100的随机数
    rd.nextLong();
    rd.nextDouble();
    ~~~

  - 生成一个范围内的随机数 例如0到10之间的随机数

    ~~~java
    Math.round(Math.random()*10)
    ~~~

  - JDK 8新增方法

    ~~~java
    rd.ints();//返回无限个int类型范围内的数据
    int[] arr = rd.ints(10).toArray();//生成10个int范围类的个数
    for(int i = 0;i<arr.length;i++){
        System.out.println(arr[i]);
    }
    arr = rd.ints(5,10,100).toArray();
    arr = rd.ints(10).limits(5).toArray();
    ~~~

- java.lang.Math

  - 绝对值函数abs
  - 对数函数log
  - 比较函数max、min
  - 幂函数pow
  - 四舍五入函数round
  - 向下取整floor
  - 向上取整ceil

### 第三节 字符串相关类

-  String

  - Java中使用频率最高的类

  - 是一个不可变对象，加减操作性能差

  - ```Java
    String a = "123;456;789;123 ";
    a.charAt(0);//返回第0个元素
    a.indexOf(";");//返回第一个；的位置
    a.concat(";000");//连接一个新字符串并返回，a不变
    a.contains("000");//判断a是否包含000
    a.endswith("000");//判断a是否以000结尾
    a.equals("000");//判断是否等于000
    a.equalsIgnoreCase("000");//判断在忽略大小写情况下是否等于000
    a.length();//返回a长度
    a.trim();//返回a去除前后空格后的字符串，a不变
    a.spilt(";");//将a字符串按照；分割成数组
    a.substring(2,5); //截取a的第2个到第5个字符 a不变
    a.replace("1","a");
    a.replaceAll("1","a"); //repalceAll第一个参数是正则表达式
    s1.replaceAll("[\\d]","a")//可以将s1字符串里面所有的数字替换为a并返回。请注意，s1的值没有变化
    ```

- 可变字符串

  - StringBuffer （字符串加减，同步，性能好）
  - StringBuilder （字符串加减，不同步，性能更好）

- StringBuffer/StringBuilder：方法一样，区别在于同步

  - append/insert/delete/replace/substring

  - length字符串实际大小，capacity字符串占用空间大小

  - trimToSize();去除空隙，将字符串存储压缩到实际大小

  - 如有大量append，事先预估大小，再调用相应构造函数

    ![62167318986](D:\学习资料\java\Mooc笔记\第八章第三节.png)

- 字符串拼接速度比较，StringBuilder>StringBuffer>>>String

- StringBuffer的初始大小为(16+初始字符串长度)即capacity=16+初始字符串长度

- StringBuffer预留的空间将为频繁字符串增加操作提高性能

  - 一旦length大于capacity时，capacity便在前一次的基础上加1后翻倍
  - 当前sb2length 20 capacity 40 ,再append 70 个字符超过了（加1再2倍数额）则直接capacity=length
  - trimToSize方法可以让capacity=length，但不利于频繁字符串增加操作的性能

### 第四节 时间相关性

- java.sql.Date(和数据库对应的时间类)

- Calendar是目前程序中最常用的，但是是抽象类

  - Calendar gc = Calendar.getInstance();
  - Calendar gc = new GregorianCalendar();
  - 简单工厂模式

- Calendar

  - get(Field) 来获取时间中每个属性的值，注意，月份0-11
  - getTime(),返回相应的Date对象
  - getTimeInMillis(),返回自1970.1.1以来的毫秒数
  - set(Field)设置时间字段
  - add(field,amount)根据指定字段增加/减少时间
  - roll(field,amount)根据指定字段增加/减少时间，但不影响上一级的时间段

  ```java
  int year = calendar.get(Calendar.YEAR);//获取年
  int month = calendar.get(Calendar.MONTH)+1;//获取月，需要加1
  int day = calendar.get(Calendar.DAY_OF_MONTH);//获取日
  int hour = calendar.get(Calendar.HOUR);//获取时
  int hour2 = calendar.get(Calendar.HOUR_OF_DAY);//24小时表示
  int minute = calendar.get(Calendar.MINUTE);//获取分
  int second = calendar.get(Calendar.SECOND);//获取秒
  int weekday = calendar.get(Calendar.DAY OF WEEK);//星期
  calendar.add(Calendar.YEAR,1);//一年后的今天，其它同理
  //获取任意一个月的最后一天
  public void test3(){
      int currentMonth = 6;//假设求6月的最后一天
      calendar.set(calendar.get(Calendar.YEAR),currentMonth,1);
      calendar.add(Calendar.DATE,-1);
      //用下一个月的一号减一天
      int day = calendar.get(Calendar.DAY_OF_MONTH);
  }
  ```

- roll函数做加减法，只影响当前字段，不影响进位。add函数则会影响进位

- Java 8 推出新的时间API

  - java.time包
  - 旧的设计不好（重名的类、线程不安全等）
  - 新版本有点
    - 不变性，在多线程环境下
    - 遵循设计模式，设计得更好，可扩展性更强

- Java 8 java.time包主要类

  - LocalDate: 日期类
  - LocalTime：时间类（时分秒-纳秒）
  - LocalDateTime：LocalDate + LocalTime
  - Instant：时间戳

~~~ java
LocalDate today = LocalDate.now();//当前时间
LocalDate firstDay_2014 = LocalDate.of(2014,Month.JANUARY,1);//根据指定时间创建LocalDate
//给定错误时间参数，将报异常java.time.DateTimeException
//LocalDate feb29_2014 = LocalDate.of(2014,Month.FEBRURY,29);

//可以更改时区
LocalDate todayBeijing = LocalDate.now(ZoneId.of("Asia/Shanghai"));
//从纪元日01/01/1970开始的365天
LocalDate dateFromBase = LocalDate.ofEpochDay(365);
//2014年的第100天
LocalDate hundredDay2014 = LocalDate.ofYearDay(2014,100);

//当前时间 时分秒 纳秒
LocalTime time = LocalTime.now();
//根据时分秒
LocalTime specificTime = LocalTime.of(12,20,25,40);
//一天当中第几秒
LocalTime specificSecondTime = LocalTime.ofSecondOfDay(10000);
~~~

### 第五节 格式化相关类

- java.text包java.text.Format的子类
  - NumberFormat:数字格式化，抽象类
    - DecimalFormat 工厂模式
  - MessageFormat:字符串格式化
    - 支持多个参数-值对位复制文本
    - 支持变量的自定义格式
  - DateFormat:日期/时间格式化,抽象类
    - SimpleDateFormat 工厂模式
    - parse:将字符串格式转化为时间对象
    - format:将时间对象格式转化为字符串
    - 如将当前时间转为化YYYY-MM-DD HH24：MI：SS输出
- java.time.format包下
  - DataTimeFormatter