package pe.com.core.mongo.mongodb.exception;

public class CoreException extends RuntimeException  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ExceptionDetails	exceptionDetails;

	public CoreException(Throwable cause, ExceptionDetails exceptionDetails) {
		super(cause);
		this.exceptionDetails = exceptionDetails;
	}

	public CoreException(ExceptionDetails exceptionDetails) {
		this.exceptionDetails = exceptionDetails;
	}

	public CoreException(String anErrorCode) {
		this(null, anErrorCode, null);
	}

	public CoreException(String anErrorCode, Object[] anErrorMessageArguments) {
		this(null, anErrorCode, anErrorMessageArguments);
	}

	public CoreException(Throwable cause, String anErrorCode) {
		this(cause, anErrorCode, null);
	}

	public CoreException(Throwable cause, String anErrorCode, Object[] anErrorMessageArguments) {
		this(cause, new ExceptionDetails(anErrorCode, anErrorMessageArguments));
	}

	public ExceptionDetails getExceptionDetails() {
		return this.exceptionDetails;
	}

	public void setExceptionDetails(ExceptionDetails exceptionDetails) {
		this.exceptionDetails = exceptionDetails;
	}
}
