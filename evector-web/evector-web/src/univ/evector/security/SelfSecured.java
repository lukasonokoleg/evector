package univ.evector.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * By default all beans before they are returned by service are additionally checked has user rights to access them.
 * Use this annotation on service method to indicate what additional check should be skipped.  
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SelfSecured {

}
