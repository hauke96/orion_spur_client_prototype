package material

type RemoteObjectListDto struct {
	RemoteObjectList  []RemoteObjectDto `json:"RemoteObjectList"`
}

func NewRemoteObjectListDto(remoteObjectList []RemoteObjectDto)RemoteObjectListDto{
	return RemoteObjectListDto{RemoteObjectList: remoteObjectList}
}

func (r RemoteObjectListDto) GetRemoteObjectList() []RemoteObjectDto {
	return r.RemoteObjectList
}


