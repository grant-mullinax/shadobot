package shadobot2.CommandHandling.commands;

import de.btobastian.javacord.entities.message.Message;
import shadobot2.CommandHandling.Command;

public class CustomPing {
    private String[] names = new String[1];
    private String reply;

    public CustomPing(String name,String reply){
        this.names[0] = name;
        this.reply = reply;
    }

    public Command init(){
        return new Command(names) {
            @Override
            public void execute(Message message, String suffix) {
                message.reply(reply);
            }
        };
    }
}
