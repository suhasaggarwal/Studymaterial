package main

import "fmt"

type UserOne User

type User struct {
	Id             int
	Name, Location string
}

func (u *User) Greetings() string {
	return fmt.Sprintf("Hi %s from %s",
		u.Name, u.Location)
}

type Player struct {
	*User
	GameId int
}



func main() {
	user := UserOne{Id:1, Name:"a", Location:"b" }
	fmt.Println(user)
	u := &User{Id:1, Name:"a", Location:"b" }
	u1 := User{Id:1, Name:"a", Location:"b" }
	fmt.Println(u.Greetings())
	fmt.Println(u1.Greetings())
	p := Player{}
	p.Id = 42
	p.Name = "Matt"
	p.Location = "LA"
	fmt.Println(p.Greetings())
}