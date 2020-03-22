#ifndef MAXHEAP_H
#define MAXHEAP_H


#include<iostream>
#include<string>
#include<ctime>
#include<cmath>
#include<cassert>
#include<typeinfo>
using namespace std; 


template<typename Item>
class MaxHeap{
public:
	MaxHeap(int capaticy):data(new Item[capaticy+1]),count(0){
		this->capaticy=capaticy;
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
    
	~MaxHeap(){
	delete []data;
	}
		
	int size(){
		return count;
	}	
	
	bool isEmpty(){
		return count==0;
	}
	
	void Insert(Item item){
		if(count+1>capaticy)
			throw("NO room!");
		data[count+1]=item;
		count++;
		shiftUp(count);
	}
	
	Item extractMax();
	void testPrint();
	
private:
	Item* data;
	int count;
	int capaticy;//�����Ǵ�1��ʼ�� 
	void shiftUp(int k);
	void shiftDown(int k);//ֻ��ȡ��� ���ȼ����� 
	
	
	
	
	void putNumberInLine( int num, string &line, int index_cur_level, int cur_tree_width, bool isLeft);
	void putBranchInLine( string &line, int index_cur_level, int cur_tree_width);
};

//�Ż� ��swap��Ϊ��ֵ 
template<typename Item>
void MaxHeap<Item>::shiftUp(int k){
	Item e = data[k];
	while(k>1 && data[k/2]<data[k]){
		//swap(data[k/2],data[k]);
		data[k] = data[k/2];
		k/=2;
	}
	data[k] = e;
}

	
template<typename Item>	
Item MaxHeap<Item>::extractMax(){
	if(count<=0)
			throw("NO data!");
	Item ret=data[1];
	swap(data[1],data[count--]);
	shiftDown(1);
	return ret;
}


template<typename Item>	
void MaxHeap<Item>::shiftDown(int k){
	Item e = data[k];
	while(2*k<=count){//�����������ӽڵ� 
		int j=2*k;//����ѭ����data[k]��data[j]����λ�� 
		//�߽籣��  j+1�����ҽڵ� 
		if(j+1<=count &&data[j+1]>data[j]) 
			j+=1;
		
		if(data[k]<data[j]){
			//swap(data[k],data[j]);
			data[k] = data[j];
			k=j;
		}
		else 
			break;	
		
	}
	data[k] = e;
}	



//ԭ�ض����� 
template<typename T>
void __shiftDown(T arr[],int n,int k){
	T e = arr[k];
	while(2*k+1<n){//�����������ӽڵ� 
		int j=2*k+1;//����ѭ����arr[k]��arr[j]����λ�� 
		//�߽籣��  j+1�����ҽڵ� 
		if(j+1<n &&arr[j+1]>arr[j]) 
			j+=1;
		
		if(arr[k]<arr[j]){
			//swap(data[k],data[j]);
			arr[k] = arr[j];
			k=j;
		}
		else 
			break;	
		
	}
arr[k] = e;
	
}	

	
template<typename T>//ԭ�ض����� 
void HeapSort(T arr[],int n){
	
	for(int i=(n-1-1)/2;i>=0;i--)
		__shiftDown(arr,n,i);
		
	for(int i=n-1;i>0;i--){
		swap(arr[0],arr[i]);
		__shiftDown(arr,i,0);
	}
		
}	
	







template<typename Item>
void MaxHeap<Item>::testPrint(){
	// ���ǵ�testPrintֻ�ܴ�ӡ100��Ԫ�����ڵĶѵ���״��Ϣ
        if( size() >= 100 ){
            cout<<"This print function can only work for less than 100 int";
            return;
        }

        // ���ǵ�testPrintֻ�ܴ���������Ϣ
        if( typeid(Item) != typeid(int) ){
            cout <<"This print function can only work for int item";
            return;
        }

        cout<<"The max heap size is: "<<count<<endl;
        cout<<"Data in the max heap: ";
        for( int i = 1 ; i <=	size() ; i ++ ){
            // ���ǵ�testPrintҪ����е�����������[0, 100)�ķ�Χ��
            assert( data[i] >= 0 && data[i] < 100 );
            cout<<data[i]<<" ";
        }
        cout<<endl;
        cout<<endl;

        int n = size();
        int max_level = 0;
        int number_per_level = 1;
        while( n > 0 ) {
            max_level += 1;
            n -= number_per_level;
            number_per_level *= 2;
        }

        int max_level_number = int(pow(2, max_level-1));
        int cur_tree_max_level_number = max_level_number;
        int index = 1;
        for( int level = 0 ; level < max_level ; level ++ ){
            string line1 = string(max_level_number*3-1, ' ');

            int cur_level_number = min(count-int(pow(2,level))+1,int(pow(2,level)));
            bool isLeft = true;
            for( int index_cur_level = 0 ; index_cur_level < cur_level_number ; index ++ , index_cur_level ++ ){
                putNumberInLine( data[index] , line1 , index_cur_level , cur_tree_max_level_number*3-1 , isLeft );
                isLeft = !isLeft;
            }
            cout<<line1<<endl;

            if( level == max_level - 1 )
                break;

            string line2 = string(max_level_number*3-1, ' ');
            for( int index_cur_level = 0 ; index_cur_level < cur_level_number ; index_cur_level ++ )
                putBranchInLine( line2 , index_cur_level , cur_tree_max_level_number*3-1 );
            cout<<line2<<endl;

            cur_tree_max_level_number /= 2;
        }
}

template<typename Item>
void MaxHeap<Item>::putNumberInLine( int num, string &line, int index_cur_level, int cur_tree_width, bool isLeft){
	int sub_tree_width = (cur_tree_width - 1) / 2;
        int offset = index_cur_level * (cur_tree_width+1) + sub_tree_width;
        assert(offset + 1 < line.size());
        if( num >= 10 ) {
            line[offset + 0] = '0' + num / 10;
            line[offset + 1] = '0' + num % 10;
        }
        else{
            if( isLeft)
                line[offset + 0] = '0' + num;
            else
                line[offset + 1] = '0' + num;
        }
}

template<typename Item>
void MaxHeap<Item>::putBranchInLine( string &line, int index_cur_level, int cur_tree_width){
	 int sub_tree_width = (cur_tree_width - 1) / 2;
        int sub_sub_tree_width = (sub_tree_width - 1) / 2;
        int offset_left = index_cur_level * (cur_tree_width+1) + sub_sub_tree_width;
        assert( offset_left + 1 < line.size() );
        int offset_right = index_cur_level * (cur_tree_width+1) + sub_tree_width + 1 + sub_sub_tree_width;
        assert( offset_right < line.size() );

        line[offset_left + 1] = '/';
        line[offset_right + 0] = '\\';
    }






#endif
