package shadobot.CommandHandling.CommandDirectors.Core;

import shadobot.CommandHandling.CommandAssemblyComponents.Command;
import shadobot.CommandHandling.CommandAssemblyComponents.CommandData;
import shadobot.CommandHandling.CommandAssemblyComponents.UserSupplied;
import shadobot.CommandHandling.CommandListener;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

@CommandData(
        aliases = {"help"},
        description = "Supplies the description for a command."
)
public class Help extends Command{
    CommandListener commandListener;

    public Help(CommandListener commandListener){
        this.commandListener = commandListener;
    }

    public void execute(@UserSupplied String commandName, IChannel channel) throws
            RateLimitException,
            DiscordException,
            MissingPermissionsException {
        Command registeredCommand = commandListener.getRegisteredCommand(commandListener.getPrefix()+commandName);

        if (registeredCommand!= null){
            channel.sendMessage(registeredCommand.getClass().getAnnotation(CommandData.class).description());
        }
    }
}