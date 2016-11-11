import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Ben on 10/26/2016.
 */
public class Psych {

    private static Psych game;
    private static PsychGUI gui;
    private static String serverIP = "localhost";
    private static int serverPort = 9999; // Default Port
    private static PrintWriter out;
    private static InputStreamReader isr;
    private static BufferedReader in;

    private static int gameState = GameState.LOGINREGISTER;
    private static String playerKey = "";
    private static String hostToken = "";
    private static String joinKey = "";
    private static String username = "";
    private static ArrayList<String> players = new ArrayList<String>();
    public static final int TOTALPLAYERS = 2;

    private static String gameTerm = "";
    private static String gameAnswer = "";
    private static ArrayList<String> answers = new ArrayList<String>();
    private static ArrayList<String> roundInfo = new ArrayList<String>();
    private static ButtonGroup choiceButtons;
    private static String myAnswer = "";
    private static boolean isGameOver = false;

    public Psych(){
        game = this;
        try{
            Socket socket = new Socket(serverIP, serverPort);
            out = new PrintWriter(socket.getOutputStream(), true);
            isr = new InputStreamReader(socket.getInputStream());
            in = new BufferedReader(isr);
        }catch(IOException e){
            e.printStackTrace();
        }

        gui = new PsychGUI(this, gameState);

    }

    public static Psych getGame(){
        return game;
    }

    public static PrintWriter getOut() {
        return out;
    }

    public static BufferedReader getIn() {
        return in;
    }

    public static int getGameState() {
        return gameState;
    }

    public static String getPlayerKey() {
        return playerKey;
    }

    public static void setPlayerKey(String playerKey) {
        Psych.playerKey = playerKey;
    }

    public static String getHostToken() {
        return hostToken;
    }

    public static void setHostToken(String hostToken) {
        Psych.hostToken = hostToken;
    }

    public static String getJoinKey() {
        return joinKey;
    }

    public static void setJoinKey(String joinKey) {
        Psych.joinKey = joinKey;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        Psych.username = username;
    }

    public static void setGameState(int gameState) {
        Psych.gameState = gameState;
    }

    public static ArrayList<String> getPlayers() {
        return players;
    }

    public static String getGameTerm() {
        return gameTerm;
    }

    public static String getGameAnswer() {
        return gameAnswer;
    }

    public static ArrayList<String> getAnswers() {
        return answers;
    }

    public static boolean isGameOver() {
        return isGameOver;
    }

    public static void setGameOver(boolean isGameOver) {
        Psych.isGameOver = isGameOver;
    }

    public static void setGameTerm(String gameTerm) {
        Psych.gameTerm = gameTerm;
    }

    public static void setGameAnswer(String gameAnswer) {
        Psych.gameAnswer = gameAnswer;
    }

    public static String getMyAnswer() {
        return myAnswer;
    }

    public static void setMyAnswer(String myAnswer) {
        Psych.myAnswer = myAnswer;
    }

    public static ButtonGroup getChoiceButtons() {
        return choiceButtons;
    }

    public static void setChoiceButtons(ButtonGroup choiceButtons) {
        Psych.choiceButtons = choiceButtons;
    }

    public static ArrayList<String> getRoundInfo() {
        return roundInfo;
    }

    public static void createNewGUI(int gameState){
        Psych.setGameState(gameState);
        PsychGUI.getGUI().dispose();
        new PsychGUI(getGame(), gameState);
    }

    public static void searchForGame(String key) {
        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            String response = "";
            @Override
            public void run() {

                out.println(FoilMakerNetworkProtocol.MSG_TYPE.JOINGAME + "--" + playerKey + "--" + key);
                try {
                    response = in.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(response.contains("JOINGAME") && response.contains("SUCCESS"))
                {
                    Psych.createNewGUI(GameState.JOINWAIT);
                    Psych.waitForLeader();
                    this.cancel();
                }
                System.out.println("NO GAME FOUND!");
                this.cancel();

            }
        }, 0, 500);
    }

    public static void waitForPlayers() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            String response = "";
            @Override
            public void run() {
                try {
                    if(Psych.in.ready())
                        response = Psych.in.readLine();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(response.contains("NEWPARTICIPANT"))
                {
                    String[] info = response.split("--");
                    String joinedPlayer = info[1];
                    if(!Psych.getPlayers().contains(joinedPlayer))
                    {
                        Psych.getPlayers().add(joinedPlayer);
                        Psych.createNewGUI(GameState.CREATE);
                        this.cancel();
                    }
                }

                if(response.contains("ROUNDRESULT"))
                {
                    ArrayList<String> info = new ArrayList<String>();
                    for(String item : response.split("--"))
                        info.add(item);
                    info.remove(0);

                    //String joinedPlayer = info[1];
                    //Psych.getPlayers().add(joinedPlayer);

                    String gameover = "";
                    try {
                        if(Psych.in.ready())
                             gameover = Psych.in.readLine();
                        if(gameover.contains("GAMEOVER"))
                            Psych.setGameOver(true);
                        else if(gameover.contains("NEWGAMEWORD")){
                            Psych.getAnswers().clear();
                            Psych.setGameTerm(gameover.split("--")[1]);
                            Psych.setGameAnswer(gameover.split("--")[2]);
                        }
                        Psych.createNewGUI(GameState.RESULTS);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    this.cancel();
                }
                response = "";
            }
        }, 0, 500);


    }

    public static void waitForLeader() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            String response = "";
            @Override
            public void run() {

                try {
                    if(in.ready())
                        response = in.readLine();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(response.contains("NEWGAMEWORD"))
                {
                    Psych.gameTerm = response.split("--")[1];
                    Psych.gameAnswer = response.split("--")[2];
                    Psych.createNewGUI(GameState.SUGGESTION);

                }
                response = "";
                if(Psych.getGameState() != GameState.JOINWAIT)
                    this.cancel();
            }
        }, 0, 500);
    }

    public static void waitForSuggestions(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            String response = "";
            @Override
            public void run() {

                try {
                    if(in.ready())
                        response = in.readLine();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(response.contains("ROUNDOPTIONS"))
                {
                    for(String option : response.split("--")){
                        if(!option.equalsIgnoreCase(response.split("--")[0]))
                            Psych.getAnswers().add(option);
                    }

                    Psych.createNewGUI(GameState.PICKING);

                }
                response = "";
                if(Psych.getGameState() != GameState.WAITING)
                    this.cancel();
            }
        }, 0, 500);
    }

    public static void main(String args[]){ new Psych(); }


}
