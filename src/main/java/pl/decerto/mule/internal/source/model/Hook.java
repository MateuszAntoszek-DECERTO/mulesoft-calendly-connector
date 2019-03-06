package pl.decerto.mule.internal.source.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Hook {

	private String type;

	private String id;

	private HookAttributes attributes;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public HookAttributes getAttributes() {
		return attributes;
	}

	public void setAttributes(HookAttributes attributes) {
		this.attributes = attributes;
	}
}
