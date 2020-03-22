#ifndef INDEXHEAP_H
#define INDEXHEAP_H


#include<iostream>


#include<cassert>

using namespace std; 


template<typename Item>
class IndexHeap{
public:
	IndexHeap(int capaticy):data(new Item[capaticy+1]),indexs(new int[capaticy+1]),reverse(new int[capaticy+1]),count(0){
		this->capaticy=capaticy;
		for(int i=0;i<=capaticy;i++)
			reverse[i]=0;
	}
	
	// 构造函数, 通过一个给定数组创建一个最大堆
    // 该构造堆的过程, 时间复杂度为O(n)
    MaxHeapO(Item arr[], int n){
        this->data = new Item[n+1];
        this->capacity = n;

        for( int i = 0 ; i < n ; i ++ )
            data[i+1] = arr[i];
        this->count = n;

        for( int i = count/2 ; i >= 1 ; i -- )
            shiftDown(i);
    }
    
	~IndexHeap(){
	delete []data;
	delete []indexs;
	delete []reverse;
	}
		
	int size(){
		return count;
	}	
	
	bool isEmpty(){
		return count==0;
	}
	
	//传入的i对用户而言，是从0索引开始的 
	void Insert(int i,Item item){
		assert(i>=0 && i < capacity);
		if(count+1>capacity)
			throw("NO room!");
		
		i+=1;//内部实现的时候索引从1开始 
		data[count+1]=item;
		indexs[count+1]=i;
		reverse[i]=count+1;
		
		count++;
		shiftUp(count);
		
	}
	
	int extractMaxIndex();
	Item extractMax();
	void testPrint();
	Item getItem(int i);
	void change(int i,Item  newItem);
	bool contain(int i){
		assert(i+1>=1&&i+1<=capacity);
		return reverse[i+1]!=0;
	}
	
private:
	Item* data;
	int* indexs;
	int* reverse;
	int count;
	int capacity;//索引是从1开始的 
	void shiftUp(int k);
	void shiftDown(int k);//只能取最大 优先级队列 
	

};


//优化 把swap改为赋值 
template<typename Item>
void IndexHeap<Item>::shiftUp(int k){//这里的k是索引数组的位置 

	while(k>1 && data[indexs[k/2]]<data[indexs[k]]){
		swap(indexs[k/2],indexs[k]);
		reverse[indexs[k/2]]=k/2;
		reverse[indexs[k]]=k;
		k/=2;
	}

}

template<typename Item>	
int IndexHeap<Item>::extractMaxIndex(){
	if(count<=0)
			throw("NO data!");
	int ret=indexs[1]-1;
	swap(indexs[1],indexs[count]);
	reverse[indexs[1]]=1;
	reverse[indexs[count]]=0;//被删除了 
	count--;
	shiftDown(1);
	return ret;
}

template<typename Item>	
Item IndexHeap<Item>::extractMax(){
	if(count<=0)
			throw("NO data!");
	Item ret=data[indexs[1]];
	swap(indexs[1],indexs[count]);
	reverse[indexs[1]]=1;
	reverse[indexs[count]]=0;//被删除了 
	count--;
	shiftDown(1);
	return ret;
}

template<typename Item>	
void IndexHeap<Item>::shiftDown(int k){

	while(2*k<=count){//看有无左右子节点 
		int j=2*k;//此轮循环中data[k]和data[j]交换位置 
		//边界保护  j+1有无右节点 
		if(j+1<=count && data[j+1]>data[j] ) 
			j+=1;
		
		if(data[indexs[k]]<data[indexs[j]]){
			swap(indexs[k],indexs[j]);
			reverse[indexs[k]]=k;
			reverse[indexs[j]]=j;//被删除了
			k=j;
		}
		else 
			break;	
		
	}

}

template<typename Item>		
Item IndexHeap<Item>::getItem(int i){
	assert(contain(i));
	return data[i+1];
}

template<typename Item>	
void IndexHeap<Item>::change(int i,Item newItem){
	i+=1;
	data[i]=newItem;
	assert(contain(i));//元素在堆中 
	
	
	
	
	//找到indexes[j]=i,j表示data[i]在堆中的位置 
	//之后是shiftUp（j）再shiftDown（j） 
//	for(int j=1;j<=count;j++)
//		if(indexs[i]==i){
//			shiftUp(j);
//			shiftDown(j);
//			return ;
//		}
	int j=reverse[i];	
	shiftUp(j);
	shiftDown(j);	
				 
}


#endif
