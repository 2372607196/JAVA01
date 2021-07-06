## 第十章 Java数据结构

### 第一节 数组

![62190980166](D:\学习资料\java\Mooc笔记\第十章第一节.png)

- Java数组定义和初始化

  ~~~java
  int a[];//a 还没有new操作，实际上是null，也不知道内存位置
  int[] b;//同上
  int[] c = new int[2];//c有两个元素，都是0
  c[0] = 10;c[1] = 20;//逐个初始化

  int d[] = new int[]{0,2,4};//d有3个元素，0，2，4，同时定义和初始化
  int d1[] = {1,3,5}; //同上
  //注意声明变量时候没有分配内存，不需要指定大小，以下是错误示例
  //int e[5];
  //int[5] f;
  //int[5] g = new int[5];
  //int h[5] = new int[5];
  ~~~

- 数组不能越界访问，否则会报ArrayIndexOutOfBoundsException异常

- 数组遍历 for-each语句

  ```java
  for(int e:d){//不存在也不会出现越界访问
      System.out.println(e);
  }
  ```

- 多维数组

  - 数组的数组
  - 存储是按照行存储原则

~~~java
//规则数组
int a[][] = new int[2][3];
//不规则数组
int b[][];
b = new int[3][];//即一维数组里的一维数组
b[0] = new int[3];
b[1] = new int[4];
b[2] = new int[5];
int k = 0;//第一种遍历方法
for(int i=0;i<a.length;i++)
{
    for(int j=0;j<a[i].length;j++){
        a[i][j] = ++k;
    }
}
for(int[] items:a)//不规则数组的遍历方法
{
    for(int item:items)
    {
        System.out.print(item+",");
    }
    System.out.println();
}
~~~

### 第二节 JCF

- 容器：能够存放数据的空间结构
  - 数组/多维数组,只能线性存放
  - 列表/散列集/树/....
- 容器框架：为表示和操作容器而规定的一种标准体系结构
  - 对外的接口：容器中所能存放的抽象数据类型
  - 接口的实现：可复用的数据结构
  - 算法：对数据的查找和排序
- 容器框架优点：提高数据存取效率，避免程序员重复劳动
- C++的STL,Java的JCF
- ![62191140791](D:\学习资料\java\Mooc笔记\第十章第二节.png)
  - 绿色部分是列表（List）；
  - 黄色部分是散列集合（Set）；
  - 浅蓝色是映射（Map）；
  - 工具类Arrays Collections
- 早期接口Enumeration
- JCF的集合接口是Collection
  - add(增加),contains(包含),remove(删除),size(数据元素个数)
  - iterator(迭代器)
- JCF的迭代器接口Iterator
  - hasNext //hasNext判断是否有下一个元素;
  - next //获取下一个元素
  - remove //删除某一个元素
- JCF主要的数据结构实现类
  - 列表(List,ArrayList,LinkedList)
  - 集合(Set,HashSet,TreeSet,LinkedHashSet)
  - 映射（Map,HashMap,TreeMap,LinkedHashMap)
- JCF主要的算法类
  - Arrays:对数组进行查找和排序等操作
  - Collections:对Collection及其子类进行排序和查找操作

### 第三节 列表List

- List:列表

  - 有序的Collection
  - 允许重复元素
  - ｛1,2,4,{5,2},1,3}//允许嵌套

- List主要实现

  - ArrayList(非同步的)
  - LinkedList（非同步)
  - Vector(同步)

- ArrayList:

  - 以数组实现的列表，不支持同步
  - 利用索引位置可以快速定位访问
  - 不适合指定位置的插入、删除操作
  - 在Java中，数组是静态的，数组长度一旦确定就不能修改；ArrayList是动态的，容量（长度）可以随时扩充
  - 适合变动不大，主要用于查询的数据
  - 和Java数组相比，其容量是可动态调整的
  - ArrayList在元素填满容器时会自动扩充容器大小的50%

  ~~~java
  ArrayList<Integer> al = new ArrrayList<Integer>();
  al.add(3);//ArrayList只能装对象，当add（3）时，会自动将普通int变量自动装箱为Integer（3）的对象，然后放入ArrayList容器中
  al.add(2);
  al.add(1);
  al.add(new Integer(6));
  al.remove(3);//删除第四个元素，后面元素往前挪动
  al.add(3,9);//将9插入到第4个元素，后面元素往后挪动
  Iterator<Integer> iter1 = al.iterator();//Iterator遍历
  while(iter1.hasNext()){
      iter1.next();
  }
  for(int i=0;i<al.size();i++){//索引位置遍历
      al.get(i);
  }
  long startTime = System.nanoTime();//开始时间
  for(Integer item:al){//for-each遍历  最快
      ;
  }
  long endTime = System.nanoTime(); //结束时间
  long duration = endTime-startTime;//用的时间
  ~~~

