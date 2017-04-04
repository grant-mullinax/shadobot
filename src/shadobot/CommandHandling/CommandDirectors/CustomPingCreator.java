package shadobot.CommandHandling.CommandDirectors;

import shadobot.CommandHandling.CommandAssemblyComponents.Command;
import shadobot.CommandHandling.CommandAssemblyComponents.CommandData;
import shadobot.CommandHandling.CommandAssemblyComponents.UserSupplied;
import shadobot.CommandHandling.CommandListener;

@CommandData(
        aliases = {"define"},
        description = "defines a ping"
)
public class CustomPingCreator extends Command {
    CommandListener commandListener;

    public CustomPingCreator(CommandListener commandListener){
        this.commandListener = commandListener;
    }

    public void execute(@UserSupplied String arg) {
        String[] args = arg.split(">");
        commandListener.directlyRegister(args[0],new CustomPing(args[1]));
    }
}
