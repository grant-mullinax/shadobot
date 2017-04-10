package shadobot.UI.ParameterInput;

import sx.blah.discord.handle.obj.IGuild;

import javax.swing.*;

/**
 * Created by shado on 4/9/2017.
 */
public class StringInput extends ParameterInputElement {
    private JTextField interfaceElement = new JTextField();

    public StringInput(int x, int y, int w, int h, JFrame frame, IGuild guild){
        interfaceElement.setBounds(x,y,w,h);
        frame.add(interfaceElement);
    }

    public String getValue(){
        return interfaceElement.getText();
    }
}
