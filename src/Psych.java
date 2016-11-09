import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
    private static int numPlayers = 0;

    public static final int MAXPLAYERS = 2;

    Psych(){
        game = this;
        try{
            Socket socket = new Socket(serverIP, serverPort);
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.isr = new InputStreamReader(socket.getInputStream());
            this.in = new BufferedReader(isr);
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

    public static InputStreamReader getIsr() {
        return isr;
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

    public static void setGameState(int gameState) {
        Psych.gameState = gameState;
    }

    public static void createNewGUI(int gameState){
        Psych.setGameState(gameState);
        PsychGUI.getGUI().dispose();
        new PsychGUI(getGame(), gameState);
    }

    public static void searchForGame(String key) {
        String response = "";
        do{
            out.println(FoilMakerNetworkProtocol.MSG_TYPE.JOINGAME + "--" + playerKey + "--" + key);
            try {
                response = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }while (!response.contains("JOINGAME") || !response.contains("SUCCESS"));

    }

    public static void main(String args[]){
        new Psych();

    }

}
