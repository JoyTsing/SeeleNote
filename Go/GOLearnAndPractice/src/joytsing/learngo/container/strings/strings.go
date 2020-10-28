package main

import (
	"fmt"
	"unicode/utf8"
)

func main() {
	s := "Imp冲击!"
	for _, b := range []byte(s) {
		fmt.Printf("%X ", b)
	}
	fmt.Println()
	//utf8
	fmt.Println("Rune count: ",utf8.RuneCountInString(s))
	bytes:=[]byte(s)
	for len(bytes)>0 {
		ch,size:=utf8.DecodeRune(bytes)
		bytes=bytes[size:]
		fmt.Printf("%c ",ch)
	}
	fmt.Println()

	for i,ch:=range []rune(s){
		fmt.Printf("(%d %c)",i,ch)
	}
	fmt.Println()
}
