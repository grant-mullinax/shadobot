package shadobot2.CommandHandling.commands;

import de.btobastian.javacord.entities.message.Message;
import shadobot2.CommandHandling.Command;

public class Ping{
    private static final String[] names = {"ping"};

    public Command init(){
        return new Command(names) {
            @Override
            public void execute(Message message,String suffix) {
                message.reply("pong");
            }
        };
    }
}
