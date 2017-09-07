package ua.training.model.dao;

import ua.training.model.entities.Author;

public interface AuthorDao extends GenericDao<Author, Long>, AutoCloseable  {

	
	void close();
	
}
