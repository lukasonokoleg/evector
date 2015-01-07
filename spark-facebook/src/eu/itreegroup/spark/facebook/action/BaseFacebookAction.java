package eu.itreegroup.spark.facebook.action;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import facebook4j.Facebook;
import facebook4j.FacebookException;

public interface BaseFacebookAction {

    HttpServlet getHttpServlet();

    HttpServletRequest getHttpServletRequest();

    HttpServletResponse getHttpServletResponse();

    Facebook getFacebook();

    void setAccessToken(String accessToken) throws FacebookException;

    void clean();

}
