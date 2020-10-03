package main

import (
	"context"
	"fmt"
	"io/ioutil"
	"net/http"
	"sync"
	"time"
)

var wg sync.WaitGroup

func work(ctx context.Context) error {
	defer wg.Done()

	tr := &http.Transport{}
	client := &http.Client{Transport: tr}

	c := make(chan struct {
		r   *http.Response
		err error
	}, 1)
    //Execute a new http request
	req, _ := http.NewRequest("GET", "http://localhost:1111", nil)
	go func() {
		resp, err := client.Do(req)
		fmt.Println("Doing http request is a hard job")
		pack := struct {
			r   *http.Response
			err error
		}{resp, err}
		fmt.Println(time.Now().Format(time.RFC850))
		//time.Sleep(10*time.Second)
		fmt.Println(time.Now().Format(time.RFC850))
		//when http requests execute successfully and sleep is added, data is written to channel after 10 second
		c <- pack
	}()
//A select blocks until one of its cases can run, then it executes that case. It chooses one at random if multiple are ready.
	select {
//Ctx.Done occurs earlier hence select unblocks on this case
	case <-ctx.Done():
    //http request gets cancelled
		tr.CancelRequest(req)
		ok := <-c
		fmt.Println("Cancel the context")
	//Request cancel err got printed
		fmt.Println(ok.err)
		return ctx.Err()
	case ok := <-c:
		err := ok.err
		resp := ok.r
		if err != nil {
			fmt.Println("Error", err)
			return err
		}

		defer resp.Body.Close()
		out, _ := ioutil.ReadAll(resp.Body)
		fmt.Printf("Server response: %s\n", out)
	}
	return nil
}

func main() {
	ctx, cancel := context.WithTimeout(context.Background(), 4*time.Second)

	defer cancel()

	fmt.Println("Hey, I'm going to do some work")

	wg.Add(1)
	go work(ctx)
	wg.Wait()

	fmt.Println("Finished. I'm going home.")
}
