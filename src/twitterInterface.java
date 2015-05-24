import java.rmi.Remote;


public interface twitterInterface extends Remote{
	//send a message
    public void post(String topicName,String text) throws java.rmi.RemoteException;
    //follow a topic
    public void follow(String topicName) throws java.rmi.RemoteException;
    //create a topic
    public void createTopic(String topicName) throws java.rmi.RemoteException;
    //a commune topic used to notify the creation of a new topic
    public void notifyNewTopic(String topicName) throws java.rmi.RemoteException;
    //login verification
    public boolean login(String name, String password) throws java.rmi.RemoteException;
}
