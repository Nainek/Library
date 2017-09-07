package ua.training.model.dao;

import ua.training.model.entities.Order;


public interface OrderDao extends GenericDao<Order, Long>, AutoCloseable {

	void close();
	
}

