package ua.training.model.dao.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.training.exceptions.DaoException;
import ua.training.model.dao.AuthorDao;
import ua.training.model.dao.BookDao;
import ua.training.model.dao.BookInstanceDao;
import ua.training.model.dao.DaoConnection;
import ua.training.model.dao.DaoFactory;
import ua.training.model.dao.OrderDao;
import ua.training.model.dao.UserDao;

public class JdbcDaoFactory extends DaoFactory {
	
	private static final Logger LOGGER = LogManager.getLogger(JdbcDaoFactory.class);
	
	private DataSource dataSource;
	
    public JdbcDaoFactory() {
        try{
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup("java:comp/env/jdbc/library");            
        }catch(Exception e){
        	LOGGER.error("Can't load pool connection from Initial Context", e);
			throw new DaoException(e);
        }
    }
	
	@Override
	public DaoConnection getConnection() {
		try {
			return new JdbcDaoConnection(dataSource.getConnection());
		} catch (SQLException e) {
			LOGGER.error("Can't get DB connection to the data source ", e);
			throw new DaoException(e);
		}
	}

	
	@Override
	public UserDao createUserDao() {
        try {
			return new JdbcUserDao(dataSource.getConnection() , true);
		} catch (SQLException e) {
			LOGGER.error("Can't get DB connection for the JdbcUserDao creation", e);
			throw new DaoException(e);
		}
	}

	@Override
	public UserDao createUserDao(DaoConnection connection) {
		JdbcDaoConnection jdbcConnection = (JdbcDaoConnection) connection;
		Connection sqlConnection = jdbcConnection.getConnection();
		return new JdbcUserDao(sqlConnection);
	}
	
    @Override
	public BookDao createBookDao() {
        try {
			return new JdbcBookDao(dataSource.getConnection() , true);
		} catch (SQLException e) {
			LOGGER.error("Can't get DB connection for the JdbcBookDao creation", e);
			throw new DaoException(e);
		}
	}

	@Override
	public BookDao createBookDao(DaoConnection connection) {
		JdbcDaoConnection jdbcConnection = (JdbcDaoConnection) connection;
		Connection sqlConnection = jdbcConnection.getConnection();
		return new JdbcBookDao(sqlConnection);
	}

	@Override
	public OrderDao createOrderDao() {
        try {
			return new JdbcOrderDao(dataSource.getConnection() , true);
		} catch (SQLException e) {
			LOGGER.error("Can't get DB connection for the JdbcOrderDao creation", e);
			throw new DaoException(e);
		}
	}

	@Override
	public OrderDao createOrderDao(DaoConnection connection) {
		JdbcDaoConnection jdbcConnection = (JdbcDaoConnection) connection;
		Connection sqlConnection = jdbcConnection.getConnection();
		return new JdbcOrderDao(sqlConnection);
	}

	@Override
	public AuthorDao createAuthorDao() {
        try {
			return new JdbcAuthorDao(dataSource.getConnection() , true);
		} catch (SQLException e) {
			LOGGER.error("Can't get DB connection for the JdbcAuthorDao creation", e);
			throw new DaoException(e);
		}
	}

	@Override
	public AuthorDao createAuthorDao(DaoConnection connection) {
		JdbcDaoConnection jdbcConnection = (JdbcDaoConnection) connection;
		Connection sqlConnection = jdbcConnection.getConnection();
		return new JdbcAuthorDao(sqlConnection);
	}

	@Override
	public BookInstanceDao createBookInstanceDao() {
        try {
			return new JdbcBookInstanceDao(dataSource.getConnection() , true);
		} catch (SQLException e) {
			LOGGER.error("Can't get DB connection for the JdbcBookInstanceDao creation", e);
			throw new DaoException(e);
		}
	}

	@Override
	public BookInstanceDao createBookInstanceDao(DaoConnection connection) {
		JdbcDaoConnection jdbcConnection = (JdbcDaoConnection) connection;
		Connection sqlConnection = jdbcConnection.getConnection();
		return new JdbcBookInstanceDao(sqlConnection);
	}

	

	



}
