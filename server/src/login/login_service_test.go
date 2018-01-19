package login

import (
	"testing"
)

func create() *LoginService {
	return &LoginService{}
}

func TestAddWorks(t *testing.T) {
	s := create()

	e1 := s.Login("a")
	e2 := s.Login("b")

	if e1 != nil {
		t.Log("a failed: " + e1.Error())
		t.Fail()
	}

	if e2 != nil {
		t.Log("b failed: " + e2.Error())
		t.Fail()
	}
}

func TestMultipleAddFails(t *testing.T) {
	s := create()

	s.Login("a")
	s.Login("b")
	e3 := s.Login("a")

	if e3 == nil {
		t.Fail()
	}
}

func TestLogoutWorks(t *testing.T) {
	s := create()

	s.Login("a")
	s.Login("b")

	e1 := s.Logout("a")
	e2 := s.Logout("b")

	if e1 != nil || e2 != nil {
		t.Fail()
	}
}

func TestMultipleLogoutFails(t *testing.T) {
	s := create()

	s.Login("a")
	s.Login("b")

	s.Logout("a")
	s.Logout("b")
	e3 := s.Logout("a")

	if e3 == nil {
		t.Fail()
	}
}

func TestFind(t *testing.T) {
	s := create()

	s.Login("a")
	s.Login("b")
	s.Login("c")
	s.Login("d")

	_, a := s.find("a")
	_, b := s.find("b")
	_, c := s.find("c")
	_, d := s.find("d")

	_, empty := s.find("")

	_, e := s.find("e")

	if a != nil {
		t.Log("a failed: " + a.Error())
		t.Fail()
	}
	if b != nil {
		t.Log("b failed: " + b.Error())
		t.Fail()
	}
	if c != nil {
		t.Log("c failed: " + c.Error())
		t.Fail()
	}
	if d != nil {
		t.Log("d failed: " + d.Error())
		t.Fail()
	}
	if empty == nil {
		t.Log("empty failed: " + empty.Error())
		t.Fail()
	}
	if e == nil {
		t.Log("e failed: " + e.Error())
		t.Fail()
	}
}
