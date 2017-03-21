package shadobot2.CommandHandling.commands;

import de.btobastian.javacord.entities.message.Message;
import shadobot2.CommandHandling.Command;

import java.util.List;

public class CustomPingCreator {
    private static final String[] names = {"define"};
    private String reply;

    public Command init(final List<Command> unprefixedCommands){
        return new Command(names) {
            @Override
            public void execute(Message message,String suffix) {
                String[] args = suffix.split(">");
                unprefixedCommands.add(new CustomPing(args[0],args[1]).init());
            }
        };
    }
}
