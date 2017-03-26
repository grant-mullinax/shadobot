package shadobot.CommandHandling.CommandDirectors;


import shadobot.CommandHandling.CommandAssemblyComponents.Command;
import shadobot.CommandHandling.CommandAssemblyComponents.CommandData;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;



@CommandData(
        aliases = {"e","encounter"},
        description = "toggles a raid encounter, muting scrubs when enabled",
        requiredRole = "295104552594833409"
)
public class RaidMute extends Command{
    private boolean toggle = false;

    @Override
    public void execute(IMessage message, String args) throws RateLimitException,DiscordException,
            MissingPermissionsException {
        IGuild guild = message.getChannel().getGuild();

        //System.out.println(message.getAuthor().getConnectedVoiceChannels().size());
        if (message.getAuthor().getConnectedVoiceChannels().size()==0){
            message.reply("youre not in a channel dummy");
            return;
        }

        for (IUser user: message.getAuthor().getConnectedVoiceChannels().get(0).getConnectedUsers()){
            if (!user.getRolesForGuild(guild).contains(guild.getRoleByID("295104552594833409"))){
                guild.setMuteUser(user, toggle);
                toggle = !toggle;
            }
        }
    }
}