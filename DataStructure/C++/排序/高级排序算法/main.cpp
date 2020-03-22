#include"MergeSort.h"
#include"quickSort.h"
#include"SortTestHelp.h"
#include<iostream>
using namespace std;

int main(){
	int n=500000;
	int* arr1=SortTestHelp::generateRandomArray(n,0,n);
	int *arr2=SortTestHelp::copyIntArray(arr1,n);	
	SortTestHelp::testSort("quickSort",quickSort,arr1,n);
	SortTestHelp::testSort("mergeSort",mergeSort,arr2,n);
	delete []arr1;
	delete []arr2;
	return 0;
}












