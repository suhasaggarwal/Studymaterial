package main

import (
	"fmt"
)

// Creating an interface
type tank interface {

	// Methods
	Tarea() float64
	Volume() float64
}

type myvalue struct {
	radius float64
	height float64
}

// Implementing methods of
// the tank interface
func (m myvalue) Tarea() float64 {

	return 2*m.radius*m.height +
		2*3.14*m.radius*m.radius
}

func (m myvalue) Volume() float64 {

	return 3.14 * m.radius * m.radius * m.height
}

func main() {

	var t tank
	var m myvalue
	fmt.Printf("t is a nil interface: %v\n", t == nil)
	//fmt.Printf("t is a nil pointer: %v\n", m == nil)
	//m = myvalue{10, 14}
    t = m
	fmt.Printf("t is a nil pointer: %v\n", t)


}