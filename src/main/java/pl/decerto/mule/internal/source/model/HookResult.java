package pl.decerto.mule.internal.source.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HookResult {

	private List<Hook> data;

	public List<Hook> getData() {
		return data;
	}

	public void setData(List<Hook> data) {
		this.data = data;
	}
}
