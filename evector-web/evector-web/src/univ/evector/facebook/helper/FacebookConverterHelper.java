package univ.evector.facebook.helper;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import univ.evector.facebook.converter.FacebookConverter;

public class FacebookConverterHelper {

    private final static Logger LOGGER = Logger.getLogger(Logger.class);

    public static <E extends Serializable, T> LinkedList<E> convert(Iterator<T> iterator, FacebookConverter<E, T> converter) {
        if (converter == null) {
            String message = " Input variable converter must be initialized! ";
            LOGGER.error(message);
            throw new NullPointerException(message);
        }
        LinkedList<E> retVal = new LinkedList<>();
        if (iterator != null) {
            while (iterator.hasNext()) {
                T inValue = iterator.next();
                E outValue = converter.convert(inValue);
                retVal.add(outValue);
            }
        }
        return retVal;
    }

}