package eu.itreegroup.spark.facebook.helper;

import org.apache.log4j.Logger;

public class FacebookExceptionHelper {

    public static void throwRuntimeException(Logger logger, String message) {
        logger.error(message);
        throw new RuntimeException(message);
    }

}