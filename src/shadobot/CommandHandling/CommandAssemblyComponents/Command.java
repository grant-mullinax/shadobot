package shadobot.CommandHandling.CommandAssemblyComponents;

import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

public abstract class Command {
    public boolean check(IMessage message){
        return true;
    }

    public abstract void execute(IMessage message, String suffix) throws RateLimitException,DiscordException,
    MissingPermissionsException;

    private void postExecutionRoutine(IMessage message){}
}