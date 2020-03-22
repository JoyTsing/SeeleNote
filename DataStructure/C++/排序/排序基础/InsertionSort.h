#ifndef INSERTIONSORT_H
#define INSERTIONSORT_H

#include<iostream>
using namespace std;

//改进 
template<typename T>
void insertionSort(T arr[],int n){
	
	for(int i=1;i<n;i++){	
		//寻找arr[i]合适的插入位置 
		T cur=arr[i];
		int j=i;
		for(; j>0 && arr[j-1]>cur;j--)//寻找合适的位置，不合适就往后挪位置 
			arr[j]=arr[j-1];
		arr[j]=cur;
			
	}
	
}


#endif
