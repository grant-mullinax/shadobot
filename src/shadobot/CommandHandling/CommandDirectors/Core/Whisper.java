package shadobot.CommandHandling.CommandDirectors.Core;

import shadobot.CommandHandling.CommandAssemblyComponents.Command;
import shadobot.CommandHandling.CommandAssemblyComponents.CommandData;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

@CommandData(
        aliases = {"whisper"},
        description = "Whisper someone"
)
public class Whisper extends Command{

    public void execute(IUser user, String string) throws RateLimitException,DiscordException, MissingPermissionsException {
        user.getOrCreatePMChannel().sendMessage(string);

    }
}