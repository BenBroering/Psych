import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class StartJoinGUI extends JFrame{
	private JFrame frame;
	private String user;
	
	public StartJoinGUI(String username){
		frame = this;
		this.user = username;
		frame.setTitle(user);
		this.setSize(400,600);
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    JPanel mainPanel = (JPanel) this.getContentPane();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
		
		JPanel startPanel1 = new JPanel();
        JButton newGame = new JButton("Start New Game");
        
        JButton joinGame = new JButton("Join a Game");
        
        startPanel1.add(newGame, BorderLayout.WEST);
        startPanel1.add(joinGame, BorderLayout.EAST);
        mainPanel.add(startPanel1, BorderLayout.CENTER);
        
        this.setVisible(true);
	}
}
