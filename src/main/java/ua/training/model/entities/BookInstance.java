package ua.training.model.entities;



public class BookInstance {
	
	private Long id;
	private Long inventoryNumber;
	private Long isbn;
	private Accessibility accessibility;
	
	
	public static class Builder{
		
		private Long id;
		private Long inventoryNumber;
		private Long isbn;
		private Accessibility accessibility;
		
		public Builder setId(Long id) {
			this.id = id;
			return this;
		}

		public Builder setInventoryNumber(Long inventoryNumber) {
			this.inventoryNumber = inventoryNumber;
			return this;
		}

		public Builder setIsbn(Long isbn) {
			this.isbn = isbn;
			return this;
		}

		public Builder setAccessibility(Accessibility accessibility) {
			this.accessibility = accessibility;
			return this;
		}

		public BookInstance build(){
			BookInstance bookInstance = new BookInstance();
			bookInstance.setId(id);
			bookInstance.setInventoryNumber(inventoryNumber);
			bookInstance.setIsbn(isbn);
			bookInstance.setAccessibility(accessibility);
			return bookInstance;
		}
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getInventoryNumber() {
		return inventoryNumber;
	}
	
	public void setInventoryNumber(Long inventoryNumber) {
		this.inventoryNumber = inventoryNumber;
	}
	
	public Long getIsbn() {
		return isbn;
	}
	
	public void setIsbn(Long isbn) {
		this.isbn = isbn;
	}
	
	public Accessibility getAccessibility() {
		return accessibility;
	}

	public void setAccessibility(Accessibility accessibility) {
		this.accessibility = accessibility;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accessibility == null) ? 0 : accessibility.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((inventoryNumber == null) ? 0 : inventoryNumber.hashCode());
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookInstance other = (BookInstance) obj;
		if (accessibility != other.accessibility)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (inventoryNumber == null) {
			if (other.inventoryNumber != null)
				return false;
		} else if (!inventoryNumber.equals(other.inventoryNumber))
			return false;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BookInstance [" + 
					"id=" + id + 
					", inventoryNumber=" + inventoryNumber + 
					", isbn=" + isbn + 
					", accessibility=" + accessibility + 
					"]";
	}	
	
}
