#ifndef DENSEGRAPH_H	
#define DENSEGRAPH_H

#include<iostream>
#include<vector>
#include<cassert>
using namespace std;

//稠密图 邻接矩阵 
class DenseGraph{
private:
	int n,m;
	bool directed;
	vector<vector<bool>> g;
	
public:
	
class adjIterator{
private:
	DenseGraph &G;
	int v;
	int index;//迭代到哪一个 
public:
	adjIterator(DenseGraph &graph,int v):G(graph){
		this->v=v;
		this->index=-1;//不是找0这个元素，而是找第一个为true的数 
	}
	
	int begin(){
		index=-1;
		return next();
	}
	
	int next(){
		for(index+=1;index<G.V();index++)
			if(G.g[v][index])
				return index;
		return -1;
	}
	
	bool end(){
		return index>=G.V();
	}
	
};

	DenseGraph(int n,bool directed):m(0){
		this->n=n;
		this->directed=directed;
		for(int i=0;i<n;i++)
			g.push_back(vector<bool>(n,false));
	}
	
	~DenseGraph(){}
	
	int V(){ return n;}//顶点个数 
	int E(){ return m;}
	
	void addEdge(int v,int w){
		assert(v>=0 && v<n);
		assert(w>=0 && w<n);
		
		if(hasEdge(v,w))
			return;
			
		g[v][w]=true;
		if(!directed)
			g[w][v]=true;
			
		m++;
	}
	
	bool hasEdge(int v,int w){
		assert(v>=0 && v<n);
		assert(w>=0 && w<n);	
		return g[v][w];
	}
	
	void show(){

        for( int i = 0 ; i < n ; i ++ ){
            for( int j = 0 ; j < n ; j ++ )
                cout<<g[i][j]<<"\t";
            cout<<endl;
        }
    }
};

#endif
