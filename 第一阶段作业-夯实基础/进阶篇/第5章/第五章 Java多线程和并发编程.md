## 第五章 Java多线程和并发编程

### 第一节 多进程和多线程简介

#### 多进程概念

- 当前的操作系统都是多任务OS
- 每个独立执行的**任务**就是一个**进程**
- OS将时间划分为多个时间片(时间很短)
- **每个时间片内将CPU分配给某一个任务，时间片结束，CPU将自动回收，再分配给另外任务。**从外部看，所有任务是同时在执行。但是在CPU上，任务是按照**串行**依次运行(**单核CPU**)。如果是多核，多个进程任务可以并行。**但是单个核上，多进程只能串行执行**。
- 多进程的优点
  - 可以同时运行多个任务
  - 程序因IO堵塞时，可以释放CPU，让CPU为其他程序服务
  - 当系统有多个CPU时，可以为多个程序同时服务
    - 我们的CPU不再提高频率，而是提高核数
    - 2005年Herb Sutter的文章The free lunch is over，指明**多核和并行程序**才是提高程序性能的唯一办法
    - 串行程序，是指程序只能在单核上运行，无法利用多个CPU
    - 并行程序，是指程序可以利用多个计算核运行，加快计算速度
- 多进程的缺点
  - 太笨重，不好管理
  - 太笨重，不好切换

#### 多线程概念

- 一个程序可以包括多个子任务，可串/并行
- 每个子任务可以称为一个线程
- **如果一个子任务堵塞，程序可以将CPU调度另外一个子任务进行工作**。这样**CPU还是保留在本程序中**，而不是被调度到 别的程序（进程）去。这样，**提高本程序所获得CPU时间和利用率**

#### 多进程和多线程对比

- 多进程VS多线程
  - 线程共享数据
  - 线程通讯更加高效
  - 线程更轻量级，更容易切换
  - 多个线程更容易管理

### 第二节 Java多线程实现

#### Java多线程创建

- java.lang.Thread

  - 线程继承Thread类，实现run方法

  ~~~java
  public class Thread1 extends Thread{
      public void run(){
         Sop("hello");
      }
  }
  ~~~

- java.lang.Runnable接口

  - 线程实现Runnable接口，实现run方法

  ~~~java
  public class Thread2 implements Runnable{
      public void run(){
          Sop("hello");
      }
  }
  ~~~

#### Java多线程启动

- 启动
  - **start方法，会自动以新进程调用run方法**
  - **直接调用run方法，将变成串行执行**
  - 同一个线程，多次start会报错，只执行第一次start方法
  - 多个线程启动，其启动的先后顺序是随机的
  - 线程无需关闭，只要其run方法执行结束后，自动关闭
  - main函数(线程)可能早于新线程结束，整个程序并不终止
  - 整个程序终止是等所有的线程都终止(包括main函数线程)
- Runnable方式
  - 实现Runnable的对象必须包装在Thread类里面，才可以启动
    - new Thread(new Thread2()).start()
- 运行规则
  - 调用run方法，来启动run方法，将会是串行运行
  - 调用start方法，来启动run方法，将会是并行运行(多线程运行)

#### Java 多线程实现对比

- Thread vs Runnable
  - **Thread占据了父类的名额，不如Runnable方便**
  - Thread 类实现Runnable
  - Runnable启动时需要Thread类的支持
  - Runnable更容易实现多线程中资源共享
- 结论：建议实现Runnable接口来完成多线程

### 第三节 Java多线程信息共享

#### 多线程信息共享

- 线程类
  - 通过继承Thread或实现Runnable
  - 通过start方法，调用run方法，run方法工作
  - 线程run结束后，线程退出
- 粗粒度：子线程与子线程之间、和main线程之间缺乏交流
- 细粒度：线程之间有信息交流通讯
  - 通过共享变量达到信息共享
  - JDK原生库暂不支持发送消息
- 通过共享变量在多个线程中共享消息
  - static 变量
  - 同一个Runnable类的成员变量
- **多线程信息共享问题**
  - **工作缓存副本**
  - **关键步骤缺乏加锁限制**
- i++,并非原子性操作
  - 读取**主存i(正本)**到**工作缓存(副本)**中
  - 每个CPU执行**(副本)**i+1操作
  - CPU将结果写入到**缓存(副本)**中
  - 数据从**工作缓存(副本)**刷到**主存(正本)**中
- 变量副本问题的解决方法
  - **采用volatile关键字修饰变量**
  - 保证不同线程对共享变量操作时的可见性
  - volatile boolean flag = true//用volatile修饰的变量可以及时在各线程里面通知
