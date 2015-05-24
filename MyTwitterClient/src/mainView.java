import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.EventListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class mainView extends JFrame implements EventListener {

	private static final long serialVersionUID = 4759251482696378302L;
	Container base;
	twitterInterface _t;
	JTextField topicChosen;
	JTextArea showArea, tapeArea;
	JButton post, relayer, topics, topic, follow,history;
	JPanel buttonJPanel, mainJPanel, textJPanel;
	String ClientID;

	public mainView(twitterInterface t, String ClientID) {
		_t = t;
		initFrame();
		this.ClientID = ClientID;
	}

	// set Frame
	public void initFrame() {
		// set base layout of this frame
		base = this.getContentPane();
		base.setLayout(new BorderLayout());
		// set size and location of the window
		Dimension d = new Dimension(600, 400);
		this.setMinimumSize(d);
		this.setLocationRelativeTo(null);

		// Two buttons : login when already have a account, register when need
		// to create a new account
		post = new JButton("Post");
		relayer = new JButton("getMsg");
		topics = new JButton("Show Topics");
		topicChosen = new JTextField();
		topicChosen.setColumns(9);
		topic = new JButton("New Topic");
		follow = new JButton("Follow");
		history = new JButton("History");
		buttonJPanel = new JPanel();
		buttonJPanel.add(topicChosen);
		buttonJPanel.add(topic);
		buttonJPanel.add(follow);
		buttonJPanel.add(topics);
		buttonJPanel.add(post);
		buttonJPanel.add(relayer);
		buttonJPanel.add(history);

		// text areas for showing messages
		showArea = new JTextArea("messages will be shown here");
		// text areas for taping messages
		tapeArea = new JTextArea("tape your message here");
		mainJPanel = new JPanel();
		mainJPanel.add(showArea);
		mainJPanel.add(tapeArea);

		textJPanel = new JPanel();
		textJPanel.setLayout(new GridLayout(2, 1));
		textJPanel.add(showArea);
		textJPanel.add(tapeArea);

		// action of post
		post.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String topicString = topicChosen.getText();
				String contentString = tapeArea.getText();
				try {
					if (topicString.length() < 1 || contentString.length() < 1) {
						JOptionPane
								.showMessageDialog(
										null,
										"Please enter your topic name in the left text field \n"
												+ "Please enter your text in the field above",
										"Hi,", JOptionPane.INFORMATION_MESSAGE);
					} else {
						// publish a message to a chosen topic
						_t.post(topicString, contentString);
						_t.stockMsg(topicString, contentString);
						showArea.append("\n" + contentString);
					}
				} catch (RemoteException e) {
					e.printStackTrace();
				}

			}
		});
		// action of new topic
		topic.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (topicChosen.getText().length() < 1) {
						JOptionPane
								.showMessageDialog(
										null,
										"Please entre your topic name in the left text field",
										"Hi,", JOptionPane.INFORMATION_MESSAGE);
					} else {
						System.out.println(topicChosen.getText());
						_t.createTopic(topicChosen.getText());
						showArea.setText("New topic:" + topicChosen.getText()
								+ " is created.");
					}
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		// action of relayer
		relayer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
			try {
				ArrayList<String> msg = _t.getMessage();
				for (String string : msg) {
					showArea.append("\n"+string);
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}

			}
		});

		// action of get topics
		topics.addActionListener(new ActionListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<String> topicList;
				try {
					topicList = _t.getTopicList();
					if (topicList.size() == 0)
						JOptionPane
								.showMessageDialog(
										null,
										"0 topic at the moment, tape 'new topic' to create one",
										"Hi,", JOptionPane.INFORMATION_MESSAGE);
					for (String i : topicList) {
						showArea.append("\n" + i);
					}
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		// action of follow a topic
		follow.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (topicChosen.getText().length() < 1) {
						JOptionPane
								.showMessageDialog(
										null,
										"Please entre the topic name that you wanna follow in the left text field",
										"Hi,", JOptionPane.INFORMATION_MESSAGE);
					} else {
						showArea.setText("Following twitter hashtag: "
								+ topicChosen.getText());
	                            String msg = _t.follow(topicChosen.getText(),ClientID);
	                            System.out.println(msg);
	                            showArea.append("\n" + msg);		
						
					}
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		//to show the history messages along to topic
		history.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (topicChosen.getText().length() < 1) {
						JOptionPane
								.showMessageDialog(
										null,
										"Please choose a topic",
										"Hi,", JOptionPane.INFORMATION_MESSAGE);
					} else {
						ArrayList<String> history = _t.historyMsg(topicChosen.getText());
						showArea.setText("HISTORY MESSAGE IN TOPIC: "+topicChosen.getText()+"\n");
						for (String string : history) {
							showArea.append("\n"+string);
						}
					}
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		base.add(textJPanel, BorderLayout.CENTER);
		base.add(buttonJPanel, BorderLayout.SOUTH);
		this.setVisible(false);
	}
}
