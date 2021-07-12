package com.xxx;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String[] args)throws Exception{
        System.out.println("产生服务器对象");
        WarehouseImple cemtralWarehouse = new WarehouseImple();

        System.out.println("将服务器对象绑定在8001端口，对外提供服务");
        LocateRegistry.createRegistry(8001);
        Naming.rebind("rmi://127.0.0.1:8001/warehouse1",cemtralWarehouse);
        System.out.println("等待客户端");
    }
}
