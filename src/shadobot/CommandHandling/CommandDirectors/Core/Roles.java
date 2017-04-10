package shadobot.CommandHandling.CommandDirectors.Core;

import shadobot.CommandHandling.CommandAssemblyComponents.Command;
import shadobot.CommandHandling.CommandAssemblyComponents.CommandData;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

@CommandData(
        aliases = {"roles"},
        description = "Gets all the roles and ids for a given server"
)
public class Roles extends Command{

    public void execute(IGuild guild, IChannel channel) throws RateLimitException,DiscordException,
            MissingPermissionsException {
        String text = "Roles: \n```everyone : "+guild.getRoles().get(0).getID()+"\n"; //so @everyone doesn't ping everyone

        for (int i = 1; i < guild.getRoles().size() ; i++) {
            IRole role = guild.getRoles().get(i);
            text+=role.getName() + " : "+role.getID()+"\n";
        }

        channel.sendMessage(text+"```");
    }
}