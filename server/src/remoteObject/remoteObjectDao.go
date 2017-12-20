package remoteObject

import (
	"generated"
	"player"
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
