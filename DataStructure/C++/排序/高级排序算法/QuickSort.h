#ifndef QUICKSORT_H
#define QUICKSORT_H

#include<ctime>
#include"InsertionSort.h"
#include<iostream>
using namespace std;

//partition操作
//返回p 且使得p两侧都是＞或＜arr[p]的数 

template<typename T>
int __partition1_0(T arr[],int l,int r){
	
	swap(arr[l],arr[rand()%(r-l+1)+l]);
	T taget=arr[l];
	int j=l;//j代表小的元素的指针位置 
	// arr [l+1...j] < v ;arr[j+1...i)>v
	for(int i=l+1;i<=r;i++){
		if(arr[i]<taget){//arr[i]为当前考察元素 
			j++;
			swap(arr[j],arr[i]);
		}
			
	}
	swap(arr[l],arr[j]);//不要忘记最左边应该到它该到的位置 
	return j;
}

template<typename T>
int __partition(T arr[],int l,int r){//_2way 改进都在这里改进 
	//表现良好 
	swap(arr[l],arr[rand()%(r-l+1)+l]);
	T taget=arr[l];
	// arr [l+1...i) <= v ;arr(j+1...r)>=v
	int i=l+1,j=r;//j代表小的元素的指针位置 
	
	while(1){
		while(i<=r && arr[i]<taget)	i++;
		while(j>=l+1 && arr[j]>taget)	j--;
		if(i>j)	break;
		swap(arr[i++],arr[j--]);
	}
			
	swap(arr[l],arr[j]);//注意是j不是i 
	return j;
}

template<typename T>
void __quicksort(T arr[],int l,int r){//_1_2
//	if(l>=r)
//		return ; 
	if(r-l<=15){
			insertionSort(arr,l,r);
			return ;
	}
	int p=__partition(arr,l,r);
	__quicksort(arr,l,p-1);
	__quicksort(arr,p+1,r);
}

//三路快排 
//将arr[l.....r]分为<v;==v;>v三部分 
//之后递归对<v;>v两部分继续进行三路快排 
//小范围多数据更优秀，其他表现不太行 
template<typename T>
void __quicksort_3Way(T arr[],int l,int r){//
//	if(l>=r)
//		return ;
	if(r-l<=15){
			insertionSort(arr,l,r);
			return ;
	}
	//int p=__partition(arr,l,r);
	
	//partition
	swap(arr[l],arr[rand()%(r-l+1)+l]);
	T taget=arr[l];
	
	int lt=l;// arr[l+1.....lt] <v 注意开闭区间  使该指针指向的位置仍属于这个区间内 
	int gt=r+1;// arr[gt....r] <v 注意开闭区间
	int i=l+1;//arr[l+1....i)==v 
	
	while(i<gt){
		if(arr[i]<taget){
				swap(arr[i],arr[lt+1]);
				lt++;
				i++;
		}
		else if(arr[i]>taget){
				swap(arr[i],arr[gt-1]);
				gt--;
			//注意i不用移动因为交换后仍然为一个新的未处理元素 
		}
		else{
			i++;
		}
		
	}
	swap(arr[l],arr[lt]);//与交换有关 
	lt--;//多了一个等于的数 
	__quicksort_3Way(arr,l,lt);
	__quicksort_3Way(arr,gt,r);
}

template<typename T>
void quickSort(T arr,int n){
	//开始优化 
	srand(time(NULL));
	
	__quicksort(arr,0,n-1);
}



#endif
