package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
)

func convertToBin(n int) string {
	result := ""
	for ; n > 0; n /= 2 {
		lsb := n % 2
		result = strconv.Itoa(lsb) + result
	}
	return result
}

func printFile(filename string) {
	file, err := os.Open(filename)
	if err != nil {
		panic(err)
	}

	scanner := bufio.NewScanner(file)
	for scanner.Scan() { //GO中没有while 当省略掉;;时即为while
		fmt.Println(scanner.Text())
	}
}

func forever() {
	var count int = 0

	for {
		fmt.Println("wow")
		count++
		if count == 10{
			break
		}
	}
}

func main() {
	//fmt.Println(
	//	convertToBin(5),  //  101
	//	convertToBin(13), // 1101
	//	convertToBin(12345678))
	///printFile("abc.txt")
	forever()
}
