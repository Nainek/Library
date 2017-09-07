package ua.training.model.entities;


public class Book {

	private Long id;
	private String title;
	private Author author;
	private Long isbn;
	
	public static class Builder{
		
		private Long id;
		private String title;
		private Author author;
		private Long isbn;
		
		public Builder setId(Long id) {
			this.id = id;
			return this;
		}
		
		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}
		
		public Builder setAuthor(Author author) {
			this.author = author;
			return this;
		}
		
		public Builder setIsbn(Long isbn) {
			this.isbn = isbn;
			return this;
		}
		
		public Book build(){
			Book book = new Book();
			book.setId(id);
			book.setTitle(title);
			book.setAuthor(author);
			book.setIsbn(isbn);
			return book;
		}
	}
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Author getAuthor() {
		return author;
	}
	
	public void setAuthor(Author author) {
		this.author = author;
	}
	
	public Long getIsbn() {
		return isbn;
	}
	
	public void setIsbn(Long isbn) {
		this.isbn = isbn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Book other = (Book) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Book [" + 
					"id=" + id + 
					", title=" + title + 
					", author=" + author + 
					", isbn=" + isbn + 
					"]";
	}
	

	
	
	
	
}
