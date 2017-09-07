package ua.training.model.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.training.model.dao.AuthorDao;
import ua.training.model.dao.DaoFactory;
import ua.training.model.entities.Author;

public class AuthorService {
	
	private static final Logger LOGGER = LogManager.getLogger(AuthorService.class);
	
	private DaoFactory daoFactory;

	AuthorService(DaoFactory daoFactory) {
		super();
		this.daoFactory = daoFactory;
	}
	
	private static class Holder {
		static final AuthorService INSTANCE = new AuthorService(DaoFactory.getDaoFactory());
	}
	
	public static AuthorService getInstance(){
		return Holder.INSTANCE;
	}
	
	public void createAuthor(Author author){
		LOGGER.info("Creating author " + author.getFirstName());
		try (AuthorDao authorDao = daoFactory.createAuthorDao()){
			authorDao.create(author);
		}
	}
	
	public List<Author> getAllAuthors(){
		LOGGER.info("Get all authors ");
		try (AuthorDao authorDao = daoFactory.createAuthorDao()){
			return authorDao.findAll();
		}
	}
}
