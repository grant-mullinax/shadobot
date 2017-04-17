package shadobot.UI.MiscListeners;

import shadobot.Shadobot;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.GuildCreateEvent;
import sx.blah.discord.handle.obj.IGuild;

public class GuildJoinListener implements IListener<GuildCreateEvent> {
    public void handle(GuildCreateEvent guildCreateEvent){
        IGuild guild = guildCreateEvent.getGuild();
        Shadobot.UI.logAdd("Connecting to: "+guild.getName());
        Shadobot.UI.addGuild(guild);
        Shadobot.UI.addConnectedChannels(guild);
    }
}
