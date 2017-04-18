Please provide your Discord Application token in a token.txt file with the format:  
`token = 324324gd32fdBLAHgK3424J423BLAH2kgdsJ5J32`  
*[Token can be retrieved here](https://discordapp.com/developers/applications/)*

# shadobot  

Commands are implemented by creating a class that extends Command with annotaton @CommandData where all relevant info can be inserted. Then the command class should be instantiated and registered to the CommandHandler using the .register() method in main.

Parameters will be dynamically handled and passed to the execute method using information provided in the message. When executing the command, the user should provide the relevant information in the order the parameters are listed in the execute method seperated by spaces. Some information can also be left to be assumed by the bot, either by having a _ in place of the parameter, or leaving the parameter out entirely if it is at the end. A IVoiceChannel parameter will be assumed to be a voice channel, and the handler will search for a channel with the same name as the parameter, a IRole parameter will be assumed to be the role's name, and so on. Things like Strings, which cant logistically be assumed, are not possible to omit.

All commands are able to be executed via console by entering in the command name in the bottom left. Just define the guild you would like to operate in the context of and just enter in the command name in the bottom left without the prefix. Once you press enter input boxes will appear that will contain all relevant choices for each parameter. All information will have to be provided as there is no context to assume it from. Once all parameters are inputted, press execute on the bottom right.

### Simple Example:  
```
@CommandData(
        aliases = {"ping"},
        description = "it pings!"
)
public class DoublePing extends Command{

    public void execute(String string1, IChannel channel) throws RateLimitException,DiscordException,
            MissingPermissionsException {
        channel.sendMessage(string1);
    }
}
```
Then, in main:  
```
commandListener.register(new DoublePing());
```
To execute, say any either the following:

For the ping to be sent to the channel the command was sent in:
```
!ping STRINGREPLY
```

To ping a spesific channel with your string:
```
!ping STRINGREPLY CHANNELNAME
```

### Usable objects in execute method:
####String
read as itself, cannot be assumed

####IChannel
read as chat channel name in guild, assumed as channel the message was sent in

####IVoiceChannel
read as voice channel name in guild, assumed as channel the sender is currently in (if they are in one)

####IRole
read as role name in guild, cannot be assumed

####IUser
read as @mention, cannot be assumed

####IMessage
the message itself. will only be assumed, information should not be provided in the user message.

####IGuild 
the guild the message was sent in. will only be assumed, information should not be provided in the user message.

### @CommandData annotation parameters:
#### String[] aliases
##### REQUIRED
the initial string(s) that follow the command prefix (default: !) that will trigger this message
#### String description
##### default "N/A"
the description that will be provided by !help [commandname]
####String example
#####default "N/A"
the example that will be provided by !help [commandname]
#### String requiredRole
##### default "N/A"
the role required to execute this command [WILL ALLOW MULTIPLE SOON]
#### boolean requiresPrefix
##### default true
if the user has to say the prefix before the command alias to execute this command
#### boolean takeChannelMessages
##### default true
if this command can be executed by messages in channels in guilds
#### boolean takePrivateMessages
##### default false
if this command can be executed from private messages
#### boolean deletePrompt
##### default false
if the message that triggers the activation of this command should be deleted

