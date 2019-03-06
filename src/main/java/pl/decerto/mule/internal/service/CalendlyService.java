package pl.decerto.mule.internal.service;

import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import pl.decerto.mule.internal.service.model.CalendlyData;
import pl.decerto.mule.internal.service.model.EventType;
import pl.decerto.mule.internal.source.model.Hook;
import pl.decerto.mule.internal.source.model.HookResult;

public class CalendlyService {

	private static final String CALENDLY_URL = "https://calendly.com/api/v1/hooks";

	private RestTemplate template;

	public CalendlyService(RestTemplate template) {
		this.template = template;
	}

	public String createHook(String url, EventType eventType) {
		CalendlyData data = new CalendlyData();
		data.setUrl(url);
		data.addEvent(eventType.getCode());
		ResponseEntity<String> response = template.postForEntity(CALENDLY_URL, data, String.class);
		return response.getBody();
	}

	public void deleteHook(String hookId) {
		template.delete(CALENDLY_URL + "/" + hookId);
	}

	public String getHookId(String url) {
		HookResult result = template.getForObject(CALENDLY_URL, HookResult.class);
		Optional<Hook> first = result.getData().stream()
				.filter(hook -> hook.getAttributes().getUrl().equals(url))
				.findFirst();

		return first
				.map(Hook::getId)
				.orElse(null);
	}
}
