import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class mainView extends JFrame implements EventListener {

	private static final long serialVersionUID = 4759251482696378302L;
	Container base;
	public mainView() {
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
		
		// Two buttons : login when already have a account, register when need to create a new account
		JButton post = new JButton("Post");
		JButton relayer = new JButton("Relayer");
		JButton topics = new JButton("Show Topics");
		JTextField topicChosen = new JTextField("topic?"); 
		topicChosen.setColumns(9);
		JPanel buttonJPanel = new JPanel();
		buttonJPanel.add(topicChosen);	
		buttonJPanel.add(topics);	
		buttonJPanel.add(post);
		buttonJPanel.add(relayer);	
		
		
		//text areas for showing messages
		JTextArea showArea = new JTextArea("messages will be shown here");
		//text areas for taping messages
		JTextArea tapeArea = new JTextArea("tape your message here");
		JPanel mainJPanel = new JPanel();
		mainJPanel.add(showArea);
		mainJPanel.add(tapeArea);
		
		JPanel textJPanel = new JPanel();
		textJPanel.setLayout(new GridLayout(2,1));
		textJPanel.add(showArea);
		textJPanel.add(tapeArea);
		
		//action of post
        post.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
		
			}});
        
      //action of relayer
        relayer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			    
			}});
        
        //action of get topics
        topics.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			    
			}});
		base.add(textJPanel,BorderLayout.CENTER);
		base.add(buttonJPanel,BorderLayout.SOUTH);
		this.setVisible(false);
	}}

