#ifndef PRIMMST_H
#define PRIMMST_H

#include <iostream>
#include <vector>
#include <cassert>
#include "Edge.h"
#include "IndexMinHeap.h"

using namespace std;

template<typename Graph, typename Weight>
class PrimMST{

private:
	Graph &G;
	IndexMinHeap<Edge<Weight>> ipq;		// 最小索引堆, 算法辅助数据结构
	vector<Edge<Weight>*> edgeTo;		//访问的点所对应的边, 算法辅助数据结构
	bool *marked;
	vector<Edge<Weight>> mst;
	Weight mstWeight;
	
	void visit(int v){
		
		assert(!marked[v]);
		marked[v]=true;
		
		// 将和节点v相连接的未访问的另一端点, 和与之相连接的边, 放入最小堆中
		typename Graph::adjIterator adj(G,v);
		for(Edge<Weight> *e=adj.begin();!adj.end();e=adj.next()){
			int w=e->other();
			if(!marked[w]){
				// 如果从没有考虑过这个端点, 直接将这个端点和与之相连接的边加入索引堆
				if(!edgeTo[w])
				{
					ipq.insert(w,e->wt());
					edgeTo[w]=e;
				}
				 // 如果曾经考虑这个端点, 但现在的边比之前考虑的边更短, 则进行替换
				else if(e->wt() < edgeTo[w]->wt() ){
					edgeTo[w]=e;
					ipq.change(w,e->wt());
				}	
				
			}
		}
	}
	
	
public:
	PrimMST(Graph &graph):G(graph){
		
		marked=new bool[G.V()];
		ipq=IndexMinHeap<double>(graph.V());
		for(int i=0;i<G.V();i++){
			marked[i]=false;
			edgeTo.push_back(NULL);
		}
		mst.clear();
		
		//prim
		visit(0);
		while(! ipq.isEmpty() ){
			int v=ipq.extractMinIndex();
			assert(edgeTo[v]);
			mst.push_back( *edgeTo[v] );
			visit(v);
		}
		
		mstWeight=mst[0].wt();
		for(int i=1;i<mst.size();i++)
			mstWeight+=mst[i].wt();
		
		
	}


 	~PrimMST(){
        delete[] marked;
    }

    vector<Edge<Weight>> mstEdges(){
        return mst;
    };

    Weight result(){
        return mstWeight;
    };
};


#endif
