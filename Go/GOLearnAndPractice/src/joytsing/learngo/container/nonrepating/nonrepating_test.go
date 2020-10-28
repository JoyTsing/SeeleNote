package main

import "testing"

func TestSubstr(t *testing.T){
	tests:=[]struct{
		s string
		ans int
	}{
		{"abcabcbb",3},
		{"pwwkew",3},
		//Edge cases
		{"",0},
		{"b",1},
		{"bbbbbbbb",1},
		{"abcabcabcd",4},
		//Ch
		{"一二三一二三",3},
	}

	for _,tt := range tests{
		actual:=lengthOfSubStr(tt.s)
		if actual!=tt.ans{
			t.Errorf("Got %d for input %s;"+
				"expected %d",actual,tt.s,tt.ans)
		}
	}
}

func BenchmarkSubstr(b *testing.B){
	s,ans:="黑化肥挥发发灰会花飞灰化肥挥发发黑会飞花",8
	actual:=lengthOfSubStr(s)

	for i:=0;i<b.N;i++{
		if actual!=ans{
			b.Errorf("Got %d for input %s;"+
				"expected %d",actual,s,ans)
		} 
	}

}
