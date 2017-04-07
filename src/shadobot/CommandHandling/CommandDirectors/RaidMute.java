package shadobot.CommandHandling.CommandDirectors;


import shadobot.CommandHandling.CommandAssemblyComponents.Command;
import shadobot.CommandHandling.CommandAssemblyComponents.CommandData;
import shadobot.Shadobot;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.util.concurrent.TimeUnit;


@CommandData(
        aliases = {"e","encounter"},
        description = "toggles a raid encounter, muting scrubs when enabled",
        requiredRole = "296093085069475842"
)
public class RaidMute extends Command{ //todo redo this with new system
    private boolean toggle = false;

    public void execute(IMessage message) throws RateLimitException,DiscordException,
            MissingPermissionsException {
        IGuild guild = message.getChannel().getGuild();

        //System.out.println(message.getAuthor().getConnectedVoiceChannels().size());
        if (message.getAuthor().getConnectedVoiceChannels().size()==0){
            message.reply("youre not in a channel dummy");
            return;
        }

        Shadobot.UI.logAdd(message.getAuthor().getConnectedVoiceChannels().get(0).getName());

        for (IUser user: message.getAuthor().getConnectedVoiceChannels().get(0).getConnectedUsers()){
            Shadobot.UI.logAdd(user.getName());
            if (!user.getRolesForGuild(guild).contains(guild.getRoleByID("296093085069475842"))){
                guild.setMuteUser(user, !toggle);
                toggle = !toggle;
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e){

            }
        }
    }
}