package ua.training.model.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ua.training.exceptions.DaoException;


public abstract class DaoFactory {
	
	private static final Logger LOGGER = LogManager.getLogger(DaoFactory.class);
	
	public abstract DaoConnection getConnection();
    public abstract UserDao createUserDao();
    public abstract BookDao createBookDao();
    public abstract BookInstanceDao createBookInstanceDao();
    public abstract OrderDao createOrderDao();
    public abstract AuthorDao createAuthorDao();

    
    public abstract UserDao createUserDao( DaoConnection connection );
    public abstract BookDao createBookDao( DaoConnection connection );
    public abstract BookInstanceDao createBookInstanceDao( DaoConnection connection );
    public abstract OrderDao createOrderDao( DaoConnection connection );
    public abstract AuthorDao createAuthorDao( DaoConnection connection );

    public static final String DB_FILE = "/db.properties";
    private static final String DB_FACTORY_CLASS = "factory.class";
    private static  DaoFactory instance;

    public static DaoFactory getDaoFactory(){
        if( instance == null) {
            try {
                InputStream inputStream =
                        DaoFactory.class.getResourceAsStream(DB_FILE);
                Properties dbProps = new Properties();
                dbProps.load(inputStream);
                String factoryClass = dbProps.getProperty(DB_FACTORY_CLASS);
                instance = (DaoFactory) Class.forName(factoryClass).newInstance();

            } catch (IOException | IllegalAccessException|
                     InstantiationException |ClassNotFoundException e ) {
            	LOGGER.error("Can't load inputStream db config file to properties object",e);
                throw new DaoException(e);
            }
        }
        return instance;
    }
}