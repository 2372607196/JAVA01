package com.xxx;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class WarehouseImple extends UnicastRemoteObject implements WarHouse{
    String cmd1 = "javac HelloWorld";
    String cmd2 = "java HelloWorld";
    protected WarehouseImple() throws RemoteException{
        super();
    }

    @Override
    public List<String> cmdExec(String cmd) throws RemoteException{
        List<String> result = new ArrayList<String>();
        if(cmd1.equals(cmd)){
            Process p ;
            String [] cmds=new String[2];
            cmds[0]="javac";
            cmds[1]="D:/temp/HelloWord.java";
            try{
                p = Runtime.getRuntime().exec(cmds);//执行命令
                InputStream fiS = p.getInputStream(); //取得命令结果的输出流
                InputStreamReader isR = new InputStreamReader(fiS);//读出输出流
                BufferedReader br = new BufferedReader(isR);//缓冲器读行
                String line = null;
                while((line=br.readLine())!=null){
                    result.add(line);
                }
                System.out.println("");
                int exit = p.waitFor();//获取进程的最后返回状态
                System.out.println("Process exitValue:"+exit);
            }catch (Exception ex){
                ex.printStackTrace();
            }
            result.add("编译成功！");
            return  result;
        }else if(cmd2.equals(cmd)){
            Process p;
            String[] cmds = new String[2];
            cmds[0] = "java";
            cmds[1] = "HelloWorld";
            try{
                p=Runtime.getRuntime().exec(cmds,null,new File("D:/temp"));
                InputStream fiS = p.getInputStream();
                InputStreamReader isR = new InputStreamReader(fiS);
                BufferedReader br = new BufferedReader(isR);
                String line = null;
                while((line=br.readLine())!=null){
                    result.add(line);
                    System.out.println(line);
                }
                System.out.println("");
                int exit = p.waitFor();
                System.out.println("Process exitValue:"+exit);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        else {
            Process p;
            try {
                p=Runtime.getRuntime().exec(cmd);
                InputStream fiS = p.getInputStream();
                InputStreamReader isR = new InputStreamReader(fiS);
                BufferedReader br = new BufferedReader(isR);
                String line = null;
                while((line=br.readLine())!=null){
                    result.add(line);
                }
                System.out.println("");
                int exit = p.waitFor();
                System.out.println("Process exitValue:"+exit);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return  result;
    }
}
