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
    private static String password = "";
    private static ArrayList<String> players = new ArrayList<String>();
    public static final int TOTALPLAYERS = 2;

    private static String gameTerm = "";
    private static String gameAnswer = "";
    private static ArrayList<String> answers = new ArrayList<String>();

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

    public static void setPassword(String password) {
        Psych.password = password;
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

    public static void setGameTerm(String gameTerm) {
        Psych.gameTerm = gameTerm;
    }

    public static void setGameAnswer(String gameAnswer) {
        Psych.gameAnswer = gameAnswer;
    }

    public void resetAnswers(){
        answers = new ArrayList<String>();
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
        }, 0, 1000);
    }

    public static void waitForPlayers() {
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
                if(response.contains("NEWPARTICIPANT"))
                {
                    String[] info = response.split("--");
                    String joinedPlayer = info[1];
                    if(!Psych.getPlayers().contains(joinedPlayer))
                    {
                        Psych.getPlayers().add(joinedPlayer);
                        Psych.createNewGUI(GameState.CREATE);
                    }
                }
                response = "";
                if(Psych.getGameState() != GameState.CREATE)
                    this.cancel();
            }
        }, 0, 1000);


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
                    answers.add(gameAnswer);
                    Psych.createNewGUI(GameState.SUGGESTION);

                }
                response = "";
                if(Psych.getGameState() != GameState.JOINWAIT)
                    this.cancel();
            }
        }, 0, 1000);
    }

    public static void main(String args[]){ new Psych(); }


}
