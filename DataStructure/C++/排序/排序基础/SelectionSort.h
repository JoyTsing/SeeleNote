#ifndef SELECTION_H
#define SELECTION_H
#include<iostream>
using namespace std;

/*自己写的 
void selectionSort(int *arr,int n){
	for(int i=0;i<n;i++){
		int taget=i;
		for(int k=i;k<n;k++)
			if(arr[k]<arr[taget])
				taget=k;
		int tmp=arr[i];
		arr[i]=arr[taget];
		arr[taget]=tmp;
	}
for(int i=0;i<n;i++)
	cout<<arr[i]<<endl;		
}
*/

template<typename T>
void selectionSort(T *arr,int n){
	
	for(int i=0;i<n;i++){
		//寻找【i，n）区间中的最小值 
		int minIndex=i;
		for(int j=i+1;j<n;j++)
			if(arr[j]<arr[minIndex])
				minIndex=j;
		swap(arr[i],arr[minIndex]);
	}
	
}


#endif
