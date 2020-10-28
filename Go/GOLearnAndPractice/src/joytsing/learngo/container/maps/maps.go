package main

import "fmt"

func main() {
	m := map[string]string{
		"name":    "mouse",
		"course":  "golang",
		"site":    "wwww",
		"quality": "good",
	}
	m2 := make(map[string]int) //m2 == empty map
	var m3 map[string]int      //m3 == nil
	fmt.Println(m, m2, m3)
	fmt.Println("Traversing map")
	for k,v:=range m{
		fmt.Println(k,v)
	}
	fmt.Println("*********In map**********")
	fmt.Println(m["course"])
	fmt.Println("********Not in map********")
	if notInMap,ok:=m["NotIn"];ok{
		fmt.Println(notInMap,ok)
	}

	fmt.Println("**********Delete values***********")
	delete(m,"name")
	name,ok:=m["name"]
	fmt.Println(name,ok)
}
