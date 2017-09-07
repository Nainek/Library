package ua.training.model.dao;

import ua.training.model.entities.BookInstance;

public interface  BookInstanceDao extends GenericDao <BookInstance, Long>, AutoCloseable  {

	void close();
	
}
