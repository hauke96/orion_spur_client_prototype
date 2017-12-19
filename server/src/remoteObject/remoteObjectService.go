package remoteObject

import (
	"orion_spur_server_prototype/src/generated"
	"orion_spur_server_prototype/src/logger"
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
