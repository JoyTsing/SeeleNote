#ifndef COMPONENT_H
#define COMPONENT_H

#include<iostream>
#include<cassert>
using namespace std;


template<typename Graph>//深度优先 
class Component{
private:
	Graph &G;
	bool *visited;
	int ccount;//连通分支 
	int *id;
	
	
	void dfs(int v){
		visited[v]=true;
		//adjIterator为一个 类型 
		typename Graph::adjIterator	adj(G,v);//访问 v相邻的所有节点 
		id[v]=ccount;//某个连通分支 
		for( int i=adj.begin();!adj.end();i=adj.next() ){
			if(!visited[i])
				dfs(i);
		}
	}
	
public:	
	Component(Graph &graph):G(graph){
		
		visited=new bool[G.V()];
		id=new int [G.V()];
		ccount=0;
		for(int i=0;i<G.V();i++){
			visited[i]=false;
			id[i]=-1;
		}
		
		
		for(int i=0;i<G.V();i++)
			if(!visited[i]){
				dfs(i);
				ccount++;
			}
				
				
	}
	
	~Component(){
		delete[] visited;
		delete[] id;
	}
	
	int count(){
		return ccount;
	}
	
	bool isConnected(int v,int w){
		assert(v>=0&&v<G.V());
		assert(w>=0&&w<G.V());
		return id[v]==id[w];
	}
};
#endif
