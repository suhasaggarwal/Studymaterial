package main

import (
	"fmt"
	"strings"
)

type MyString string

func (s MyString) toUpperCase() string {
	normalString := string(s)
	return strings.ToUpper(normalString)
}

func main() {
	str := MyString("Hello World")
	fmt.Println(str.toUpperCase())
}
