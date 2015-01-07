package lt.jmsys.spark.gwt.application.client.helper;

public class UuidHelper {

    private static final char[] CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    public static String uuid() {
        char[] uuid = new char[24];
        int r;
        for (int i = 0; i < uuid.length; i++) {
            if (uuid[i] == 0) {
                r = (int) (Math.random() * 16);
                uuid[i] = CHARS[(i == 19) ? (r & 0x3) | 0x8 : r & 0xf];
            }
        }
        return new String(uuid);
    }
}
