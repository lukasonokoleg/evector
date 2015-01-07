package eu.itreegroup.spark.application.shared;

public class Version {

    private static final String buildDate = "${TODAY_LT}";
    private static final String number = "${proj.rev}";

    public static String getVersion() {
        return "v." + number + " " + buildDate;
    }
}
