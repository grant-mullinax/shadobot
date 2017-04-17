package shadobot;

import shadobot.CommandHandling.CommandAssemblyComponents.Command;
import shadobot.CommandHandling.CommandDirectors.Core.*;
import shadobot.CommandHandling.CommandDirectors.*;
import shadobot.CommandHandling.CommandListener;
import shadobot.UI.MainWindow;
import shadobot.UI.MiscListeners.GuildJoinListener;
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
    public static CommandListener commandListener;

    public static void main(String[] args)
    {
        UI = new MainWindow();

        File tokenFile = new File("token.txt");
        String token;
        try {
            Scanner fReader = new Scanner(tokenFile);
            String in = fReader.nextLine();
            in = in.replace("token =", "");
            in = in.trim();
            token = in;
        }
        catch (FileNotFoundException e) {
            UI.logAdd("\"Token\" file missing. Please re-clone the repository.");
            return;
        }

        client = createClient(token,true);
        EventDispatcher dispatcher = client.getDispatcher();

        commandListener = new CommandListener(PREFIX);
        dispatcher.registerListener(commandListener);
        dispatcher.registerListener(new GuildJoinListener());

        Command[] commands = {
                //core
                new Help(commandListener),
                new Example(commandListener),
                new Roles(),
                new JoinChannel(),
                new Ping(),
                new Whisper(),

                new SpamPing(),
                new CustomPingCreator(commandListener),
                new Echo(),
                new Music(),
                new RaidMute()
        };

        commandListener.register(commands);

        System.out.println();
        UI.logAdd("");
        UI.logAdd("!!!!!!!!!!!!! SHADOBOT VERSION "+VERSION+" ONLINE !!!!!!!!!!!!!");
    }

    public static IDiscordClient createClient(String token, boolean login) { // Returns a new instance of the Discord client
        ClientBuilder clientBuilder = new ClientBuilder(); // Creates the ClientBuilder instance
        clientBuilder.withToken(token); // Adds the login info to the builder4
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

