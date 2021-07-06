## 第六章 Java网络编程

### 第一章 网络基础知识

#### 网络基础知识

- IP地址:每个网卡/机器都有一个或多个IP地址
  - IPV4:192.168.0.100,每段从0到255
  - IPV6:128bit长，分成8段，每段4个16进制数
  - 查询:Windows平台ipconfig,Linux/Max平台ifconfig
- port:端口，0-65535
  - 0~1023，OS已经占用了，80是Web，23是telnet
  - 1024~65535，一般程序可使用
- 两台机器通讯就是在IP+Port上进行的
- 在Windows/Linux/Mac上都可以通过netstat -an来查询
- 保留ip:127.0.0.1 本机
- 公网(万维网/互联网)和内网(局域网)
  - 网络是分层的
  - 最外层是公网/互联网
  - 底下的每层都是内网
  - ip地址可以在每个层次的网重用
  - tracert看当前机器和目标机器的访问中继
- 通讯协议：TCP和UDP
- TCP(Transmission Control Protocol)
  - 传输控制协议，面向**连接**的协议
  - 两台机器的**可靠无差错**的数据传输
  - **双向**字节流传递
- UDP(User Datagram Protocol)
  - 用户数据报协议，面向**无连接**协议
  - **不保证可靠**的数据传输
  - 速度快，也可以在较差网络下使用

![62365434485](D:\学习资料\java\Mooc笔记\高级第六章第一节.png)

### 第二节 Java UDP编程

#### UDP

- 计算机通讯:数据从一个IP的port出发(发送方),运输到另外一个IP的port(接收方)
- UDP：无连接无状态的通讯协议
  - 发送方发送消息，如果接收方刚好在目的地，则可以接受。如果不在，那这个消息就丢失了
  - 发送方也无法得知是否发送成功
  - UDP的好处就是简单，节省，经济
- DatagramSocket：通讯的数据管道
  - send和receive方法
  - (可选，多网卡)绑定一个IP和Port
- DatagramPacket
  - 集装箱:封装数据
  - 地址标签:目的地IP和Port
- 实例
  - 无主次之分
  - 接收方必须早于发起方执行

~~~java
import java.net.*;
public class UdpRecv
{
	public static void main(String[] args) throws Exception
	{
		DatagramSocket	ds=new DatagramSocket(3000);
		byte [] buf=new byte[1024];
		DatagramPacket dp=new DatagramPacket(buf,1024);
		
		System.out.println("UdpRecv: 我在等待信息");
		ds.receive(dp);
		System.out.println("UdpRecv: 我接收到信息");
		String strRecv=new String(dp.getData(),0,dp.getLength()) +
		" from " + dp.getAddress().getHostAddress()+":"+dp.getPort(); 
		System.out.println(strRecv);
		
		Thread.sleep(1000);
		System.out.println("UdpRecv: 我要发送信息");
		String str="hello world 222";
		DatagramPacket dp2=new DatagramPacket(str.getBytes(),str.length(), 
				InetAddress.getByName("127.0.0.1"),dp.getPort());
		ds.send(dp2);
		System.out.println("UdpRecv: 我发送信息结束");
		ds.close();
	}
}
~~~

~~~java
import java.net.*;
public class UdpSend
{
	public static void main(String [] args) throws Exception
	{
		DatagramSocket ds=new DatagramSocket();
		String str="hello world";
		DatagramPacket dp=new DatagramPacket(str.getBytes(),str.length(),
				InetAddress.getByName("127.0.0.1"),3000);
		
		System.out.println("UdpSend: 我要发送信息");
		ds.send(dp);
		System.out.println("UdpSend: 我发送信息结束");
		
		Thread.sleep(1000);
		byte [] buf=new byte[1024];
		DatagramPacket dp2=new DatagramPacket(buf,1024);
		System.out.println("UdpSend: 我在等待信息");
		ds.receive(dp2);
		System.out.println("UdpSend: 我接收到信息");
		String str2=new String(dp2.getData(),0,dp2.getLength()) +
				" from " + dp2.getAddress().getHostAddress()+":"+dp2.getPort(); 
		System.out.println(str2);
				
		ds.close();
	}
}
~~~

### 第三节 Java TCP编程

#### TCP

