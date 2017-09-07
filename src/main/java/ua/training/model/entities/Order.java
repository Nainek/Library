package ua.training.model.entities;

import java.sql.Timestamp;


public class Order {
	
	private Long id;
	private BookInstance bookInstance;
	private User user;
	private Timestamp orderDate;
	private Timestamp requireReturnDate;
	private Timestamp actualReturnDate;
	private TypeOfOrder typeOfOrder;
	
	
	public static class Builder{
		
		private Long id;
		private BookInstance bookInstance;
		private User user;
		private Timestamp orderDate;
		private Timestamp requireReturnDate;
		private Timestamp actualReturnDate;
		private TypeOfOrder typeOfOrder;
		
		
		public Builder setId(Long id) {
			this.id = id;
			return this;
		}

		public Builder setBookInstance(BookInstance bookInstance) {
			this.bookInstance = bookInstance;
			return this;
		}

		public Builder setUser(User user) {
			this.user = user;
			return this;
		}

		public Builder setOrderDate(Timestamp orderDate) {
			this.orderDate = orderDate;
			return this;
		}

		public Builder setReuireReturnDate(Timestamp requireReturnDate) {
			this.requireReturnDate = requireReturnDate;
			return this;
		}

		public Builder setActualReturnDate(Timestamp actualReturnDate) {
			this.actualReturnDate = actualReturnDate;
			return this;
		}

		public Builder setTypeOfOrder(TypeOfOrder typeOfOrder) {
			this.typeOfOrder = typeOfOrder;
			return this;
		}

		
		public Order build(){
			Order order = new Order();
			order.setId(id);
			order.setBookInstance(bookInstance);
			order.setUser(user);
			order.setOrderDate(orderDate) ;
			order.setRequireReturnDate(requireReturnDate);
			order.setActualReturnDate(actualReturnDate);
			order.setTypeOfOrder(typeOfOrder);
			return order;
		}
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public BookInstance getCopyOfTheBook() {
		return bookInstance;
	}
	
	public void setBookInstance(BookInstance bookInstance) {
		this.bookInstance = bookInstance;
	}
	
	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Timestamp getOrderDate() {
		return orderDate;
	}
	
	public void setOrderDate(Timestamp orderDate) {
		this.orderDate = orderDate;
	}
	
	public Timestamp getRequireReturnDate() {
		return requireReturnDate;
	}
	
	public void setRequireReturnDate(Timestamp requireReturnDate) {
		this.requireReturnDate = requireReturnDate;
	}
	
	public Timestamp getActualReturnDate() {
		return actualReturnDate;
	}
	
	public void setActualReturnDate(Timestamp actualReturnDate) {
		this.actualReturnDate = actualReturnDate;
	}
	
	public TypeOfOrder getTypeOfOrder() {
		return typeOfOrder;
	}
	
	public void setTypeOfOrder(TypeOfOrder typeOfOrder) {
		this.typeOfOrder = typeOfOrder;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actualReturnDate == null) ? 0 : actualReturnDate.hashCode());
		result = prime * result + ((bookInstance == null) ? 0 : bookInstance.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((orderDate == null) ? 0 : orderDate.hashCode());
		result = prime * result + ((requireReturnDate == null) ? 0 : requireReturnDate.hashCode());
		result = prime * result + ((typeOfOrder == null) ? 0 : typeOfOrder.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Order other = (Order) obj;
		if (actualReturnDate == null) {
			if (other.actualReturnDate != null)
				return false;
		} else if (!actualReturnDate.equals(other.actualReturnDate))
			return false;
		if (bookInstance == null) {
			if (other.bookInstance != null)
				return false;
		} else if (!bookInstance.equals(other.bookInstance))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (orderDate == null) {
			if (other.orderDate != null)
				return false;
		} else if (!orderDate.equals(other.orderDate))
			return false;
		if (requireReturnDate == null) {
			if (other.requireReturnDate != null)
				return false;
		} else if (!requireReturnDate.equals(other.requireReturnDate))
			return false;
		if (typeOfOrder != other.typeOfOrder)
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Order [" + 
					"id=" + id + 
					", bookInstance=" + bookInstance + 
					", user=" + user + 
					", orderDate=" + orderDate + 
					", requireReturnDate=" + requireReturnDate + 
					", actualReturnDate=" + actualReturnDate + 
					", typeOfOrder=" + typeOfOrder + 
					"]";
	}
	

	
	

}
