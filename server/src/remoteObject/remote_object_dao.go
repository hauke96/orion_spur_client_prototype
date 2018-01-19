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
	return dao.playerDao.GetAll()
}
