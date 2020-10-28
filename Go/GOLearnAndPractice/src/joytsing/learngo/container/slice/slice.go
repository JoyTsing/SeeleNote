package main

import "fmt"

func updateSlice(s []int){
	s[0]=100
	s[1]=200
}
//如果要通过slice来改变值，达到类似于JAVA中的效果的话则是用这样的方法

func printSlice(s[]int){
	fmt.Printf("%v,len=%d,cap=%d \n", s, len(s), cap(s))
}

func main() {
	//arr1 := [...]int{0, 1, 2, 3, 4, 5, 6, 7}
	//fmt.Println("arr[2:6]=", arr1[2:6])
	//fmt.Println("arr[:6]=", arr1[:6])
	//fmt.Println("arr[2:]=", arr1[2:])
	//fmt.Println("arr[:]=", arr1[:])
	//updateSlice(arr1[:])
	var s[]int
	for i := 0; i < 100; i++ {
		s=append(s,2*i+1)
	}
	s1:=[]int{2,4,6,8}
	printSlice(s1)
	s2:=make([]int,16)
	copy(s2,s1)
	printSlice(s2)

	fmt.Println(("Deleteing elements from slice"))
	s2=append(s2[:3],s2[4:]...)
	printSlice(s2)

	fmt.Println("Delete front")
	s2=s2[1:]
	printSlice(s2)

	fmt.Println("Delete tail")
	s2=s2[:len(s2)-1]
	printSlice(s2)
}
