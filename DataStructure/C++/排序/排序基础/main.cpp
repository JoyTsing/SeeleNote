#include<iostream>
#include"SortTestHelp.h"
#include"SelectionSort.h"
#include"InsertionSort.h"
using namespace std;

int main()
{
	int n=10000;
	
	int *arr=SortTestHelp::generateNearlyOrderedArray(n,10);
	int *arr2=SortTestHelp::copyIntArray(arr,n);
	//selectionSort(arr,n);
	//SortTestHelp::printArry(arr,n);
	SortTestHelp::testSort("SelectionSort",selectionSort,arr,n);
	SortTestHelp::testSort("insertionSort",insertionSort,arr2,n);
	
	delete []arr;
	delete []arr2;
	
	return 0;
}
