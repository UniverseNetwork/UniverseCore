package id.universenetwork.universecore.manager;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInfo {
    String name();
    int argsLength() default 0;
    String permission() default "";
    boolean onlyPlayer();
    String usage();
}
