package player

import (
	"common"
	"encoding/json"
	"errors"
	"generated"
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

	p := &generated.RemoteObjectDto{
		//0, -23013, 600, -6467355351055975L
		X:              generated.NewCoordinateDto(0, 600),
		Y:              generated.NewCoordinateDto(-23013, -6467355351055975),
		Name:           name,
		Rotation:       0,
		MovementVector: generated.NewVectorDto(0, 0),
		AssetFile:      "assets/textures/spaceship.png",
	}

	err := service.dao.CreatePlayer(p)

	if err == nil {
		return service.sendPlayer(p, common.PLAYER_CREATED)
	}

	return err
}

func (service *PlayerService) GetPlayer(name string) (*generated.RemoteObjectDto, error) {
	logger.Info("Called GetPlayer with name '" + name + "'")

	player, err := service.dao.GetPlayer(name)

	if err != nil {
		return nil, err
	}

	return player, nil
}

func (service *PlayerService) GetAllPlayer() []*generated.RemoteObjectDto {
	logger.Info("Called GetAllPlayer")

	return service.dao.GetAllPlayer()
}

func (service *PlayerService) SetPlayerPosition(name string, x generated.CoordinateDto, y generated.CoordinateDto, movementVector generated.VectorDto, rotation float32) error {
	logger.Info("Called SetPlayerPosition with name '" + name + "'")

	p, err := service.dao.GetPlayer(name)

	if err != nil {
		return err
	}

	err = service.dao.SetPlayerPosition(name, x, y, movementVector, rotation)

	if err == nil {
		return service.sendPlayer(p, common.PLAYER_MOVED)
	}

	return err
}

func (service *PlayerService) sendPlayer(player *generated.RemoteObjectDto, topic string) error {
	data, err := json.Marshal(*player)

	if err != nil {
		return errors.New("Could not send message: Error while converting player into JSON: " + err.Error())
	}

	playerString := string(data)

	return service.messagingService.Send(playerString, topic)
}
