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

        if(e.getActionCommand().equalsIgnoreCase("register")){
            System.out.println("howdy partener");
            int numComp = 0;
            String username = "";
            String password = "";
            for(Component component : PsychGUI.getGUI().getStuffInFrame()){
                System.out.println((component instanceof JTextField) + "--" + component.toString());
                if(component instanceof JTextField){
                    JTextField textField = (JTextField) component;
                    if(numComp == 0){
                        username = textField.getText();
                        System.out.println(username);
                    }
                    if (numComp == 1){
                        password = textField.getText();
                        System.out.println(password);
                    }
                    numComp++;
                }

            }
            PsychGUI.getPsych().getOut().println(FoilMakerNetworkProtocol.MSG_TYPE.CREATENEWUSER + "--" + username + "--" + password);
            try{
                Psych.getGame().getIn().readLine();
            }catch (IOException el){
                System.out.println("lul");
            }

        }

        if(e.getActionCommand().equalsIgnoreCase("login")){
            System.out.println("Hey");
            //Psych.createNewGUI(GameState.ENDGAME);

        }

        if(e.getActionCommand().equalsIgnoreCase("create")){

        }

        if(e.getActionCommand().equalsIgnoreCase("join")){

        }
        
        if(e.getActionCommand().equalsIgnoreCase("joinKey")){

        }
        
        if(e.getActionCommand().equalsIgnoreCase("startGame")){

        }

    }


}
