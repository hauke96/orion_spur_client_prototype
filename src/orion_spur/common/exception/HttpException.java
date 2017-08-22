package orion_spur.common.exception;

import com.badlogic.gdx.net.HttpStatus;

import juard.contract.Contract;

public class HttpException extends Exception {
	private int _statusCode;
	private String _message;

	public HttpException(int statusCode, String message)
	{
		Contract.Satisfy(statusCode >= 0);
		Contract.NotNullOrEmpty(message);
		
		_statusCode = statusCode;
		_message = message;
	}
	
	public int getStatusCode()
	{
		return _statusCode;
	}
}
