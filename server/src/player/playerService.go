package player

import (
	"common"
	"common/remoteObject"
	"encoding/json"
	"errors"
	"goms4go"
	"logger"
)

type PlayerService struct {
	dao              *LocalPlayerDao
	messagingService *goms4go.GomsClient
}

func (service *PlayerService) Init(messagingService *goms4go.GomsClient, playerDao *LocalPlayerDao) {
	service.messagingService = messagingService
	service.dao = playerDao
}

func (service *PlayerService) CreatePlayer(name string) error {
	logger.Info("Called CreatePlayer with name '" + name + "'")

	p := &remoteObject.RemoteObject{
		//0, -23013, 600, -6467355351055975L
		X:              common.Coordinate{LightYears: 0, Meters: 600},
		Y:              common.Coordinate{LightYears: -23013, Meters: -6467355351055975},
		Name:           name,
		Rotation:       0,
		MovementVector: common.Vector{X: 0, Y: 0},
		AssetFile:      "assets/textures/spaceship.png",
	}

	err := service.dao.CreatePlayer(p)

	if err == nil {
		return service.sendPlayer(p, common.PLAYER_CREATED)
	}

	return err
}

func (service *PlayerService) GetPlayer(name string) (*remoteObject.RemoteObject, error) {
	logger.Info("Called GetPlayer with name '" + name + "'")

	player, err := service.dao.GetPlayer(name)

	if err != nil {
		return nil, err
	}

	return player, nil
}

func (service *PlayerService) GetAllPlayer() []*remoteObject.RemoteObject {
	logger.Info("Called GetAllPlayer")

	list := []*remoteObject.RemoteObject{}

	for _, v := range service.dao.GetAllPlayer() {

		player, err := service.GetPlayer(v.Name)

		if err == nil {
			list = append(list, player)
		}
	}

	return list
}

func (service *PlayerService) SetPlayerPosition(name string, x common.Coordinate, y common.Coordinate, movementVector common.Vector, rotation float32) error {
	logger.Info("Called SetPlayerPosition with name '" + name + "'")

	p, err := service.GetPlayer(name)

	if err != nil {
		return err
	}

	err = service.dao.SetPlayerPosition(name, x, y, movementVector, rotation)

	if err == nil {
		return service.sendPlayer(p, common.PLAYER_MOVED)
	}

	return err
}

func (service *PlayerService) sendPlayer(player *remoteObject.RemoteObject, topic string) error {
	dto := remoteObject.ToDto(*player)

	data, err := json.Marshal(dto)

	if err != nil {
		return errors.New("Could not send message: Error while converting player into JSON: " + err.Error())
	}

	playerString := string(data)

	return service.messagingService.Send(playerString, topic)
}
