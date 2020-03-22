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
	
	// ���캯��, ͨ��һ���������鴴��һ������
    // �ù���ѵĹ���, ʱ�临�Ӷ�ΪO(n)
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
	
	//�����i���û����ԣ��Ǵ�0������ʼ�� 
	void Insert(int i,Item item){
		assert(i>=0 && i < capacity);
		if(count+1>capacity)
			throw("NO room!");
		
		i+=1;//�ڲ�ʵ�ֵ�ʱ��������1��ʼ 
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
	int capacity;//�����Ǵ�1��ʼ�� 
	void shiftUp(int k);
	void shiftDown(int k);//ֻ��ȡ��� ���ȼ����� 
	

};


//�Ż� ��swap��Ϊ��ֵ 
template<typename Item>
void IndexHeap<Item>::shiftUp(int k){//�����k�����������λ�� 

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
	reverse[indexs[count]]=0;//��ɾ���� 
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
	reverse[indexs[count]]=0;//��ɾ���� 
	count--;
	shiftDown(1);
	return ret;
}

template<typename Item>	
void IndexHeap<Item>::shiftDown(int k){

	while(2*k<=count){//�����������ӽڵ� 
		int j=2*k;//����ѭ����data[k]��data[j]����λ�� 
		//�߽籣��  j+1�����ҽڵ� 
		if(j+1<=count && data[j+1]>data[j] ) 
			j+=1;
		
		if(data[indexs[k]]<data[indexs[j]]){
			swap(indexs[k],indexs[j]);
			reverse[indexs[k]]=k;
			reverse[indexs[j]]=j;//��ɾ����
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
	assert(contain(i));//Ԫ���ڶ��� 
	
	
	
	
	//�ҵ�indexes[j]=i,j��ʾdata[i]�ڶ��е�λ�� 
	//֮����shiftUp��j����shiftDown��j�� 
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
