package eu.itreegroup.spark.test.report;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TestDescriptionRegistry {

    private static Map<String, String> attributes = Collections.synchronizedMap(new HashMap<String, String>());

    public static void registerAttribute(String testName, String attributeName, String attributeValue) {
        attributes.put(testName + "-" + attributeName, attributeValue);
    }

    public static String getAttribute(String testName, String attribute) {
        return attributes.get(testName + "-" + attribute);
    }
}
