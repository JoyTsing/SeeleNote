#include"MaxHeap.h"
#include<iostream>
using namespace std;

template<typename T>
void HeapSort1(T arr[],int n){
	MaxHeap<T> maxheap = MaxHeap <T> (n);
	//���ڰ������������и��õ���ʾ 
	for(int i=0;i<n;i++)
		maxheap.Insert(arr[i]);
	
	for(int i=n-1;i>=0;i--)
		arr[i]=maxheap.extractMax();
}

template<typename T>
void HeapSort2(T arr[],int n){
	MaxHeap<T> maxheap = MaxHeap <T> (arr,n);

	for(int i=n-1;i>=0;i--)
		arr[i]=maxheap.extractMax();
		
}
/*
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
*/
