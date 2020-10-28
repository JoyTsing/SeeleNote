package main

import (
	"fmt"
	reflect "reflect"
	"runtime"
)

func eval(a int, b int, op string) (int, error) {
	switch op {
	case "+":
		return a + b, nil
	case "-":
		return a - b, nil
	case "*":
		return a * b, nil
	case "/":
		q, _ := div(a, b)
		return q, nil
	default:
		return 0, fmt.Errorf(
			"unsupported operation: %s", op)
	}
}

func div(a, b int) (q, r int) {
	return a / b, a % b
}

func apply(op func(int, int) int, a, b int) int {
	p := reflect.ValueOf(op).Pointer()
	opName := runtime.FuncForPC(p).Name()
	fmt.Printf("Calling function %s with args (%d,%d)\n", opName, a, b)
	return op(a, b)
}

//GO没有重载函数 没有默认参数 只有可变参数列表
func sum(numbers ...int) int {
	s := 0
	for i := range numbers {
		s += numbers[i]
	}
	return s
}

func swap(a,b *int){
	*a,*b=*b,*a
}

func main() {
	//if result, err := eval(3, 4, "x"); err != nil {
	//	fmt.Println("Error", err)
	//} else {
	//	fmt.Println(result)
	//}
	//q, r := div(13, 3)
	//fmt.Println(q, r)
	//add := func(a int, b int) int { return a + b } //函数表达式
	//apply(add, 2, 3)
	//fmt.Println(apply(
	//	func(a int, b int) int {
	//		return int(math.Pow(float64(a), float64(b)))
	//	},
	//	3, 4)) //匿名类型
	//fmt.Println(sum(1, 2, 3, 4, 5))
	a,b:=3,4
	swap(&a,&b)
}