- LinkedList：

  - 以双向链表实现的列表，不支持同步
  - 可被当作堆栈、队列和双端队列进行操作
  - 顺序访问高效，随机访问较差，中间插入和删除高效
  - 适用于经常变化的数据

  ~~~java
  LinkedList<Integer> ll =new LinkedList<Integer>();//随机访问会很慢
  ll.add(3);
  ll.add(2);
  ll.add(6);
  ll.add(6);
  ll.size();//长度为4
  ll.addFirst(9);//在头部增加9
  ll.add(3,10);//将10插入到第四个元素，四以及后续的元素往后挪动
  ll.remove(3);//将第四个元素删除
  ~~~

- Vector(同步)

  - 和ArrayList类似，可变数组实现的列表
  - Vector同步，适合在多线程下使用
  - 原先不属于JCF框架，属于Java最早的数据结构，性能较差
  - 从JDK1.2开始，Vector被重写，并纳入到JCF
  - 官方文档建议在非同步情况下，优先采用ArrayList

### 第四节 集合Set

- 集合Set
  - 确定性：对任意对象都能判定其是否属于某一个集合
  - 互异性：集合内每个元素都是不相同的，注意是内容互异
  - 无序性：集合内的顺序无关
- Java中的集合接口Set
  - HashSet(基于散列函数的集合，无序，不支持同步)
  - TreeSet(基于树结构的集合，可排序的，不支持同步)
  - LinkedHashSet(基于散列函数和双向链表的集合，可排序的，不支持同步)
- HashSet
  - 基于HashMap实现的，可以容纳null元素，不支持同步
    - Set s = Collections.synchronizedSet(new HashSet(...));
  - add 添加一个元素
  - clear 清除整个HashSet
  - contains 判定是否包含一个元素
  - remove 删除一个元素 size 大小
  - retainAll计算两个集合交集 //set1.retainAll(set2);
  - 加入同一个数据，第二个add语句无效
  - HashSet是无序的结构
- LinkedHashSet
  - 继承HashSet，也是基于HashMap实现的，可以容纳null元素
  - 不支持同步
    - Set s = Collections.synchronizedSet(new LinkedHashSet(...));
  - 方法和HashSet基本一致
    - add,clear,contains,remove,size
  - **通过一个双向链表维护插入顺序**
  - LinkedHashSet是保留顺序的，其遍历顺序和插入顺序一致；而HashSet没有保留顺序，其遍历顺序无序
- TreeSet
  - 基于TreeMap实现的，**不可以容纳Null元素**，不支持同步
    - SortedSet s = Collections.synchronizedSortedSet(new TreeSet(...));
  - add 添加一个元素
  - clear 清除整个TreeSet
  - contains 判定是否包含一个元素
  - remove 删除一个元素 size 大小
  - 根据compareTo方法或指定Comparator排序
  - **TreeSet是按照所存储对象大小升序输出地**
- HashSet，LinkedHashSet，TreeSet的元素都只能是对象
- HashSet和LinkedHashSet判定元素重复的原则
  - 判定两个元素的hashCode返回值是否相同，若不同，返回false
  - 若两者hashCode相同，判定equals方法，若不同，返回false；否则返回true
  - hashCode和equals方法是所有类都有的，因为Object类有
  - **对于复杂的类时，改写类里的hashCode（）、equals（）方法来去除重复的数据**
  - HashSet的元素判定规则只和hashCode、equals这两个方法有关，和compareTo方法无关
- TreeSet判定元素重复的原则
  - **需要元素继承自Comparable接口**
  - 比较两个元素的compareTo方法 
  - **添加到TreeSet的，需要实现Comparable接口，即实现compareTo方法**
- Java的四个重要接口
  - Comparable:可比较的；
  - Clonable:可克隆的；
  - Runnable:可线程化的；
  - Serializable:可序列化的。

### **第五节** 映射Map

- Map映射

  - 数学定义：两个集合之间的元素对应关系
  - 一个输入对应到一个输出
  - ｛1，张三｝，｛2，李四｝，｛Key,Value｝，键值对，K-V对

- Java中Map

  - Hashable(同步，慢，数据量小)
  - HashMap（不支持同步，快，数据量大）
  - Properties（同步，文件形式，数据量小）

