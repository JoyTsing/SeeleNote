#ifndef INSERTIONSORT_H
#define INSERTIONSORT_H

#include<iostream>
using namespace std;

//�Ľ� 
template<typename T>
void insertionSort(T arr[],int n){
	
	for(int i=1;i<n;i++){	
		//Ѱ��arr[i]���ʵĲ���λ�� 
		T cur=arr[i];
		int j=i;
		for(; j>0 && arr[j-1]>cur;j--)//Ѱ�Һ��ʵ�λ�ã������ʾ�����Ųλ�� 
			arr[j]=arr[j-1];
		arr[j]=cur;
			
	}
	
}


#endif
