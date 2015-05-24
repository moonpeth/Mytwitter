

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class twitterClient {
    
    public static void main(String[] args) {
        String topicName = null;
        String text = null;   
        try { 
            twitterInterface t =(twitterInterface) Naming.lookup("rmi://localhost:8888/twitterDistante");
            loginView login = new loginView(t);
           
           // mainView chat = new mainView(t);
            try {
                BufferedReader commandLine = new java.io.BufferedReader(
                        new InputStreamReader(System.in));
                // Loop until the word "exit" is typed
             
                while (true) {
                    String s =null;
                    s = commandLine.readLine();
                    //follow , done
                    if (s.equalsIgnoreCase("follow")) {
                        System.out.println("Topic:");
                        topicName=commandLine.readLine();
                        System.out.println("Following twitter hashtag: "+ topicName);
                        String msg = t.follow(topicName);
                        System.out.println(msg);
                    } else if(s.equalsIgnoreCase("notify")){
                        t.getNotify();
                        //send a new msg to a chosen topic, done
                    } else if(s.equalsIgnoreCase("post")){
                        System.out.println("Topic:");
                        topicName=commandLine.readLine();
                        System.out.println("Text:");
                        text = commandLine.readLine();
                        t.post(topicName,text);
                        //create a new topic, done
                    }else if(s.equalsIgnoreCase("newtopic")){
                        System.out.println("Name of the new topic:");
                        topicName=commandLine.readLine();
                        t.createTopic(topicName);
                        System.out.println("New topic:"+topicName+" is created.");
                        //get all topics, done
                    }else if(s.equalsIgnoreCase("showHashtags")){
                        ArrayList<String> topicList = t.getTopicList();
                        for(String i:topicList){
                            System.out.println(i);
                        }
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

