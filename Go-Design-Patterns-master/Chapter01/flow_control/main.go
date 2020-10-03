package main

import "fmt"

func main() {




	const (
		Pi    = 3.14
		Truth = false
		Big   = 1 << 5 //Not the index - number of places to move ahead, 0 to 5 steps ahead i.e 6th index 5th position
		Small = Big >> 61
	)

		const Greeting = "ハローワールド" //declaring a constant
		fmt.Println(Greeting)
		fmt.Println(Pi)
		fmt.Println(Truth)
		fmt.Println(Big)















	ten := 10
	if ten == 20 {
		println("This shouldn't be printed as 10 isn't equal to 20")
	} else {
		println("Ten is not equals to 20")
	}

	// OR operator is "||" yo just one condition needs to be true
	if "a" == "b" || 10 == 10 || true == false {
		// Enter here. Althought "a" isn't "b" and true isn't false, 10 is equal
		// to 10 so at least one condition is satisfied
		println("10 is equal to 10")

		// AND operator is && so BOTH conditions must be satisfied
	} else if 11 == 11 && "go" == "go" {
		println("This isn't print because previous condition was satisfied")

	} else {
		println("In case no condition is satisfied, print this")
	}
}
