package shadobot.CommandHandling;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.listener.message.MessageCreateListener;

import java.util.List;

public class CommandListener implements MessageCreateListener{
    private String prefix;
    private List<Command> prefixedCommands;
    private List<Command> unprefixedCommands;

    public CommandListener(String prefix,List<Command> prefixedCommands,List<Command> unprefixedCommands){
        this.prefix = prefix;
        this.prefixedCommands = prefixedCommands;
        this.unprefixedCommands = unprefixedCommands;
    }

    public void onMessageCreate(DiscordAPI api, Message message) {
        final String messageContent = message.getContent();
        boolean commandFound = false;

        for (Command command : unprefixedCommands) {
            if (command.check(message,"")){
                commandFound = true;
                return;
            }
        }

        if (!commandFound) {
            if (messageContent.startsWith(prefix)) {
                for (Command command : prefixedCommands){
                    if (command.check(message,prefix)){
                        return;
                    }
                }
            }
        }

    }
}
