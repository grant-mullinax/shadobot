package shadobot;

import shadobot.CommandHandling.CommandDirectors.CustomPingCreator;
import shadobot.CommandHandling.CommandDirectors.Ping;
import shadobot.CommandHandling.CommandDirectors.RaidMute;
import shadobot.CommandHandling.CommandListener;
import shadobot.ShadobotInterface.MainWindow;
import shadobot.ShadobotInterface.UserInterface;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.util.DiscordException;


public class Shadobot {
    private static final String TOKEN = "MjM5NjEyODM0OTIzNjc1NjUw.C6dhzQ.fn9jOrqeN2fNRhcz4yESeBFvjiY";
    private static final String PREFIX = "!";
    private static final String VERSION = "ABCDEFG";

    public static void main(String[] args)
    {
        UserInterface userInterface = new UserInterface(new MainWindow());

        IDiscordClient client = createClient(TOKEN,true);
        EventDispatcher dispatcher = client.getDispatcher();

        CommandListener commandListener = new CommandListener(PREFIX);
        dispatcher.registerListener(commandListener);

        commandListener.register(new Ping());
        commandListener.register(new RaidMute());
        commandListener.register(new CustomPingCreator(commandListener));

        System.out.println();
        userInterface.logAdd("!!!!!!!!!!!!! SHADOBOT VERSION "+VERSION+" ONLINE !!!!!!!!!!!!!");
    }

    public static IDiscordClient createClient(String token, boolean login) { // Returns a new instance of the Discord client
        ClientBuilder clientBuilder = new ClientBuilder(); // Creates the ClientBuilder instance
        clientBuilder.withToken(token); // Adds the login info to the builder
        try {
            if (login) {
                return clientBuilder.login(); // Creates the client instance and logs the client in
            } else {
                return clientBuilder.build(); // Creates the client instance but it doesn't log the client in yet, you would have to call client.login() yourself
            }
        } catch (DiscordException e) { // This is thrown if there was a problem building the client
            e.printStackTrace();
            return null;
        }
    }
}

