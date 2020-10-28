package main

import "fmt"

func adder() func(int)int{
	sum:=0
	return func(v int)int{
		sum+=v
		return sum
	}
}

type iAdder func(int)(int ,iAdder)
//正规式函数编程
func adder2(base int)iAdder{
	return func(v int)(int,iAdder){
		return base+v,adder2(base+v)
	}
}

func main() {
	a:=adder()

	for i := 0; i < 10; i++ {
		fmt.Printf("0 + 1 + .... %d = %d \n",i,a(i))
	}
}
