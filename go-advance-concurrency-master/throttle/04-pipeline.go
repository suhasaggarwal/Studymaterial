package main

import (
	"log"
	"sync"
	"time"
)

func main() {
	numCPU := 4
	sampleSize := 12

	wg := new(sync.WaitGroup)
	in := make(chan time.Time, sampleSize)
	for i := 0; i < numCPU; i++ {
		wg.Add(1)
		go consumerWorker(in, wg)
	}

	for i := 0; i < sampleSize; i++ {
		in <- time.Now()
	}
	//After populating the channel, one can close the channel safely
	close(in)

	wg.Wait()
}

//When channel is not drained or consumed by a goroutine and channel is closed.. channel will be read completely

func consumerWorker(in <-chan time.Time, wg *sync.WaitGroup) {
	for {
		//ok and more are same just  for reference
		start, more := <-in
		if !more {
			break
		}

		time.Sleep(500 * time.Millisecond)
		log.Println(time.Now().Sub(start).Seconds())
	}
	wg.Done()
}
