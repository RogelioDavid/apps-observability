package org.grafana.ms1.config.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
 

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AppMonitor {

	
	  private Logger log = LoggerFactory.getLogger(AppMonitor.class);
	  
	  
	    public void logStatistics(String uri, String responseStatus, String httpStatus, String time, String description) {
	    	SmartLogger.info()
	                .withAction("statisticsRequest")
	                .with("uri", uri)
	                .with("responseStatus", responseStatus)
	                .with("serviceStatusCode", responseStatus)
	                .with("httpStatusCode", httpStatus)
	                .with("responseTime", time)
	                .with("description", description)
	                .to(log);
	    }
	    
	    public void logStatistics(String event , String httpStatus, String time, String description) {
	    	SmartLogger.info()
	                .withAction("statisticsEvent")
	                .with("event", event)
	                .with("serviceStatusCode", httpStatus)
	                .with("responseTime", time)
	                .with("description", description)
	                .to(log);
	    }
}
