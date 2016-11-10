import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.PseudoColumnUsage;

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
                    Psych.setUsername(username);
                    Psych.setPassword(password);
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
                Psych.getPlayers().add(Psych.getUsername());
                Psych.setHostToken(hostToken);
                Psych.setJoinKey(hostToken);
                Psych.createNewGUI(GameState.CREATE);
                Psych.waitForPlayers();

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
            Psych.searchForGame(key);
        }
        
        if(e.getActionCommand().equalsIgnoreCase("startGame")){
            if(Psych.getPlayers().size() > Psych.TOTALPLAYERS)
                return;
            Psych.getOut().println(FoilMakerNetworkProtocol.MSG_TYPE.ALLPARTICIPANTSHAVEJOINED + "--" + Psych.getPlayerKey() + "--" + Psych.getHostToken());
            try {
                String wordInfo = Psych.getIn().readLine();
                Psych.setGameTerm(wordInfo.split("--")[1]);
                Psych.setGameAnswer(wordInfo.split("--")[2]);

                Psych.getAnswers().add(Psych.getGameAnswer());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            Psych.createNewGUI(GameState.SUGGESTION);
        }
        
        if(e.getActionCommand().equalsIgnoreCase("suggestion")){
            int numComp = 0;
            String suggestion = "";
            for(Component component : PsychGUI.getGUI().getStuffInFrame()){
                if(component instanceof JTextField){
                    JTextField textField = (JTextField) component;
                    if(numComp == 0){
                        suggestion = textField.getText();
                    }
                    numComp++;
                }
            }
            Psych.getOut().println(FoilMakerNetworkProtocol.MSG_TYPE.PLAYERSUGGESTION + "--" + Psych.getPlayerKey() + "--" + Psych.getJoinKey() + "--" + suggestion);
        }
    }
}
