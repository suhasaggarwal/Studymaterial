package main

import (
	"context"
	"fmt"
	"sync"
	"time"
)

var wg3 sync.WaitGroup

func work3(ctx context.Context) error {
	defer wg3.Done()

	for i := 0; i < 1000; i++ {
		select {
		case <-time.After(2 * time.Second):
			fmt.Println("Doing some work", i)
   //Returns an empty Struct when context is cancelled (ctx.Done returns a channel)
		case <-ctx.Done():
			fmt.Println("Cancel the context", i)
			return ctx.Err()
		}
	}
	return nil
}

func main() {
	ctx, cancel := context.WithTimeout(context.Background(), 4*time.Second)
	//cancel occurs with a timeout after 4 seconds
	defer cancel()

	fmt.Println("Hey, I'm going to do some work")

	wg3.Add(1)
	go work3(ctx)
	wg3.Wait()

	fmt.Println("Finished. I'm going home.")
}
