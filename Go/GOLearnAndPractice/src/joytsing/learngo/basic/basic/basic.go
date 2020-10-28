package main

import (
	"fmt"
	"math"
)

func variableZeroValue() {
	var a int
	var s string
	fmt.Printf("%d %q\n", a, s)
}

func variableInitialValue() {
	var a, b int = 3, 4
	var s string = "abc"
	fmt.Println(a, b, s)
}

func variableTypeDeduction() {
	var a, b, c, s = 3, 4, true, "def"
	fmt.Println(a, b, c, s)
}

//Go语言仅能显式的转换数据类型而不能隐式转换
func triangle() {
	var a, b int = 3, 4
	fmt.Println(calcTriangle(a,b))
}

func calcTriangle(a int,b int)int{
	var c int
	c=int(math.Sqrt(float64(a*a+b*b)))
	return c
}

/*
//包内部变量则无法使用:= 可以使用括号来省略
var aa int =3
var char rune='a'//go中的char
var ss string="abc"
var(
	dd int=3
	ee bool=true
)
*/

func variableShorter() {
	a, b, c, d := 3, 4, true, "def" //简化定义语法 使用:
	fmt.Println(a, b, c, d)
	var e, f, g, h int = 1, 2, 3, 4
	fmt.Println(e, f, g, h)
}

func consts() {
	const (
		filename string = "abc.txt"
		a, b            = 3, 4
	)
	var c int
	c = int(math.Sqrt(a*a + b*b))
	fmt.Println(c)
}

//枚举类型
func enums() {
	//const (
	//	cpp=0
	//	java=1
	//	python=2
	//	golang=3
	//)
	//另外一组写法

	const (
		cpp = iota //iota是类似自增索引的东西,其是一个自增种子
		java
		python
		golang
	)

	const (
		b=1<<(10*iota)
		kb
		mb
		gb
		tb
		pb
	)
	fmt.Println(b,kb,mb,gb,tb,pb)

}



func main() {
	fmt.Println("hello world")
	//variableZeroValue()
	//variableInitialValue()
	enums()
}