- 关键步骤加锁限制
  - 互斥：某一个线程运行一个**代码段(关键区)**,其他线程不能同时运行这个代码段
  - 同步：多个线程的运行，必须按照某一规定的先后顺序来运行
  - 互斥是同步的一种特例
- 互斥的关键字是synchronized
  - **synchronized代码块/函数，只能一个线程进入**
  - synchronized加大性能负担，但是使用简便

### 第四节 Java多线程管理

#### 多线程管理

- 细粒度：线程之间有同步协作
  - 等待
  - 通知/唤醒
  - **终止**

- 线程状态
  - NEW刚创建(new)
  - RUNNABLE就绪态(start)
  - RUNNING运行中(run)
  - BLOCK阻塞(sleep)
  - TERMINATED结束
  - ![62354961715](D:\学习资料\java\Mooc笔记\第五章-2.png)

- Thread的部分API已经废弃
  - 暂停和恢复suspend/resume
  - 消亡stop/destroy

- 线程阻塞/和唤醒
  - sleep,时间一到，自己会醒来
  - wait/notify/notifyAll,等待，需要别人来唤醒
  - join,等待另外一个线程结束
  - interrupt,向另外一个线程发送中断信号，该线程收到信号，会触发InterruptedException(可解除阻塞)，并进行下一步处理

- 线程被动地暂停和终止
  - 依靠别的线程来拯救自己
  - 没有及时释放资源

- **线程主动暂停和终止**
  - 定期监测共享变量
  - 如果需要暂停或者终止，先释放资源，再主动动作
  - 暂停:Thread.sleep(),休眠
  - 终止:run方法结束，线程终止
  - interrupted()是Thread类的方法，用来测试当前线程是否收到一个INTERRUPT的信号。如果收到，该方法返回true，否则返回false

