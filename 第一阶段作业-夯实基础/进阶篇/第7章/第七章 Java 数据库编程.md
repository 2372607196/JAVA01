## 第七章 Java 数据库编程

### 第一节 数据库和SQL

#### DB

- DB:保存数据的地方
  - **数据安全、安全、安全**
  - 存取效率
  - 性价比高

#### DB分类

- DB(文件集合，类似.doc,.docx文件)
- DBMS:Database Management System (类似Office)
  - 操纵和管理数据库的软件，可建立、使用和维护
- DB种类
  - 文本文件/二进制文件
  - Xls文件
  - Access(包含在office里面，收费，只能运行在Windows上，有32位和64位)

#### 表

- 表：table,实体
  - 列：列、属性、字段
  - 行：记录、元组tuple，数据
- 数据值域：数据的取值范围
- 字段类型
  - int:整数 -2147483648~2147483647，4个字节
  - double：小数，8个字节
  - datetime：时间，7个字节
  - varchar：字符串，可变字节

### 第二节 JDBC基本操作

#### JDBC

- Java和数据库的连接方法
  - Native API(**不能跨平台**)
  - ODBC/JDBC-ODBC(**效率很差，也无法跨平台**)
  - JDBC(主流):Java Database Connectivity
    - JDBC 1,JDK 1.1
    - JDBC 2 JDK 1.3~1.4
    - JDBC 3 JDK 5
    - JDBC 4 JDK6,(JDK7,JDBC4.1;JDK8,JDNC4.2)

#### Java SQL操作类

- java.sql.*,javax.sql.*,这两个包只是接口类
- 根据数据库版本和JDBC版本合理选择
- 一般数据库发行包都会提供jar包，同时也要注意区分32位和64位(数据库分32/64位，JDK也分32/64位。)
- 连接字符串(**样例**)
  - jdbc:oracle:thin:@127.0.0.1:1521:dbname
  - jdbc:mysql://localhost:3306/mydb
  - jdbc:sqlserver://localhost:1433;DatabaseName=dbname

#### Java连接数据库操作步骤

- 构建连接(搭桥)
  - 注册驱动，寻找材质，class.forName("...");
  - 确定对岸目标，建桥Connection
- 执行操作(派个人过桥，提着篮子，去拿数据)
  - Statement(执行者)
  - ResultSet(结果集)
- 释放连接(拆桥) connection.close();

![62389665672](D:\学习资料\java\Mooc笔记\进阶第七章第二节.png)

#### Statement

- Statement 执行者类
  - 使用executeQuery()执行select语句，赶回结果放在ResultSet
  - 使用executeUpdate()执行insert/update/delete，返回修改的行数
  - 一个Statement对象一次只能执行一个命令
- ResultSet 结果对象
  - next()判断是否还有下一条记录
  - getInt/getString/getDouble/......
    - 可以按索引位置，可以按照列名

#### 注意事项

- ResultSet不能多个做笛卡尔积连接
- ResultSet最好不要超过百条，否则极其影响性能
- ResultSet也不是一口气加载所有的select结果数据
- Connection很昂贵，需要及时close
- Connection所用的jar包和数据库要匹配

~~~java
import java.sql.*;

public class SelectTest {
    public static void main(String[] args){
    	
    	//构建Java和数据库之间的桥梁介质
        try{            
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("注册驱动成功!");
        }catch(ClassNotFoundException e1){
            System.out.println("注册驱动失败!");
            e1.printStackTrace();
            return;
        }
        
        String url="jdbc:mysql://localhost:3306/test";        
        Connection conn = null;
        try {
        	//构建Java和数据库之间的桥梁：URL，用户名，密码
            conn = DriverManager.getConnection(url, "root", "123456");
            
            //构建数据库执行者
            Statement stmt = conn.createStatement(); 
            System.out.println("创建Statement成功！");      
            
            //执行SQL语句并返回结果到ResultSet
            ResultSet rs = stmt.executeQuery("select bookid, bookname, price from t_book order by bookid");
                        
            //开始遍历ResultSet数据
            while(rs.next())
            {
            	System.out.println(rs.getInt(1) + "," + rs.getString(2) + "," + rs.getInt("price"));
            }
            
            rs.close();
            stmt.close();
            
        } catch (SQLException e){
            e.printStackTrace();
        }
        finally
        {
        	try
        	{
        		if(null != conn)
        		{
            		conn.close();
            	}
        	}
        	catch (SQLException e){
                e.printStackTrace();
        	}        	
        }
    }
}
~~~

### 第三节 JDBC高级操作

#### 事务

- 数据库事务，Database Transaction
- 作为单个逻辑工作单元执行的一系列操作，**要么完全地执行，要么完全地不执行**
- 事务，必须满足所谓的ACID(**原子性、一致性、隔离性和持久性**)属性
- 事务是数据库运行中的逻辑工作单位，由DBMS中的事务管理子系统负责事务的处理

#### JDBC事务

- 关闭自动提交，实现多语句同一事务
- connection.setAutoCommit(false);
- connection.commit();提交事务
- connection.rollback();回滚事务
- 保存点机制
  - connection.setSavepoint()
  - connection.rollback(Savepoint)

#### PreparedStatement

