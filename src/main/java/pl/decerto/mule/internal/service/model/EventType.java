package pl.decerto.mule.internal.service.model;

public enum  EventType {

	NEW("invitee.created"),
	CANCELED("invitee.canceled");

	EventType(String code) {
		this.code = code;
	}

	String code;

	public String getCode() {
		return code;
	}
}
