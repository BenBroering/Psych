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
            String gameKey = Psych.getHostToken();
            JTextField field1 = new JTextField(gameKey);
            
            stuffInFrame.add(label1);
            stuffInFrame.add(field1);
            
            JPanel particPanel = new JPanel();
            JLabel label2 = new JLabel("Participants");
            //Replaced TextField with TextArea
            JTextArea textArea = new JTextArea(5,15);
            for(String player : Psych.getPlayers())
                textArea.append("• " + player + "\n");
            //JTextField field2 = new JTextField(10);
            //Field needs to hear from server about other players joining.
            //field2.addActionListener(aListener);
            stuffInFrame.add(label2);
            stuffInFrame.add(textArea);
            particPanel.add(label2);
            particPanel.add(textArea);
            
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
        
        if(gameState == GameState.SUGGESTION){
        	JPanel mainPanel = (JPanel) this.getContentPane();
            mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
            JPanel startPanel1 = new JPanel();
            //Program for receiving question from server goes here
            String input = "";
            JLabel question = new JLabel("What is the word for " + Psych.getGameTerm() + "?");
            stuffInFrame.add(question);
            
            JPanel suggestion = new JPanel();
            suggestion.setName("Your Suggestion:");
            JTextField field2 = new JTextField(15);
            stuffInFrame.add(field2);
            suggestion.add(field2);
            JButton submitSuggest = new JButton("Submit Suggestion");
            submitSuggest.setActionCommand("suggestion");
            submitSuggest.addActionListener(aListener);
            stuffInFrame.add(submitSuggest);
            
            startPanel1.add(question);
            startPanel1.add(suggestion);
            startPanel1.add(submitSuggest, BorderLayout.SOUTH);
            
            mainPanel.add(startPanel1, BorderLayout.CENTER);
        }

        if(gameState == GameState.WAITING){
            JPanel mainPanel = (JPanel) this.getContentPane();
            mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
            JPanel startPanel1 = new JPanel();
            JLabel label1  = new JLabel("Waiting for other players...");

            startPanel1.add(label1);

            mainPanel.add(startPanel1, BorderLayout.CENTER);
        }
        
        if(gameState == GameState.PICKING){
        	JPanel mainPanel = (JPanel) this.getContentPane();
            mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
            JPanel startPanel1 = new JPanel();
            
            JLabel label1 = new JLabel("Pick your option below");
            stuffInFrame.add(label1);
            startPanel1.add(label1, BorderLayout.NORTH);
            
            String[] options = new String[0]; //Action for getting suggestions in a array goes here.
                                              //Unsure of exact amount of players.

            Psych.setChoiceButtons(new ButtonGroup());

            for(String answer : Psych.getAnswers()){
                JRadioButton choice = new JRadioButton(answer);
                Psych.getChoiceButtons().add(choice);
                choice.setActionCommand(answer);
                choice.addActionListener(aListener);
                if(Psych.getMyAnswer().equalsIgnoreCase(answer))
                    choice.setEnabled(false);
                stuffInFrame.add(choice);
                Psych.getChoiceButtons().add(choice);
                startPanel1.add(choice, BorderLayout.CENTER);
            }

            JButton submitOption = new JButton("Submit Option");
            submitOption.setActionCommand("submit");
            submitOption.addActionListener(aListener);
            stuffInFrame.add(submitOption);
            startPanel1.add(label1, BorderLayout.SOUTH);
            startPanel1.add(submitOption);
            
            mainPanel.add(startPanel1);
        }
        
        if(gameState == GameState.RESULTS){
        	JPanel mainPanel = (JPanel) this.getContentPane();
            mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));
            
            JPanel roundResults = new JPanel();
            roundResults.setName("Round Result");
            String playerResults = ""; //Get this round results of player
            JTextArea textAreaDescription = new JTextArea(3,15);
            JTextArea textAreaScoreboard = new JTextArea(5,15);
            /*for(String player : Psych.getPlayers())
                textArea.append("• " + player + "\n")*/
            ArrayList<String> roundInfo = Psych.getRoundInfo();
            for(int findPlayer = 0; findPlayer < roundInfo.size(); findPlayer += 5) {
                if(roundInfo.get(findPlayer).equalsIgnoreCase(Psych.getUsername())){
                    textAreaScoreboard.append(roundInfo.get(findPlayer) + " ==> Score:" + roundInfo.get(findPlayer+2)
                            + "| Fooled: " + roundInfo.get(findPlayer+3)
                            + " player(s) | Fooled By: " + roundInfo.get(findPlayer+4) + " player(s)");
                }
            }
            JTextArea area1 = new JTextArea(playerResults,5,20);
            stuffInFrame.add(area1);
            
            JPanel overallResults = new JPanel();
            overallResults.setName("Overall Results");
            String[] scores = new String[0]; //Get the current scores of all players
            JTextArea area2 = new JTextArea(5,20);
            for(String score : scores){
            	area2.append(score);
            }
            stuffInFrame.add(area2);
            
            JButton nextRound = new JButton("Next Round");
            nextRound.setActionCommand("nextRound");
            nextRound.addActionListener(aListener);
            stuffInFrame.add(nextRound);
            if(Psych.isGameOver())
                nextRound.setEnabled(false);
            
            mainPanel.add(roundResults, BorderLayout.NORTH);
            mainPanel.add(overallResults, BorderLayout.CENTER);
            mainPanel.add(nextRound, BorderLayout.SOUTH);
            
            
            
        }


        // Always run this stuff too.
        this.setVisible(true);
        //
    }

    public static PsychGUI getGUI(){
        return gui;
    }

    public static ArrayList<Component> getStuffInFrame(){
        return stuffInFrame;
    }


}