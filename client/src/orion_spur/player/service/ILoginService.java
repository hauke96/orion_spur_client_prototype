package orion_spur.player.service;

import java.io.IOException;

import orion_spur.common.exception.HttpException;

public interface ILoginService
{
	void Login(String id) throws IOException, HttpException;
	
	void Logout(String id) throws IOException, HttpException;
}
