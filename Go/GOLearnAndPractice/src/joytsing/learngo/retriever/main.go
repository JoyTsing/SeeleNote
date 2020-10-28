package main

import (
	"fmt"
	"joytsing/learngo/retriever/mock"
	reale "joytsing/learngo/retriever/real"
	"time"
)

const url = "http://baidu.com"

type Retriever interface {
	Get(url string) string
}

type Poster interface {
	Post(url string, form map[string]string) string
}

func post(poster Poster) {
	poster.Post(url, map[string]string{
		"name":   "joy",
		"course": "golang",
	})
}

type RetrieverPoster interface {
	Retriever
	Poster
}

func session(s RetrieverPoster) string{
	s.Post(url,map[string]string{
		"contents":"another faked retriever",
	})
	return s.Get(url)
}

func download(r Retriever) string {
	return r.Get(url)
}

func main() {
	var r Retriever
	retriever:=mock.Retriever{"this is a fake retriever"}
	r = &retriever
	inspect(r)

	r = &reale.Retriever{
		UserAgent: "Mozilla/5.0",
		TimeOut:   time.Minute,
	}
	inspect(r)

	//Type assertion
	if mockRetriever, ok := r.(*mock.Retriever); ok {
		fmt.Println(mockRetriever.Contents)
	} else {
		fmt.Println("not a mock retriever")
	}

	fmt.Println("Try a session")
	fmt.Println(session(&retriever))
}

func inspect(r Retriever) {
	fmt.Println("Inspecting",r)
	fmt.Printf(" > %T %v\n", r, r)
	fmt.Print(" > Type switch:")
	switch v := r.(type) {
	case *mock.Retriever:
		fmt.Println("Contents:", v.Contents)
	case *reale.Retriever:
		fmt.Println("UseAgent:", v.UserAgent)
	}
	fmt.Println( )
}
