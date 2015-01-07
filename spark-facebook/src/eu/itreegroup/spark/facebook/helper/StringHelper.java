package eu.itreegroup.spark.facebook.helper;

public class StringHelper {

    public static boolean isEmpty(String value) {
        boolean retVal = value != null && value.isEmpty();
        return retVal;
    }

}
