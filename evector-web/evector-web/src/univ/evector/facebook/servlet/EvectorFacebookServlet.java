package univ.evector.facebook.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import univ.evector.facebook.EvectorFacebookSettingsProvider;
import eu.itreegroup.spark.facebook.server.FacebookServlet;

@SuppressWarnings("serial")
public class EvectorFacebookServlet extends FacebookServlet {

    private WebApplicationContext springContext;

    @Override
    protected void processInit(ServletConfig config) throws ServletException {
        springContext = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        initFacebookSettingsProvider(config);
    }

    @Override
    protected void initFacebookSettingsProvider(ServletConfig config) throws ServletException {
        fbSettingsProvider = springContext.getBean(EvectorFacebookSettingsProvider.class);
    }

}