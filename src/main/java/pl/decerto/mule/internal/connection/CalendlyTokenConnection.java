package pl.decerto.mule.internal.connection;


import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;


public final class CalendlyTokenConnection {

	private final Logger LOGGER = LoggerFactory.getLogger(CalendlyTokenConnection.class);

	private RestTemplate template = new RestTemplate();

	public CalendlyTokenConnection(String token) {
		try {
			List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
			interceptors.add(new HeaderRequestInterceptor("X-TOKEN", token));
			template.setInterceptors(interceptors);

			LOGGER.debug("Connecting: Success" );
		} catch (Exception e) {
			LOGGER.error("Connecting: Error", e);
		}
	}

	public RestTemplate getTemplate() {
		return template;
	}
}
