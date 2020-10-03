package main

import (
	"fmt"
	"time"
)

type data interface {
	error() string
}

type Datav1 struct {
	 a int
	 b string

}

func (d *Datav1) error() string {
	return fmt.Sprintf("at %d, %s",
		d.a, d.b)
}

type MyError struct {
	When time.Time
	What string
}

func (e *MyError) Error() string {
	return fmt.Sprintf("at %v, %s",
		e.When, e.What)
}

func run() error {
	return &MyError{
		time.Now(),
		"it didn't work",
	}
}

//Pointer reference - deferences Structure receiver
//Normal Structure supplying to pointer receiver - won't do the conversion gives an error - because error method is binded to pointer receiver
//Pointer receiver - Supply a normal pointer - interface gets equated t pointer
func run1() data {
	 return &Datav1{
	 	a:1,
	 	b:"string",
	 }


}

func run2() data {
	return nil


}


func run3() data {
	return &Datav1{
		a:1,
		b:"string",
	}


}



func main() {
   //Equates  it to pointer receiver and then supply, equates pointer to object and then supply
	a := Datav1{
		a:1,
		b:"string",
	}

	fmt.Println(a.error())

	b1 := &Datav1{
		a:1,
		b:"string",
	}

	b1.error()
	fmt.Println(b1.error())



	if err := run(); err != nil {
		fmt.Println(err)
	}

	b:=run1()
	fmt.Println(b)


    c:=run2()
    fmt.Println(c)
}