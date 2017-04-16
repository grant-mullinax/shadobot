package shadobot.CommandHandling.CommandAssemblyComponents;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface UserCannotSupply {
    String description() default "N/A";
}
