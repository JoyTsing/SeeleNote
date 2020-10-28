package main

import (
	"fmt"
	"net/http"
	"net/http/httputil"
)

func main() {
	request,nil:= http.NewRequest(http.MethodGet,"http://imooc.com",nil)
	resp,err:=http.Get("http://imooc.com")
	request.Header.Add("User-Agent","找到的")
	
	client:=http.Client{CheckRedirect: func(req *http.Request, via []*http.Request) error {
		fmt.Println("Redirect: ",req)
		return nil
	}}
	
	client.Do(request)
	if err!=nil{
		panic(err)
	}
	defer resp.Body.Close()

	s,err:=httputil.DumpResponse(resp,true)
	if err!=nil{
		panic(err)
	}
	fmt.Printf("%s\n",s)
}
