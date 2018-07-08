package orion_spur.particles.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.net.HttpStatus;
import com.google.gson.Gson;

import juard.contract.Contract;
import orion_spur.common.converter.ICoordinateConverter;
import orion_spur.common.exception.HttpException;
import orion_spur.common.generated.ParticleDto;
import orion_spur.common.generated.ParticleListDto;
import orion_spur.particles.material.BulletParticleDtoConverter;
import orion_spur.particles.material.Particle;
import orion_spur.remoteObjects.material.RemoteObjectDtoConverter;

public class ParticleServiceProxy implements IParticleService
{
	private String						_serviceUrlString	= "http://localhost:8080/particles";
	private Gson						_gson;
	private BulletParticleDtoConverter	_dtoConverter;
	private Collection<Particle>		_particles;
	
	public ParticleServiceProxy(ICoordinateConverter coordinateConverter)
	{
		Contract.NotNull(coordinateConverter);
		
		_gson = new Gson();
		
		RemoteObjectDtoConverter remoteObjectDtoConverter = new RemoteObjectDtoConverter();
		
		_dtoConverter = new BulletParticleDtoConverter(remoteObjectDtoConverter, coordinateConverter);
		
		_particles = new HashSet<>();
		
		// TODO Create goms-event for added particles
		// TODO Register to goms-event and add new particle(s) to collection. Consider the elapsed time and set position
		// properly!
	}
	
	@Override
	public void add(Particle particle) throws IOException, HttpException
	{
		Contract.NotNull(particle);
		
		_particles.add(particle);
		
		StringBuilder params = new StringBuilder(_serviceUrlString);
		
		URL url = new URL(params.toString());
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(HttpMethods.POST);
		connection.setDoOutput(true);
		
		ParticleDto dto = _dtoConverter.convert(particle);
		
		String data = _gson.toJson(dto);
		
		connection.getOutputStream().write(data.getBytes());
		
		if (connection.getResponseCode() != HttpStatus.SC_OK)
		{
			throwHttpException(connection);
		}
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
		
		// TODO set position based on elapsed time. First add in the Particle-class the timestamp
		
		// Do not set, but add missing particles
		_particles = _dtoConverter.convert(particleDtos);
		
		return _particles;
	}
	
	@Override
	public void act(float delta)
	{
		for (Particle particle : _particles)
		{
			Vector2 movementAdjustion = particle.getMovementVector().scl(delta);
			
			Vector2 newPosition = particle.getPosition().add(movementAdjustion);
			
			particle.setPosition(newPosition);
		}
	}
	
	private void throwHttpException(HttpURLConnection connection) throws IOException, HttpException
	{
		String message = "unknown";
		InputStream errorStream = connection.getErrorStream();
		
		if (errorStream != null)
		{
			Reader reader = new BufferedReader(new InputStreamReader(errorStream));
			// TODO first charavter is thrown away
			char[] cbuf = new char[reader.read()];
			reader.read(cbuf);
			message = new String(cbuf);
		}
		
		throw new HttpException(connection.getResponseCode(), message);
	}
}
