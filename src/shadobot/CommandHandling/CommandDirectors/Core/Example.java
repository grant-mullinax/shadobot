package shadobot.CommandHandling.CommandDirectors.Core;

import shadobot.CommandHandling.CommandAssemblyComponents.Command;
import shadobot.CommandHandling.CommandAssemblyComponents.CommandData;
import shadobot.CommandHandling.CommandAssemblyComponents.ParamInfo;
import shadobot.CommandHandling.CommandListener;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

@CommandData(
        aliases = {"example","usage"},
        description = "Supplies an example usage for a command."
)
public class Example extends Command{
    CommandListener commandListener;

    public Example(CommandListener commandListener){
        this.commandListener = commandListener;
    }

    public void execute(@ParamInfo String commandName, IChannel channel) throws
            RateLimitException,
            DiscordException,
            MissingPermissionsException {
        Command registeredCommand = commandListener.getRegisteredCommand(commandListener.getPrefix()+commandName);

        if (registeredCommand!= null){
            channel.sendMessage(registeredCommand.getClass().getAnnotation(CommandData.class).example());
        }
    }
}