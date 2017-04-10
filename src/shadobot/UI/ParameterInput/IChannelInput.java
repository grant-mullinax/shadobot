package shadobot.UI.ParameterInput;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;

import javax.swing.*;
import java.util.HashMap;

public class IChannelInput extends ParameterInputElement {
    private JComboBox interfaceElement = new JComboBox();
    HashMap<String,IChannel> channelRegister = new HashMap<String, IChannel>();

    public IChannelInput(int x, int y, int w, int h, JFrame frame, IGuild guild){

        interfaceElement.setBounds(x,y,w,h);

        String[] channelNames = new String[guild.getChannels().size()];
        for (int i = 0; i < guild.getChannels().size(); i++) {
            IChannel channel = guild.getChannels().get(i);
            channelNames[i] = channel.getName();
            channelRegister.put(channel.getName(),channel);
        }
        DefaultComboBoxModel voiceChannelBoxModel = new DefaultComboBoxModel(channelNames);
        interfaceElement.setModel(voiceChannelBoxModel);
        frame.add(interfaceElement);
    }

    public IChannel getValue(){
        return channelRegister.get(interfaceElement.getSelectedItem());
    }
}
