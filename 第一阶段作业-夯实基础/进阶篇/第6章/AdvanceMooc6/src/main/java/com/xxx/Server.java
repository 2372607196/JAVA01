package com.xxx;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Hello world!
 *
 */
public class Server
{
    public static void main( String[] args )
    {
        //线程池
        ThreadPoolExecutor executor=(ThreadPoolExecutor) Executors.newFixedThreadPool(4);
        List<Socket> socketList = new ArrayList<>();
        int num = 0;
        try{
            ServerSocket ss = new ServerSocket(8008);
            while (true){
                Socket s = ss.accept();
                socketList.add(s);
                System.out.println("接收到一个客户端");
                executor.execute(new Worker(s,socketList,num));
                num++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
