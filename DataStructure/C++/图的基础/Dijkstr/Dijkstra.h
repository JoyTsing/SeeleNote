#ifndef DIJKSTRA_H
#define DIJKSTRA_H

#include <iostream>
#include <vector>
#include <stack>
#include "Edge.h"
#include "IndexMinHeap.h"

using namespace std;

// Dijkstra算法求最短路径
template<typename Graph, typename Weight>
class Dijkstra{

private:
    Graph &G;                   // 图的引用
    int s;                      // 起始点
    Weight *distTo;             // distTo[i]存储从起始点s到i的最短路径长度
    bool *marked;               // 标记数组, 在算法运行过程中标记节点i是否被访问
    
    vector<Edge<Weight>*> from; // from[i]记录最短路径中, 到达i点的边是哪一条
public:
	Dijkstra(Graph &graph,int s):G(graph){
		this->s=s;
		
		distTo=new Weight[G.V()];
		marked=new bool[G.V()];
		for(int i=0;i<G.V();i++){
			distTo[i]=Weight();//未知采用模板类型默认构造函数  类似于置空 
			marked[i]=false;
			from.push_back(NULL);
		}
		
		// 使用索引堆记录当前找到的到达每个顶点的最短距离
		IndexMinHeap<Weight> ipq(G.V());
		
		//Dijkstra
		 // 对于其实点s进行初始化
		from[s] = new Edge<Weight>(s, s, Weight());//这句比较重要
		 
		distTo[s]=Weight();
		marked[s]=true; 
		ipq.insert(s,distTo[s]);
		while(!ipq.isEmpty()){
			
			int v=ipq.extractMinIndex();
			
			// distTo[v]就是s到v的最短距离
            marked[v] = true;
			typename Graph::adjIterator adj(G, v);
			//Relaxtion
            for( Edge<Weight>* e = adj.begin() ; !adj.end() ; e = adj.next() ){
                int w = e->other(v);
                // 如果从s点到w点的最短路径还没有找到
                if(!marked[w]){
                	// 如果w点以前没有访问过,
                    // 或者访问过, 但是通过当前的v点到w点距离更短, 则进行更新
                	if(from[w]==NULL||distTo[v]+e->wt()<distTo[w]){
                		distTo[w]=distTo[v]+e->wt();
                		from[w]=e;
                		if(ipq.contain(w))
                			ipq.change(w,distTo[w]);
                		else
                			ipq.insert(w,distTo[w]);
					}
                	
				}
				
            }
            
		}
		 
		 
	}
	
	~Dijkstra(){
        delete[] distTo;
        delete[] marked;
        delete from[0];
    }

    // 返回从s点到w点的最短路径长度
    Weight shortestPathTo( int w ){
        assert( w >= 0 && w < G.V() );
        assert( hasPathTo(w) );
        return distTo[w];
    }

    // 判断从s点到w点是否联通
    bool hasPathTo( int w ){
        assert( w >= 0 && w < G.V() );
        return marked[w];
    }

    // 寻找从s到w的最短路径, 将整个路径经过的边存放在vec中
    void shortestPath( int w, vector<Edge<Weight>> &vec ){

        assert( w >= 0 && w < G.V() );
        assert( hasPathTo(w) );

        // 通过from数组逆向查找到从s到w的路径, 存放到栈中
        stack<Edge<Weight>*> s;
        Edge<Weight> *e = from[w];
        while( e->v() != this->s ){
            s.push(e);
            e = from[e->v()];
        }
        s.push(e);

        // 从栈中依次取出元素, 获得顺序的从s到w的路径
        while( !s.empty() ){
            e = s.top();
            vec.push_back( *e );
            s.pop();
        }
    }

    // 打印出从s点到w点的路径
    void showPath(int w){

        assert( w >= 0 && w < G.V() );
        assert( hasPathTo(w) );

        vector<Edge<Weight>> vec;
        shortestPath(w, vec);
        for( int i = 0 ; i < vec.size() ; i ++ ){
            cout<<vec[i].v()<<" -> ";
            if( i == vec.size()-1 )
                cout<<vec[i].w()<<endl;
        }
    }
};

#endif
