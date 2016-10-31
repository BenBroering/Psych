import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Ben on 10/31/2016.
 */
public class AListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equalsIgnoreCase("register")){
            for(Component component : PsychGUI.getCurrentFrame().getContentPane().getComponents()){
                if(component instanceof JTextField){
                    JTextField textField = (JTextField) component;
                    if(textField){
                        
                    }
                    String username = textField.getText();
                    String
                }
            }
        }
        if(e.getActionCommand().equalsIgnoreCase("login")){

        }
    }
}
