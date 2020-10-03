package main

import "fmt"

type Vertex struct {
	Lat, Long float64
}

var m = map[string]Vertex{
	"Bell Labs": {40.68433, -74.39967},
	// same as "Bell Labs": Vertex{40.68433, -74.39967}
	"Google": {37.42202, -122.08408},
}

func main() {


	m1 := map[string]Vertex{
		"Bell Labs": {40.68433, -74.39967},
		// same as "Bell Labs": Vertex{40.68433, -74.39967}
		"Google": {37.42202, -122.08408},
	}
	fmt.Println(m1)
	m["Splice"] = Vertex{34.05641, -118.48175} //inserting a new (key,value) here
	fmt.Println(m["Splice"])
	delete(m, "Splice")    //deleting the element
	fmt.Printf("%v\n", m)
	name, ok := m["Splice"]      //checks to see if element is present
	fmt.Printf("key 'Splice' is present?: %t - value: %v\n", ok, name)
	//Delete key printed empfmt.Printf("%v\n", m)ty stucture
	var k1 string
	fmt.Printf("%v\n", k1)
	name, ok = m["Google"]
	fmt.Printf("key 'Google' is present?: %t - value: %v\n", ok, name)
}
