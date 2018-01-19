package orion_spur.player.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.net.HttpStatus;

import juard.contract.Contract;
import orion_spur.common.exception.HttpException;

public class PlayerLoginServiceProxy implements ILoginService
{
	private String _serviceUrlString = "http://localhost:8080/login/";
	
	@Override
	public void Login(String id) throws IOException, HttpException
	{
		Contract.NotNullOrEmpty(id);
		
		URL url = new URL(_serviceUrlString + id);
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethods.POST);
		
		if (connection.getResponseCode() != HttpStatus.SC_OK)
		{
			throwHttpException(connection);
		}
	}
	
	@Override
	public void Logout(String id) throws IOException, HttpException
	{
		Contract.NotNullOrEmpty(id);
		
		URL url = new URL(_serviceUrlString + id);
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethods.DELETE);
		
		if (connection.getResponseCode() != HttpStatus.SC_OK)
		{
			throwHttpException(connection);
		}
	}
	
	private void throwHttpException(HttpURLConnection connection) throws IOException, HttpException
	{
		Reader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
		// TODO first charavter is thrown away
		char[] cbuf = new char[reader.read()];
		reader.read(cbuf);
		throw new HttpException(connection.getResponseCode(), new String(cbuf));
	}
}
