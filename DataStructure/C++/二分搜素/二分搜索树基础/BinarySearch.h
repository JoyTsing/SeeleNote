#ifndef BINARYSEARCH_H
#define BINARYSEARCH_H

#include<iostream>
using namespace std;

template<typename T>
int binarySearch(T arr[],int n,T target){
	int l=0,r=n-1;
	while(l<=r){
	//	int mid=(l+r)/2;//可能在大数下有bug 
		int mid=l+(r-l)/2;
		if(arr[mid]==target)
			return mid;
		
		if(target<arr[mid])
			r=mid-1;	
		
		if(target>arr[mid])
			r=mid+1;
		
	}
	
	return -1;
}




#endif



