package main

import (
	"context"
	"fmt"
	"time"
)

func main() {
	d := time.Now().Add(50 * time.Millisecond)

	ctx, cancel := context.WithDeadline(context.Background(), d)

	defer cancel()

	select {
	case <-time.After(1 * time.Second):
		fmt.Println("overslept")
	case <-ctx.Done():
		//context deadline exceeded
		fmt.Println(ctx.Err())
	}
}
