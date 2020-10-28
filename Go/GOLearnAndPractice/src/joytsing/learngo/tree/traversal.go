package tree

import "fmt"

func (node*Node)Traverse(){
	//if node==nil{
	//	return
	//}
	//node.Left.Traverse()
	//node.Print()
	//node.Right.Traverse()
	node.TraverseFunc(func(node *Node){
		node.Print()
	})
	fmt.Println()
}

//自定义遍历的同时所做的事情
func (node *Node)TraverseFunc (f func(*Node))  {
	if node==nil{
		return
	}

	node.Left.TraverseFunc(f)
	f(node)
	node.Right.TraverseFunc(f)
}

func (node *Node)TraverseWithChannel() chan *Node{
	out:=make(chan *Node)
	go func() {
		node.TraverseFunc(func(node *Node){
			out<-node
		})
		close(out)
	}()
	return out
}