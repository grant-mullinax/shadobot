# shadobot
Commands are implimented by creating a class that extends Command with annotaton @CommandData where all relevant info can be inserted. Then the command class should be instantiated and registered to the CommandHandler using the .register() method in main.
###Simple Example:
`@CommandData(
        aliases = {"ping"},
        description = "it pings!",
        example = "!ping"
)
public class Ping extends Command{

    @Override
    public void execute(IMessage message, String args) throws RateLimitException,DiscordException,
            MissingPermissionsException {
        message.reply("pong");
    }
}`
Then, in main:
`commandListener.register(new Ping());`

Commands that are logistically linked can be placed as subclases into a class that extends CommandNetwork with the same syntax as up above. The CommandNetwork should be then be registered, but not the subclasses.

##Advanced Info
Parameters will be dynamically handled using executor-provided information, or information that can be assumed. When executing the command, the user should provide the relevant information in the order it is listed in the execute method. A IVoiceChannel parameter will be assumed to be a voice channel, and the handler will search for a channel with the same name as the parameter, a String parameter will be assumed to be just a string, and so on. All commands are able to be executed via console, (well not actually but when I impliment it this little note will be gone!) but in the console all information will be provided as there is no context to assume it from.
