package ua.training.model.entities;

public enum TypeOfOrder {
	
	ORDER_FOR_HOME("OrderForHome"), OEDER_FOR_AUDITORIUM("OrderForAuditorium");
	
	String value;
	
	TypeOfOrder(String value){
		this.value = value;
	}
	
	public static TypeOfOrder forValue(String value) {
		for (TypeOfOrder type : TypeOfOrder.values()) {
			if (type.getValue().equals(value)) {
				return type;
			}
		}
		throw new RuntimeException("TypeOfOrder with such string value doesn't exist");
	}
	
	
	
    public String getValue() {
		return value;
	}

	@Override
    public String toString() {
        return name().toLowerCase() ;
    }

}
