package main

import (
	"fmt"
	"strings"
)

type MyStr string //using MyStr as an alias for type string
type Integer int

func (s MyStr) Uppercase() string {
	return strings.ToUpper(string(s))
}

func main() {
	a := MyStr("test")
	b := Integer(1)
	//var b MyString
	//b="test"
	fmt.Println(b)
	//var a Integer
	//a=1
	fmt.Println(a)
	fmt.Println(MyStr("test").Uppercase())
}