package pl.decerto.mule.internal.source.results;

import java.io.Serializable;

public class CalendlyChangePayload implements Serializable {

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
