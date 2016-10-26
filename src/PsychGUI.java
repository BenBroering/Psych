import javax.swing.*;
import java.awt.*;

/**
 * Created by Ben on 10/26/2016.
 */
public class PsychGUI extends JFrame{

    private JFrame frame;

    PsychGUI(){
        frame = this;
        this.setSize(400,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = (JPanel) this.getContentPane();
        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
        JPanel startPanel1 = new JPanel();
        JButton startNewGame = new JButton("Start New Game");
        JButton joinGame = new JButton("Join a Game");

        startPanel1.add(startNewGame);
        startPanel1.add(joinGame);

        JPanel startPanel2 = new JPanel();
        JButton butt = new JButton("HOWDY");
        JTextField username = new JTextField("Username");
        startPanel2.add(butt);



        mainPanel.add(butt, BorderLayout.NORTH);
        mainPanel.add(startPanel1, BorderLayout.CENTER);


        this.setVisible(true);
    }


}
