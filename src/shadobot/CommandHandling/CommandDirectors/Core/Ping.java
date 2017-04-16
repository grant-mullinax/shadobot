package shadobot.CommandHandling.CommandDirectors.Core;

import shadobot.CommandHandling.CommandAssemblyComponents.Command;
import shadobot.CommandHandling.CommandAssemblyComponents.CommandData;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

@CommandData(
        aliases = {"ping"},
        description = "it pings!"
)
public class Ping extends Command{

    public void execute(String string, IChannel channel) throws RateLimitException,DiscordException,
            MissingPermissionsException {
        channel.sendMessage(string);
    }
}