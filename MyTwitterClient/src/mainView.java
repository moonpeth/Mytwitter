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
	JButton post, relayer, topics, topic, follow;
	JPanel buttonJPanel, mainJPanel, textJPanel;

	public mainView(twitterInterface t) {
		_t = t;
		initFrame();
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
		relayer = new JButton("Relayer");
		topics = new JButton("Show Topics");
		topicChosen = new JTextField();
		topicChosen.setColumns(9);
		topic = new JButton("New Topic");
		follow = new JButton("Follow");
		buttonJPanel = new JPanel();
		buttonJPanel.add(topicChosen);
		buttonJPanel.add(topic);
		buttonJPanel.add(follow);
		buttonJPanel.add(topics);
		buttonJPanel.add(post);
		buttonJPanel.add(relayer);

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
				// TODO Auto-generated method stub

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
						 while(true){
	                            String msg = _t.follow(topicChosen.getText());
	                            System.out.println(msg);
	                            showArea.append("\n" + msg);
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
