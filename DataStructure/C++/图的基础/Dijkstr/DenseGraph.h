#ifndef DENSEGRAPH_H	
#define DENSEGRAPH_H

#include"Edge.h"
#include<iostream>
#include<vector>
#include<cassert>
using namespace std;

//稠密图 邻接矩阵 
template<typename Weight>
class DenseGraph{
private:
	int n,m;//n为顶点个数 m为边数 
	bool directed;
	vector<vector<Edge<Weight>* >> g;
	
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
	
	Edge<Weight>* begin(){
		index=-1;
		return next();
	}
	
	Edge<Weight>* next(){
		for(index+=1;index<G.V();index++)
			if(G.g[v][index])
				return G.g[v][index];
		return NULL;
	}
	
	bool end(){
		return index>=G.V();
	}
	
};

	DenseGraph(int n,bool directed):m(0){
		this->n=n;
		this->directed=directed;
		for(int i=0;i<n;i++)
			g.push_back( vector<Edge<Weight> *>(n,NULL));
	}
	
	~DenseGraph(){
		
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
				if(g[i][j]!=NULL)
					delete g[i][j];
	
	}
	
	int V(){ return n;}//顶点个数 
	int E(){ return m;}
	
	void addEdge(int v,int w,Weight weight){
		assert(v>=0 && v<n);
		assert(w>=0 && w<n);
		
		if(hasEdge(v,w)){
			delete g[v][w];
			if(!directed)
				delete g[w][v];
			m--;
		}
			
			
		g[v][w]=new Edge<Weight>(v,w,weight);
		if(!directed)
			g[w][v]=new Edge<Weight>(v,w,weight);
			
		m++;
	}
	
	bool hasEdge(int v,int w){
		assert(v>=0 && v<n);
		assert(w>=0 && w<n);	
		return g[v][w] != NULL;
	}
	
	void show(){

        for( int i = 0 ; i < n ; i ++ ){
            for( int j = 0 ; j < n ; j ++ )
            	if(g[i][j])
                	cout<<g[i][j]->wt()<<"\t";
                else 
                	cout<<"NULL\t";
            cout<<endl;
        }
    }
};

#endif
