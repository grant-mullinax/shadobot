package shadobot.UI.ParameterInput;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;

import javax.swing.*;
import java.util.HashMap;

public class IRoleInput extends ParameterInputElement {
    private JComboBox interfaceElement = new JComboBox();
    HashMap<String,IRole> roleRegister = new HashMap<String, IRole>();

    public IRoleInput(int x, int y, int w, int h, JFrame frame, IGuild guild){

        interfaceElement.setBounds(x,y,w,h);

        String[] roleNames = new String[guild.getRoles().size()];
        for (int i = 0; i < guild.getRoles().size(); i++) {
            IRole role = guild.getRoles().get(i);
            roleNames[i] = role.getName();
            roleRegister.put(role.getName(),role);
        }
        DefaultComboBoxModel roleBoxModel = new DefaultComboBoxModel(roleNames);
        interfaceElement.setModel(roleBoxModel);
        frame.add(interfaceElement);
    }

    public IRole getValue(){
        return roleRegister.get(interfaceElement.getSelectedItem());
    }
}
