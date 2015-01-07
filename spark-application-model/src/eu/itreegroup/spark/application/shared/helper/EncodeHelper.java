package eu.itreegroup.spark.application.shared.helper;

public class EncodeHelper {

    public static String hex(String text) {
        if (null != text) {
            StringBuilder buff = new StringBuilder();
            for (char c : text.toCharArray()) {
                String hex = "00" + Integer.toHexString(c);
                hex = hex.substring(hex.length() - 3);
                buff.append(hex);
            }
            text = buff.toString();
        }
        return text;
    }

    public static String unhex(String hexText) {
        if (null != hexText) {
            if (hexText.length() % 3 != 0) {
                throw new IllegalArgumentException("Illegal text, expected hex encoded string");
            }
            StringBuilder buff = new StringBuilder();
            for (int i = 0; i < hexText.length(); i += 3) {
                String h = hexText.substring(i, i + 3);
                buff.append((char) Integer.parseInt(h, 16));
            }
            return buff.toString();
        } else {
            return null;
        }
    }
}
