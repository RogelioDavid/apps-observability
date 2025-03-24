package org.grafana.ms2.config.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

 

public  abstract class FilterAsInterceptorAdapter  implements Filter  {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        if (preHandle(httpServletRequest, httpServletResponse)) {
            chain.doFilter(httpServletRequest, httpServletResponse);
        }
        afterCompletion(httpServletRequest, httpServletResponse);
		
	}
    @Override
    public void destroy() {
        // Do nothing
    }

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		 
	}
    /**
     * This implementation is empty.
     */
    protected boolean preHandle(HttpServletRequest request,
                                HttpServletResponse response)
        throws IOException, ServletException {

        return true;
    }

    /**
     * This implementation is empty.
     */
    protected void afterCompletion(HttpServletRequest request,
                               HttpServletResponse response)
        throws IOException, ServletException {
        // Do nothing
    }
}
