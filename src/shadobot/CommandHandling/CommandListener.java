package shadobot.CommandHandling;

import shadobot.CommandHandling.CommandAssemblyComponents.Command;
import shadobot.CommandHandling.CommandAssemblyComponents.CommandData;
import shadobot.CommandHandling.CommandAssemblyComponents.CommandNetwork;
import shadobot.Shadobot;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class CommandListener implements IListener<MessageReceivedEvent> {
    private String prefix;
    private HashMap<String,Command> registeredCommands = new HashMap<String, Command>();
    private ArrayList<Command> registeredFreeCommands = new ArrayList<Command>();

    public CommandListener(String prefix) {
        this.prefix = prefix;
    }

    public void handle(MessageReceivedEvent event) {
        final IMessage message = event.getMessage();

        if (message.getAuthor().isBot()) return;

        final String[] splitMessage = message.getContent().split(" ");
        final Command command = registeredCommands.get(splitMessage[0].toLowerCase());

        if (command!=null) if (command.check(message)) {

            IGuild guild = message.getChannel().getGuild();
            CommandData annotation = command.getClass().getAnnotation(CommandData.class);

            //handle all possible trigger-dependent annotations
            if (annotation.takeChannelMessages() && event.getMessage().getChannel().isPrivate()) return;
            if (annotation.takePrivateMessages() && !event.getMessage().getChannel().isPrivate()) return;

            /*if (annotation.requiredRole()!="")
                if (!message.getAuthor().getRolesForGuild(guild).contains(guild.getRoleByID(annotation.requiredRole())))
                    return;*/

            Shadobot.UI.logAdd("executed command "+command.getClass().getSimpleName());

            try{
                execute(command, message, splitMessage);
                if (annotation.deletePrompt()) message.delete();

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
        }

        /*for (Command freeCommand: registeredFreeCommands){
            CommandData annotation = freeCommand.getClass().getAnnotation(CommandData.class);
            if (annotation.takeChannelMessages() && event.getMessage().getChannel().isPrivate()) return;

            if (freeCommand.check(message)) {
                try {
                    freeCommand.execute(message, (splitMessage.length > 1) ? splitMessage[1] : "");
                    if (annotation.deletePrompt()) message.delete();
                } catch (RateLimitException e) {
                    System.err.print("Sending messages too quickly!");
                    e.printStackTrace();
                } catch (DiscordException e) {
                    System.err.print(e.getErrorMessage());
                    e.printStackTrace();
                } catch (MissingPermissionsException e) {}
            }
        }*/
    }

    private void execute(Command command, IMessage message, String[] splitMessage){ //todo can potentially reduce
        // runtimes by adding a execute method registry
        for (Method method:command.getClass().getMethods()){
            if (method.getName().equals("execute")){

                Object[] params = new Object[method.getParameterTypes().length];
                int userSuppliedParams = 0;
                int i = 0;

                for (Class param: method.getParameterTypes()){
                    if (param.equals(IMessage.class)){
                        params[i] = message;
                    }else if(param.equals(String.class)){
                        params[i] = splitMessage[userSuppliedParams+1];
                        userSuppliedParams++;
                    }else if(param.equals(IChannel.class)){
                        params[i] = message.getChannel();
                    }else if(param.equals(IGuild.class)){
                        params[i] = message.getChannel().getGuild();
                    }
                    i++;
                }
                try {
                    method.invoke(command, params);
                } catch (IllegalAccessException e){
                    e.printStackTrace();
                } catch (InvocationTargetException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void register(Command command){
        CommandData annotation = command.getClass().getAnnotation(CommandData.class);

        if (annotation != null) {
            for (String alias : annotation.aliases()) {
                registeredCommands.put(
                        (annotation.requiresPrefix() ? prefix:"")+alias.toLowerCase(),
                        command
                );

                Shadobot.UI.logAdd("registered "+command.getClass().getSimpleName()+" as \""+alias+"\"");
            }
        }else{
            Shadobot.UI.logAdd(command.getClass().getName()+" has no annotation");
        }
    }

    public void register(CommandNetwork commandNetwork){
        for (Class commandClass:commandNetwork.getClass().getClasses()){
            try{
                register((Command)commandClass.newInstance());
            } catch(InstantiationException e) {
                System.out.println(e.toString());
            } catch(IllegalAccessException e) {
                System.out.println(e.toString());
            }
        }
    }

    public void directlyRegister(String alias, Command command){
        registeredCommands.put(alias,command);
    }
    public void directlyRegisterFreeCommand(Command command){
        registeredFreeCommands.add(command);
    }
}
