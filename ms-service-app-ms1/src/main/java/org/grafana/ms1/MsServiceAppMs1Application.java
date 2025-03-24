package org.grafana.ms1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import io.prometheus.client.exporter.MetricsServlet;
import io.prometheus.client.hotspot.DefaultExports;

@SpringBootApplication
public class MsServiceAppMs1Application {

	
	
	/**
	 * Expose Prometheus metrics.
	 */
	@Bean
	public ServletRegistrationBean<MetricsServlet> metricsServlet() {
		ServletRegistrationBean<MetricsServlet> bean = new ServletRegistrationBean<>(new MetricsServlet(), "/metrics");
		bean.setLoadOnStartup(1);
		return bean;
	}
	
	
	public static void main(String[] args) {
		DefaultExports.initialize();
		SpringApplication.run(MsServiceAppMs1Application.class, args);
	}

	
	

}
