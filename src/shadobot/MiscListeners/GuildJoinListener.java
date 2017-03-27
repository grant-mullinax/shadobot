package shadobot.MiscListeners;

import shadobot.Shadobot;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.GuildCreateEvent;

public class GuildJoinListener implements IListener<GuildCreateEvent> {
    public void handle(GuildCreateEvent guildCreateEvent){
        Shadobot.UI.logAdd("Connecting to: "+guildCreateEvent.getGuild().getName());
        Shadobot.UI.addServer(guildCreateEvent.getGuild());
    }
}
