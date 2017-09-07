package ua.training.model.dao;



import ua.training.model.entities.User;


public interface UserDao extends GenericDao<User, Long>, AutoCloseable {
	
	void close();
	
}
