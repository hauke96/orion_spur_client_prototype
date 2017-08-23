package orion_spur.common.exception;

import com.badlogic.gdx.net.HttpStatus;

import juard.contract.Contract;

public class HttpException extends Exception {
	private int _statusCode;

	public HttpException(int statusCode, String message)
	{
		super("Exited with HTTP status code "+statusCode+". Details: "+message);
		
		Contract.Satisfy(statusCode >= 0);
		Contract.NotNullOrEmpty(message);
		
		_statusCode = statusCode;
	}
	
	public int getStatusCode()
	{
		return _statusCode;
	}
}
