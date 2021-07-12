package com.xxx;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
public interface WarHouse extends Remote{
    List<String> cmdExec(String cmd)throws RemoteException;
}
