package shadobot;

import shadobot.CommandHandling.CommandDirectors.*;
import shadobot.CommandHandling.CommandListener;
import shadobot.MiscListeners.GuildJoinListener;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventDispatcher;
import sx.blah.discord.util.DiscordException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Shadobot {
    private static final String PREFIX = "!";
    private static final String VERSION = "ABCDEFG";

    public static IDiscordClient client;
    public static MainWindow UI;

    public static void main(String[] args)
    {
        File tokenFile = new File("token.txt");
        String token;
        try
        {
            Scanner fReader = new Scanner(tokenFile);
            String in = fReader.nextLine();
            in = in.replace("token =", "");
            in = in.trim();
            token = in;
        }
        catch (FileNotFoundException e)
        {
            System.out.println("\"Token\" file missing. Please re-clone the repository.");
            return;
        }

        client = createClient(token,true);
        EventDispatcher dispatcher = client.getDispatcher();


        UI = new MainWindow();
        UI.init(client);

        CommandListener commandListener = new CommandListener(PREFIX);
        dispatcher.registerListener(commandListener);
        dispatcher.registerListener(new GuildJoinListener());

        commandListener.register(new JoinChannel());
        commandListener.register(new RaidMute());
        commandListener.register(new CustomPingCreator(commandListener));
        commandListener.register(new Ping2());


        System.out.println();
        UI.logAdd("");
        UI.logAdd("!!!!!!!!!!!!! SHADOBOT VERSION "+VERSION+" ONLINE !!!!!!!!!!!!!");
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

