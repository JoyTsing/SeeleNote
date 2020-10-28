package main

import (
	"fmt"
	"sync"
	"time"
)

type worker struct {
	in chan int
	//done chan bool
	//wg *sync.WaitGroup
	done func()
}

func createWorker(id int, wg *sync.WaitGroup) worker { //chan<- int 外面只能给chan发数据
	w := worker{
		in: make(chan int),
		done : func(){
			wg.Done()
		},
	}
	go doWorker(id, w)
	return w
}

func chanDemo() {
	//var c chan int //c == nil
	var workers [10]worker
	var wg sync.WaitGroup

	for i := 0; i < 10; i++ {
		workers[i] = createWorker(i, &wg)
	}
	wg.Add(20)

	for i, worker := range workers {
		worker.in <- 'a' + i

	}
	for i, worker := range workers {
		worker.in <- 'A' + i
	}
	wg.Wait()

	time.Sleep(time.Millisecond)
}

func doWorker(id int, w worker) {
	for n := range w.in {
		//if n, ok := <-c; !ok {
		//	break
		//} else {
		fmt.Printf("Worker %d received %c \n", id, n)
		//}
		//go func() { done <- true }()
		w.done()
	}
}

func main() {
	chanDemo()
}