- TCP协议：有链接、保证可靠的无误差通讯

  - 服务器：创建一个ServerSocket，等待连接

    //软件服务器有两个要求：

    1）它能够实现一定的功能

    2）它必须在一个公开地址上对外提供服务

  - 客户机：创建一个Socket，连接到服务器

  - 服务器：ServerSocket接收到连接，创建一个Socket和客户的Socket建立专线连接，后续服务器和客户机的对话(这一对Socket)会在一个单独的线程(服务器端)上运行

  - 服务器的ServerSocket继续等待连接，返回第一步

![62365590601](D:\学习资料\java\Mooc笔记\进阶第六章第三节.png)

- ServerSocket:服务器码头

  - 需要绑定port
  - 如果有多块网卡，需要绑定一个IP地址

- Socket：运输通道

  - 客户端需要绑定服务器的地址和Port
  - 客户端往Socket输入流写入数据，送到服务端
  - 客户端从Socket输出流取服务器端送多来的数据
  - 服务端反之亦然

- 服务端等待响应时，处于阻塞状态

- 服务端可以同时响应多个客户端

- 服务端每接受一个客户端，就启动一个独立的线程与之对应

- 客户端或者服务端都可以选择关闭这对Socket的通道

- 实例

  - 服务端先启动，且一直保留

  - 客户端后启动，可以先退出

    ~~~java
    import java.net.*;
    import java.io.*;
    //包装类
    class Worker implements Runnable {
    	Socket s;

    	public Worker(Socket s) {
    		this.s = s;
    	}

    	public void run() {
    		try {
    			System.out.println("服务人员已经启动");
    			InputStream ips = s.getInputStream();
    			OutputStream ops = s.getOutputStream();

    			BufferedReader br = new BufferedReader(new InputStreamReader(ips));
    			DataOutputStream dos = new DataOutputStream(ops);
    			while (true) {
    				String strWord = br.readLine();
    				System.out.println("client said:" + strWord +":" + strWord.length());
    				if (strWord.equalsIgnoreCase("quit"))
    					break;
    				String strEcho = strWord + " 666";
    				// dos.writeBytes(strWord +"---->"+ strEcho +"\r\n");
    				System.out.println("server said:" + strWord + "---->" + strEcho);
    				dos.writeBytes(strWord + "---->" + strEcho + System.getProperty("line.separator"));
    			}
    			br.close();
    			// 关闭包装类，会自动关闭包装类中所包装的底层类。所以不用调用ips.close()
    			dos.close();
    			s.close();
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    }
    ~~~

    ~~~java
    import java.net.*;
    public class TcpServer2
    {
    	public static void main(String [] args)
    	{
    		try
    		{
    			ServerSocket ss=new ServerSocket(8001);
    			while(true)
    			{
    				Socket s=ss.accept();
    				System.out.println("来了一个client");
    				new Thread(new Worker(s)).start();
    			}
    			//ss.close();
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
    	}
    }
    ~~~

    ~~~java
    import java.net.*;
    import java.io.*;

    public class TcpClient {
    	public static void main(String[] args) {
    		try {
    			Socket s = new Socket(InetAddress.getByName("127.0.0.1"), 8001); //需要服务端先开启
    			
    			//同一个通道，服务端的输出流就是客户端的输入流；服务端的输入流就是客户端的输出流
    			InputStream ips = s.getInputStream();    //开启通道的输入流
    			BufferedReader brNet = new BufferedReader(new InputStreamReader(ips));
    			
    			OutputStream ops = s.getOutputStream();  //开启通道的输出流
    			DataOutputStream dos = new DataOutputStream(ops);			

    			BufferedReader brKey = new BufferedReader(new InputStreamReader(System.in));
    			while (true) 
    			{
    				String strWord = brKey.readLine();
    				if (strWord.equalsIgnoreCase("quit"))
    				{
    					break;
    				}
    				else
    				{
    					System.out.println("I want to send: " + strWord);
    					dos.writeBytes(strWord + System.getProperty("line.separator"));
    					
    					System.out.println("Server said: " + brNet.readLine());
    				}
    				
    			}
    			
    			dos.close();
    			brNet.close();
    			brKey.close();
    			s.close();
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}
    }
    ~~~

    ​

### 第四节 Java HTTP编程