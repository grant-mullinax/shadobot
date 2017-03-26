package shadobot.CommandHandling;

import shadobot.CommandHandling.CommandAssemblyComponents.Command;
import shadobot.CommandHandling.CommandAssemblyComponents.CommandData;
import shadobot.ShadobotInterface.UserInterface;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.HashMap;

public class CommandListener implements IListener<MessageReceivedEvent> {
    private String prefix;
    private HashMap<String,Command> registeredCommands = new HashMap<String, Command>();

    public CommandListener(String prefix) {
        this.prefix = prefix;
    }

    public void handle(MessageReceivedEvent event) {
        final IMessage message = event.getMessage();

        //if (message.getAuthor().isBot()) return;

        final String[] splitMessage = message.getContent().split(" ");
        final Command command = registeredCommands.get(splitMessage[0]);

        if (command!=null) if (command.check(message)) {
            UserInterface.logAdd("executed command "+command.getClass().getSimpleName());

            try{
                command.execute(message, (splitMessage.length > 1) ? splitMessage[1] : "");
            } catch (RateLimitException e) {
                System.err.print("Sending messages too quickly!");
                e.printStackTrace();
            } catch (DiscordException e) {
                System.err.print(e.getErrorMessage());
                e.printStackTrace();
            } catch (MissingPermissionsException e) {
                System.err.print("Missing permissions for channel!");
                e.printStackTrace();
            }
        }
    }

    public void register(Command command){
        CommandData annotation = command.getClass().getAnnotation(CommandData.class);

        if (annotation != null) {
            for (String alias : annotation.aliases()) {
                registeredCommands.put(
                        (annotation.requiresPrefix() ? prefix:"")+alias.toLowerCase(),
                        command
                );

                UserInterface.logAdd("registered "+command.getClass().getSimpleName()+" as \""+alias+"\"");
            }
        }else{
            UserInterface.logAdd(command.getClass().getName()+" has no annotation");
        }
    }

    public void directlyRegister(String alias, Command command){
        registeredCommands.put(alias,command);
    }
}
