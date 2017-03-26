package shadobot.CommandHandling.CommandDirectors;

import shadobot.CommandHandling.CommandAssemblyComponents.Command;
import shadobot.CommandHandling.CommandAssemblyComponents.CommandBuilder;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public class CustomPing extends CommandBuilder{
    private String reply;

    public Command buildCommand(){
        return new Command() {
            @Override
            public void execute(IMessage message, String suffix) {
                try {
                    message.reply(reply);
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
        };
    }
}
