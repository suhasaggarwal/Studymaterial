package main

import (
	"log"
	"runtime"
	"strconv"
	"strings"
	"sync"
	"time"
)

var m sync.Mutex

func main() {

	keywords := strings.Split("abcdefghijklmnopqrstuvwxyz", "")

	// start holds the start time of each goroutine
	start := make(map[string]time.Time, len(keywords))

	// duration holds the time taken for each goroutine to complete
	duration := make(map[int]time.Duration, len(keywords))

	// pipeline1 is a function that "fan-out" to do some work, but can only spawn
	// limited goroutine based on the number of available CPUs
	// No context being passed here, if you want to cancel go-routine with a deadline, context needs to be passed
	pipeline1 := func(
		done <-chan interface{},
		worker chan interface{},
		values ...string,
	) <-chan interface{} {
		log.Println("start pipeline")
		stream := make(chan interface{})

		var wg sync.WaitGroup

		// Indicate the number of expected work to be done
		wg.Add(len(values))

		go func(stream chan interface{}) {

			for k, v := range values {
				// Log the start time...
				log.Println("start worker:", k)
				m.Lock()
				start["start:"+strconv.Itoa(k)] = time.Now()
				m.Unlock()
				// "Push" a value to the "queue"
				// Since it is a buffered channel, it will block once it is filled
				worker <- k
				// Create multiple workers here
				go func(v string, stream chan interface{}) {
					defer wg.Done()
					defer func() {
						// Job's done, "pop" the value from the "queue"
						out := <-worker
						log.Println("stop worker:", out)
						// Calculate the duration taken for each goroutine from the start time
						m.Lock()
						duration[out.(int)+1] = time.Since(start["start:"+strconv.Itoa(out.(int))])
						m.Unlock()
					}()

					// Sleep to mimic heavy processing here

					time.Sleep(500 * time.Millisecond)
					select {
					// You can force the goroutine to end by closing the parent channel
					// This is to prevent goroutine from leaking
					case <-done:
						return
						// Send the value to the stream
					case stream <- v:
					}
				}(v,stream)
			}
		}(stream)

		// Close the channel on completion to signal the end
		go func() {
			// Wait for all the goroutine to complete
			wg.Wait()

			// Close the stream to indicate that all the values have been processed
			close(stream)
			log.Println("stop pipeline")
		}()
		return stream
	}

	// pipeline2 is a function that "fan-out" to do some work, but can only spawn
	// limited goroutine based on the number of available CPUs
	pipeline2 := func(
		done <-chan interface{},
		value <-chan interface{}, // for the second pipeline, you will usually accept from one channel only
	) <-chan interface{} {
		log.Println("start pipeline2")
		stream := make(chan interface{})

		go func() {
			defer close(stream)

		//Value will keep coming in 'v' --> from channel value which is stream channel of pipeline1
		//This is extra processing layer over values coming from pipeline1
			for v := range value {
				//Processing of lvaues
				log.Println("pipeline2", v)
				select {
				case <-done:
					return
				case stream <- v:
				}
			}
		}()

		return stream
	}

	// We use the numCPUs as the maximum number of workers available at a time -
	// a.k.a the maximum number of goroutine that can be spawned at a time
	numCPUs := runtime.NumCPU()

	// global parent channel, close this to signal all goroutines to close
	done := make(chan interface{})

	// buffered channel to indicate the maximum number of goroutines that can be spawned
	worker := make(chan interface{}, numCPUs)

	// Close all channels at the end to prevent goroutine leak
	defer close(worker)
	defer close(done)

	t0 := time.Now()
	//Just returns the channel and go routine keeps on running - job of go-routine is to keep on writing values on stream channel
	//These values are fetched from the stream channel and result and value 'v' is printed
	//This loop will stop when channel returned by pipeline2 is closed..

	for v := range pipeline2(done, pipeline1(done, worker, keywords...)) {
	//Prints the result..
		log.Println("result:", v)
	}
	log.Println("time taken:", time.Since(t0))

	// Log the duration for each goroutine

	var total float64
	for k, v := range keywords {
		_ = v
		log.Printf("worker %v took %v", k+1, duration[k+1])
		total += duration[k+1].Seconds()
	}
	log.Println("total time taken:", total)

	log.Println("process exiting")
}
