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
import ua.training.model.dao.BookDao;
import ua.training.model.entities.Book;

public class JdbcBookDao implements BookDao {
	
    private final boolean connectionShouldBeClosed;
    private Connection connection;
    
    private static final Logger LOGGER = LogManager.getLogger(JdbcBookDao.class); 
    
    
	private static final String COLUMN_BOOK_ID = "book_id";
	private static final String COLUMN_TITLE = "title";
	private static final String COLUMN_AUTHOR_ID = "author_id";
	private static final String COLUMN_ISBN = "isbn";

	private static final String INSERT_BOOK = "INSERT INTO books (title, author_id, isbn) VALUES ( ?, ?, ?);";
	private static final String UPDATE_BOOK = "UPDATE books SET title = ?, author_id = ?, isbn = ? WHERE book_id = ?;";
	private static final String DELETE_BOOK = "DELETE FROM books WHERE `id`=?;"; // Cascade delete? From copies of the book.
	
	private static final String FIND_ALL = "SELECT * FROM books JOIN authors USING (author_id)"; //TO DO add book Instance Join
    private static final String FIND_BY_ID = FIND_ALL + " WHERE book_id = ?"; //TO DO add book Instance Join
    
   	public JdbcBookDao(Connection connection) {
   		this.connection = connection;
   		connectionShouldBeClosed = false;
   	}

   	public JdbcBookDao(Connection connection, boolean connectionShouldBeClosed) {
   		this.connection = connection;
   		this.connectionShouldBeClosed = connectionShouldBeClosed;
   	}

   	public void setConnection(Connection connection) {
   		this.connection = connection;
   	}


	@Override
	public List<Book> findAll(){
		List<Book> books = new ArrayList<>();
		try(PreparedStatement query = connection.prepareStatement(FIND_ALL)){				
			ResultSet rs = query.executeQuery();
			while (rs.next()) {
				books.add(extractBookFromResultSet(rs));
			}			
		} catch(SQLException ex){			
			LOGGER.error("JdbcAuthorDao select SQL exception ", ex);
			throw new DaoException(ex);
		}
		return books;
	}


	@Override
	public Optional<Book> getEntityById(Long id) {
		Optional<Book> book = Optional.empty();
		try(PreparedStatement query = connection.prepareStatement(FIND_BY_ID)){			
			query.setLong(1, id);
			ResultSet rs = query.executeQuery();
			if (rs.next()) {
				book = Optional.of(extractBookFromResultSet(rs));
			}			
		} catch(SQLException ex){			
			LOGGER.error("JdbcBookDao select SQL exception " + id, ex);
			throw new DaoException(ex);
		}
		return book; //!
	}


	@Override
	public Long create(Book entity) {
		try(PreparedStatement query = connection.prepareStatement(INSERT_BOOK, Statement.RETURN_GENERATED_KEYS)){			
			query.setString(1, entity.getTitle());
			query.setLong(2, entity.getAuthor().getId()); // TO DO
			query.setLong(3, entity.getIsbn());
			
			query.executeUpdate();
			ResultSet keys = query.getGeneratedKeys();
			if (keys.next()) {
				entity.setId(keys.getLong(1));
			}			
		} catch(SQLException ex){			
			LOGGER.error("JdbcAuthorDao insert SQL exception " + entity.getTitle(), ex);
			throw new DaoException(ex);
		}
		return entity.getId(); //!
	}


	@Override
	public Book update(Book entity){
		try(PreparedStatement query = connection.prepareStatement(UPDATE_BOOK)){			
			query.setString(1, entity.getTitle());
			query.setLong(2, entity.getAuthor().getId());
			query.setLong(3, entity.getIsbn());
			query.setLong(4, entity.getId());
			
			query.executeUpdate();
			
		} catch(SQLException ex){			
			LOGGER.error("JdbcBookDao update SQL exception " + entity.getId(), ex);
			throw new DaoException(ex);
		}
		return entity;
	}


	@Override
	public boolean delete(Long id){
		int deletedRow = 0;
		try (PreparedStatement query = connection.prepareStatement(DELETE_BOOK)) {
			query.setLong(1, id);
			deletedRow = query.executeUpdate();
		} catch(SQLException ex){
			LOGGER.error("JdbcBookDao delete SQL exception " + id, ex);
			throw new DaoException(ex);
		}
		return deletedRow > 0;
	}



	protected static Book extractBookFromResultSet(ResultSet resultSet) throws SQLException {
		return new Book.Builder()
						.setId(resultSet.getLong(COLUMN_BOOK_ID))
						.setTitle(resultSet.getString(COLUMN_TITLE))
						.setAuthor(JdbcAuthorDao.extractAuthorFromResultSet(resultSet)) // Need to do, but how?
						.setIsbn(resultSet.getLong(COLUMN_ISBN))
						.build();
	}
	
	
	@Override
	public void close() {
		if (connectionShouldBeClosed) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("JdbcBookDao Connection can't be closed", e);
				throw new DaoException(e);
			}
		}
	}
 
}
