package shadobot.UI.UIComponents.ParameterInput;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;

public class IUserInput extends JComboBox implements ParameterInputComponent {
    HashMap<String,IUser> userRegister = new HashMap<String, IUser>();

    public IUserInput(IGuild guild){
        super();

        List<IUser> users = guild.getUsers();
        String[] roleNames = new String[users.size()];
        for (int i = 0; i < users.size(); i++) {
            IUser user = users.get(i);
            roleNames[i] = user.getName();
            userRegister.put(user.getName(),user);
        }
        DefaultComboBoxModel roleBoxModel = new DefaultComboBoxModel(roleNames);
        setModel(roleBoxModel);
    }

    public IUser getValue(){
        return userRegister.get(getSelectedItem());
    }
}
