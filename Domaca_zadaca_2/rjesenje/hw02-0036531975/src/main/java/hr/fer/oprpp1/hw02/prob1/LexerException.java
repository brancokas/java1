package hr.fer.oprpp1.hw02.prob1;

public class LexerException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Initialize class
	 */
	public LexerException() {}

	/**
	 * @param message message with what has gone wrong
	 */
	public LexerException(String message) {
		super(message);
	}

	/**
	 * @param cause stack trace with what has gone wrong
	 */
	public LexerException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message, cause message and stack trace with what has gone wrong
	 */
	public LexerException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}
