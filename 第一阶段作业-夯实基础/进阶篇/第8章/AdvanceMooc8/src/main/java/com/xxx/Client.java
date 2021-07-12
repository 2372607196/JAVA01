package com.xxx;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingException;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws NamingException, RemoteException {
        Context namingContext = new InitialContext();

        //查找RMI注册表上的绑定服务
        System.out.println("RMI 注册表绑定列表：");
        Enumeration<NameClassPair>e = namingContext.list("rmi://127.0.0.1:8001/");
        while (e.hasMoreElements()){
            System.out.println(e.nextElement().getName());
        }

        //获取某一地址上的服务类
        String url = "rmi://127.0.0.1:8001/warehouse1";
        WarHouse centralWarehouse = (WarHouse) namingContext.lookup(url);

        Scanner in = new Scanner(System.in);
        System.out.println("Enter keywords:");
        String kewords = in.nextLine();
        List<String> result = centralWarehouse.cmdExec(kewords);
        for(String res:result){
            System.out.println(res);
        }
        in.close();
    }
}
