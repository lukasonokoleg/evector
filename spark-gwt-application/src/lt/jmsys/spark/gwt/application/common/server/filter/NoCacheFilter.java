package lt.jmsys.spark.gwt.application.common.server.filter;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NoCacheFilter implements Filter {

	public static final String CACHE_PARAM = "cachePattern";
	public static final String NOCACHE_PARAM = "nocachePattern";
	
	private Pattern nocache;
	private Pattern cache;
	
	/**
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig pConfig) throws ServletException {
		String cacheInitParam = pConfig.getInitParameter(CACHE_PARAM);
		String nocacheInitParam = pConfig.getInitParameter(NOCACHE_PARAM);
		this.cache = Pattern.compile(cacheInitParam == null ? ".+\\.cache\\..+" : cacheInitParam);
		this.nocache = Pattern.compile(nocacheInitParam == null ? ".+\\.nocache\\..+" : nocacheInitParam);
	}

	/**
	 * @see javax.servlet.Filter#soFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest pRequest, ServletResponse pResponse, FilterChain pChain) throws IOException, ServletException {
		HttpServletRequest hRequest = (HttpServletRequest)pRequest;
		HttpServletResponse hResponse = (HttpServletResponse)pResponse;
		StringBuffer url = hRequest.getRequestURL();
		if (nocache.matcher(url).matches()) {	    
			hResponse.setHeader("Cache-Control", "no-cache no-store must-revalidate");
			hResponse.setHeader("Pragma", "no-cache");
			hResponse.setDateHeader("Expires", 0L);
		} else if (cache.matcher(url).matches()) {
			hResponse.setHeader("Cache-Control", "public max-age=31536000");
			hResponse.setHeader("Pragma", "temp");
			hResponse.setHeader("Pragma", "");
			hResponse.setDateHeader("Expires", System.currentTimeMillis() + 31536000000L);
		}
		pChain.doFilter(pRequest, pResponse);
	}

	/**
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
	}

}

