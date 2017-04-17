package shadobot.CommandHandling.CommandAssemblyComponents;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ParamInfo {
    boolean userCannotSupply() default false;
    int consoleInputLength() default 100;
    String description() default "N/A";
}
