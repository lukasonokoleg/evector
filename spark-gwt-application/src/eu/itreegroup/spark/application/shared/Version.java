package eu.itreegroup.spark.application.shared;

public class Version {

    private static final String buildDate = "BUILD_DATE";
    private static final String number = "${proj.rev}";

    public static String getVersion() {
        return "v." + number + " " + buildDate;
    }

    public static String getNumber() {
        return number;
    }
}
