package ua.training.model.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import ua.training.exceptions.DaoException;
import ua.training.model.dao.AuthorDao;
import ua.training.model.entities.Author;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JdbcAuthorDao implements AuthorDao{
	
    private final boolean connectionShouldBeClosed;
    private Connection connection;
    
    private static final Logger LOGGER = LogManager.getLogger(JdbcAuthorDao.class); 
    

	private static final String COLUMN_AUTHOR_ID = "author_id";
	private static final String COLUMN_FIRST_NAME = "first_name";
	private static final String COLUMN_LAST_NAME = "last_name";

	private static final String INSERT_AUTHOR = "INSERT INTO authors (first_name, last_name) VALUES ( ?, ?);";
	private static final String UPDATE_AUTHOR = "UPDATE authors SET first_name=?, last_name=? WHERE author_id=?; ";
	private static final String DELETE_AUTHOR = "DELETE FROM authors WHERE author_id=?;";
	
	private static final String FIND_ALL = "SELECT * FROM authors";
    private static final String FIND_BY_ID = FIND_ALL + " WHERE author_id = ?";
	
    
	public JdbcAuthorDao(Connection connection) {
		this.connection = connection;
		connectionShouldBeClosed = false;
	}

	public JdbcAuthorDao(Connection connection, boolean connectionShouldBeClosed) {
		this.connection = connection;
		this.connectionShouldBeClosed = connectionShouldBeClosed;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}


	@Override
	public List<Author> findAll(){
		List<Author> authors = new ArrayList<>();;
		try(PreparedStatement query = connection.prepareStatement(FIND_ALL)){				
			ResultSet rs = query.executeQuery();
			while (rs.next()) {
				authors.add(extractAuthorFromResultSet(rs));
			}			
		} catch(SQLException ex){			
			LOGGER.error("JdbcAuthorDao select SQL exception ", ex);
			throw new DaoException(ex);
		}
		return authors;
	}

	@Override
	public Optional<Author> getEntityById(Long id){
		Optional<Author> author = Optional.empty();
		try(PreparedStatement query = connection.prepareStatement(FIND_BY_ID)){			
			query.setLong(1, id);
			ResultSet rs = query.executeQuery();
			if (rs.next()) {
				author = Optional.of(extractAuthorFromResultSet(rs));
			}			
		} catch(SQLException ex){			
			LOGGER.error("JdbcAuthorDao select SQL exception " + id, ex);
			throw new DaoException(ex);
		}
		return author; //!
	}

	@Override
	public Long create(Author entity){
		try(PreparedStatement query = connection.prepareStatement(INSERT_AUTHOR, Statement.RETURN_GENERATED_KEYS)){			
			query.setString(1, entity.getFirstName());
			query.setString(2, entity.getLastName());
			query.executeUpdate();
			ResultSet keys = query.getGeneratedKeys();
			if (keys.next()) {
				entity.setId(keys.getLong(1));
			}			
		} catch(SQLException ex){			
			LOGGER.error("JdbcAuthorDao insert SQL exception " + entity.getFirstName(), ex);
			throw new DaoException(ex);
		}
		return entity.getId(); //!
	}

	@Override
	public Author update(Author entity){
		try(PreparedStatement query = connection.prepareStatement(UPDATE_AUTHOR)){			
			query.setString(1, entity.getFirstName());
			query.setString(2, entity.getLastName());
			query.setLong(3, entity.getId());	
			
			query.executeUpdate();
			
		} catch(SQLException ex){			
			LOGGER.error("JdbcAuthorDao update SQL exception " + entity.getId(), ex);
			throw new DaoException(ex);
		}
		return entity;
	}

	@Override
	public boolean delete(Long id){
		int deletedRow = 0;
		try (PreparedStatement query = connection.prepareStatement(DELETE_AUTHOR)) {
			query.setLong(1, id);
			deletedRow = query.executeUpdate();
		} catch(SQLException ex){
			LOGGER.error("JdbcAuthorDao delete SQL exception " + id, ex);
			throw new DaoException(ex);
		}
		return deletedRow > 0;
	}
	
	
	protected static Author extractAuthorFromResultSet(ResultSet resultSet) throws SQLException {
		return new Author.Builder()
						.setId(resultSet.getLong(COLUMN_AUTHOR_ID))
						.setFirstName(resultSet.getString(COLUMN_FIRST_NAME))
						.setLastName(resultSet.getString(COLUMN_LAST_NAME))
						.build();
	}
	@Override
	public void close() {
		if (connectionShouldBeClosed) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("JdbcAuthorDao Connection can't be closed", e);
				throw new DaoException(e);
			}
		}
	}

}
