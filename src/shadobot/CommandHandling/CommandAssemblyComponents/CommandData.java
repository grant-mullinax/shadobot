package shadobot.CommandHandling.CommandAssemblyComponents;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandData {
    String[] aliases();

    String description() default "N/A";

    String example() default "N/A";

    String requiredRole() default "N/A";

    boolean requiresPrefix() default true;

    boolean takeChannelMessages() default true;

    boolean takePrivateMessages() default false;

    boolean deletePrompt() default false;
}
