package shadobot2.CommandHandling;

import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.entities.message.Message;
import de.btobastian.javacord.listener.message.MessageCreateListener;

import java.lang.reflect.Method;
import java.util.HashMap;

public class CommandListener implements MessageCreateListener{
    private String prefix;
    private HashMap<String,Command> commands;

    public CommandListener(String prefix){
        this.prefix = prefix;
    }

    public void registerCommand(Command command) {
        for (Method method : command.getClass().getMethods()) {
            CommandData annotation = method.getAnnotation(CommandData.class);

            if (annotation != null) {
                for (String alias : annotation.aliases()) {
                    if (annotation.requiresPrefix()) {
                        commands.put(prefix+alias.toLowerCase(), command);
                    }else{
                        commands.put(alias.toLowerCase(), command);
                    }
                }
            }

        }
    }

    public void onMessageCreate(DiscordAPI api, Message message) {
        final String[] splitMessage = message.getContent().split(" ");
        final Command command = commands.get(splitMessage[0]);
        if (command!=null){
            command.execute();
        }

    }
}
