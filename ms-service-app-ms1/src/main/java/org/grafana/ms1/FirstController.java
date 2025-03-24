package org.grafana.ms1;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.grafana.ms1.config.NotFoundException;
import org.grafana.ms1.config.ApplicationException;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;
import io.prometheus.client.Summary;
 

@RestController
@RequestMapping("/customers")
public class FirstController {

	private static final String FIND_ID = "/find/{id}";

	// Gauges don't have Exemplars.
	private final Gauge lastRequestTimestamp = Gauge.build().name("last_request_timestamp").help("unix time of the last request").labelNames("path").register();

	// Histogram should have Exemplars when the OpenTelemetry agent is attached.
	private final Histogram requestDurationHistogram = Histogram.build().name("request_duration_histogram").help("Request duration in seconds").labelNames("path")
			.buckets(0.001, 0.002, 0.003, 0.004, 0.005, 0.006, 0.007, 0.008, 0.009).register();

	// Summaries don't have Exemplars
	private final Summary requestDurationSummary = Summary.build().name("request_duration_summary").help("Request duration in seconds").labelNames("path").quantile(0.75, 0.01).quantile(0.85, 0.01)
			.register();

	@RequestMapping(path = FIND_ID, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.OK)
	public CustomerReponse getCustomerName(@PathVariable("id") Long idCustomer) throws NotFoundException, ApplicationException {

		CustomerReponse response = CustomerReponse.builder().build(); 
		lastRequestTimestamp.labels(FIND_ID).setToCurrentTime();
		String statusCode ="0";
		Histogram.Timer histogramRequestTimer = requestDurationHistogram.labels(FIND_ID).startTimer();
		Summary.Timer summaryRequestTimer = requestDurationSummary.labels(FIND_ID).startTimer();

		try {
			response= callCustomer(idCustomer);
		} finally {
			histogramRequestTimer.observeDuration();
			summaryRequestTimer.observeDuration();
		}
		return response;

	}

	private CustomerReponse callCustomer(Long idCustomer) throws NotFoundException, ApplicationException{

		if (idCustomer < 8){
			throw new NotFoundException();
		}
		if (idCustomer > 35){
			throw new ApplicationException();
		}
		return CustomerReponse.builder().nameCustomer("customer-" + idCustomer.toString()).build();
	} 
}
