package ua.training.model.dao.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.training.model.dao.DaoConnection;
import ua.training.exceptions.DaoException;

public class JdbcDaoConnection implements DaoConnection {
	
	private static final Logger LOGGER = LogManager.getLogger(JdbcDaoConnection.class);

	private Connection connection;
	private boolean inTransaction = false;

	
	JdbcDaoConnection(Connection connection) {
		super();
		this.connection = connection;
	}

	@Override
	public void close() {
		if(inTransaction) {
			rollback();
		}
		try {
			connection.close();
			LOGGER.info("Transaction is closed");
		} catch (SQLException e) {
			LOGGER.error("JdbcDaoConnection close error", e);
			throw new DaoException(e);
		}
	}

	@Override
	public void begin() {
		try {
			connection.setAutoCommit(false);
			inTransaction = true;
			LOGGER.info("Transaction has began");
		} catch (SQLException e) {
			LOGGER.error("JdbcDaoConnection begin error", e);
			throw new DaoException(e);
		}
	}

	@Override
	public void commit() {
		try {
			connection.commit();
			inTransaction = false;
			LOGGER.info("Transaction is commited");
		} catch (SQLException e) {
			LOGGER.error("JdbcDaoConnection commit error", e);
			throw new DaoException(e);
		}
	}

	@Override
	public void rollback() {
		try {
			connection.rollback();
			inTransaction = false;
			LOGGER.info("Transaction is rollbacked");
		} catch (SQLException e) {
			LOGGER.error("JdbcDaoConnection rollback error", e);
			throw new DaoException(e);
		}

	}

	Connection getConnection() {
		return connection;
	}
	
}
