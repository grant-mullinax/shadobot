package shadobot.CommandHandling.CommandDirectors;


import shadobot.CommandHandling.CommandAssemblyComponents.Command;
import shadobot.CommandHandling.CommandAssemblyComponents.CommandBuilder;
import shadobot.CommandHandling.CommandAssemblyComponents.CommandData;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;



@CommandData(
        aliases = {"e","encounter"},
        description = "toggles a raid encounter, muting scrubs when enabled"
)
public class RaidMute extends CommandBuilder{
    private boolean toggle = false;

    public Command buildCommand(){

        return new Command() {
            @Override
            public void execute(IMessage message, String args) {
                IGuild guild = message.getChannel().getGuild();

                //System.out.println(message.getAuthor().getConnectedVoiceChannels().size());
                if (message.getAuthor().getConnectedVoiceChannels().size()==0){
                    try {
                        message.reply("youre not in a channel dummy");
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
                    return;
                }

                for (IUser user: message.getAuthor().getConnectedVoiceChannels().get(0).getConnectedUsers()){
                    if (!user.getRolesForGuild(guild).contains(guild.getRoleByID("295104552594833409"))){

                        try {
                            guild.setMuteUser(user, toggle);
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

                        toggle = !toggle;
                    }
                }
            }

            @Override
            public boolean check(IMessage message){
                IGuild guild = message.getChannel().getGuild();

                return message.getAuthor().getRolesForGuild(guild).contains(guild.getRoleByID("295104552594833409"));
            }
        };
    }
}