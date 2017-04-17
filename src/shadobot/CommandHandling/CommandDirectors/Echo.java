package shadobot.CommandHandling.CommandDirectors;

import shadobot.CommandHandling.CommandAssemblyComponents.Command;
import shadobot.CommandHandling.CommandAssemblyComponents.CommandData;
import shadobot.Shadobot;
import sx.blah.discord.handle.audio.IAudioProvider;
import sx.blah.discord.handle.audio.impl.AudioManager;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.audio.AudioPlayer;


//this doesnt do anything yet

@CommandData(
        aliases = {"echo"},
        description = "makes the bot join a channel"
)
public class Echo extends Command{

    public void execute(IGuild guild) throws RateLimitException, DiscordException, MissingPermissionsException {
        AudioPlayer audioPlayer = AudioPlayer.getAudioPlayerForGuild(guild);
        IAudioProvider audioProvider = new AudioManager(guild).getAudioProvider();
        audioPlayer.setPaused(false);
        Shadobot.UI.logAdd((audioPlayer.isReady()) ? "true" : "false");
        try {
            audioPlayer.queue(audioProvider);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}