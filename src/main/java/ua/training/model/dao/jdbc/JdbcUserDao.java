package ua.training.model.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mysql.cj.api.jdbc.Statement;

import ua.training.exceptions.DaoException;
import ua.training.model.dao.UserDao;
import ua.training.model.entities.User;
import ua.training.model.entities.UserRole;

public class JdbcUserDao implements UserDao {

    private final boolean connectionShouldBeClosed;
    private Connection connection;
    
    private static Logger LOGGER = LogManager.getLogger(JdbcUserDao.class);
    
   	private static final String COLUMN_USER_ID = "user_id";
   	private static final String COLUMN_FIRST_NAME = "first_name";
   	private static final String COLUMN_LAST_NAME = "last_name";
   	private static final String COLUMN_EMAIL = "email";
   	private static final String COLUMN_PASSWORD = "password";
   	private static final String COLUMN_ROLE = "role";
//   	private static final String COLUMN_SALT = "salt";

   	private static final String INSERT_USER = "INSERT INTO users JOIN users_login USING (user_id) (first_name, last_name, email, password, role) VALUES ( ?, ?, ?, ?, ?);";
   	private static final String UPDATE_USER = "UPDATE users JOIN users_login USING (user_id) SET first_name=?, last_name=?, email=?, password=?, role=? WHERE user_id=?; ";
   	private static final String DELETE_USER = "DELETE FROM users WHERE user_id`=?;"; // Cascade delete? 
   	
   	private static final String FIND_ALL = "SELECT * FROM users JOIN users_login USING (user_id)"; //TO DO a
   	private static final String FIND_BY_ID = FIND_ALL + " WHERE user_id = ?"; //TO DO add book Instance Join
	
   	public JdbcUserDao(Connection connection) {
   		this.connection = connection;
   		connectionShouldBeClosed = false;
   	}

   	public JdbcUserDao(Connection connection, boolean connectionShouldBeClosed) {
   		this.connection = connection;
   		this.connectionShouldBeClosed = connectionShouldBeClosed;
   	}

   	public void setConnection(Connection connection) {
   		this.connection = connection;
   	}

	@Override
	public List<User> findAll() {
		List<User> users = new ArrayList<>();
		
		try (PreparedStatement query = connection.prepareStatement(FIND_ALL)){
						
			ResultSet rs = query.executeQuery();
			
			
			while(rs.next()){
				users.add(extractUserFromResultSet(rs));
			}
		} catch(SQLException ex){
			LOGGER.error("JdbcUserDao select SQL exception ", ex);
			throw new DaoException(ex);
		}
		return users;
	}


	@Override
	public Optional<User> getEntityById(Long id) {
		Optional<User> user = Optional.empty();
		
		try (PreparedStatement query = connection.prepareStatement(FIND_BY_ID)){
			
			query.setLong(1, id);
			
			ResultSet rs = query.executeQuery();
			
			
			if(rs.next()){
				user = Optional.of(extractUserFromResultSet(rs));
			}
		} catch(SQLException ex){
			LOGGER.error("JdbcUserDao select SQL exception " + id, ex);
			throw new DaoException(ex);
		}
		return user;
	}


	@Override
	public Long create(User entity) {
		try (PreparedStatement query = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)){
			
			query.setString(1, entity.getFirstName());
			query.setString(2, entity.getLastName());
			query.setString(3, entity.getEmail());
			query.setString(4, entity.getPassword());
			query.setString(5, entity.getUserRole().toString());
			
			
			ResultSet key = query.executeQuery();
			
			if(key.next()){
				entity.setId(key.getLong(1));
			}
			
		} catch(SQLException ex){
			LOGGER.error("JdbcUserDao insert SQL exception " + entity.getFirstName(), ex);
			throw new DaoException(ex);
		}
		return entity.getId();
	}


	@Override
	public User update(User entity) {
		try (PreparedStatement query = connection.prepareStatement(UPDATE_USER)){
			
			query.setString(1, entity.getFirstName());
			query.setString(2, entity.getLastName());
			query.setString(3, entity.getEmail());
			query.setString(4, entity.getPassword());
			query.setString(5, entity.getUserRole().toString());
			query.setLong(6, entity.getId());
			
			query.executeUpdate();
			
			
			
		}catch (SQLException ex){
			LOGGER.error("JdbcUserDao update SQL exception" + entity.getId(), ex);
			throw new DaoException(ex);
		}
		return entity;
	}


	@Override
	public boolean delete(Long id) {
		int deletedRow = 0;
		try(PreparedStatement query = connection.prepareStatement(DELETE_USER)){
			
			query.setLong((1), id);
			
			deletedRow = query.executeUpdate();
			
			
		} catch(SQLException ex) {
			LOGGER.error("JdbcUserDao delete SQL exception" + id, ex);
			throw new DaoException(ex);
		}
		
		
		return deletedRow > 0;
	}



	public static User extractUserFromResultSet (ResultSet rs) throws SQLException{
		
		return new User.Builder()
						.setId(rs.getLong(COLUMN_USER_ID))
						.setFirstName(rs.getString(COLUMN_FIRST_NAME))
						.setLastName(rs.getString(COLUMN_LAST_NAME))
						.setEmail(rs.getString(COLUMN_EMAIL))
						.setPassword(rs.getString(COLUMN_PASSWORD))
						.setUserRole(UserRole.forValue(rs.getString(COLUMN_ROLE))) //TODO
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
