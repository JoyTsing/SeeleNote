package fib

import (
	"bufio"
	"fmt"
	"io"
	"strings"
)

//类似生成器，与文件相似,只要为闭包实现了reader接口就可以实现类似文件读取的功能
func Fibonacci() intGen{
	//初始条件
	a,b:=0,1
	return func()int{
		a,b=b,a+b
		return a
	}
}

//是函数的类型，但只要它是类型就能实现接口
type intGen func() int

func (g intGen) Read(p []byte) (n int, err error) {
	next:=g()
	if next>10000{
		return 0,io.EOF
	}
	s:=fmt.Sprintf("%d\n",next)
	return strings.NewReader(s).Read(p)
}

func printFileContents(reader io.Reader){
	scanner:=bufio.NewScanner(reader)

	for scanner.Scan(){
		fmt.Println(scanner.Text())
	}
}

func main() {
	f:=Fibonacci()
	printFileContents(f)

}
