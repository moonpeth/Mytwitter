import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.jms.JMSException;

public class twitterImpl extends UnicastRemoteObject implements twitterInterface{

    protected twitterImpl() throws RemoteException {
        super();
    }

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

	public boolean login(String name, String password){
		BufferedReader br = null;	
		String userString = name+ " "+password;
		try { 
			String sCurrentLine;
			//get users list
			br = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/userList")); 
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
				//when user exists, return true
				if(userString.equals(sCurrentLine)){
				br.close();
				return true;
				}
			}
			br.close();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
		}
       return false;
	}
		
	}


