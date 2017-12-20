package remoteObject

import "common"

type RemoteObject struct {
	Name           string
	AssetFile      string
	MovementVector common.Vector
	X              common.Coordinate
	Y              common.Coordinate
	Rotation       float32
}
