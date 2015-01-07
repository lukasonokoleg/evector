package eu.itreegroup.spark.facebook.helper;

import java.lang.reflect.Constructor;

public class ClassHelper {

    public static <E> E createObjectByClassName(String className, Class<E> requiredClass) throws Exception {
        E retVal = null;
        Object object = createObjectByClassName(className);
        if (requiredClass.isInstance(object)) {
            retVal = requiredClass.cast(object);
        }
        return retVal;
    }

    private static Object createObjectByClassName(String className) throws Exception {
        if (StringHelper.isEmpty(className)) {
            throw new IllegalArgumentException("Cannot create object from empty string");
        }
        Class<?> tmpClass = Class.forName(className);
        Constructor<?> constructor = tmpClass.getConstructor(Void.class);
        Object retVal = constructor.newInstance();
        return retVal;
    }

}
