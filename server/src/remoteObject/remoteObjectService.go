package remoteObject

import (
	"orion_spur_server_prototype/src/logger"
	"orion_spur_server_prototype/src/material"
)

type RemoteObjectService struct {
	dao *LocalRemoteObjectDao
}

func (service *RemoteObjectService) Init(dao *LocalRemoteObjectDao) {
	service.dao = dao
}

func (service *RemoteObjectService) GetAll() *material.RemoteObjectListDto {
	logger.Info("Called GetAll")

	return service.dao.GetAll()
}
