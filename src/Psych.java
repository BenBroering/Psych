import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Ben on 10/26/2016.
 */
public class Psych {

    PsychGUI gui;
    private String serverIP = "localhost";
    private int serverPort = 9999;

    Psych(){
        gui = new PsychGUI(this);
        try{
            Socket socket = new Socket(serverIP, serverPort);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            BufferedReader in = new BufferedReader(isr);

            //out.println(FoilMakerNetworkProtocol.MSG_TYPE.CREATENEWUSER + "--Ben--ben123");
            //String serverMessage = in.readLine();





        }catch(IOException e){
            e.printStackTrace();
        }



    }

    public static void main(String args[]){
        new Psych();

    }
}
