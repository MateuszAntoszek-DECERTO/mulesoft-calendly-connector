package pl.decerto.mule.internal.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CalendlyData implements Serializable {

	private String url;

	private List<String> events = new ArrayList<>();

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<String> getEvents() {
		return events;
	}

	public void addEvent(String event) {
		this.events.add(event);
	}
}
