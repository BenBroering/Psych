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
        super("Psych Game");
        this.psych = psych;
        this.gui = this;
        this.setSize(400,600);
        AListener aListener = new AListener();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        stuffInFrame = new ArrayList<Component>();
        //

        if (gameState == GameState.ENDGAME){
            System.out.println("The Game Has Ended.");
            System.exit(0);
        }

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
            JPanel startPanel2 = new JPanel();
            JPanel startPanel3 = new JPanel();
            JPanel startPanel4 = new JPanel();
            JLabel label1 = new JLabel("Username");
            JTextField field1 = new JTextField("username",15);
            JLabel label2 = new JLabel("Password");
            JTextField field2 = new JPasswordField("password",15);
            JButton register = new JButton("register");
            JButton login = new JButton("login");
            register.setActionCommand("register");
            login.setActionCommand("login");
            register.addActionListener(aListener);
            login.addActionListener(aListener);
            stuffInFrame.add(label1);
            stuffInFrame.add(label2);
            stuffInFrame.add(register);
            stuffInFrame.add(login);
            stuffInFrame.add(field1);
            stuffInFrame.add(field2);
            
            startPanel2.add(label1, BorderLayout.WEST);
            startPanel2.add(field1, BorderLayout.EAST);
            startPanel3.add(label2, BorderLayout.WEST);
            startPanel3.add(field2, BorderLayout.EAST);
            startPanel4.add(register, BorderLayout.WEST);
            startPanel4.add(login, BorderLayout.EAST);

            mainPanel.add(startPanel1, BorderLayout.NORTH);
            mainPanel.add(startPanel2, BorderLayout.CENTER);
            mainPanel.add(startPanel3, BorderLayout.CENTER);
            mainPanel.add(startPanel4, BorderLayout.CENTER);

        }

        if(gameState == GameState.JOINCREATE){
        	 JPanel mainPanel = (JPanel) this.getContentPane();
             mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
             JPanel startPanel1 = new JPanel();
             JButton create = new JButton("Start New Game");
             JButton join = new JButton("Join a Game");
             create.setActionCommand("create");
             join.setActionCommand("join");
             create.addActionListener(aListener);
             join.addActionListener(aListener);
             stuffInFrame.add(create);
             stuffInFrame.add(join);
             
             startPanel1.add(create);
             startPanel1.add(join);
             
             mainPanel.add(startPanel1, BorderLayout.CENTER);
        }
        
        if(gameState == GameState.JOIN){
        	JPanel mainPanel = (JPanel) this.getContentPane();
            mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
            JPanel startPanel1 = new JPanel();
            JLabel label1 = new JLabel("Enter the game key to join a game");
            JTextField gameKey = new JTextField("",5);
            JButton joinKey = new JButton("Join a Game");
            joinKey.setActionCommand("joinKey");
            joinKey.addActionListener(aListener);
            stuffInFrame.add(label1);
            stuffInFrame.add(gameKey);
            stuffInFrame.add(joinKey);
            
            startPanel1.add(label1);
            startPanel1.add(gameKey);
            startPanel1.add(joinKey);
            
            mainPanel.add(startPanel1, BorderLayout.CENTER);
            
        }
        
        if(gameState == GameState.JOINWAIT){
        	JPanel mainPanel = (JPanel) this.getContentPane();
            mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
            JPanel startPanel1 = new JPanel();
            JLabel label1  = new JLabel("Waiting for leader...");
            
            startPanel1.add(label1);
            
            mainPanel.add(startPanel1, BorderLayout.CENTER);
        }
        
        if(gameState == GameState.CREATE){
        	JPanel mainPanel = (JPanel) this.getContentPane();
            mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
            JPanel startPanel1 = new JPanel();
            JLabel label1 = new JLabel("Others should use this key to join your game");
            //Need a method in either server or here to generate a random key.
            String gameKey = Psych.getHostToken();
            JTextField field1 = new JTextField(gameKey,5);
            
            stuffInFrame.add(label1);
            stuffInFrame.add(field1);
            
            JPanel particPanel = new JPanel();
            JLabel label2 = new JLabel("Participants");
            JTextField field2 = new JTextField(10);
            //Field needs to hear from server about other players joining.
            field2.addActionListener(aListener);
            stuffInFrame.add(label2);
            stuffInFrame.add(field2);
            particPanel.add(label2);
            particPanel.add(field2);
            
            JButton startGame = new JButton("Start Game");
            startGame.setActionCommand("startGame");
            startGame.addActionListener(aListener);
            stuffInFrame.add(startGame);
            
            startPanel1.add(label1);
            startPanel1.add(field1);
            startPanel1.add(particPanel);
            startPanel1.add(startGame);
            
            mainPanel.add(startPanel1, BorderLayout.CENTER);
         
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