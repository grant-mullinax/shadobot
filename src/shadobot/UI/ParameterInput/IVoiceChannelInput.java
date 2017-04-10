package shadobot.UI.ParameterInput;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IVoiceChannel;

import javax.swing.*;
import java.util.HashMap;

public class IVoiceChannelInput extends ParameterInputElement {
    private JComboBox interfaceElement = new JComboBox();
    HashMap<String,IVoiceChannel> voiceChannelRegister = new HashMap<String, IVoiceChannel>();

    public IVoiceChannelInput(int x, int y, int w, int h, JFrame frame, IGuild guild){

        interfaceElement.setBounds(x,y,w,h);

        String[] channelNames = new String[guild.getVoiceChannels().size()];
        for (int i = 0; i < guild.getVoiceChannels().size(); i++) {
            IVoiceChannel voiceChannel = guild.getVoiceChannels().get(i);
            channelNames[i] = voiceChannel.getName();
            voiceChannelRegister.put(voiceChannel.getName(),voiceChannel);
        }
        DefaultComboBoxModel voiceChannelBoxModel = new DefaultComboBoxModel(channelNames);
        interfaceElement.setModel(voiceChannelBoxModel);
        frame.add(interfaceElement);
    }

    public IVoiceChannel getValue(){
        return voiceChannelRegister.get(interfaceElement.getSelectedItem());
    }
}
