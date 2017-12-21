package player

import (
	"common"
	"common/remoteObject"
	"errors"
	"fmt"
	"logger"
)

type LocalPlayerDao struct {
	players map[string]*remoteObject.RemoteObject
}

func (dao *LocalPlayerDao) Init() {
	dao.players = make(map[string]*remoteObject.RemoteObject)
}

func (dao *LocalPlayerDao) Add(player *remoteObject.RemoteObject) error {
	_, err := dao.Get(player.Name)

	if err != nil {
		dao.players[player.Name] = player

		return nil
	}

	return errors.New("Player " + player.Name + " already exists")
}

func (dao *LocalPlayerDao) Get(name string) (*remoteObject.RemoteObject, error) {
	player := dao.players[name]

	var err error

	if player == nil {
		err = errors.New("Player " + name + " does not exist")
	}

	return player, err
}

func (dao *LocalPlayerDao) GetAll() []*remoteObject.RemoteObject {
	result := make([]*remoteObject.RemoteObject, len(dao.players))

	i := 0
	for _, v := range dao.players {
		if v != nil {
			result[i] = v
			i++
		}
	}

	return result
}

func (dao *LocalPlayerDao) UpdatePosition(name string, newXPosition common.Coordinate, newYPosition common.Coordinate, newMovementVector common.Vector, rotation float32) error {
	player, err := dao.Get(name)

	if err != nil {
		return err
	}

	logger.Debug(fmt.Sprintf("%v", player))

	player.X = common.Coordinate{LightYears: newXPosition.LightYears, Meters: newXPosition.Meters}
	player.Y = common.Coordinate{LightYears: newYPosition.LightYears, Meters: newYPosition.Meters}
	player.Rotation = rotation
	player.MovementVector = newMovementVector

	p2, _ := dao.Get(name)
	logger.Debug(fmt.Sprintf("%v", p2))

	return nil
}
