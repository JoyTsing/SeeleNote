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
	
    // ��ʼ����iָ����벿�ֵ���ʼ����λ��l��jָ���Ұ벿����ʼ����λ��mid+1
    int i = l, j = mid+1;
    for( int k = l ; k <= r; k ++ ){

        if( i > mid ){  // �����벿��Ԫ���Ѿ�ȫ���������
            arr[k] = aux[j-l]; 
			j ++;
        }
        else if( j > r ){  // ����Ұ벿��Ԫ���Ѿ�ȫ���������
            arr[k] = aux[i-l]; 
			i ++;
        }
        else if( aux[i-l] < aux[j-l] ) {  // ��벿����ָԪ�� < �Ұ벿����ָԪ��
            arr[k] = aux[i-l]; 
			i ++;
        }
        else{  // ��벿����ָԪ�� >= �Ұ벿����ָԪ��
            arr[k] = aux[j-l]; 
			j ++;
        }
    }

    //delete[] aux;
}

// �ݹ�ʹ�ù鲢����,��arr[l...r]�ķ�Χ��������
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
    //���Լ���һ�� 
	if(arr[mid]>arr[mid+1]) //�Ż�����������µ���� 
    	__merge(arr, l, mid, r);
    	
}

template<typename T>
void mergeSort(T arr[], int n){

    __mergeSort( arr , 0 , n-1 );
    
}

template<typename T>
void mergeSortBU(T arr[], int n){

    for(int sz=1;sz<=n;sz+=sz)//������size��С 1 2 4 8 16
    	for(int i=0;i+sz<n;i+=sz+sz)//��ʼλ�� �������sz��Ϊ����sz���½�����merge���� 
    	//��arr[i....i+sz-1][i+sz....i+2*sz-1]���й鲢 
    	//i+sz �ⲿ�ֺ���Ҫ �߽�����
    		__merge(arr,i,i+sz-1,min(i+sz+sz-1,n-1));//����������Ļ��ⲿ����Ҫ�� 
		
 	
}
#endif
