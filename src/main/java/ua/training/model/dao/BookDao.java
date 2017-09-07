package ua.training.model.dao;

import ua.training.model.entities.Book;


public interface BookDao extends GenericDao<Book, Long>, AutoCloseable  {

	void close();
	
}

