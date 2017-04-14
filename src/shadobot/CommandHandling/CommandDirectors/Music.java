package shadobot.CommandHandling.CommandDirectors;

import com.github.axet.vget.VGet;
import shadobot.CommandHandling.CommandAssemblyComponents.Command;
import shadobot.CommandHandling.CommandAssemblyComponents.CommandData;
import shadobot.CommandHandling.CommandAssemblyComponents.UserSupplied;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.audio.AudioPlayer;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


//this doesnt do anything yet

@CommandData(
        aliases = {"play"},
        description = "makes the bot join a channel"
)
public class Music extends Command{

    public void execute(@UserSupplied String url, @UserSupplied IGuild guild, IChannel channel) throws RateLimitException,
            DiscordException,
            MissingPermissionsException {
        AudioPlayer audioPlayer = AudioPlayer.getAudioPlayerForGuild(guild);
        try {
            VGet v = new VGet(new URL(url), new File("yt-downloads"));
            v.download();

            audioPlayer.setPaused(false);
        } catch (MalformedURLException e) {
            channel.sendMessage("That URL is invalid!");
        } catch (IOException e) {
            channel.sendMessage("An IO exception occured: " + e.getMessage());
        }
    }
}