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

    private static final String TOKEN = "MjM5NjEyODM0OTIzNjc1NjUw.C6dhzQ.fn9jOrqeN2fNRhcz4yESeBFvjiY";

    private static final String prefix = "!";
    private static List<Command> unprefixedCommands = new ArrayList<Command>();

    private static List<Command> prefixedCommands = new ArrayList<Command>(Arrays.asList(
            new CustomPingCreator().init(unprefixedCommands),
            new Ping().init()
    ));

    public static void main(String[] args)
    {
        DiscordAPI api = Javacord.getApi(TOKEN,true);

        // connect
        api.connect(new FutureCallback<DiscordAPI>()
        {
            public void onSuccess(DiscordAPI api)
            {
                // register listener
                api.registerListener(new CommandListener(prefix,prefixedCommands,unprefixedCommands));
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!! SHADOBOT ONLINE !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                Object shadobotWindow = new MainWindow();
            }

			public void onFailure(Throwable t)
			{
                t.printStackTrace();
            }
        });
    }
}
