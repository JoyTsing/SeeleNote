#include<iostream>
#include"DenseGraph.h"
#include"SparseGraph.h"
#include"ReadGraph.h"
#include"Component.h"
#include"Path.h"

using namespace std;

int main(){
	
	string filename="testG1.txt";
	SparseGraph g1(13,false);
	ReadGraph<SparseGraph> readGraph1(g1,filename);
	Component<SparseGraph> component(g1);
	cout<<"testG1,component count:"<<component.count()<<endl;
	return 0;
	
}
