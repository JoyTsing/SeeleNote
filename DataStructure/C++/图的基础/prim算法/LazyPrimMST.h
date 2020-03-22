#ifndef LAZYPRIMMST_H
#define LAZYPRIMMST_H

#include <iostream>
#include <vector>
#include <cassert>
#include "Edge.h"
#include "MinHeap.h"

using namespace std;

// 使用Prim算法求图的最小生成树
template<typename Graph, typename Weight>
class LazyPrimMST{
private:
	Graph &G;
	MinHeap<Edge<Weight>> pq;
	
	bool *marked;
	vector<Edge<Weight>> mst;
	Weight mstWeight;
	
	void visit(int v){//除了访问还有扩展 
		
		assert(!marked[v]);
		marked[v]=true;
		
		// 将和节点v相连接的所有未访问的边放入最小堆中
		typename Graph::adjIterator adj(G,v);
		for(Edge<Weight> *e=adj.begin();!adj.end();e=adj.next()){
			if(!marked[e->other(v)])
				pq.insert(*e);
				
		}
	}
	
public:
	LazyPrimMST(Graph &graph):G(graph){
		pq=MinHeap<Edge<Weight>>(graph.E());
		marked=new bool[G.V()];
		for(int i=0;i<G.V();i++)
			marked[i]=false;
		mst.clear();
		
		//Lazy prim
		visit(0);
		while(!pq.isEmpty()){
			Edge<Weight> e=pq.extractMin();//取出再判断是不是横切边 
			if(marked[e.v()]==marked[e.w()])
				continue;
			mst.push_back(e);//要找到蓝色那一端的端点 
			if(!marked[e.v()])
				visit(e.v());
			else
				visit(e.w());
		}
		
		mstWeight=mst[0].wt();
		for(int i=1;i<mst.size();i++)
			mstWeight+=mst[i].wt();
	}

	~LazyPrimMST(){
		delete marked;
	}
	
	vector<Edge<Weight>> mstEdges(){
        return mst;
    };
    
	Weight result(){
		return mstWeight;
	}
};

#endif
