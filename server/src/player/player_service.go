package player

import (
	"common"
	"common/remoteObject"
	"encoding/json"
	"errors"
	"logger"

	"github.com/go-messaging-service/goms4go"
)

type PlayerService struct {
	PlayerMovedHandler []func(*SpaceShip)
	dao                *LocalPlayerDao
	messagingService   *goms4go.GomsClient
}

func (service *PlayerService) Init(messagingService *goms4go.GomsClient, playerDao *LocalPlayerDao) {
	service.messagingService = messagingService
	service.dao = playerDao
}

func (service *PlayerService) CreatePlayer(name string) error {
	logger.Debug("Called CreatePlayer with name '" + name + "'")

	base := remoteObject.RemoteObject{
		//0, -23013, 0, -646735535105623500
		X:              common.Coordinate{LightYears: 0, Meters: 0},
		Y:              common.Coordinate{LightYears: -23013, Meters: -646735535105623500},
		Name:           name,
		Rotation:       0,
		MovementVector: common.Vector{X: 0, Y: 0},
		AssetFile:      "assets/textures/spaceship.png",
	}

	ship := &SpaceShip{
		RemoteObject:  base,
		Acceleration:  1000,
		MaxSpeed:      10000,
		RotationSpeed: 250,
	}

	err := service.dao.Add(ship)

	if err == nil {
		return service.sendPlayer(ship, common.PLAYER_CREATED)
	}

	return err
}

func (service *PlayerService) Get(name string) (*SpaceShip, error) {
	logger.Debug("Called GetPlayer with name '" + name + "'")

	player, err := service.dao.Get(name)

	if err != nil {
		return nil, err
	}

	return player, nil
}

func (service *PlayerService) GetAll() []*SpaceShip {
	logger.Debug("Called GetAllPlayer")

	list := []*SpaceShip{}

	for _, v := range service.dao.GetAll() {

		player, err := service.Get(v.Name)

		if err == nil {
			list = append(list, player)
		}
	}

	return list
}

func (service *PlayerService) UpdatePosition(name string, x common.Coordinate, y common.Coordinate, movementVector common.Vector, rotation float32) error {
	logger.Debug("Called SetPlayerPosition with name '" + name + "'")

	p, err := service.Get(name)

	if err != nil {
		return err
	}

	err = service.dao.UpdatePosition(name, x, y, movementVector, rotation)

	if err == nil {
		for _, v := range service.PlayerMovedHandler {
			v(p)
		}
		//return service.sendRemoteObject(&p.RemoteObject, common.REMOTE_OBJECT_MOVED)
		return nil
	}

	return err
}

func (service *PlayerService) sendPlayer(player *SpaceShip, topic string) error {
	dto := ToDto(*player)

	data, err := json.Marshal(dto)

	if err != nil {
		return errors.New("Could not send message: Error while converting player into JSON: " + err.Error())
	}

	playerString := string(data)

	return service.messagingService.Send(playerString, topic)
}
