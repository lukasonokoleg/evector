package eu.itreegroup.spark.facebook.helper;

public class ErrorMessageHelper {
    
    
    

    public static String getServletInitException(String servletName) {
        StringBuilder builder = new StringBuilder();
        builder.append("Could not init servlet : ");
        builder.append(servletName);
        return builder.toString();
    }
}
