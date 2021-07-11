package com.xxx;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try{
            Socket s = new Socket(InetAddress.getByName("127.0.0.1"),8008);
            InputStream ips = s.getInputStream();
            BufferedReader brNet = new BufferedReader(new InputStreamReader(ips));
            OutputStream ops = s.getOutputStream();
            DataOutputStream dos = new DataOutputStream(ops);
            String msg = "778";
            while (true){
                Thread.sleep(10000);
                dos.writeBytes(msg+System.getProperty("line.separator"));
                System.out.println("Server said:"+brNet.readLine());
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
