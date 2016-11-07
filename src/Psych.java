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

    public static void setGameState(int gameState) {
        Psych.gameState = gameState;
    }

    public static void createNewGUI(int gameState){
        Psych.setGameState(gameState);
        PsychGUI.getGUI().dispose();
        new PsychGUI(getGame(), gameState);
    }

    public static void main(String args[]){
        new Psych();

    }
}
