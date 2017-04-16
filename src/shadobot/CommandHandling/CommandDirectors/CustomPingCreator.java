package shadobot.CommandHandling.CommandDirectors;

import shadobot.CommandHandling.CommandAssemblyComponents.Command;
import shadobot.CommandHandling.CommandAssemblyComponents.CommandData;
import shadobot.CommandHandling.CommandListener;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

@CommandData(
        aliases = {"define"},
        description = "defines a ping"
)
public class CustomPingCreator extends Command {
    CommandListener commandListener;

    public CustomPingCreator(CommandListener commandListener){
        this.commandListener = commandListener;
    }

    public void execute(String arg) {
        String[] args = arg.split(">");
        commandListener.directlyRegister(args[0],new CustomPing(args[1]));
    }

    public class CustomPing extends Command{
        private String reply;

        public CustomPing(String reply){
            this.reply = reply;
        }

        public void execute(IChannel channel) throws RateLimitException,DiscordException,
                MissingPermissionsException {
            channel.sendMessage(reply);
        }
    }
}
