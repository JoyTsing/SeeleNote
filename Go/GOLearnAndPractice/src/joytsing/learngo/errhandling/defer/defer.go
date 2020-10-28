package main

import (
	"bufio"
	"fmt"
	os "os"
)


func fibonacci() func() int{
	a,b:=0,1
	return func() int {
		a,b=b,a+b
		return a
	}
}

func tryDefer(){
	defer fmt.Println(1)
	defer fmt.Println(2)
	fmt.Println(3)
}

func writeFile(filename string) {
	file, err := os.OpenFile(
		filename,os.O_EXCL|os.O_CREATE,0666)
	if err!=nil{
		if PathError,ok:=err.(*os.PathError);!ok{
			panic(err)
		}else{
			fmt.Printf("%s,%s,%s\n",PathError.Op,PathError.Path,PathError.Err)
		}
		return
	}
	defer file.Close()

	writer:=bufio.NewWriter(file)
	defer writer.Flush()
	f:=fibonacci()
	for i:=0;i<20;i++{
		fmt.Fprintln(writer,f())
	}
}

func main() {
	writeFile("fib.txt")
}
