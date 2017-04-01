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
public class Ping2 extends Command{

    public void execute(String arg, String arg2, IMessage message) throws RateLimitException,DiscordException,
            MissingPermissionsException {
        message.reply("pong "+arg+" 2 "+arg2);
    }
}