package ua.training.model.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import ua.training.exceptions.DaoException;

public interface GenericDao <T, PK extends Serializable> {
	
	
    List<T> findAll() throws DaoException; 
    Optional<T> getEntityById(PK id) throws DaoException;
    PK create(T entity) throws DaoException;
    T update(T entity) throws DaoException;
    boolean delete(PK id) throws DaoException;
    
//    T read(PK id);   
    
}