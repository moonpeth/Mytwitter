import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import javax.jms.JMSException;

public class twitterImpl extends UnicastRemoteObject implements
		twitterInterface {

	protected twitterImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
     * 
     */

	// public destination, notify everyone when there is a new topic

	private static String pubTopic = "public_notification";
	private ArrayList<String> topicList = new ArrayList<String>();

	public void post(String topicName, String text) {
		try {
			(new pub()).configurer(topicName, text);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public String follow(String topicName,  String ClientID) {
		String s = null;
		try {
			s = (new sub()).configurer(topicName, ClientID);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return s;
	}

	public void getNotify(String ClientID) {
		follow(pubTopic, ClientID);
	}

	public void createTopic(String topicName) {
		topicList.add(topicName);
		notifyNewTopic(topicName);
	}

	public void notifyNewTopic(String topicName) {
		String msg = "New topic:" + topicName + " is created.";
		post(pubTopic, msg);
	}

	public ArrayList getTopicList() {
		return topicList;
	}

	public boolean login(String name, String password) {
		BufferedReader br = null;
		String userString = name + " " + password;
		try {
			String sCurrentLine;
			// get users list
			br = new BufferedReader(new FileReader(
					System.getProperty("user.dir") + "/userList"));
			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
				// when user exists, return true
				if (userString.equals(sCurrentLine)) {
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

	public void register(String name, String password) {
		BufferedWriter bw = null;
		String userString = name + " " + password;
		try {
			bw = new BufferedWriter(new FileWriter(
					System.getProperty("user.dir") + "/userList", true));
			bw.write("\n" + userString);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stockMsg(String topic,String content){
    String msg = topic+"@"+content;
    BufferedWriter bw = null;
	try {
		bw = new BufferedWriter(new FileWriter(
				System.getProperty("user.dir") + "/msgHistory", true));
		bw.write("\n" + msg);
		bw.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
		
	}
	
	public ArrayList<String> historyMsg(String topic)  {
		BufferedReader br = null;
		ArrayList<String> history = new ArrayList<>();
		String[] rs;
		try {
			String sCurrentLine;
			// get users list
			br = new BufferedReader(new FileReader(
					System.getProperty("user.dir") + "/msgHistory"));
			while ((sCurrentLine = br.readLine()) != null) {
				rs =  sCurrentLine.split("@");
              if(rs[0].equals(topic)){
            	  history.add(rs[1]);
              }	
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return history;
	
		
	}
	

}
