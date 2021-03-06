package login

import (
	"errors"
	"logger"
)

type LoginService struct {
	loggedInPlayer []string
}

func (service *LoginService) Login(id string) error {
	logger.Debug("Called Login with id " + id)

	_, err := service.find(id)

	// find works -> user is already logged in
	if err == nil {
		return errors.New("Unable to login: User " + id + " already logged in")
	}

	service.loggedInPlayer = append(service.loggedInPlayer, id)

	return nil
}

func (service *LoginService) Logout(id string) error {
	logger.Debug("Called Logout with id " + id)

	i, err := service.find(id)

	// find didn't works -> user is not logged in
	if err != nil {
		return errors.New("Unable to logout: User " + id + " is not logged in")
	}

	service.loggedInPlayer = append(service.loggedInPlayer[:i], service.loggedInPlayer[i+1:]...)

	return nil
}

func (service *LoginService) IsLoggedIn(id string) bool {
	logger.Debug("Called IsLoggedIn with id " + id)

	_, err := service.find(id)

	return err == nil
}

func (service *LoginService) find(id string) (int, error) {
	for i := range service.loggedInPlayer {
		if service.loggedInPlayer[i] == id {
			return i, nil
		}
	}

	return -1, errors.New("Element " + id + " not found")
}
