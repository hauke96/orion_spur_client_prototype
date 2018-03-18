package remoteObject

import (
	"common/remoteObject"
	"player"
)

type LocalRemoteObjectDao struct {
	playerDao *player.LocalPlayerDao
}

func (dao *LocalRemoteObjectDao) Init(playerDao *player.LocalPlayerDao) {
	dao.playerDao = playerDao
}

func (dao *LocalRemoteObjectDao) GetAll() []*remoteObject.RemoteObject {
	result := []*remoteObject.RemoteObject{}

	spaceShip := dao.playerDao.GetAll()

	for _, v := range spaceShip {
		result = append(result, &v.RemoteObject)
	}

	return result
}
