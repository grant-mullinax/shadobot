package shadobot.CommandHandling.CommandAssemblyComponents;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ParamInfo {
    boolean userCannotSupply() default false;
    String description() default "N/A";
}
