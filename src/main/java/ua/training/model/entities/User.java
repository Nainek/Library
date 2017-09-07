package ua.training.model.entities;


public class User {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private UserRole userRole;
	private byte salt[];
	
	
	public static class Builder{
		
		private Long id;
		private String firstName;
		private String lastName;
		private String email;
		private String password;
		private UserRole userRole;
		private byte salt[];
		
		public Builder setId(Long id) {
			this.id = id;
			return this;
		}

		public Builder setFirstName(String firstName) {
			this.firstName = firstName;
			return this;
		}
		
		public Builder setLastName(String lastName) {
			this.lastName = lastName;
			return this;
		}
		
		public Builder setEmail(String email) {
			this.email = email;
			return this;
		}

		public Builder setPassword(String password) {
			this.password = password;
			return this;
		}

		public Builder setUserRole(UserRole userRole) {
			this.userRole = userRole;
			return this;
		}
		
		public Builder setSalt(byte[] salt) {
			this.salt = salt;
			return this;
		}
		

		public User build(){
			User user = new User();
			user.setId(id);
			user.setFirstName(firstName);
			user.setLastName(lastName);
			user.setEmail(email);
			user.setPassword(password);
			user.setUserRole(userRole);
			user.setSalt(salt);
			return user;
		}
	}
	
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public UserRole getUserRole() {
		return userRole;
	}
	
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public byte[] getSalt() {
		return salt;
	}

	public void setSalt(byte[] salt) {
		this.salt = salt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((userRole == null) ? 0 : userRole.hashCode());
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
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userRole != other.userRole)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [" + 
				"id=" + id +
				", firstName=" + firstName + 
				", lastName=" + lastName + 
				", email=" + email + 
				", password=" + password + 
				", userRole=" + userRole + 
				"]";
	}


	
}
