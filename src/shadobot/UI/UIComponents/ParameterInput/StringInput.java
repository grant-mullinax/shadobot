package shadobot.UI.UIComponents.ParameterInput;

import shadobot.Shadobot;
import sx.blah.discord.handle.obj.IGuild;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StringInput extends JTextField implements ParameterInputComponent {

    public StringInput(IGuild guild){ //needs param for standardized instantiation
        super();
        addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Shadobot.UI.executeCommand(); //is static reference okay?
            }
        });
    }

    public String getValue(){
        return getText();
    }
}
