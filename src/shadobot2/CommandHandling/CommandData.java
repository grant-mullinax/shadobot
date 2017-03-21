package shadobot2.CommandHandling;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandData {
    String[] aliases();

    String description() default "N/A";

    String example() default "N/A";

    boolean requiresPrefix() default true;

    boolean channelMessages() default true;

    boolean privateMessages() default false;
}
