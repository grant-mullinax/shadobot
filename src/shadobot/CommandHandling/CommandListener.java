package shadobot.CommandHandling;

import shadobot.CommandHandling.CommandAssemblyComponents.*;
import shadobot.Shadobot;
import sx.blah.discord.api.events.IListener;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

//todo maybe rename this, allow command line handling

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

        if (command!=null) {

            IGuild guild = message.getChannel().getGuild();
            CommandData annotation = command.getClass().getAnnotation(CommandData.class);

            if (annotation!=null) {
                //handle all possible trigger-dependent annotations
                if (annotation.takeChannelMessages() && event.getMessage().getChannel().isPrivate()) return;
                if (annotation.takePrivateMessages() && !event.getMessage().getChannel().isPrivate()) return;

                /*String debugMessage = "with roles: ";
                for (IRole role: message.getAuthor().getRolesForGuild(guild)) debugMessage += role.getID() +", ";
                Shadobot.UI.logAdd(debugMessage);
                Shadobot.UI.logAdd(guild.getRoleByID(annotation.requiredRole()).getID());*/

                if (!annotation.requiredRole().equals("N/A")) {
                    if (guild.getRoleByID(annotation.requiredRole()) != null) {
                        if (!message.getAuthor().getRolesForGuild(guild).contains(guild.getRoleByID(annotation.requiredRole()))){
                            return;
                        }
                    } else {
                        Shadobot.UI.logAdd(command.getClass().getSimpleName() + "'s required role is invalid!");
                    }
                }
            }

            Shadobot.UI.logAdd("executed command "+command.getClass().getSimpleName());

            try{
                execute(command, message, splitMessage);
                if (annotation.deletePrompt()) message.delete();

            } catch (InvalidParamException e) {
                System.err.print(e.getErrorMessage());

            } catch (RateLimitException e) {
                Shadobot.UI.logAdd("Sending messages too quickly!");
                e.printStackTrace();
            } catch (DiscordException e) {
                System.err.print(e.getErrorMessage());
                e.printStackTrace();
            } catch (MissingPermissionsException e) {
                System.err.print("Missing permissions for channel!");
                e.printStackTrace();
            } catch (IllegalAccessException e){
                e.printStackTrace();
            } catch (InvocationTargetException e){
                e.printStackTrace();
            }
        }

        /*for (Command freeCommand: registeredFreeCommands){ todo handle annotations in function
            CommandData annotation = freeCommand.getClass().getAnnotation(CommandData.class);
            if (annotation.takeChannelMessages() && event.getMessage().getChannel().isPrivate()) return;

            if (freeCommand.check(message)) {
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
        }*/
    }

    //todo add nullparamexception
    private void execute(Command command, IMessage message, String[] splitMessage)
            throws IllegalAccessException,InvocationTargetException,InvalidParamException{

        for (Method method:command.getClass().getMethods()){
            if (method.getName().equals("execute")){

                Object[] params = new Object[method.getParameterTypes().length];

                Annotation[][] paramAnnotations = method.getParameterAnnotations();
                Class[] parameterTypes = method.getParameterTypes();

                //todo tell the user if they put invalid param

                int userSuppliedParams = 1; //0 is the command name itself
                for (int i = 0; i < parameterTypes.length; i++) {
                    if (isUserSupplied(paramAnnotations[i]) && splitMessage.length >= userSuppliedParams+1 &&
                            !splitMessage[userSuppliedParams].equals("_")) {
                        /*USER SUPPLIED*/
                        if (parameterTypes[i].equals(String.class)) {
                            params[i] = splitMessage[userSuppliedParams];

                        } else if (parameterTypes[i].equals(IVoiceChannel.class)) {
                            for (IVoiceChannel channel:message.getGuild().getVoiceChannels()){
                                Shadobot.UI.logAdd(channel.getName());
                                if (channel.getName().equals(splitMessage[userSuppliedParams])){
                                    params[i] = channel;
                                    break;
                                }
                            }

                        } else if (parameterTypes[i].equals(IChannel.class)) {
                            for (IChannel channel:message.getGuild().getChannels()){
                                if (channel.getName().equals(splitMessage[userSuppliedParams])){
                                    params[i] = channel;
                                    break;
                                }
                            }

                        } else if (parameterTypes[i].equals(IRole.class)) {
                            for (IRole role:message.getGuild().getRoles()){
                                if (role.getName().equals(splitMessage[userSuppliedParams])){
                                    params[i] = role;
                                    break;
                                }
                            }
                        }else{
                            Shadobot.UI.logAdd("Something has gone wrong! " +Command.class.getSimpleName()+
                                    " objects are not handled by the parameter assembler! (Your execute function has a parameter of inappropriate type.)");
                        }
                        if (params[i]==null) throw new InvalidParamException();

                        userSuppliedParams++; //TODO NOT ADDED IF _ PARAM
                    }else{
                        /*USER OMITTED*/
                        if (parameterTypes[i].equals(IMessage.class)) { //the message itself
                            params[i] = message;
                        } else if (parameterTypes[i].equals(IVoiceChannel.class)) { //the voice channel the user is in
                            params[i] = message.getAuthor().getConnectedVoiceChannels().get(0);
                        } else if (parameterTypes[i].equals(IChannel.class)) { // the chat channel of the message
                            params[i] = message.getChannel();
                        } else if (parameterTypes[i].equals(String[].class)) { //assume they want the splitmessage
                            params[i] = splitMessage;
                        } else if (parameterTypes[i].equals(IGuild.class)) { //get the guild the message was sent in
                            params[i] = message.getGuild();
                        }else{
                            Shadobot.UI.logAdd("Something has gone wrong! "
                                    +Command.class.getSimpleName()+" is looking for an unprovided "+parameterTypes[i].getName());
                        }
                    }
                }
                method.invoke(command, params);
            }
        }
    }

    private boolean isUserSupplied(Annotation[] annotations){
        for (Annotation annotation: annotations){
            if (annotation.annotationType().equals(UserSupplied.class)) {
                return true;
            }
        }
        return false;
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
        Shadobot.UI.logAdd("registering "+commandNetwork.getClass().getSimpleName());
        for (Class commandClass:commandNetwork.getClass().getClasses()){
            //Shadobot.UI.logAdd("registering "+commandClass.getSimpleName()+" from commandnetwork");
            try{
                Command command = (Command)commandClass.getConstructors()[0].newInstance();
                Shadobot.UI.logAdd("registering "+command.getClass().getSimpleName());
                register((Command)commandClass.newInstance());
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void directlyRegister(String alias, Command command){
        registeredCommands.put(alias,command);
    }
    public void directlyRegisterFreeCommand(Command command){
        registeredFreeCommands.add(command);
    }

    public String getPrefix(){
        return prefix;
    }
    public Command getRegisteredCommand(String key){
        return registeredCommands.get(key);
    }
}
