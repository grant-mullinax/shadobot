package shadobot.CommandHandling.CommandDirectors;

import shadobot.CommandHandling.CommandAssemblyComponents.Command;
import shadobot.CommandHandling.CommandAssemblyComponents.CommandData;
import shadobot.CommandHandling.CommandAssemblyComponents.UserSupplied;
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

    public void execute(@UserSupplied IGuild guild, @UserSupplied String url, IChannel channel) throws RateLimitException,
            DiscordException,
            MissingPermissionsException {
        AudioPlayer audioPlayer = AudioPlayer.getAudioPlayerForGuild(guild);
        try {
            URL music = new URL(url);
            audioPlayer.queue(music);
            audioPlayer.setPaused(false);
        } catch (MalformedURLException e) {
            channel.sendMessage("That URL is invalid!");
        } catch (IOException e) {
            channel.sendMessage("An IO exception occured: " + e.getMessage());
        } catch (UnsupportedAudioFileException e) {
            channel.sendMessage("That type of file is not supported!");
        }
    }
}