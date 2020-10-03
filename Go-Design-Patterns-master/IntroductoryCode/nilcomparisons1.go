package main

import (
	"fmt"
)

type Temp struct {
}

func main() {

	// taking a pointer
	var val *Temp

	// taking a interface
	var val2 interface{}

	// val2 is a non-nil interface
	// holding a nil pointer (val)
	val2 = val

	fmt.Printf("val2 is a nil interface: %v\n", val2 == nil)
	fmt.Printf("val2 is a nil"+
		" pointer: %v\n", val == nil)
	fmt.Printf("val2 is a interface holding a nil"+
		" pointer: %v\n", val2 == (*Temp)(nil))
}