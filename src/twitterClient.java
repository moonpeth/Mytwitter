import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
public class twitterClient {
    
    public static void main(String[] args) {
        String topicName = null;
        String text = null;
        String pubTopic="public_notification";     
        //interface
        loginView login = new loginView();
        try { 
            twitterInterface t =(twitterInterface) Naming.lookup("rmi://localhost:8888/twitterDistante"); 
            try {
                BufferedReader commandLine = new java.io.BufferedReader(
                        new InputStreamReader(System.in));
                // Loop until the word "exit" is typed
             
                while (true) {
                    String s =null;
                    s = commandLine.readLine();
                    //follow a topic
                    if (s.equalsIgnoreCase("follow")) {
                        System.out.println("Topic:");
                        topicName=commandLine.readLine();
                        t.follow(topicName);
                    //follow the commune topic "notification"
                    } else if(s.equalsIgnoreCase("notify")){
                        t.follow(pubTopic);
                    //send a new message to a chosen topic
                    } else if(s.equalsIgnoreCase("post")){
                        System.out.println("Topic:");
                        topicName=commandLine.readLine();
                        System.out.println("Text:");
                        text = commandLine.readLine();
                        t.post(topicName,text);
                     //create a new topic
                    }else if(s.equalsIgnoreCase("newtopic")){
                        System.out.println("Name of the new topic:");
                        topicName=commandLine.readLine();
                        t.createTopic(topicName);
                     //disconnect
                    }else if(s.equalsIgnoreCase("exit")){
                        System.exit(0);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (NotBoundException e) { 
            e.printStackTrace(); 
        } catch (MalformedURLException e) { 
            e.printStackTrace(); 
        } catch (RemoteException e) { 
            e.printStackTrace();   
        } 
        

    }
}
