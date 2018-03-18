package remoteObject

import (
	"common"
	"common/remoteObject"
	"encoding/json"
	"errors"
	"logger"
	"player"

	"github.com/go-messaging-service/goms4go"
)

type RemoteObjectService struct {
	dao              *LocalRemoteObjectDao
	messagingService *goms4go.GomsClient
}

func (service *RemoteObjectService) Init(dao *LocalRemoteObjectDao, messagingService *goms4go.GomsClient, playerService *player.PlayerService) {
	service.dao = dao
	service.messagingService = messagingService

	playerService.PlayerMovedHandler = append(playerService.PlayerMovedHandler, service.handlePlayerMoved)
}

func (service *RemoteObjectService) GetAll() []*remoteObject.RemoteObject {
	logger.Debug("Called GetAll")

	return service.dao.GetAll()
}

func (service *RemoteObjectService) handlePlayerMoved(ship *player.SpaceShip) {
	err := service.sendRemoteObject(&ship.RemoteObject, common.REMOTE_OBJECT_MOVED)

	if err != nil {
		logger.Error("Sending space ship '" + ship.Name + "' via messaging failed")
	}
}

func (service *RemoteObjectService) sendRemoteObject(object *remoteObject.RemoteObject, topic string) error {
	dto := remoteObject.ToDto(*object)

	data, err := json.Marshal(dto)

	if err != nil {
		return errors.New("Could not send message: Error while converting player into JSON: " + err.Error())
	}

	playerString := string(data)

	return service.messagingService.Send(playerString, topic)
}
