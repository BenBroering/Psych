import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by Ben on 10/31/2016.
 */
public class AListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
        // All this garbage is going to handle button presses.

        if(e.getActionCommand().equalsIgnoreCase("register") || e.getActionCommand().equalsIgnoreCase("login")){
            int numComp = 0;
            String username = "";
            String password = "";
            for(Component component : PsychGUI.getGUI().getStuffInFrame()){
                if(component instanceof JTextField){
                    JTextField textField = (JTextField) component;
                    if(numComp == 0){
                        username = textField.getText();
                    }
                    if (numComp == 1){
                        password = textField.getText();
                    }
                    numComp++;
                }
            }
            if(e.getActionCommand().equals("register")) {
                Psych.getOut().println(FoilMakerNetworkProtocol.MSG_TYPE.CREATENEWUSER + "--" + username + "--" + password);
            }

            if(e.getActionCommand().equals("login")){
                Psych.getOut().println(FoilMakerNetworkProtocol.MSG_TYPE.LOGIN + "--" + username + "--" + password);
                try{
                    String playerKey;
                    do{
                        playerKey = Psych.getIn().readLine();
                    }while ((!playerKey.contains("LOGIN")) || (!playerKey.contains("SUCCESS")));
                    playerKey = playerKey.split("--")[3];
                    Psych.setPlayerKey(playerKey);
                    Psych.createNewGUI(GameState.JOINCREATE);
                }catch (IOException e1){
                    e1.printStackTrace();
                }
            }
        }

        if(e.getActionCommand().equalsIgnoreCase("create")){
            Psych.getOut().println(FoilMakerNetworkProtocol.MSG_TYPE.STARTNEWGAME + "--" + Psych.getPlayerKey());
            try{
                String hostToken;
                do{
                    hostToken = Psych.getIn().readLine();
                }while (!hostToken.contains("SUCCESS"));
                hostToken = hostToken.split("--")[3];
                Psych.setHostToken(hostToken);
                Psych.createNewGUI(GameState.CREATE);
            }catch (IOException e1){
                e1.printStackTrace();
            }

        }

        if(e.getActionCommand().equalsIgnoreCase("join")){
            Psych.createNewGUI(GameState.JOIN);
        }
        
        if(e.getActionCommand().equalsIgnoreCase("joinKey")){
            int numComp = 0;
            String key = "";
            for(Component component : PsychGUI.getGUI().getStuffInFrame()){
                if(component instanceof JTextField){
                    JTextField textField = (JTextField) component;
                    if(numComp == 0){
                        key = textField.getText();
                    }
                    numComp++;
                }
            }
            Psych.setJoinKey(key);
            Psych.createNewGUI(GameState.JOINWAIT);
            Psych.searchForGame(key);
        }
        
        if(e.getActionCommand().equalsIgnoreCase("startGame")){

        }
    }
}
