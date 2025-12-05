package software.blacknode.backend.domain.exception;

public class BlacknodeException extends RuntimeException {

	public static final String DEFAULT_MESSAGE = "An unexpected error occurred in Blacknode.";
	
	public static BlacknodeException of(String message) {
		return new BlacknodeException(message);
	}
	
	public static void throwWith(String message) {
		throw new BlacknodeException(message);
	}
	
	private static final long serialVersionUID = 6153034980513150002L;
	
	public BlacknodeException() {
		super();
	}

	public BlacknodeException(String message) {
		super(message);
	}

	public BlacknodeException(String message, Throwable cause) {
		super(message, cause);
	}

	public BlacknodeException(Throwable cause) {
		super(cause);
	}

}
