package org.grafana;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;
import io.prometheus.client.Summary;
import io.prometheus.client.exporter.MetricsServlet;
import io.prometheus.client.hotspot.DefaultExports;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@SpringBootApplication
@RestController
public class HelloObservabilityBootApp {

	private final OkHttpClient client = new OkHttpClient();

 

	// Gauges don't have Exemplars.
	private final Gauge lastRequestTimestamp = Gauge.build().name("last_request_timestamp")
			.help("unix time of the last request").labelNames("path").register();

	// Histogram should have Exemplars when the OpenTelemetry agent is attached.
	private final Histogram requestDurationHistogram = Histogram.build().name("request_duration_histogram")
			.help("Request duration in seconds").labelNames("path")
			.buckets(0.001, 0.002, 0.003, 0.004, 0.005, 0.006, 0.007, 0.008, 0.009).register();

	// Summaries don't have Exemplars
	private final Summary requestDurationSummary = Summary.build().name("request_duration_summary")
			.help("Request duration in seconds").labelNames("path").quantile(0.75, 0.01).quantile(0.85, 0.01)
			.register();

	public static void main(String[] args) {
		DefaultExports.initialize();
		SpringApplication.run(HelloObservabilityBootApp.class, args);
	}


	@GetMapping("/start-all")
	public String startProcess() throws IOException {
		String path = "/start-all";
 
		lastRequestTimestamp.labels(path).setToCurrentTime();
		Histogram.Timer histogramRequestTimer = requestDurationHistogram.labels(path).startTimer();
		Summary.Timer summaryRequestTimer = requestDurationSummary.labels(path).startTimer();
		long userId;
		try {
			startCustomer();
			try {
				Thread.sleep((long) (Math.random() * 2000));
 
			} catch (InterruptedException e) {
				throw new IOException(e);
			}
			startSeller();	
			return "End Process ";
		} finally {
			histogramRequestTimer.observeDuration();
			summaryRequestTimer.observeDuration();
		}
	}


	/**
	 * GET /hello will trigger a GET request to /god-of-fire. That way, we get a
	 * nice distributed trace for the OpenTelemetry agent.
	 */
	@GetMapping("/start-customer")
	public String startCustomer() throws IOException {
		String path = "/start-customer";
 
		lastRequestTimestamp.labels(path).setToCurrentTime();
		Histogram.Timer histogramRequestTimer = requestDurationHistogram.labels(path).startTimer();
		Summary.Timer summaryRequestTimer = requestDurationSummary.labels(path).startTimer();
		long userId;
		try {
			// Generate some random errors
			randomError(path);

			// Randomly sleeps a bit
			try {
				Thread.sleep((long) (Math.random() * 1000));
				userId =(long) (Math.random() * 40);
			} catch (InterruptedException e) {
				throw new IOException(e);
			}

			Request request2 = new Request.Builder().url("http://ms-service-app-ms1.applications.svc.cluster.local:8081/customers/find/"+userId).build();
			Response response2 = client.newCall(request2).execute(); 
			return "Find  " + response2.body().string() + "!\n";
		} finally {
			histogramRequestTimer.observeDuration();
			summaryRequestTimer.observeDuration();
		}
	}

	@GetMapping("/start-seller")
	public String startSeller() throws IOException {
		String path = "/start-seller";
 
		lastRequestTimestamp.labels(path).setToCurrentTime();
		Histogram.Timer histogramRequestTimer = requestDurationHistogram.labels(path).startTimer();
		Summary.Timer summaryRequestTimer = requestDurationSummary.labels(path).startTimer();
		long userId;
		try {
			// Generate some random errors
			randomError(path);
			// Randomly sleeps a bit
			try {
				Thread.sleep((long) (Math.random() * 2000));
				userId =(long) (Math.random() * 40);
			} catch (InterruptedException e) {
				throw new IOException(e);
			}
			Request request2 = new Request.Builder().url("http://ms-service-app-ms2.applications.svc.cluster.local:8082/seller/find/"+userId).build();
			Response response2 = client.newCall(request2).execute(); 
			return "Find  " + response2.body().string() + "!\n";
		} finally {
			histogramRequestTimer.observeDuration();
			summaryRequestTimer.observeDuration();
		}
	}

	
	@GetMapping("/observability")
	public String observability() throws IOException {
		String path = "/observability";
 
		lastRequestTimestamp.labels(path).setToCurrentTime();
		Histogram.Timer histogramRequestTimer = requestDurationHistogram.labels(path).startTimer();
		Summary.Timer summaryRequestTimer = requestDurationSummary.labels(path).startTimer();		
		try {
			// Generate some random errors
			randomError(path);
			// Randomly sleeps a bit
			try {
				Thread.sleep((long) (Math.random() * 3000));
			} catch (InterruptedException e) {
				throw new IOException(e);
			}
		}
		finally {
			histogramRequestTimer.observeDuration();
			summaryRequestTimer.observeDuration();
		}
		return "Observability";
	}

	private void randomError(String path) throws IOException {
		if (Math.random() > 0.9) {
			throw new IOException("Random error with " + path + "!");
		}
	}

	/**
	 * Expose Prometheus metrics.
	 */
	@Bean
	public ServletRegistrationBean<MetricsServlet> metricsServlet() {
		ServletRegistrationBean<MetricsServlet> bean = new ServletRegistrationBean<>(new MetricsServlet(), "/metrics");
		bean.setLoadOnStartup(1);
		return bean;
	}
}
