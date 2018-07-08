package orion_spur.particles.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.net.HttpStatus;
import com.google.gson.Gson;

import orion_spur.common.exception.HttpException;
import orion_spur.common.generated.ParticleListDto;
import orion_spur.particles.material.Particle;

public class ParticleServiceProxy implements IParticleService
{
	private String	_serviceUrlString	= "http://localhost:8080/particles";
	private Gson	_gson;
	// TODO private ParticleDtoConverter _dtoConverter;
	
	@Override
	public void add(Particle particle)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Collection<Particle> getAll() throws IOException, HttpException
	{
		HttpURLConnection connection = (HttpURLConnection) new URL(_serviceUrlString).openConnection();
		connection.setRequestMethod(HttpMethods.GET);
		
		if (connection.getResponseCode() != HttpStatus.SC_OK)
		{
			throwHttpException(connection);
		}
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		
		// read response lines into single string
		String response = reader.lines().collect(Collectors.joining());
		
		ParticleListDto particleDtos = _gson.fromJson(response, ParticleListDto.class);
		
		if (particleDtos == null)
		{
			return new ArrayList<>();
		}
		
		// TODO return _dtoConverter.convert(particleDtos);
		return new ArrayList<>();
	}
	
	@Override
	public void act(float delta)
	{
		// TODO Auto-generated method stub
		
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
