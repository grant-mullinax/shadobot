package shadobot.CommandHandling.CommandDirectors;

import shadobot.CommandHandling.CommandAssemblyComponents.Command;
import shadobot.CommandHandling.CommandAssemblyComponents.CommandData;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.audio.AudioPlayer;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


//this doesnt do anything yet

@CommandData(
        aliases = {"play"},
        description = "makes the bot join a channel"
)
public class Music extends Command{

    public void execute(String url, IGuild guild, IChannel channel) throws RateLimitException,
            DiscordException,
            MissingPermissionsException {
        AudioPlayer audioPlayer = AudioPlayer.getAudioPlayerForGuild(guild);
        try {
            audioPlayer.queue(new URL(url));
            audioPlayer.setPaused(false);
        } catch (MalformedURLException e) {
            channel.sendMessage("That URL is invalid!");
        } catch (UnsupportedAudioFileException e) {
            channel.sendMessage("That file is invalid!");
        } catch (IOException e) {
            channel.sendMessage("An IO exception occured: " + e.getMessage());
        }
    }
}