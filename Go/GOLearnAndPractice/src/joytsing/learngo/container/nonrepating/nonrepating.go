package main

import "fmt"

var lastOccurred = make([]int, 0xffff)

func lengthOfSubStr(s string) int {
	//lastOccurred := make(map[rune]int)

	for i := range lastOccurred{
		lastOccurred[i]=0
	}

	start := 0
	maxLength := 0
	for i, ch := range []rune(s) {

		//if lastI, ok := lastOccurred[ch]; ok && lastI >= start {
		if lastI := lastOccurred[ch]; lastI >= start {
			start = lastI
		}
		if i-start+1 > maxLength {
			maxLength = i - start + 1
		}
		lastOccurred[ch] = i + 1
	}
	return maxLength
}

func main() {
	fmt.Println(lengthOfSubStr("abcabcbb"))
	fmt.Println(lengthOfSubStr("pwwkew"))
	fmt.Println(lengthOfSubStr("abcdef"))
}
