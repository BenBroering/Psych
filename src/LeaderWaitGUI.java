import javax.swing.JFrame;


public class LeaderWaitGUI extends JFrame{
	private JFrame frame;
	private String user;
	
	public LeaderWaitGUI(String username){
		frame = this;
		this.user = username;
		frame.setTitle(user);
	}
}
