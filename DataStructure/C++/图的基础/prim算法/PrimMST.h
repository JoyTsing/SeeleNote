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
	IndexMinHeap<Edge<Weight>> ipq;		// ��С������, �㷨�������ݽṹ
	vector<Edge<Weight>*> edgeTo;		//���ʵĵ�����Ӧ�ı�, �㷨�������ݽṹ
	bool *marked;
	vector<Edge<Weight>> mst;
	Weight mstWeight;
	
	void visit(int v){
		
		assert(!marked[v]);
		marked[v]=true;
		
		// ���ͽڵ�v�����ӵ�δ���ʵ���һ�˵�, ����֮�����ӵı�, ������С����
		typename Graph::adjIterator adj(G,v);
		for(Edge<Weight> *e=adj.begin();!adj.end();e=adj.next()){
			int w=e->other();
			if(!marked[w]){
				// �����û�п��ǹ�����˵�, ֱ�ӽ�����˵����֮�����ӵı߼���������
				if(!edgeTo[w])
				{
					ipq.insert(w,e->wt());
					edgeTo[w]=e;
				}
				 // ���������������˵�, �����ڵı߱�֮ǰ���ǵı߸���, ������滻
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
