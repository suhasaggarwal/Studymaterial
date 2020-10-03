package main

import (
	"fmt"
	"sync"
	"time"
)

var wg1 sync.WaitGroup

func work1() error {
	quit := make(chan bool, 3)
	quit <- true
	quit <- true
	quit <- true
	defer wg1.Done()
	for i := 0; i < 1000; i++ {
		select {
//Will block till 2 seconds here
//From 3rd Second case 1 will execute only
		case <-time.After(2 * time.Second):
			fmt.Println("Doing work", i)
//Data will be fetched from quit channel
//This case will be ready for 0,1,2 seconds
		case <-quit:
			fmt.Println("Not Doing work", i)
			}
	}
	return nil
}

func main() {
	fmt.Println("Hey. I'm going to do some work.")

	wg1.Add(1)
	go work1()
	wg1.Wait()

	fmt.Println("Done")
}
