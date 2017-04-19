package shadobot.UI.UIComponents.ParameterInput;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;

import javax.swing.*;
import java.util.HashMap;

public class IChannelInput extends JComboBox implements ParameterInputComponent {
    HashMap<String,IChannel> channelRegister = new HashMap<String, IChannel>();

    public IChannelInput(IGuild guild){
        super();

        String[] channelNames = new String[guild.getChannels().size()];
        for (int i = 0; i < guild.getChannels().size(); i++) {
            IChannel channel = guild.getChannels().get(i);
            channelNames[i] = channel.getName();
            channelRegister.put(channel.getName(),channel);
        }
        DefaultComboBoxModel voiceChannelBoxModel = new DefaultComboBoxModel(channelNames);
        setModel(voiceChannelBoxModel);
    }

    public IChannel getValue(){
        return channelRegister.get(getSelectedItem());
    }
}
