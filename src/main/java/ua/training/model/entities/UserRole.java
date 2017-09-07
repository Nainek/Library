package ua.training.model.entities;

public enum UserRole {
		
	READER("reader"), LIBRARIAN("librarian");
	
	private String value;

	UserRole(String value) {
		this.value = value;
	}
	
	public static UserRole forValue(String value) {
		for (UserRole role : UserRole.values()) {
			if (role.getValue().equals(value)) {
				return role;
			}
		}
		throw new RuntimeException("Role with such string value doesn't exist");
	}
	
	public String getValue() {
		return value;
	}
	
    @Override
    public String toString() {
        return name().toLowerCase() ;
    }
}
