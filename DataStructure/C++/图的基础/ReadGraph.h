#ifndef READGRAPH_H
#define READGRAPH_h

#include<iostream>
#include<fstream>
#include<string>
#include<sstream>
#include<cassert>

using namespace std;

template<typename Graph,typename Weight>
class ReadGraph{//图的算法大多封装在一个类之中 
private:
	
public:
	ReadGraph(Graph &graph,const string &filename){
		ifstream file(filename);
		string line;
		int V ,E;
		
		assert( file.is_open() );
		assert(getline(file,line));
		
		stringstream ss(line);
		ss>>V>>E;
		
		assert(V==graph.V());//点数是否匹配 
		
		for(int i=0;i<E;i++){
			assert(getline(file,line));
			stringstream ss(line);
			
			int a,b;
			Weight w;
			ss>>a>>b>>w;
			assert( a >= 0 && a < V );
            assert( b >= 0 && b < V );
			graph.addEdge(a,b,w);
		}
			
	}
};
#endif
