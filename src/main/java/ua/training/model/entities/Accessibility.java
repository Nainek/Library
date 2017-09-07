package ua.training.model.entities;

public enum Accessibility {
	
	YES("yes"), NO("no");
	
	private String value;

	Accessibility(String value) {
		this.value = value;
	}
	
	public static Accessibility forValue(String value) {
		for (Accessibility access : Accessibility.values()) {
			if (access.getValue().equals(value)) {
				return access;
			}
		}
		throw new RuntimeException("Type of accessibility with such string value doesn't exist");
	}
	
	public String getValue() {
		return value;
	}
	
    @Override
    public String toString() {
        return name().toLowerCase() ;
    }

}
