package shadobot.CommandHandling.CommandDirectors;

import shadobot.CommandHandling.CommandAssemblyComponents.Command;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public class CustomPing extends Command{
    private String reply;

    public CustomPing(String reply){
        this.reply = reply;
    }

    @Override
    public void execute(IMessage message, String suffix) throws RateLimitException,DiscordException,
            MissingPermissionsException {
        message.reply(reply);
    }
}
