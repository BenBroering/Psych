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
            System.out.println("howdy partener");
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
            else if(e.getActionCommand().equals("login")){
                Psych.getOut().println(FoilMakerNetworkProtocol.MSG_TYPE.LOGIN + "--" + username + "--" + password);
                Psych.createNewGUI(GameState.JOINCREATE);

            }

        }

        if(e.getActionCommand().equalsIgnoreCase("create")){
            Psych.createNewGUI(GameState.CREATE);
        }

        if(e.getActionCommand().equalsIgnoreCase("join")){
            Psych.createNewGUI(GameState.JOIN);
        }
        
        if(e.getActionCommand().equalsIgnoreCase("joinKey")){

        }
        
        if(e.getActionCommand().equalsIgnoreCase("startGame")){

        }

    }


}
