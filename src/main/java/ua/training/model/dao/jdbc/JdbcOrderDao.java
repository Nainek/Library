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
import ua.training.model.dao.OrderDao;
import ua.training.model.entities.TypeOfOrder;
import ua.training.model.entities.Order;

public class JdbcOrderDao implements OrderDao {

	
    private final boolean connectionShouldBeClosed;
    private Connection connection;
    
    private static Logger LOGGER = LogManager.getLogger(JdbcOrderDao.class);
    


   	private static final String COLUMN_ORDER_ID = "order_id";
   	private static final String COLUMN_USER_ID = "user_id";
   	private static final String COLUMN_INVENTORY_NUMBER_OF_BOOK = "inventory_number_of_book";
   	private static final String COLUMN_DATE_FROM = "date_from";
   	private static final String COLUMN_REUQIRE_RETURN_DATE = "require_return_date";
   	private static final String COLUMN_ACTUAL_RETURN_DATE = "actual_return_date";
   	private static final String COLUMN_TYPE_OF_ORDER = "type_of_order";


   	private static final String INSERT_ORDER = "INSERT INTO orders  (`user_id, inventory_number_of_book, "
   			+ "date_from, require_return_date, actual_return_date, type_of_order) VALUES ( ?, ?, ?, ?, ?, ?);";
   	private static final String UPDATE_ORDER = "UPDATE orders SET user_id = ?, inventory_number_of_book = ?, "
   			+ "date_from = ?, require_return_date = ?, actual_return_date = ?, type_of_order = ? WHERE order_id = ?; ";
   	private static final String DELETE_ORDER = "DELETE FROM orders WHERE order_id=?;"; // Cascade delete? 
   	
   	private static final String FIND_ALL = "SELECT * FROM orders JOIN users USING(user_id)"; //TO DO a
   	private static final String FIND_BY_ID = FIND_ALL + " WHERE order_id = ?"; //TO DO add book Instance Join
	
	
   	public JdbcOrderDao(Connection connection) {
   		this.connection = connection;
   		connectionShouldBeClosed = false;
   	}

   	public JdbcOrderDao(Connection connection, boolean connectionShouldBeClosed) {
   		this.connection = connection;
   		this.connectionShouldBeClosed = connectionShouldBeClosed;
   	}

   	public void setConnection(Connection connection) {
   		this.connection = connection;
   	}


	@Override
	public List<Order> findAll() {
		List<Order> order = new ArrayList<>();
		
		try (PreparedStatement query = connection.prepareStatement(FIND_ALL)){
			
			
			ResultSet rs = query.executeQuery();
			
			
			while(rs.next()){
				order.add(extractOrderFromResultSet(rs));
			}
		} catch(SQLException ex){
			LOGGER.error("JdbcUserDao select SQL exception ", ex);
			throw new DaoException(ex);
		}
		return order;
	}


	@Override
	public Optional<Order> getEntityById(Long id) {
		Optional<Order> order = Optional.empty();
		
		try (PreparedStatement query = connection.prepareStatement(FIND_BY_ID)){
			
			query.setLong(1, id);
			
			ResultSet rs = query.executeQuery();
			
			
			if(rs.next()){
				order = Optional.of(extractOrderFromResultSet(rs));
			}
		} catch(SQLException ex){
			LOGGER.error("JdbcUserDao select SQL exception " + id, ex);
			throw new DaoException(ex);
		}
		return order;
	}


	@Override
	public Long create(Order entity) {
try (PreparedStatement query = connection.prepareStatement(INSERT_ORDER, Statement.RETURN_GENERATED_KEYS)){
			
			query.setLong(1, entity.getUser().getId());
			query.setLong(2, entity.getCopyOfTheBook().getInventoryNumber());
			query.setTimestamp(3, entity.getOrderDate());
			query.setTimestamp(4, entity.getRequireReturnDate());
			query.setTimestamp(5, entity.getActualReturnDate());
			query.setString(6, entity.getTypeOfOrder().toString());
			
			
			ResultSet key = query.executeQuery();
			
			if(key.next()){
				entity.setId(key.getLong(1));
			}
			
		} catch(SQLException ex){
			LOGGER.error("JdbcUserDao insert SQL exception ", ex);
			throw new DaoException(ex);
		}
		return entity.getId();
	}


	@Override
	public Order update(Order entity) {
		try (PreparedStatement query = connection.prepareStatement(UPDATE_ORDER)){
			
			query.setLong(1, entity.getUser().getId());
			query.setLong(2, entity.getCopyOfTheBook().getId());
			query.setTimestamp(3, entity.getOrderDate());
			query.setTimestamp(4, entity.getRequireReturnDate());
			query.setTimestamp(5, entity.getActualReturnDate());
			query.setString(6, entity.getTypeOfOrder().toString());
			query.setLong(7, entity.getId());
			
			query.executeUpdate();
			
			
			
		}catch (SQLException ex){
			LOGGER.error("JdbcOrderDao update SQL exception" + entity.getId(), ex);
			throw new DaoException(ex);
		}
		return entity;
	}
	


	@Override
	public boolean delete(Long id) {
		int deletedRow = 0;
		try(PreparedStatement query = connection.prepareStatement(DELETE_ORDER)){
			
			query.setLong((1), id);
			
			deletedRow = query.executeUpdate();
			
			
		} catch(SQLException ex) {
			LOGGER.error("JdbcOrderDao delete SQL exception" + id, ex);
			throw new DaoException(ex);
		}
		
		
		return deletedRow > 0;
	}
	
	
	public static Order extractOrderFromResultSet (ResultSet rs) throws SQLException{
		
		return new Order.Builder()
						.setId(rs.getLong(COLUMN_ORDER_ID))
						.setUser(JdbcUserDao.extractUserFromResultSet(rs))
						.setBookInstance(JdbcBookInstanceDao.extractBookInstanceFromResultSet(rs))
						.setOrderDate(rs.getTimestamp(COLUMN_DATE_FROM))
						.setReuireReturnDate(rs.getTimestamp(COLUMN_REUQIRE_RETURN_DATE))
						.setActualReturnDate(rs.getTimestamp(COLUMN_ACTUAL_RETURN_DATE))
						.setTypeOfOrder(TypeOfOrder.forValue(rs.getString(COLUMN_TYPE_OF_ORDER)))
						.build();
	}
	
	@Override
	public void close() {
		if (connectionShouldBeClosed) {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("JdbcOrderDao Connection can't be closed", e);
				throw new DaoException(e);
			}
		}
	}




}
