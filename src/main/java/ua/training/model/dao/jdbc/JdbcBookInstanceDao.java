package ua.training.model.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.training.exceptions.DaoException;
import ua.training.model.dao.BookInstanceDao;
import ua.training.model.entities.Accessibility;
import ua.training.model.entities.BookInstance;

public class JdbcBookInstanceDao implements BookInstanceDao {

	private final boolean connectionShouldBeClosed;
    private Connection connection;
    
    private static final Logger LOGGER = LogManager.getLogger(JdbcBookInstanceDao.class); 

	private static final String COLUMN_BOOK_INSTANCE_ID = "book_instance_id";
	private static final String COLUMN_INVENTORY_NUMBER_OF_BOOK = "inventory_number_of_book";
	private static final String COLUMN_ISBN = "isbn";
	private static final String COLUMN_ACCESSIBILITY= "accessibility";

	private static final String INSERT_BOOK_INSTANCE = "INSERT INTO `book_instances` (`inventory_number_of_book`, `isbn`,'accessibility') VALUES ( ?, ?, ?);";
	private static final String UPDATE_BOOK_INSTANCE = "UPDATE book_instances SET inventory_number_of_book=?, isbn=?, accessibility=? WHERE book_instance_id=?; ";
	private static final String DELETE_BOOK_INSTANCE = "DELETE FROM `book_instances` WHERE book_instance_id=?;"; // Cascade delete? From copies of the book.
	
	private static final String FIND_ALL = "SELECT * FROM book_instances"; //TO DO add book Instance Join
    private static final String FIND_BY_ID = FIND_ALL + " WHERE book_instance_id = ?"; //TO DO add book Instance Join
	
   	public JdbcBookInstanceDao(Connection connection) {
   		this.connection = connection;
   		connectionShouldBeClosed = false;
   	}
    
    public JdbcBookInstanceDao(Connection connection, boolean connectionShouldBeClosed) {
		
    	this.connectionShouldBeClosed = connectionShouldBeClosed;
    	this.connection = connection;
    }
    
    
	@Override
	public List<BookInstance> findAll() {
		List<BookInstance> bookInstances = new ArrayList<>();
		
		try(PreparedStatement query = connection.prepareStatement(FIND_ALL)){
			
			ResultSet rs = query.executeQuery();
			if(rs.next()){
				bookInstances.add(extractBookInstanceFromResultSet(rs));
			}
			
		} catch (SQLException ex){
			
			LOGGER.error("JdbcBookInstance select SQL exception", ex);
			throw new DaoException(ex);
		}
		return bookInstances;
 	}

	@Override
	public Optional<BookInstance> getEntityById(Long id) {
		Optional<BookInstance> bookInstance = Optional.empty();
		try(PreparedStatement query = connection.prepareStatement(FIND_BY_ID)){
			
			query.setLong(1, id);
			
			ResultSet rs = query.executeQuery();
			if(rs.next()){
				bookInstance = Optional.of(extractBookInstanceFromResultSet(rs));
			} 
			
		}catch (SQLException ex) {
			LOGGER.error("JdbcBookInstancesDao select SQL exception", ex);
			throw new DaoException(ex);
		}
		return bookInstance;
	}

	@Override
	public Long create(BookInstance entity) {
		
		try(PreparedStatement query = connection.prepareStatement(INSERT_BOOK_INSTANCE, Statement.RETURN_GENERATED_KEYS)){
			query.setLong(1, entity.getInventoryNumber());
			query.setLong(2, entity.getIsbn());
			query.setString(3, entity.getAccessibility().toString());
			query.setLong(4, entity.getId());
			query.executeUpdate();
			ResultSet rs = query.getGeneratedKeys();
			if(rs.next()){
				entity.setId(rs.getLong(1));
			}
			
		} catch(SQLException ex) {
			LOGGER.error("JdbcBookInstanceDao create SQL exeption ", ex);
			throw new DaoException(ex);
		}
		return entity.getId();
	}

	@Override
	public BookInstance update(BookInstance entity) {
		try(PreparedStatement query = connection.prepareStatement(UPDATE_BOOK_INSTANCE)){
			query.setLong(1, entity.getInventoryNumber());
			query.setLong(2, entity.getIsbn());
			query.setString(3, entity.getAccessibility().toString());
			query.setLong(4, entity.getId());
			query.executeUpdate();
		} catch(SQLException ex) {
			LOGGER.error("JdbcBookInstanceDao update SQL exeption ", ex);
			throw new DaoException(ex);
		}
		return entity;
	}

	@Override
	public boolean delete(Long id) {
		int deletedRow = 0;
				try(PreparedStatement query = connection.prepareStatement(DELETE_BOOK_INSTANCE)){
					query.setLong(1, id);
					deletedRow = query.executeUpdate();
				} catch(SQLException ex) {
					LOGGER.error("JdbcBookInstanceDao delete SQL exeption ", ex);
					throw new DaoException(ex);
				}
		return deletedRow > 0;
	}

	protected static BookInstance extractBookInstanceFromResultSet(ResultSet resultSet) throws SQLException {
		return new BookInstance.Builder()
				.setId(resultSet.getLong(COLUMN_BOOK_INSTANCE_ID))
				.setInventoryNumber(resultSet.getLong(COLUMN_INVENTORY_NUMBER_OF_BOOK))
				.setIsbn(resultSet.getLong(COLUMN_ISBN))
				.setAccessibility(Accessibility.forValue(resultSet.getString(COLUMN_ACCESSIBILITY)))		// TO DO ENUM		
				.build();
		
		
	}
	
	@Override
	public void close() {
		if (connectionShouldBeClosed) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("JdbcUserDao Connection can't be closed", e);
				throw new DaoException(e);
			}
		}
	}
	
}
