package remoteObject

import (
	"orion_spur_server_prototype/src/material"
	"orion_spur_server_prototype/src/player"
)

type LocalRemoteObjectDao struct {
	playerDao *player.LocalPlayerDao
}

func (dao *LocalRemoteObjectDao) Init(playerDao *player.LocalPlayerDao) {
	dao.playerDao = playerDao
}

func (dao *LocalRemoteObjectDao) GetAll() *material.RemoteObjectListDto {
	list := []material.RemoteObjectDto{}

	for _, v := range dao.playerDao.GetAllPlayer() {
		dto := material.NewRemoteObjectDto(v.GetName(), "assets/textures/spaceship.png", v.GetMovementVector(), v.GetX(), v.GetY(), v.GetRotation())
		list = append(list, dto)
	}

	result := material.RemoteObjectListDto{
		RemoteObjectList: list,
	}

	return &result
}
