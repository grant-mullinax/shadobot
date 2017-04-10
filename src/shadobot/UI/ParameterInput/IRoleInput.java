package shadobot.UI.ParameterInput;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;

import javax.swing.*;
import java.util.HashMap;

public class IRoleInput extends JComboBox implements ParameterInputComponent {
    HashMap<String,IRole> roleRegister = new HashMap<String, IRole>();

    public IRoleInput(IGuild guild){
        super();

        String[] roleNames = new String[guild.getRoles().size()];
        for (int i = 0; i < guild.getRoles().size(); i++) {
            IRole role = guild.getRoles().get(i);
            roleNames[i] = role.getName();
            roleRegister.put(role.getName(),role);
        }
        DefaultComboBoxModel roleBoxModel = new DefaultComboBoxModel(roleNames);
        setModel(roleBoxModel);
    }

    public IRole getValue(){
        return roleRegister.get(getSelectedItem());
    }
}
