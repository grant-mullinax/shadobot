package shadobot.CommandHandling.CommandDirectors;

import shadobot.CommandHandling.CommandAssemblyComponents.Command;
import shadobot.CommandHandling.CommandAssemblyComponents.CommandData;
import shadobot.CommandHandling.CommandListener;
import sx.blah.discord.handle.obj.IMessage;

@CommandData(
        aliases = {"define"},
        description = "defines a ping"
)
public class CustomPingCreator extends Command {
    CommandListener commandListener;

    public CustomPingCreator(CommandListener commandListener){
        this.commandListener = commandListener;
    }

    public void execute(IMessage message, String suffix) {
        String[] args = suffix.split(">");
        commandListener.directlyRegister(args[0],new CustomPing(args[1]));
    }
}
