package main

import (
	"fmt"
	"runtime"
	"time"
)

func main() {
	var a[10] int
	for i := 0; i < 10; i++ {
		go func(i int){
			for {
				a[i]++
				//fmt.Printf("Hello from "+ "goroutine %d \n",i)
				runtime.Gosched()//手动交出控制权
			}
		}(i)
	}
	time.Sleep(time.Millisecond)
	fmt.Println(a)
}
