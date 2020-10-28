package main

import (
	"fmt"
	"joytsing/learngo/tree"
)

type MyTreeNode struct {
	node *tree.Node
}

func (myNode *MyTreeNode) postOrder()  {
	if myNode==nil||myNode.node==nil{
		return
	}
	left := MyTreeNode{myNode.node.Left}
	left.postOrder()
	right := MyTreeNode{myNode.node.Right}
	right.postOrder()
	myNode.node.Print()
}

func main() {
	var root tree.Node

	root =tree.Node{Value: 3}
	root.Left=&tree.Node{}
	root.Right=&tree.Node{5,nil,nil}
	root.Right.Left=new(tree.Node)
	root.Left.Right=tree.CreateNode(2)
	root.Right.Left.SetValue(4)
	root.Traverse()
	//fmt.Println()
	//myRoot:=MyTreeNode{&root}
	//myRoot.postOrder()
	//fmt.Println()

	nodeCount:=0
	root.TraverseFunc(func(node *tree.Node){
		nodeCount++
	})
	fmt.Println("Node Count:",nodeCount)

	c :=root.TraverseWithChannel()
	maxNode:=0
	for node:=range c{
		if node.Value>maxNode{
			maxNode=node.Value
		}
	}
	fmt.Println("Max node value:",maxNode)
}