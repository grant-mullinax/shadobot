package shadobot;

import com.google.common.util.concurrent.FutureCallback;
import de.btobastian.javacord.DiscordAPI;
import de.btobastian.javacord.Javacord;
import shadobot.CommandHandling.Command;
import shadobot.CommandHandling.CommandListener;
import shadobot.CommandHandling.commands.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ShadobotWindow.MainWindow;;


public class Shadobot
{

    private static final String TOKEN = "Mjk0OTkwNTk5NDExNTk3MzEy.C7dMYg.Wt8Skpog6u9gKKpkPIznK05QzuI";

    private static final String prefix = "!";
    private static List<Command> unprefixedCommands = new ArrayList<Command>();

    private static List<Command> prefixedCommands = new ArrayList<Command>(Arrays.asList(
            new CustomPingCreator().init(unprefixedCommands),
            new Ping().init()
    ));
    
    private static DiscordAPI api;

    public static void main(String[] args)
    {
        api = Javacord.getApi(TOKEN,true);
        final MainWindow shadobotWindow = new MainWindow();

        // connect
        api.connect(new FutureCallback<DiscordAPI>()
        {
            public void onSuccess(DiscordAPI api)
            {
                // register listener
                api.registerListener(new CommandListener(prefix,prefixedCommands,unprefixedCommands));
                System.out.println();
                shadobotWindow.logAdd(">>> SHADOBOT ONLINE <<<");
                api.setGame("Neptunic is God");
            }

			public void onFailure(Throwable t)
			{
                t.printStackTrace();
            }
        });
    }
    
    public static DiscordAPI getAPI()
    {
    	return api;
    }
}
