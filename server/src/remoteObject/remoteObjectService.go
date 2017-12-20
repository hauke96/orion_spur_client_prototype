package remoteObject

import (
	"logger"
)

type RemoteObjectService struct {
	dao *LocalRemoteObjectDao
}

func (service *RemoteObjectService) Init(dao *LocalRemoteObjectDao) {
	service.dao = dao
}

func (service *RemoteObjectService) GetAll() *[]RemoteObject {
	logger.Info("Called GetAll")

	return service.dao.GetAll()
}
