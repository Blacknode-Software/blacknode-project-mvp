package software.blacknode.backend.domain.exception;

public class BlacknodeException extends RuntimeException {

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
