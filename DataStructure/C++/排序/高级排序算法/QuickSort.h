#ifndef QUICKSORT_H
#define QUICKSORT_H

#include<ctime>
#include"InsertionSort.h"
#include<iostream>
using namespace std;

//partition����
//����p ��ʹ��p���඼�ǣ���arr[p]���� 

template<typename T>
int __partition1_0(T arr[],int l,int r){
	
	swap(arr[l],arr[rand()%(r-l+1)+l]);
	T taget=arr[l];
	int j=l;//j����С��Ԫ�ص�ָ��λ�� 
	// arr [l+1...j] < v ;arr[j+1...i)>v
	for(int i=l+1;i<=r;i++){
		if(arr[i]<taget){//arr[i]Ϊ��ǰ����Ԫ�� 
			j++;
			swap(arr[j],arr[i]);
		}
			
	}
	swap(arr[l],arr[j]);//��Ҫ���������Ӧ�õ����õ���λ�� 
	return j;
}

template<typename T>
int __partition(T arr[],int l,int r){//_2way �Ľ���������Ľ� 
	//�������� 
	swap(arr[l],arr[rand()%(r-l+1)+l]);
	T taget=arr[l];
	// arr [l+1...i) <= v ;arr(j+1...r)>=v
	int i=l+1,j=r;//j����С��Ԫ�ص�ָ��λ�� 
	
	while(1){
		while(i<=r && arr[i]<taget)	i++;
		while(j>=l+1 && arr[j]>taget)	j--;
		if(i>j)	break;
		swap(arr[i++],arr[j--]);
	}
			
	swap(arr[l],arr[j]);//ע����j����i 
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

//��·���� 
//��arr[l.....r]��Ϊ<v;==v;>v������ 
//֮��ݹ��<v;>v�����ּ���������·���� 
//С��Χ�����ݸ����㣬�������ֲ�̫�� 
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
	
	int lt=l;// arr[l+1.....lt] <v ע�⿪������  ʹ��ָ��ָ���λ����������������� 
	int gt=r+1;// arr[gt....r] <v ע�⿪������
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
			//ע��i�����ƶ���Ϊ��������ȻΪһ���µ�δ����Ԫ�� 
		}
		else{
			i++;
		}
		
	}
	swap(arr[l],arr[lt]);//�뽻���й� 
	lt--;//����һ�����ڵ��� 
	__quicksort_3Way(arr,l,lt);
	__quicksort_3Way(arr,gt,r);
}

template<typename T>
void quickSort(T arr,int n){
	//��ʼ�Ż� 
	srand(time(NULL));
	
	__quicksort(arr,0,n-1);
}



#endif
