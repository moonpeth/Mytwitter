import java.rmi.Remote;


public interface twitterInterface extends Remote{
    public void post(String topicName,String text) throws java.rmi.RemoteException;
    public void follow(String topicName) throws java.rmi.RemoteException;
    public void createTopic(String topicName) throws java.rmi.RemoteException;
    public void notifyNewTopic(String topicName) throws java.rmi.RemoteException;
}
