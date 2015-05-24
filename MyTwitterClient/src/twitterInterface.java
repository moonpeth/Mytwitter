

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface twitterInterface extends java.rmi.Remote{
    public void post(String topicName,String text) throws java.rmi.RemoteException;
    public String follow(String topicName) throws java.rmi.RemoteException;
    public void createTopic(String topicName) throws java.rmi.RemoteException;
    public void notifyNewTopic(String topicName) throws java.rmi.RemoteException;
    public void getNotify() throws java.rmi.RemoteException;
    public ArrayList getTopicList() throws java.rmi.RemoteException;
    //login verification
    public boolean login(String name, String password) throws java.rmi.RemoteException;
    //register a new account
    public void register(String name, String password) throws java.rmi.RemoteException;
}
