package shadobot.CommandHandling.CommandDirectors;

import shadobot.CommandHandling.CommandAssemblyComponents.Command;
import shadobot.CommandHandling.CommandAssemblyComponents.CommandData;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

@CommandData(
        aliases = {"ping"},
        description = "it pings!"
)
public class Ping extends Command{

    public void execute(IMessage message, String args) throws RateLimitException,DiscordException,
            MissingPermissionsException {
        message.reply("pong");
    }
}