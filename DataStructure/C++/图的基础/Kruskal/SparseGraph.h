#ifndef SPARSEGRAPH_H
#define SPARSEGRAPH_H

#include"Edge.h"
#include<iostream>
#include<vector>
#include<cassert>

using namespace std;

//ϡ��ͼ �ڽӱ� 
template<typename Weight>
class SparseGraph{
private:
	int n,m;
	bool directed;	
	vector<vector<Edge<Weight>* >> g;
	
public:
	
class adjIterator{
private:
	SparseGraph &G;
	int v;
	int index;//��������һ�� 
public:
	adjIterator(SparseGraph &graph,int v):G(graph){
		this->v=v;
		this->index=0;
	}
	
	Edge<Weight>* begin(){
		index=0;
		if(G.g[v].size())
			return G.g[v][index];
		return NULL;
	}
	
	Edge<Weight>* next(){
		index++;
		if(index<G.g[v].size())
			return G.g[v][index];
		return NULL;
	}
	
	bool end(){
		return index>=G.g[v].size();
	}
	
};
	
	SparseGraph(int n,bool directed):n(n),m(0),directed(directed){
		for(int i=0;i<n;i++)
			g.push_back( vector<Edge<Weight> *>() );
	}
	
	~SparseGraph(){
		
		for(int i=0;i<n;i++)
			for(int j=0;j<g[i].size();j++)
					delete g[i][j];
	}
	
	int V(){ return n;}
	int E(){ return m;}
	
	void addEdge(int v,int w,Weight weight){
		assert(v>=0 && v<n);
		assert(w>=0 && w<n);
		
		g[v].push_back(new Edge<Weight>(v,w,weight));
		if(v!=w && !directed)
			g[w].push_back(new Edge<Weight>(v,w,weight));
			
		m++;
		
	}
	
	bool hasEdge(int v,int w){
		assert(v>=0 && v<n);
		assert(w>=0 && w<n);	
		for(int i=0;i< g[v].size() ; i++)
			if(g[v][i]->other(v)==w)
				return true;
		return false;
	}
	
	 // ��ʾͼ����Ϣ
    void show(){

        for( int i = 0 ; i < n ; i ++ ){
            cout<<"vertex "<<i<<":\t";
            for( int j = 0 ; j < g[i].size() ; j ++ )
                cout<<"( to:"<<g[i][j]->w()<<",wt:"<<g[i][j]->wt()<<")\t";
            cout<<endl;
        }
    }
};
#endif
