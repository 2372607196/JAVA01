package com.xxx;

import java.io.*;
import java.net.DatagramPacket;
import java.net.Socket;
import java.util.List;

class Worker implements Runnable{
    Socket s;
    List<Socket>  socketList;
    int num;

    public Worker(Socket s,List<Socket> socketList,int num){
        this.s=s;
        this.socketList=socketList;
        this.num=num;
    }

    @Override
    public void run(){
        try{
            System.out.println("工作启动");
            InputStream ips = s.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(ips));

            while (true){
                String strWord = br.readLine();
                System.out.println("No."+num+"client said"+strWord+":"+strWord.length());
                if(strWord.equals("quit")){
                    break;
                }
                if(strWord!=null){
                    for(int i = 0; i<socketList.size();i++){
                        Socket sc = socketList.get(i);
                        OutputStream ops = sc.getOutputStream();
                        DataOutputStream dos = new DataOutputStream(ops);
                        //向所有客户端发送信息
                        dos.writeBytes("No."+num+"client said"+strWord+"---->"+System.getProperty("line.separator"));
                    }
                }
            }
            br.close();
            s.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
