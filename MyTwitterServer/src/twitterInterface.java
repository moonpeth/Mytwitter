

import java.awt.List;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface twitterInterface extends java.rmi.Remote{
	//send a message
    public void post(String topicName,String text) throws java.rmi.RemoteException;
    //follow a topic
    public String follow(String topicName,  String ClientID) throws java.rmi.RemoteException;
    //create a topic
    public void createTopic(String topicName) throws java.rmi.RemoteException;
    //a commune topic used to notify the creation of a new topic
    public void notifyNewTopic(String topicName) throws java.rmi.RemoteException;
    
    public void getNotify(String ClientID) throws java.rmi.RemoteException;
    //get all the topics
    public ArrayList getTopicList() throws java.rmi.RemoteException;
    //login verification
    public boolean login(String name, String password) throws java.rmi.RemoteException;
    //register a new account
    public void register(String name, String password) throws java.rmi.RemoteException;
    //store message
    public void stockMsg(String topic,String content) throws java.rmi.RemoteException;
    //read message history
    public ArrayList<String> historyMsg(String topic) throws java.rmi.RemoteException;
}

