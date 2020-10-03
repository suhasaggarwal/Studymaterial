package main

import "fmt"

type Stringer interface {
	String() string
	StringData() string
	SetString()
	SetStringData()
}

type fakeString struct {
	content string
}

// function used to implement the Stringe interface
func (s *fakeString) String() string {
	return s.content
}

type NormalString struct {
	content string
}

// function used to implement the Stringe interface
func (s NormalString) String() string {
	return s.content
}

func (s NormalString) SetString() {
	 s.content = "Normal String"
}

func (s *NormalString) StringData() string {
	return s.content
}

func (s *NormalString) SetStringData() {
	 s.content = "Normal String1"
}

//Empty interface pass anything
func printString(value interface{}) {
	switch str := value.(type) {  //Deriving type //very much like instance of
	case int:
		fmt.Println(str)
	case string:
		fmt.Println(str) //Equivalent string it prints if supplied with string interface type also, given to fmt.Println
	case Stringer:
		str.SetString()
		fmt.Println(str.String())
		str.SetStringData()
		fmt.Println(str.StringData())
	//	fmt.Println(value.String()) Gives error if passed as interface type
	}
}

func anotherprintString(value interface{}) {
	str := value.(NormalString)   //Deriving type //very much like instance of
	fmt.Println(str)

}

func anotherprintString1(value interface{}) {
	str := value.(*NormalString)   //Deriving type //very much like instance of
	fmt.Println(str)
}


func printStringValue(value *fakeString) {
	fmt.Println(value.String())
}

func printNormalStringValue(value NormalString) {
	fmt.Println(value)
}



func main() {
	//s2:= NormalString{"Test String"}
	//s1 := NormalString{"Ceci n'est pas un string1"}

    //a := 2
	//printString(a)



   /*
    s2:= NormalString{"Test String"}
	s := &fakeString{"Ceci n'est pas un string"}
	printStringValue(s)
	s1 := NormalString{"Ceci n'est pas un string1"}
	printNormalStringValue(s1)
	printString(s2)
	fmt.Println(s2.content)
*/
    //Pointer receivers gets priority automatically
	s1 := NormalString{"Ceci n'est pas un string1"}
	//Pointer receiver matched the interface
	printString(&s1)
    //Non pointer receiver did not match the interface
	printString(s1)

	//printString(&s1)   //Pass a pointer to the structure as an interface, if set method is called on the interface, value of original Structure will be changed
	//fmt.Println(s1)
	//printString(s1)
	//fmt.Println(s1.content)
	//printString(s)
	//printString("Hello, Gophers")

	//anotherprintString(s2)
	//anotherprintString(&s2)
	//Application function is returning an error and error is of particular type
	//anotherprintString1(&s1)
	//anotherprintString1(s1)
}