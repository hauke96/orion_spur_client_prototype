package remoteObject

import (
	"common"
	"common/remoteObject"
	"player"
)

type LocalRemoteObjectDao struct {
	playerDao *player.LocalPlayerDao
}

func (dao *LocalRemoteObjectDao) Init(playerDao *player.LocalPlayerDao) {
	dao.playerDao = playerDao
}

func (dao *LocalRemoteObjectDao) GetAll() *[]remoteObject.RemoteObject {
	list := []remoteObject.RemoteObject{}

	for _, v := range dao.playerDao.GetAllPlayer() {
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

		dto := remoteObject.RemoteObject{Name: v.GetName(), AssetFile: "assets/textures/spaceship.png", MovementVector: vec, X: x, Y: y, Rotation: v.GetRotation()}
		list = append(list, dto)
	}

	return &list
}
