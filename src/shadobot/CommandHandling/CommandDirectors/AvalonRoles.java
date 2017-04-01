package shadobot.CommandHandling.CommandDirectors;

import shadobot.CommandHandling.CommandAssemblyComponents.Command;
import shadobot.CommandHandling.CommandAssemblyComponents.CommandData;
import shadobot.CommandHandling.CommandAssemblyComponents.CommandNetwork;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.HashMap;

/**
 * Created by shado on 3/26/2017.
 */
public class AvalonRoles extends CommandNetwork {
    private HashMap<IUser,String> playerRoles = new HashMap<IUser, String>();
    private IChannel responseChannel;

    private boolean receivingRoles = false;

    @CommandData(
            aliases = {"newAvalon"},
            description = "creates a new avalon role handler"
    )
    public class newAvalon extends Command {
        public void execute(IMessage message, String args) throws RateLimitException,DiscordException,
                MissingPermissionsException {
            responseChannel = message.getChannel();
        }
    }

    @CommandData(
            aliases = {"good guy","bad guy","merlin","percival","morgana"},
            description = "defines your role",
            takeChannelMessages = false,
            requiresPrefix = false
    )
    public class submitRole extends Command{
        public void execute(IMessage message, String args) throws RateLimitException,DiscordException,
                MissingPermissionsException {
            playerRoles.put(message.getAuthor(),message.getContent().split(" ")[0]);
            responseChannel.sendMessage(message.getAuthor().mention() + " has submitted their role!");
        }
    }

    public class sendRoles extends Command{
        public void execute(IMessage message, String args) throws RateLimitException,DiscordException,
                MissingPermissionsException {
            for (IUser user: playerRoles.keySet()){

            }
        }
    }
}
