package shadobot.CommandHandling.CommandDirectors;

import shadobot.CommandHandling.CommandAssemblyComponents.Command;
import shadobot.CommandHandling.CommandAssemblyComponents.CommandBuilder;
import shadobot.CommandHandling.CommandAssemblyComponents.CommandData;
import shadobot.CommandHandling.CommandListener;
import sx.blah.discord.handle.obj.IMessage;

@CommandData(
        aliases = {"define"},
        description = "it ping"
)
public class CustomPingCreator extends CommandBuilder {
    CommandListener commandListener;
    private String reply;

    public CustomPingCreator(CommandListener commandListener){
        this.commandListener = commandListener;
    }

    @Override
    public Command buildCommand(){
        return new Command() {
            @Override
            public void execute(IMessage message, String suffix) {
                String[] args = suffix.split(">");
                commandListener.register(new CustomPing());
            }
        };
    }
}