- Hashtable

  - K-V对，K和V都不允许为null
  - 同步，多线程安全
  - 无序的
  - 适合小数据量
    - clear 清空数据
    - contains 等同于containsValue;
    - containsKey 是否包含某一个Key
    - containsValue 是否包含某一个值
    - get 根据Key获取相应的值
    - put 增加新的K-V对
    - remove 删除某一个K-V对
    - size 返回数据大小

  ~~~java
  Hashtable<Integer,String> ht =new Hashtable<Integer,String>();
  //ht.put(1,null);编译不抱错 运行报错
  //ht.put(null,1);编译报错
  ht.put(1000,"aaa");
  ht.put(1000,"ddd");//更新覆盖aaa
  //Entry迭代器遍历
  Iterator<Entry<Integer,String>>iter = ht.entrySet().iterator();
  Integer key;
  String value;
  while(iter.hasNext()){
      Map.Entry<Integer,String>entry = iter.next();
      //获取key
      key = entry.getKey();
      //获取value
      value = entry.getValue();
  }
  //KeySet迭代器遍历
  Iterator<Integer> iter = ht.keySet().iterator();
  while(iter.hasNext()){
      key = iter.next();
      //获取value
      value = iter.next();
  }
  ~~~

- HashMap

  - K-V对，K和V都允许为null
  - 不同步，多线程不安全
    - Map m = Collections.synchronizedMap(new HashMap(...));
  - 无序的
  - 主要方法:clear,containsValuse,containsKey,get,put,remove,size 

- LinkedHashMap

  - 基于双向链表的维持插入顺序的HashMap

- TreeMap

  - 基于红黑树的Map，可以根据key的自然排序或者compareTo方法进行排序输出

- Properties

  - 继承于Hashtable
  - 可以将K-V保存在文件中
  - 适用于数量少的配置文件
  - **从文件加载的load方法，写入到文件中的store方法**
  - 获取属性getProperty，设置属性setProperty

  ~~~java
  File file = new File(filePath);
  if(!file.exists()){
      file.createNewFile();
  }
  Properties pps = new Properties();
  InputStream in = new FileInputStream(filePath);
  ~~~

  ​

### 第六节 工具类

- JCF中工具类

  - 不存储数据，而是在数据容器上，实现高效操作
    - 排序
    - 搜索
  - Arrays类
  - Collections类

- Arrays：处理对象是数组

  - 排序：对数组排序，sort/parallelSort

  - 查找：从数组中查找一个元素，binarySearch

  - 批量拷贝:从源数组批量复制元素到目标数组，copyOf

  - 批量赋值：对数组进行批量赋值，fill

    int[] a =new int[10];

    Array.fill(a,2,8,200);//2-8赋值200

  - 等价性比较：判定两个数组内容是否相同，equals

- Collections:处理对象是Collection及其子类

  - 排序：对List进行排序，sort
  - 搜索：从List中搜索元素，binarySearch
  - 批量赋值：对List批量赋值，fill
  - 最大、最小：查找集合中最大/最小值，max,min
  - 反序：将List反序排列，reverse

- 对象排序需要遵循一定的规则。Integer对象可以按照数值大小进行排序。而普通的自定义对象无法排序。因此，普通的自定义对象需要实现Comparable接口，按照compareTo方法所规定的原则进行排序。

- 对象实现Comparable接口（需要修改对象类）

  - compareTo方法
    - 大于返回1，==返回0，<返回-1
    - int a =obj1.compareTo(obj2)
    - a>0,则obj1>obj2
    - a==0,则obj1==obj2
    - a<0,则obj1<obj2
  - Arrays和Collections在进行对象sort时，自动调用该方法

  ~~~java
  class xxx implements comparable{
      public int compareTo(Person another)
      {
          int i =0;
          i = name.compareTo(another.name);//使用字符串的比较
          if(i==0)
          {
              //如果名字一样，比较年龄，返回比较年龄结果
              return age = another.age;
          }
          else
          {
              return i;//名字不一样，返回比较名字的结果
          }
      }
  }

  ~~~

  ​

- 新建Comparator（适用于对象类不可更改的情况）

  - compare方法
    - 大于返回1，==返回0，<返回-1
  - Comparator比较器将作为参数提交给工具类的sort方法

  ~~~java
  public class Person2Comparator implements Comparator<Person2>{
      public int compare(Person2 one,Person2 another){
          int i = 0;
          i = one.getName().compareTo(another.getName());
          if(i==0){
              //如果名字一样，比较年龄，返回比较年龄结果
              return one.getAge() - another.getAge();
          }
          else{
              return i;//名字不一样，返回比较名字的结果
          }
      }
  }
  ~~~

  ​