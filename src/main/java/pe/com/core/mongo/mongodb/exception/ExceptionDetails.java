package pe.com.core.mongo.mongodb.exception;

import java.util.Map;

public class ExceptionDetails {

	private String errorCode;
	private String causeMessage;
	private Object[] errorMessageArguments;
	private Map<String, Object> properties;
	private Throwable baseException;

	/**
	 * 
	 * @param errorCode
	 * @param causeMessage
	 * @param errorMessageArguments
	 * @param baseException
	 */
	public ExceptionDetails(String errorCode, String causeMessage, Object[] errorMessageArguments,
			Throwable baseException) {
		this.errorCode = errorCode;
		this.causeMessage = causeMessage;
		if (errorMessageArguments != null) {
			this.errorMessageArguments = new Object[errorMessageArguments.length];
			System.arraycopy(errorMessageArguments, 0, this.errorMessageArguments, 0, errorMessageArguments.length);
		} else {
			this.errorMessageArguments = null;
		}
		this.baseException = baseException;
	}

	public ExceptionDetails(String errorCode) {
		this(errorCode, null, null, null);
	}

	public ExceptionDetails(String errorCode, Object[] errorMessageArguments) {
		this(errorCode, null, errorMessageArguments, null);
	}

	public ExceptionDetails(String errorCode, String causeMessage) {
		this(errorCode, causeMessage, null, null);
	}

	public ExceptionDetails(String errorCode, String causeMessage, Object[] errorMessageArguments) {
		this(errorCode, causeMessage, errorMessageArguments, null);
	}

	public Throwable getBaseException() {
		return this.baseException;
	}

	public void setBaseException(Throwable baseException) {
		this.baseException = baseException;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Object[] getErrorMessageArguments() {
		return this.errorMessageArguments == null ? null : this.errorMessageArguments.clone();
	}

	public void setErrorMessageArguments(Object[] errorMessageArguments) {
		this.errorMessageArguments = new Object[errorMessageArguments.length];
		System.arraycopy(errorMessageArguments, 0, this.errorMessageArguments, 0, errorMessageArguments.length);
	}

	public Map<String, Object> getProperties() {
		return this.properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public String getCauseMessage() {
		return this.causeMessage;
	}

	public void setCauseMessage(String causeMessage) {
		this.causeMessage = causeMessage;
	}

}
