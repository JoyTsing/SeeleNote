#ifndef UNIONFIND1_H
#define UNIONFIND1_H

#include<cassert>
#include<iostream>
using namespace std;


namespace UF1{
	
class UnionFind{
private:
	int *id;
	int count;
	
public:
	
	UnionFind1(int n):count(n),id(new int[count]){
		for(int i=0;i<n;i++)
			id[i]=i;
	}
		
	~UnionFind1(){
		delete[]id;
	}
	
	int Find(int p){
		assert(p>=0&&p<count);
		return id[p];
	}
	
	bool isConnected(int p.int q){
		return find(p)==find(q);
	}
	
	void unionElements(int p,int q){
		int pID=find(p);
		int pID=find(q);
		
		if(pID==qID)
			return ;
			
		for(int i=0;i<count;count++){
			if(id[i]==pID)
				id[i]=pID;
		}
	}
};

}

#endif
