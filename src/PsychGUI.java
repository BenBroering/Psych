import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Ben on 10/26/2016.
 */
public class PsychGUI extends JFrame{

    /**
     * gui: Stores this object.
     * psych: Contains the psych game object. Used for sending things to server.
     * stuffInFrame: Contains elements that are needed for inspection.
     *               EX: JTextBoxes should be added to this so that
     *               they can be read and used. Buttons do not need
     *               to be added, but they can and it can be safe to
     *               do so if we need them in the future.
     */
    private static PsychGUI gui;
    private static Psych psych;
    private static ArrayList<Component> stuffInFrame = new ArrayList<Component>();

    PsychGUI(Psych psych, int gameState){

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

        //These things must always run!
        this.psych = psych;
        this.gui = this;
        this.setSize(400,600);
        AListener aListener = new AListener();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //

        if (gameState == GameState.LOGINREGISTER){
            /**
             * Creates GUI for Login/Register screen.
             * TODO:
             * Create Frame with correct name.
             * Add any neccessary fields.
             */

            JPanel mainPanel = (JPanel) this.getContentPane();
            mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
            JPanel startPanel1 = new JPanel();
            JTextField field1 = new JTextField("username",15);
            JTextField field2 = new JPasswordField("password",15);
            JButton register = new JButton("register");
            JButton login = new JButton("login");
            register.setActionCommand("register");
            register.addActionListener(aListener);
            stuffInFrame.add(register);
            stuffInFrame.add(field1);
            stuffInFrame.add(field2);


            startPanel1.add(field1);
            startPanel1.add(field2);
            startPanel1.add(register);
            startPanel1.add(login);

            mainPanel.add(startPanel1, BorderLayout.CENTER);



        }

        if(gameState == GameState.JOINCREATE){

        }


        // Always run this stuff too.
        this.setVisible(true);
        //
    }

    public static PsychGUI getGUI(){
        return gui;
    }

    public static Psych getPsych(){
        return psych;
    }

    public static ArrayList<Component> getStuffInFrame(){
        return stuffInFrame;
    }


}