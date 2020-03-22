#ifndef MERGESORT_H
#define MERGESORT_H

#include"InsertionSort.h"
#include<iostream>
using namespace std;

template<typename  T>
void __merge(T arr[], int l, int mid, int r){
    T aux[r-l+1];
    
    for( int i = l ; i <= r; i ++ )
        aux[i-l] = arr[i];
	
    // 初始化，i指向左半部分的起始索引位置l；j指向右半部分起始索引位置mid+1
    int i = l, j = mid+1;
    for( int k = l ; k <= r; k ++ ){

        if( i > mid ){  // 如果左半部分元素已经全部处理完毕
            arr[k] = aux[j-l]; 
			j ++;
        }
        else if( j > r ){  // 如果右半部分元素已经全部处理完毕
            arr[k] = aux[i-l]; 
			i ++;
        }
        else if( aux[i-l] < aux[j-l] ) {  // 左半部分所指元素 < 右半部分所指元素
            arr[k] = aux[i-l]; 
			i ++;
        }
        else{  // 左半部分所指元素 >= 右半部分所指元素
            arr[k] = aux[j-l]; 
			j ++;
        }
    }

    //delete[] aux;
}

// 递归使用归并排序,对arr[l...r]的范围进行排序
template<typename T>
void __mergeSort(T arr[], int l, int r){

 //   if( l >= r )
//        return;
	if(r-l<=15){
			insertionSort(arr,l,r);
			return ;
	}
	
		
    int mid = (l+r)/2;
    __mergeSort(arr, l, mid);
    
    __mergeSort(arr, mid+1, r);
    //可以加速一下 
	if(arr[mid]>arr[mid+1]) //优化有序的数组下的情况 
    	__merge(arr, l, mid, r);
    	
}

template<typename T>
void mergeSort(T arr[], int n){

    __mergeSort( arr , 0 , n-1 );
    
}

template<typename T>
void mergeSortBU(T arr[], int n){

    for(int sz=1;sz<=n;sz+=sz)//逐渐增加size大小 1 2 4 8 16
    	for(int i=0;i+sz<n;i+=sz+sz)//起始位置 跨过两个sz因为两个sz在下讲进行merge操作 
    	//对arr[i....i+sz-1][i+sz....i+2*sz-1]进行归并 
    	//i+sz 这部分很重要 边界问题
    		__merge(arr,i,i+sz-1,min(i+sz+sz-1,n-1));//对链表排序的话这部分需要改 
		
 	
}
#endif
