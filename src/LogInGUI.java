
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class LogInGUI extends JFrame{
	private JFrame frame;
	public final static int USER_LOGIN = 1;
	public final static int REGISTER_USER = 2;
	
	public LogInGUI(){
		frame = this;
		frame.setTitle("FoilMaker");
		this.setSize(400,600);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    JPanel mainPanel = (JPanel) this.getContentPane();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
        
        
        
        JPanel startPanel1 = new JPanel();
        JButton login = new JButton("Login");
        
        
        JButton register = new JButton("Register");
        

        startPanel1.add(login, BorderLayout.WEST);
        startPanel1.add(register, BorderLayout.EAST);
        
        JPanel startPanel2 = new JPanel();
        JTextField username = new JTextField("Username");
        JTextField password = new JTextField("Password");
        startPanel2.add(username, BorderLayout.NORTH);
        startPanel2.add(password, BorderLayout.SOUTH);
        
        mainPanel.add(startPanel2, BorderLayout.CENTER);
        mainPanel.add(startPanel1, BorderLayout.SOUTH);
        
        login.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ae){
        		//Event for Login button click
        		System.out.println(USER_LOGIN);
        		new StartJoinGUI("Test");
        	}
        });
        
        register.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent ae){
        		//Event for register button
        		System.out.println(REGISTER_USER);
        	}
        });
        
        this.setVisible(true);
        
	}
}
