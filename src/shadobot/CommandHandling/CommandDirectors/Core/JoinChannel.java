package shadobot.CommandHandling.CommandDirectors.Core;

import shadobot.CommandHandling.CommandAssemblyComponents.Command;
import shadobot.CommandHandling.CommandAssemblyComponents.CommandData;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

@CommandData(
        aliases = {"joinchannel","jc","join"},
        description = "makes the bot join a channel"
)
public class JoinChannel extends Command{

    public void execute(IVoiceChannel channel) throws RateLimitException,DiscordException,
            MissingPermissionsException {
        channel.join();
    }
}