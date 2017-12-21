package player

import (
	"common"
	"common/remoteObject"
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

func (service *PlayerService) GetAllPlayer() []*remoteObject.RemoteObject {
	logger.Info("Called GetAllPlayer")

	list := []*remoteObject.RemoteObject{}

	for _, v := range service.dao.GetAllPlayer() {
		vec := common.Vector{
			X: v.GetMovementVector().GetX(),
			Y: v.GetMovementVector().GetY(),
		}

		x := common.Coordinate{
			LightYears: v.GetX().GetLightYears(),
			Meters:     v.GetX().GetMeters(),
		}

		y := common.Coordinate{
			LightYears: v.GetY().GetLightYears(),
			Meters:     v.GetY().GetMeters(),
		}

		object := remoteObject.RemoteObject{Name: v.GetName(), AssetFile: "assets/textures/spaceship.png", MovementVector: vec, X: x, Y: y, Rotation: v.GetRotation()}

		list = append(list, &object)
	}

	return list
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
