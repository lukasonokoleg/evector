package eu.itreegroup.spark.facebook.action;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FacebookActionFactory {

    public static <E extends BaseFacebookActionImpl> E getAction(Class<E> className, HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws Exception {
        E retVal = className.newInstance();
        retVal.setServletContextData(servlet, request, response);
        return retVal;
    }

}