- Java提供PreparedStatement，更为安全执行SQL
- 和Statement区别是使用“？”代替字符串拼接
- 使用setXXX(int,Object)的函数来实现对于？的替换
  - 注：不需要考虑字符串的两侧单引号
  - 参数赋值，清晰明了，拒接拼接错误
- 提供addBatch批量更新功能
- Select语句一样用ResultSet接受结果
- 使用PreparedStatement的好处：
  - 防止注入攻击
  - 防止繁琐的字符串拼接和错误
  - 直接设置对象而不需要转换为字符串
  - PreparedStatement使用预编译速度相对Statement快很多，经过了预编译

#### ResultSetMetaData

- ResultSet可以用来承载所有的select语句返回的结果集
- ResultSetMetaData来获取ResultSet返回的属性(如，每一行的名字类型等)
  - getColumnCount()，返回结果的列数
  - getColumnClassName(i),返回第i列的数据的Java类名
  - getColumnTypeName(i),返回第i列的数据库类型名称
  - getColumnType(i),返回第i列的SQL类型

~~~java
import java.sql.*;

public class ResultSetMetaDataTest {
    public static void main(String[] args){
    	
    	//构建Java和数据库之间的桥梁介质
        try{            
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("注册驱动成功!");
        }catch(ClassNotFoundException e1){
            System.out.println("注册驱动失败!");
            e1.printStackTrace();
            return;
        }
        
        String url="jdbc:mysql://localhost:3306/test";        
        Connection conn = null;
        try {
        	//构建Java和数据库之间的桥梁：URL，用户名，密码
            conn = DriverManager.getConnection(url, "root", "123456");
            
            //构建数据库执行者
            Statement stmt = conn.createStatement(); 
            System.out.println("创建Statement成功！");      
            
            //执行SQL语句并返回结果到ResultSet
            ResultSet rs = stmt.executeQuery("select bookid, bookname, price from t_book order by bookid");
                        
            //获取结果集的元数据
            ResultSetMetaData meta = rs.getMetaData(); 
            int cols = meta.getColumnCount(); 
            for(int i=1;i<=cols;i++)
            {
            	System.out.println(meta.getColumnName(i) + "," + meta.getColumnTypeName(i));
            }
            
            rs.close();
            stmt.close();
            
        } catch (SQLException e){
            e.printStackTrace();
        }
        finally
        {
        	try
        	{
        		if(null != conn)
        		{
            		conn.close();
            	}
        	}
        	catch (SQLException e){
                e.printStackTrace();
        	}        	
        }
    }
}
~~~

#### 第四节 数据库连接池

#### 享元模式

- Connection是Java和数据库两个平行系统的桥梁
- 桥梁构建不易，成本很高，单次使用成本昂贵
- 运用共享技术来实现数据库连接池(享元模式)
  - 降低系统中数据库连接Connection对象的数量
  - 降低数据库服务器的连接响应消耗
  - 提高Connection获取的响应速度
- 享元模式，Flyweight Pattern
  - 经典23个设计模式的一种，属于结构型模式
  - 一个系统中存在大量的相同的对象，由于这类对象的大量使用，会造成系统内存的耗费，可以使用享元模式来减少系统中对象的数量

#### 数据库连接池

- 理解池Pool的概念

  - 初始数、最大数、增量、超过时间等参数

- 常用的数据库连接池

  - DBCP(Apache,http://commons.apache.org/,性能较差)

  - C3P0(https://www.mchange.com/projects/c3p0/)

  - Druid(Alibaba,https://github.com/alibaba/druid)

  - ~~~java
    import java.io.BufferedInputStream;
    import java.io.FileInputStream;
    import java.io.InputStream;
    import java.sql.Connection;
    import java.util.Properties;

    import com.alibaba.druid.pool.DruidDataSource;
    import com.alibaba.druid.pool.DruidDataSourceFactory;


    public class DruidFactory2 {
    	private static DruidDataSource dataSource = null;

    	public static void init() throws Exception {
    		Properties properties = new Properties();
    		
    		InputStream in = DruidFactory2.class.getClassLoader().getResourceAsStream("druid.properties");  
    		properties.load(in); 		
    		dataSource = (DruidDataSource)DruidDataSourceFactory.createDataSource(properties);		
    		
    		in.close();
    	}
    	
    	public static Connection getConnection() throws Exception {
    		if(null == dataSource)
    		{
    			init();
    		}
            return dataSource.getConnection();
        }
    }

    ~~~

  - ~~~properties
    driverClassName=com.mysql.jdbc.Driver
    url=jdbc:mysql://127.0.0.1:3306/test
    username=root
    password=123456
    filters=stat
    initialSize=2
    maxActive=300
    maxWait=60000
    timeBetweenEvictionRunsMillis=60000
    minEvictableIdleTimeMillis=300000
    validationQuery=SELECT 1
    testWhileIdle=true
    testOnBorrow=false
    testOnReturn=false
    poolPreparedStatements=false
    maxPoolPreparedStatementPerConnectionSize=200
    ~~~

  - ​

  - ... 

- 翻译

  - driverClass 驱动class,这里为mysql的驱动
  - jdbcUrl jdbc链接
  - user password数据库用户名密码
  - initialPoolSize 初始数量：从一开始创建多少条链接
  - maxPoolSize 最大数：最多有多少条链接
  - acquireIncrement 增量：用完每次增加多少个
  - maxIdleTime 最大空闲时间：超出的链接会被抛弃