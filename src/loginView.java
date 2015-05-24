import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.EventListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class loginView extends JFrame implements EventListener {

	private static final long serialVersionUID = -3971072789962173880L;
	Container base;
	twitterClient client;
	//the main window to get and set messages
	mainView main;
	twitterInterface _t;
	JTextField nameField,passwordField;
	
			
	public loginView(twitterInterface t) {
		_t = t;
		initFrame();
		main = new mainView();
	}

	// set Frame
	public void initFrame() {
		// set base layout of this frame
		base = this.getContentPane();
		base.setLayout(new GridLayout(2,1));
		// set size and location of the window
		Dimension d = new Dimension(600, 400);
		this.setMinimumSize(d);
		this.setLocationRelativeTo(null);
		
		// Two buttons : login when already have a account, register when need to create a new account
		JButton login = new JButton("Login");
		JButton register = new JButton("Register");
		
		JPanel buttonJPanel = new JPanel();
		//buttonJPanel.setLayout(new BorderLayout());
		
		buttonJPanel.add(login);
		buttonJPanel.add(register);	
		
		//two texts areas for user name and password
		nameField = new JTextField("user name");
		nameField.setColumns(15);
		passwordField = new JTextField("password");
		passwordField.setColumns(15);
		
		JPanel textJPanel = new JPanel();
		textJPanel.setLayout(new GridLayout(4,1));
		textJPanel.add(nameField);
		textJPanel.add(passwordField);
		
		//action of login
        login.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
			//verify the user authority
			String name = nameField.getText();	
			String password = passwordField.getText();
			try {
		    //if log in success, turn to the main page of chat
			if(_t.login(name, password)){
				main.setVisible(true);
			}else{
				JOptionPane.showMessageDialog(null,
						"Please create a new account", "Hi,",
						JOptionPane.INFORMATION_MESSAGE);
			}
			} catch (RemoteException e) {
				e.printStackTrace();
			}	     
			}});
        
      //action of register
        register.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
			    
			}});
		base.add(textJPanel);
		base.add(buttonJPanel);
		this.setVisible(true);
	}}

