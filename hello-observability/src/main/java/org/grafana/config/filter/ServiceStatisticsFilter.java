package org.grafana.config.filter;

import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import io.prometheus.client.Counter;

import org.slf4j.MDC;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebFilter(filterName = "serviceStatisticsFilter", urlPatterns = { "/*" })
@Component
@Order(1)
public class ServiceStatisticsFilter extends FilterAsInterceptorAdapter {

	private static final String RESPONSE_TIME = "responseTime";
	private static final String RESPONSE_STATUS = "responseStatus";
	private static final String URI = "uri";
	private static final String TYPE = "type";
	private static final String SERVICE_NAME="ms-hello-observability-services";
	@Autowired
	private AppMonitor appMonitor;
	
	// Counter should have Exemplars when the OpenTelemetry agent is attached.
	private final Counter requestCounter = Counter.build().name("requests_total").help("Total number of requests.").labelNames("service_name","path",RESPONSE_STATUS).register();
 
	
    private static final String AFTER_COMPLETION_ACTION = "ServiceStatisticsFilter#afterCompletion";
    private static final String DESCRIPTION_END = "Finishing to measure business time";
    private static final String START_TIME = "startTime";
    private static final String SERVICE_ID = "ServiceId";
    private static final String SERVICE_STATUS_CODE = "ServiceStatusCode";
    private static final String SERVICE_TIME = "ServiceTime";
    private static final String SERVICE_NAME_KEY = "actionName";

    @Override
    protected boolean preHandle(HttpServletRequest request, HttpServletResponse response)
    		throws IOException, ServletException {

 
        
    	if(!request.getRequestURI().contains("health-check")) {
            LocalTime now = LocalTime.now();
            request.setAttribute(START_TIME, now);
            appMonitor.logStatistics(request.getRequestURI(),  
					 				  Integer.toString(response.getStatus()), 
            						 Integer.toString(response.getStatus()), 
            						 "init Trx");
        }
        return true;
    }
    
    @Override
    protected void afterCompletion(HttpServletRequest request, HttpServletResponse response)
    		throws IOException, ServletException {


            LocalTime now = LocalTime.now();
            long serviceTime = ChronoUnit.MILLIS.between((LocalTime) request.getAttribute(START_TIME), now);

            if(request.getRequestURI().contains("health")) {
            	 MDC.put(SERVICE_NAME_KEY, "health");
            }else {
            	MDC.put(SERVICE_NAME_KEY, request.getHeader("actionName"));
            	
            }
	    	MDC.put(TYPE, "http");
	    	MDC.put(URI, request.getRequestURI());
	    	MDC.put("ContextPath", request.getContextPath());
	    	MDC.put("PathInfo", request.getPathInfo());
	    	
	    	
	    	MDC.put(RESPONSE_STATUS, String.valueOf(response.getStatus()));
	    	MDC.put(RESPONSE_TIME, String.valueOf(serviceTime));

	    	if(!request.getRequestURI().contains("/metrics")) {
		    	requestCounter.labels(SERVICE_NAME,request.getRequestURI(),String.valueOf(response.getStatus())).inc();
	    	}
	    	
            appMonitor.logStatistics(request.getRequestURI(),  
					 				  Integer.toString(response.getStatus()), 
            						 Integer.toString(response.getStatus()), 
            						 Long.toString(serviceTime), 
            						 "Finish Trx "+request.getServletContext()+" | "+request.getPathInfo());
        
    }
    
    
    
}
