import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.jms.JMSException;

public class twitterImpl extends UnicastRemoteObject implements twitterInterface{

    protected twitterImpl() throws RemoteException {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // public destination, notify everyone when there is a new topic
    public String pubTopic="public_notification";
    
    public void post(String topicName,String text) {
        try {
            (new pub()).configurer(topicName,text);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    
    public void follow(String topicName) {
        try {
            (new sub()).configurer(topicName);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    
    public void createTopic(String topicName){
        System.out.println("New topic:"+topicName+" is created.");
        notifyNewTopic(topicName);
    }
    
    public void notifyNewTopic(String topicName){
        String msg="New topic:"+topicName+" is created.";
        post(pubTopic,msg);
    }

}
