package shadobot.CommandHandling.CommandDirectors;

import shadobot.CommandHandling.CommandAssemblyComponents.Command;
import shadobot.CommandHandling.CommandAssemblyComponents.CommandData;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

@CommandData(
        aliases = {"spamping"},
        description = "it pings a lot!"//,
        //requiredRole = "299757370282606602"
)
public class SpamPing extends Command{

    public void execute(String user, String count,IChannel channel) throws RateLimitException,
            DiscordException,
            MissingPermissionsException {
        for (int i = 0; i < Integer.parseInt(count); i++) {
            channel.sendMessage(user);
        }
    }
}