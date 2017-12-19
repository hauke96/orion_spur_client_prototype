package remoteObject

import (
	"orion_spur_server_prototype/src/generated"
	"orion_spur_server_prototype/src/player"
)

type LocalRemoteObjectDao struct {
	playerDao *player.LocalPlayerDao
}

func (dao *LocalRemoteObjectDao) Init(playerDao *player.LocalPlayerDao) {
	dao.playerDao = playerDao
}

func (dao *LocalRemoteObjectDao) GetAll() *generated.RemoteObjectListDto {
	list := []generated.RemoteObjectDto{}

	for _, v := range dao.playerDao.GetAllPlayer() {
		dto := generated.NewRemoteObjectDto(v.GetName(), "assets/textures/spaceship.png", v.GetMovementVector(), v.GetX(), v.GetY(), v.GetRotation())
		list = append(list, dto)
	}

	result := generated.RemoteObjectListDto{
		RemoteObjectList: list,
	}

	return &result
}
