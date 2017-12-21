package remoteObject

import (
	"common/remoteObject"
	"logger"
)

type RemoteObjectService struct {
	dao *LocalRemoteObjectDao
}

func (service *RemoteObjectService) Init(dao *LocalRemoteObjectDao) {
	service.dao = dao
}

func (service *RemoteObjectService) GetAll() *[]remoteObject.RemoteObject {
	logger.Info("Called GetAll")

	return service.dao.GetAll()
}
