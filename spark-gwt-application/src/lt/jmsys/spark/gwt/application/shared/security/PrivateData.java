package lt.jmsys.spark.gwt.application.shared.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marker annotation to mark service parameters which are private (password, person code etc.)
 * and should be kept secure (not logged to debug log, etc.)  
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface PrivateData {

}
