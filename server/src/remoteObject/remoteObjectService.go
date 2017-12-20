package remoteObject

import (
	"generated"
	"logger"
)

type RemoteObjectService struct {
	dao *LocalRemoteObjectDao
}

func (service *RemoteObjectService) Init(dao *LocalRemoteObjectDao) {
	service.dao = dao
}

func (service *RemoteObjectService) GetAll() *generated.RemoteObjectListDto {
	logger.Info("Called GetAll")

	return service.dao.GetAll()
}
