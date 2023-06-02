package hr.fer.oprpp1.custom.collections;

/**
 * Class EmptyStackException is a Runtime Exception class
 * ment to signal the client that there was an illegal
 * operation performed with stack
 * 
 * @author Branimir Stankovic
 * @version 1.0
 * */

public class EmptyStackException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Initialize class
	 */
	public EmptyStackException() {}

	/**
	 * @param message message with what has gone wrong
	 */
	public EmptyStackException(String message) {
		super(message);
	}

	/**
	 * @param cause stack trace with what has gone wrong
	 */
	public EmptyStackException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message, cause message and stack trace with what has gone wrong
	 */
	public EmptyStackException(String message, Throwable cause) {
		super(message, cause);
	}

}
