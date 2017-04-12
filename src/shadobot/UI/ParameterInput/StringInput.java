package shadobot.UI.ParameterInput;

import sx.blah.discord.handle.obj.IGuild;

import javax.swing.*;

public class StringInput extends JTextField implements ParameterInputComponent {

    public StringInput(IGuild guild){
        super();
    }

    public String getValue(){
        return getText();
    }
}
