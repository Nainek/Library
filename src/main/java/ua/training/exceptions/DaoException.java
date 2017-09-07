package ua.training.exceptions;

public class DaoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DaoException(String messageKey) {
		super(messageKey);
	}

	public DaoException(Throwable cause) {
		super(cause);
	}

	public DaoException(String messageKey, Throwable cause) {
		super(messageKey, cause);
	}
	
}
