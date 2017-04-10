package shadobot.UI.ParameterInput;

import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IVoiceChannel;

import javax.swing.*;
import java.util.HashMap;

public class IVoiceChannelInput extends JComboBox implements ParameterInputComponent {
    HashMap<String,IVoiceChannel> voiceChannelRegister = new HashMap<String, IVoiceChannel>();

    public IVoiceChannelInput(IGuild guild){
        super();

        String[] channelNames = new String[guild.getVoiceChannels().size()];
        for (int i = 0; i < guild.getVoiceChannels().size(); i++) {
            IVoiceChannel voiceChannel = guild.getVoiceChannels().get(i);
            channelNames[i] = voiceChannel.getName();
            voiceChannelRegister.put(voiceChannel.getName(),voiceChannel);
        }
        DefaultComboBoxModel voiceChannelBoxModel = new DefaultComboBoxModel(channelNames);
        setModel(voiceChannelBoxModel);
    }

    public IVoiceChannel getValue(){
        return voiceChannelRegister.get(getSelectedItem());
    }
}
