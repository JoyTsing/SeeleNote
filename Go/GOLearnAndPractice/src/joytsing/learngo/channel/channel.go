package main

import (
	"fmt"
	"time"
)

func createWorker(id int) chan<- int { //外面只能给chan发数据
	c := make(chan int)
	go worker(id, c)
	return c
}

func chanDemo() {
	//var c chan int //c == nil
	var channels [10]chan<- int
	for i := 0; i < 10; i++ {
		channels[i] = createWorker(i)
	}
	for i := 0; i < 10; i++ {
		channels[i] <- 'a' + i
	}

	for i := 0; i < 10; i++ {
		channels[i] <- 'A' + i
	}
	time.Sleep(time.Millisecond)
}

func worker(id int, c chan int) {
	for n := range c {
		//if n, ok := <-c; !ok {
		//	break
		//} else {
		fmt.Printf("Worker %d received %c \n", id, n)
		//}
	}
}

func bufferedChannel() {
	c := make(chan int, 3)
	go worker(0, c)
	c <- 'a'
	c <- 'b'
	c <- 'c'
	c <- 'd'
	time.Sleep(time.Millisecond)
}

func channelClose() {
	c := make(chan int, 3)
	go worker(0, c)
	c <- 'a'
	c <- 'b'
	c <- 'c'
	c <- 'd'
	close(c)
	time.Sleep(time.Millisecond)
}

func main() {
	//chanDemo()
	bufferedChannel()
}
