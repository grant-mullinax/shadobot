package shadobot.CommandHandling;

import de.btobastian.javacord.entities.message.Message;

public abstract class Command {
    protected String[] names;

    public Command(String[] names){
        this.names = names;
    }

    public abstract void execute(Message message,String suffix);

    public boolean check(Message message,String prefix){
        String messageContent = message.getContent();
        for (String name:names){

            /*System.out.println("name:"+name);
            System.out.println(messageContent.length());
            System.out.println(prefix.length()+name.length());*/

            if (messageContent.length()>=prefix.length()+name.length()) { //to avoid substring error

                /*System.out.println(prefix.length());
                System.out.println(messageContent.toLowerCase().substring(prefix.length(), prefix.length() + name.length()));*/

                if (messageContent.toLowerCase().substring(prefix.length(), prefix.length() + name.length()).equals(name)) {

                    if (messageContent.length() == prefix.length() + name.length()){ //if the command has no arguments
                        execute(message,""); //return with no arguments
                    }else {
                        execute(message, messageContent.substring(prefix.length() + name.length() + 1)); //return with suffix
                    }
                    return true;
                }

            }
        }
        return false;
    }
}