- 多线程死锁

  - 每个线程互相持有别人需要的锁(哲学家吃面问题)

  - **预防死锁，对资源进行等级排序**

  - ~~~java
    import java.util.concurrent.TimeUnit;

    public class ThreadDemo5
    {
    	public static Integer r1 = 1;
    	public static Integer r2 = 2;
    	public static void main(String args[]) throws InterruptedException
    	{
    		TestThread51 t1 = new TestThread51();
    		t1.start();
    		TestThread52 t2 = new TestThread52();
    		t2.start();
    	}
    }

    class TestThread51 extends Thread
    {
    	public void run() 
    	{
    		//先要r1,再要r2
    		synchronized(ThreadDemo5.r1)
    		{
    			try {
    				TimeUnit.SECONDS.sleep(3);
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    			synchronized(ThreadDemo5.r2)
    			{
    				System.out.println("TestThread51 is running");
    			}
    		}
    	}
    } 
    class TestThread52 extends Thread
    {
    	public void run() 
    	{
    		//先要r2,再要r1
    		synchronized(ThreadDemo5.r1)
    		{
    			try {
    				TimeUnit.SECONDS.sleep(3);
    			} catch (InterruptedException e) {
    				e.printStackTrace();
    			}
    			synchronized(ThreadDemo5.r2)
    			{
    				System.out.println("TestThread52 is running");
    			}
    		}
    	}
    } 
    ~~~

- 守护(后台)线程

  - 普通线程的结束，是run方法运行结束

  - 守护线程的结束，是run方法运行结束，或main函数结束

  - **守护线程永远不要访问资源，如文件或数据库等**

    ~~~java
    public class ThreadDemo4
    {
    	public static void main(String args[]) throws InterruptedException
    	{
    		TestThread4 t = new TestThread4();
    		t.setDaemon(true);
    		t.start();
    		Thread.sleep(2000);
    		System.out.println("main thread is exiting");
    	}
    }
     class TestThread4 extends Thread
    {
    	public void run() 
    	{
    		while(true)
    		{
    			System.out.println("TestThread4" + 
    			"　is running");
    			try {
    				Thread.sleep(1000);
    			} catch (InterruptedException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		}
    	}
    } 
    ~~~

    ​

- 线程查看工具jvisualvm

### 第五节 Java并发框架Executor

#### 并行计算

- 业务：人物多，数据量大
- 串行vs并行
  - 串行编程简单，并行编程困难
  - 单个计算核频率下降，计算核数增多，整体性能变高
- 并行困难(任务分配和执行过程**高度耦合**)
  - 如何控制粒度，切割任务
  - 如何分配任务给线程，监督线程执行过程
- 并行模式
  - 主从模式(Master-Slave)
  - Worker模式(Worker-Worker)(p to p)
- Java并发编程
  - Thread/Runnable/Thread组管理
  - Executor
  - Fork-Join框架

#### 线程组管理

- 线程组ThreadGroup
  -  线程的集合
  - 树形结构，大线程组可以包括小线程组
  - 可以通过enumerate方法遍历组内的线程，执行操作
  - 能够有效管理多个线程，但是**管理效率低**
  - 任务分配和执行过程**高度耦合**
  - 重复创建线程、关闭操作，**无法重用线程**
  - 线程和线程组内的线程，都是new产生出来，但是start一次以后，就不能再次使用(即再次start)。new的代价很昂贵，只运行一次，性价比过低。
  - activeCount,返回线程组中还处于active的线程数(估计数)
  - emumerate,将线程组中active的线程拷贝到数组中
  - interrupt,对线程组中所有的线程发出interrupt信号
  - list，打印线程组中所有的线程信息

### Executor

- 从JDK5开始提供Executor FrameWork(java.util.concurrent.*)

  - 分离任务的创建和执行者的创建
  - 线程重复利用(**new线程代价很大**)

- 理解共享线程池的概念

  - 预设好的多个Thread，可弹性增加
  - 多次执行很多很小的任务
  - 任务创建和执行过程解耦
  - **程序员无需关心线程池执行任务过程**

- 主要类：ExecutorService,ThreadPoolExecutor,Future

  - Executors.newCachedThreadPool/newFixedThreadPool创建线程池
  - ExecutorService线程池服务
  - Callable 具体的逻辑对象(线程类)//Callable和Runnable是等价的，可以用来执行一个任务。Runnable的run方法没有返回值，而Callable的call方法可以有返回值
  - Future返回结果

  ~~~java
  import java.util.ArrayList;
  import java.util.List;
  import java.util.concurrent.ExecutionException;
  import java.util.concurrent.Executors;
  import java.util.concurrent.Future;
  import java.util.concurrent.ThreadPoolExecutor;
  //Test类
  public class SumTest {

  	public static void main(String[] args) {
  		
  		// 执行线程池
  		ThreadPoolExecutor executor=(ThreadPoolExecutor)Executors.newFixedThreadPool(4);
  		
  		List<Future<Integer>> resultList=new ArrayList<>();

  		//统计1-1000总和，分成10个任务计算，提交任务
  		for (int i=0; i<10; i++){
  			SumTask calculator=new SumTask(i*100+1, (i+1)*100);
  			Future<Integer> result=executor.submit(calculator);
  			resultList.add(result);
  		}
  		
  		// 每隔50毫秒，轮询等待10个任务结束
  		do {
  			System.out.printf("Main: 已经完成多少个任务: %d\n",executor.getCompletedTaskCount());
  			for (int i=0; i<resultList.size(); i++) {
  				Future<Integer> result=resultList.get(i);
  				System.out.printf("Main: Task %d: %s\n",i,result.isDone());
  			}
  			try {
  				Thread.sleep(50);
  			} catch (InterruptedException e) {
  				e.printStackTrace();
  			}
  		} while (executor.getCompletedTaskCount()<resultList.size());
  		
  		// 所有任务都已经结束了，综合计算结果		
  		int total = 0;
  		for (int i=0; i<resultList.size(); i++) {
  			Future<Integer> result=resultList.get(i);
  			Integer sum=null;
  			try {
  				sum=result.get();
  				total = total + sum;
  			} catch (InterruptedException e) {
  				e.printStackTrace();
  			} catch (ExecutionException e) {
  				e.printStackTrace();
  			}
  		}
  		System.out.printf("1-1000的总和:" + total);
  		
  		// 关闭线程池
  		executor.shutdown();
  	}
  }
  ~~~

  ~~~java
  import java.util.Random;
  import java.util.concurrent.Callable;

  public class SumTask implements Callable<Integer> {
  	//定义每个线程计算的区间
  	private int startNumber;
  	private int endNumber;
  	
  	public SumTask(int startNumber, int endNumber){
  		this.startNumber=startNumber;
  		this.endNumber=endNumber;
  	}
  	
  	@Override
  	public Integer call() throws Exception {
  		int sum = 0;
  		for(int i=startNumber; i<=endNumber; i++)
  		{
  			sum = sum + i;
  		}
  		
  		Thread.sleep(new Random().nextInt(1000));
  		
  		System.out.printf("%s: %d\n",Thread.currentThread().getName(),sum);
  		return sum;
  	}
  }
  ~~~

### 第六节 Java并发框架Fork-Join

#### Fork-Join

- Java 7 提供另一种并行框架：分解、治理、合并(**分治编程**)
- 适用用于整体任务量不好确定的场合(**最小任务可确定**)

![62356910735](D:\学习资料\java\Mooc笔记\第五章第六节.png)

- 关键类
  - ForkJoinPool任务池
  - RecursiveAction
  - RecursiveTask

~~~java
import java.math.BigInteger;
import java.util.concurrent.RecursiveTask;

//分任务求和 TASK
public class SumTask extends RecursiveTask<Long> {
	
	private int start;
	private int end;

	public SumTask(int start, int end) {
		this.start = start;
		this.end = end;
	}

	public static final int threadhold = 5;

	@Override
	protected Long compute() {
		Long sum = 0L;
		
		// 如果任务足够小, 就直接执行
		boolean canCompute = (end - start) <= threadhold;
		if (canCompute) {
			for (int i = start; i <= end; i++) {
				sum = sum + i;				
			}
		} else {
			// 任务大于阈值, 分裂为2个任务
			int middle = (start + end) / 2;
			SumTask subTask1 = new SumTask(start, middle);
			SumTask subTask2 = new SumTask(middle + 1, end);

			invokeAll(subTask1, subTask2);

			Long sum1 = subTask1.join();
			Long sum2 = subTask2.join();

			// 结果合并
			sum = sum1 + sum2;
		}
		return sum;
	}
}
~~~

~~~java
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

//分任务求和 Test
public class SumTest {
    
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //创建执行线程池
    	ForkJoinPool pool = new ForkJoinPool();
    	//ForkJoinPool pool = new ForkJoinPool(4);
    	
    	//创建任务
        SumTask task = new SumTask(1, 10000000);
        
        //提交任务
        ForkJoinTask<Long> result = pool.submit(task);
        
        //等待结果
        do {
			System.out.printf("Main: Thread Count: %d\n",pool.getActiveThreadCount());
			System.out.printf("Main: Paralelism: %d\n",pool.getParallelism());
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (!task.isDone());
        
        //输出结果
        System.out.println(result.get().toString());
    }
}
~~~

### 第七节 Java并发数据结构

- 常用的数据结构是线程不安全的

  - ArrayList,HashMap,HashSet非同步的
  - 多个线程同时读写，可能会抛出异常或数据错误

- 传统Vector，Hashtable等同步集合性能过差

- 并发数据结构：数据添加和删除

  - 阻塞式集合：当集合为空或者满时，等待
  - 非阻塞式集合：当集合为空或者满时，不等待，返回null或异常

- List

  - Vector 同步安全，写多读少
  - ArrayList 不安全
  - Collections.synchronizedList(List list)基于synchronized,效率差
  - CopyOnWriteArrayList 读多写少，基于复制机制，非阻塞

  ~~~java
  import java.util.ArrayList;
  import java.util.Collections;
  import java.util.List;
  import java.util.concurrent.CopyOnWriteArrayList;

  public class ListTest {    
   
      public static void main(String[] args) throws InterruptedException{

          //线程不安全
          List<String> unsafeList = new ArrayList<String>();
          //线程安全
          List<String> safeList1 = Collections.synchronizedList(new ArrayList<String>());
          //线程安全
          CopyOnWriteArrayList<String> safeList2 = new CopyOnWriteArrayList<String>();

          ListThread t1 = new ListThread(unsafeList);
          ListThread t2 = new ListThread(safeList1);
          ListThread t3 = new ListThread(safeList2);

          for(int i = 0; i < 10; i++){
              Thread t = new Thread(t1, String.valueOf(i));
              t.start();
          }
          for(int i = 0; i < 10; i++) {
              Thread t = new Thread(t2, String.valueOf(i));
              t.start();
          }
          for(int i = 0; i < 10; i++) {
              Thread t = new Thread(t3, String.valueOf(i));
              t.start();
          }

          //等待子线程执行完
          Thread.sleep(2000);
   
          System.out.println("listThread1.list.size() = " + t1.list.size());
          System.out.println("listThread2.list.size() = " + t2.list.size());
          System.out.println("listThread3.list.size() = " + t3.list.size());

          //输出list中的值
          System.out.println("unsafeList：");
          for(String s : t1.list){
              if(s == null){
              	System.out.print("null  ");
              }
              else
              {
              	System.out.print(s + "  ");
              }
          }
          System.out.println();
          System.out.println("safeList1：");
          for(String s : t2.list){
          	if(s == null){
              	System.out.print("null  ");
              }
              else
              {
              	System.out.print(s + "  ");
              }
          }
          System.out.println();
          System.out.println("safeList2：");
          for(String s : t3.list){
          	if(s == null){
              	System.out.print("null  ");
              }
              else
              {
              	System.out.print(s + "  ");
              }
          }
      }
  }
  class ListThread implements Runnable{
  	public List<String> list;

      public ListThread(List<String> list){
          this.list = list;
      }
      @Override
      public void run() {
      	int i = 0;
      	while(i<10)
      	{
      		try {
                  Thread.sleep(10);
              }catch (InterruptedException e){
                  e.printStackTrace();
              }
              //把当前线程名称加入list中
              list.add(Thread.currentThread().getName());
              i++;
      	}        
      }
  }
  ~~~

  ​

- Set

  - HashSet 不安全

  - Collections.synchronizedSer(Set set)基于synchronized,效率差

  - CopyOnWriteArraySet(基于CopyOnWriteArrayList实现)**读多写少**，非阻塞

    ~~~java
    import java.util.*;
    import java.util.concurrent.CopyOnWriteArraySet;

    public class SetTest{  
     
        public static void main(String[] args) throws InterruptedException{

            //线程不安全
            Set<String> unsafeSet = new HashSet<String>();
            //线程安全
            Set<String> safeSet1 = Collections.synchronizedSet(new HashSet<String>());
            //线程安全
            CopyOnWriteArraySet<String> safeSet2 = new CopyOnWriteArraySet<String>();

            SetThread t1 = new SetThread(unsafeSet);
            SetThread t2 = new SetThread(safeSet1);
            SetThread t3 = new SetThread(safeSet2);

            //unsafeSet的运行测试
            for(int i = 0; i < 10; i++){
            	Thread t = new Thread(t1, String.valueOf(i));
            	t.start();
            }
            for(int i = 0; i < 10; i++) {
            	Thread t = new Thread(t2, String.valueOf(i));
                t.start();
            }
            for(int i = 0; i < 10; i++) {
            	Thread t = new Thread(t3, String.valueOf(i));
                t.start();
            }

            //等待子线程执行完
            Thread.sleep(2000);
     
            System.out.println("setThread1.set.size() = " + t1.set.size());
            System.out.println("setThread2.set.size() = " + t2.set.size());
            System.out.println("setThread3.set.size() = " + t3.set.size());

            //输出set中的值
            System.out.println("unsafeSet：");
            for(String element:t1.set){
                if(element == null){
                	System.out.print("null  ");
                }
                else
                {
                	System.out.print(element + "  ");
                }
            }
            System.out.println();
            System.out.println("safeSet1：");
            for(String element:t2.set){
            	if(element == null){
                	System.out.print("null  ");
                }
                else
                {
                	System.out.print(element + "  ");
                }
            }
            System.out.println();
            System.out.println("safeSet2：");
            for(String element:t3.set){
            	if(element == null){
                	System.out.print("null  ");
                }
                else
                {
                	System.out.print(element + "  ");
                }
            }
        }
    }

    class SetThread implements Runnable{
    	public Set<String> set;

        public SetThread(Set<String> set){
            this.set = set;
        }

        @Override
        public void run() {
        	int i = 0;
        	while(i<10)
        	{
        		i++;
        		try {
                    Thread.sleep(10);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                //把当前线程名称加入list中
                set.add(Thread.currentThread().getName() + i);
        	}        
        }
    }
    ~~~

    ​

- Map

  - Hashtable同步安全，写多读少
  - HashMap不安全
  - Collection.synchronizedMap(Map map)基于synchronized,效率差
  - ConcurrentHashMap 读多写少，非阻塞

- Que&Deque(**队列，JDK1.5提出**)

  - ConcurrentLinkedQueue 非阻塞
  - ArrayBlockingQueue/LinkedBlockingQueue 阻塞

### 第八节 Java并发协作控制

#### 线程协作

- Thread/Executor/Fork-Join
  - 线程启动，运行，结束
  - 线程之间缺少协作
- synchronized 同步
  - 限定只有一个线程才能进入关键区
  - 简单粗暴，**性能损失有点大**

#### Lock

- Lock也可以实现同步的效果
  - 实现更复杂的临界区结构
  - tryLock方法可以预判锁是否空闲
  - 允许分离读写的操作，多个读，一个写
  - 性能更好
- ReentrantLock类，可重入的互斥锁
- ReentrantReadWriteLock类，可重入的读写锁
- lock和unlock函数


~~~java
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LockExample {

	private static final ReentrantLock queueLock = new ReentrantLock(); //可重入锁
	private static final ReentrantReadWriteLock orderLock = new ReentrantReadWriteLock(); //可重入读写锁
	
	/**
	 * 有家奶茶店，点单有时需要排队 
	 * 假设想买奶茶的人如果看到需要排队，就决定不买
	 * 又假设奶茶店有老板和多名员工，记单方式比较原始，只有一个订单本
	 * 老板负责写新订单，员工不断地查看订单本得到信息来制作奶茶，在老板写新订单时员工不能看订单本
	 * 多个员工可同时看订单本，在员工看时老板不能写新订单
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		//buyMilkTea();
		handleOrder(); //需手动关闭
	}
	
	public void tryToBuyMilkTea() throws InterruptedException {
		boolean flag = true;
		while(flag)
		{
			if (queueLock.tryLock()) {
				//queueLock.lock();
				long thinkingTime = (long) (Math.random() * 500);
				Thread.sleep(thinkingTime);
				System.out.println(Thread.currentThread().getName() + "： 来一杯珍珠奶茶，不要珍珠");
				flag = false;
				queueLock.unlock();
			} else {
				//System.out.println(Thread.currentThread().getName() + "：" + queueLock.getQueueLength() + "人在排队");
				System.out.println(Thread.currentThread().getName() + "： 再等等");
			}
			if(flag)
			{
				Thread.sleep(1000);
			}
		}
		
	}
	
	public void addOrder() throws InterruptedException {
		orderLock.writeLock().lock();
		long writingTime = (long) (Math.random() * 1000);
		Thread.sleep(writingTime);
		System.out.println("老板新加一笔订单");
		orderLock.writeLock().unlock();
	}
	
	public void viewOrder() throws InterruptedException {
		orderLock.readLock().lock();
			
		long readingTime = (long) (Math.random() * 500);
		Thread.sleep(readingTime);
		System.out.println(Thread.currentThread().getName() + ": 查看订单本");
		orderLock.readLock().unlock();			

	}
	
	public static void buyMilkTea() throws InterruptedException {
		LockExample lockExample = new LockExample();
		int STUDENTS_CNT = 10;
		
		Thread[] students = new Thread[STUDENTS_CNT];
		for (int i = 0; i < STUDENTS_CNT; i++) {
			students[i] = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						long walkingTime = (long) (Math.random() * 1000);
						Thread.sleep(walkingTime);
						lockExample.tryToBuyMilkTea();
					} catch(InterruptedException e) {
						System.out.println(e.getMessage());
					}
				}
				
			}
			);
			
			students[i].start();
		}
		
		for (int i = 0; i < STUDENTS_CNT; i++)
			students[i].join();

	}
	
	
	public static void handleOrder() throws InterruptedException {
		LockExample lockExample = new LockExample();
		
		
		Thread boss = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						lockExample.addOrder();
						long waitingTime = (long) (Math.random() * 1000);
						Thread.sleep(waitingTime);
					} catch (InterruptedException e) {
						System.out.println(e.getMessage());
					}
				}
			}
		});
		boss.start();

		int workerCnt = 3;
		Thread[] workers = new Thread[workerCnt];
		for (int i = 0; i < workerCnt; i++)
		{
			workers[i] = new Thread(new Runnable() {

				@Override
				public void run() {
					while (true) {
						try {
								lockExample.viewOrder();
								long workingTime = (long) (Math.random() * 5000);
								Thread.sleep(workingTime);
							} catch (InterruptedException e) {
								System.out.println(e.getMessage());
							}
						}
				}
				
			});
			
			workers[i].start();
		}
		
	}
}
~~~



#### Semaphore

- 信号量：本质上是一个计数器
- 计数器大于0，可以使用，等于0不能使用
- 可以设置多个并发量，例如限制10个访问
- Semaphore
  - acquire获取
  - release释放
- 比Lock更进一步，可以控制多个同时访问关键区

~~~java
import java.util.concurrent.Semaphore;

public class SemaphoreExample {

	private final Semaphore placeSemaphore = new Semaphore(5);
	
	public boolean parking() throws InterruptedException {
		if (placeSemaphore.tryAcquire()) {
			System.out.println(Thread.currentThread().getName() + ": 停车成功");
			return true;
		} else {
			System.out.println(Thread.currentThread().getName() + ": 没有空位");
			return false;
		}

	}
	
	public void leaving() throws InterruptedException {
		placeSemaphore.release();
		System.out.println(Thread.currentThread().getName() + ": 开走");
	}
	
	/**
	 * 现有一地下车库，共有车位5个，由10辆车需要停放，每次停放时，去申请信号量
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		int tryToParkCnt = 10;
		
		SemaphoreExample semaphoreExample = new SemaphoreExample();
		
		Thread[] parkers = new Thread[tryToParkCnt];
		
		for (int i = 0; i < tryToParkCnt; i++) {
			parkers[i] = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						long randomTime = (long) (Math.random() * 1000);
						Thread.sleep(randomTime);
						if (semaphoreExample.parking()) {
							long parkingTime = (long) (Math.random() * 1200);
							Thread.sleep(parkingTime);
							semaphoreExample.leaving();
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
			
			parkers[i].start();
		}

		for (int i = 0; i < tryToParkCnt; i++) {
			parkers[i].join();
		}	
	}
}
~~~



#### Latch

- 等待锁，是一个同步辅助类
- 用来同步执行任务的一个或多个线程
- 不是用来保护临界区或者共享资源
- CountDownLatch
  - countDown()计数减1
  - await() 等待latch变成0

~~~java
import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {

	/**
	 * 设想百米赛跑比赛 发令枪发出信号后选手开始跑，全部选手跑到终点后比赛结束
	 * 
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		int runnerCnt = 10;
		CountDownLatch startSignal = new CountDownLatch(1);
		CountDownLatch doneSignal = new CountDownLatch(runnerCnt);

		for (int i = 0; i < runnerCnt; ++i) // create and start threads
			new Thread(new Worker(startSignal, doneSignal)).start();

		System.out.println("准备工作...");
		System.out.println("准备工作就绪");
		startSignal.countDown(); // let all threads proceed
		System.out.println("比赛开始");
		doneSignal.await(); // wait for all to finish
		System.out.println("比赛结束");
	}

	static class Worker implements Runnable {
		private final CountDownLatch startSignal;
		private final CountDownLatch doneSignal;

		Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
			this.startSignal = startSignal;
			this.doneSignal = doneSignal;
		}

		public void run() {
			try {
				startSignal.await();
				doWork();
				doneSignal.countDown();
			} catch (InterruptedException ex) {
			} // return;
		}

		void doWork() {
			System.out.println(Thread.currentThread().getName() + ": 跑完全程");
		}
	}
}
~~~



#### Barrier

- 集合点，也是一个同步辅助类
- 允许多个线程在某一个点上进行同步
- CyclicBarrier
  - 构造函数是需要同步的线程数量
  - await等待其他线程，到达数量后，就放行
  - 当在Barrier上await的线程数量达到预定的要求后，所有的await的线程不再等待，全部解锁。并且，Barrier将执行预定的回调动作

~~~java
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample {
	
	/**
	 * 假定有三行数，用三个线程分别计算每一行的和，最终计算总和
	 * @param args
	 */
	public static void main(String[] args) {
		final int[][] numbers = new int[3][5];
		final int[] results = new int[3];
		int[] row1 = new int[]{1, 2, 3, 4, 5};
		int[] row2 = new int[]{6, 7, 8, 9, 10};
		int[] row3 = new int[]{11, 12, 13, 14, 15};
		numbers[0] = row1;
		numbers[1] = row2;
		numbers[2] = row3;
		
		CalculateFinalResult finalResultCalculator = new CalculateFinalResult(results);
		CyclicBarrier barrier = new CyclicBarrier(3, finalResultCalculator);
		//当有3个线程在barrier上await，就执行finalResultCalculator
		
		for(int i = 0; i < 3; i++) {
			CalculateEachRow rowCalculator = new CalculateEachRow(barrier, numbers, i, results);
			new Thread(rowCalculator).start();
		}		
	}
}

class CalculateEachRow implements Runnable {

	final int[][] numbers;
	final int rowNumber;
	final int[] res;
	final CyclicBarrier barrier;
	
	CalculateEachRow(CyclicBarrier barrier, int[][] numbers, int rowNumber, int[] res) {
		this.barrier = barrier;
		this.numbers = numbers;
		this.rowNumber = rowNumber;
		this.res = res;
	}
	
	@Override
	public void run() {
		int[] row = numbers[rowNumber];
		int sum = 0;
		for (int data : row) {
			sum += data;
			res[rowNumber] = sum;
		}
		try {
			System.out.println(Thread.currentThread().getName() + ": 计算第" + (rowNumber + 1) + "行结束，结果为: " + sum);
			barrier.await(); //等待！只要超过3个(Barrier的构造参数)，就放行。
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
	}
	
}


class CalculateFinalResult implements Runnable {
	final int[] eachRowRes;
	int finalRes;
	public int getFinalResult() {
		return finalRes;
	}
	
	CalculateFinalResult(int[] eachRowRes) {
		this.eachRowRes = eachRowRes;
	}
	
	@Override
	public void run() {
		int sum = 0;
		for(int data : eachRowRes) {
			sum += data;
		}
		finalRes = sum;
		System.out.println("最终结果为: " + finalRes);
	}
	
}
~~~



#### Phaser

- 允许执行并发多阶段任务，同步辅助类
- 在每一个阶段结束的位置对线程进行同步，当所有的线程都到达这步，再进行下一步
- Phaser
  - arrive()
  - arriveAndAwaitAdvance()

~~~java
import java.util.concurrent.Phaser;

public class PhaserExample {

	/**
	 * 假设举行考试，总共三道大题，每次下发一道题目，等所有学生完成后再进行下一道
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		int studentsCnt = 5;
		Phaser phaser = new Phaser(studentsCnt);

		for (int i = 0; i < studentsCnt; i++) {
			new Thread(new Student(phaser)).start();
		}
	}
}

class Student implements Runnable {

	private final Phaser phaser;

	public Student(Phaser phaser) {
		this.phaser = phaser;
	}

	@Override
	public void run() {
		try {
			doTesting(1);
			phaser.arriveAndAwaitAdvance(); //等到5个线程都到了，才放行
			doTesting(2);
			phaser.arriveAndAwaitAdvance();
			doTesting(3);
			phaser.arriveAndAwaitAdvance();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void doTesting(int i) throws InterruptedException {
		String name = Thread.currentThread().getName();
		System.out.println(name + "开始答第" + i + "题");
		long thinkingTime = (long) (Math.random() * 1000);
		Thread.sleep(thinkingTime);
		System.out.println(name + "第" + i + "道题答题结束");
	}
}
~~~



#### Exchanger

- 允许在并发线程中互相交换消息
- 允许在2个线程中定义同步点，当两个线程都到达同步点，他们交换数据结构
- Exchanger
  - exchange(),线程双方互相交换数据
  - 交换数据是双向的

~~~java
import java.util.Scanner;
import java.util.concurrent.Exchanger;

public class ExchangerExample {
	
	/**
	 * 本例通过Exchanger实现学生成绩查询，简单线程间数据的交换
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		Exchanger<String> exchanger = new Exchanger<String>();
		BackgroundWorker worker = new BackgroundWorker(exchanger);
		new Thread(worker).start();
		
		Scanner scanner = new Scanner(System.in);
		while(true) {
			System.out.println("输入要查询的属性学生姓名：");
			String input = scanner.nextLine().trim();
			exchanger.exchange(input); //把用户输入传递给线程
			String value = exchanger.exchange(null); //拿到线程反馈结果
			if ("exit".equals(value)) {
				break;
			}
			System.out.println("查询结果：" + value);
		}
		scanner.close();
	} 
}

class BackgroundWorker implements Runnable {

	final Exchanger<String> exchanger;
	BackgroundWorker(Exchanger<String> exchanger) {
		this.exchanger = exchanger;
	}
	@Override
	public void run() {
		while (true) {
			try {
				String item = exchanger.exchange(null);
				switch (item) {
				case "zhangsan": 
					exchanger.exchange("90");
					break;
				case "lisi":
					exchanger.exchange("80");
					break;
				case "wangwu":
					exchanger.exchange("70");
					break;
				case "exit":
					exchanger.exchange("exit");
					return;
				default:
					exchanger.exchange("查无此人");
				}					
			} catch (InterruptedException e) {
				e.printStackTrace();
			}				
		}
	}		
}
~~~

### 第九节 Java定时任务执行

#### 定时任务

- Thread/Executor/Fork-Join多线程
  - 立即执行
  - 框架调度
- 定时执行
  - 以固定某一个时间点运行
  - 以某一个周期
- 简单定时器机制
- Executor+定时器机制
- ScheduledExecutorService
  - 定时任务
  - 周期任务
- Quartz(第三方包)
  - Quartz是一个较为完善的任务调动框架
  - 解决程序中Timer零散管理的问题
  - 功能更加强大
    - Timer执行周期任务，如果中间某一次有异常，整个任务终止执行
    - Quartz执行周期任务，如果中间某一次有异常，不影响下次任务执行