import javax.swing.*;
import java.awt.*;

/**
 * Created by Ben on 10/26/2016.
 */
public class PsychGUI extends JFrame{

    private static JFrame currentFrame;
    private Psych psych;

    PsychGUI(Psych psych){

        /**
         * GUIs NEEEDED:
         *      1. Login Screen:
         *          - two text boxes that ask for username & password
         *          - two buttons "login" and "register" with respective action commands
         *      2. Join/Create game screen:
         *          - Two buttons that ask whether player wants to join or create game
         *      3. Create Game:
         *          - Text box that lists participants
         *          - A textbox that can have a code to join
         *      4. Join Game:
         *          - 1 text box to input a code
         *          - 1 button to try and join game
         *      5. Game Frames (HAVE NOT DECIDED ON IMPLEMENTION)
         */

        this.psych = psych;
        currentFrame = this;
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

        System.out.println("Hello");

        mainPanel.add(butt, BorderLayout.NORTH);
        mainPanel.add(startPanel1, BorderLayout.CENTER);


        this.setVisible(true);
    }

    public static JFrame getCurrentFrame(){
        return currentFrame;
    }


}
