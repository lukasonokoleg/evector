package lt.jmsys.spark.gwt.application.common.server.filter;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BrowserCompatibilityFilter implements Filter {

    public static final String PARAM_ERROR_PAGE = "errorPage";

    private String errorPageBrowserNotSupported;

    /**
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig pConfig) throws ServletException {
        errorPageBrowserNotSupported = pConfig.getInitParameter(PARAM_ERROR_PAGE);
    }

    /**
     * @see javax.servlet.Filter#soFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest pRequest, ServletResponse pResponse, FilterChain pChain) throws IOException, ServletException {
        HttpServletRequest hRequest = (HttpServletRequest) pRequest;
        HttpServletResponse hResponse = (HttpServletResponse) pResponse;
        String userAgent = hRequest.getHeader("User-Agent");
        if (null != userAgent) {
            if (userAgent.matches(".*MSIE [1-6]\\..*")) {
                hResponse.sendRedirect(getErrorPage(hRequest));
                return;
            }
        }
        pChain.doFilter(pRequest, pResponse);
    }

    private String getErrorPage(HttpServletRequest hRequest) {
        String locale = hRequest.getParameter("locale");
        String page = errorPageBrowserNotSupported;
        if (null != locale) {
            try {
                page += "?locale=" + URLEncoder.encode(locale, "UTF-8");
            } catch (Exception ignore) {

            }
        }
        return page;
    }

    /**
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
    }

}
