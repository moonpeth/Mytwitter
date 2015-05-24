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

	public String follow(String topicName) {
		String s = null;
		try {
			s = (new sub()).configurer(topicName);
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return s;
	}

	public void getNotify() {
		follow(pubTopic);
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
}
