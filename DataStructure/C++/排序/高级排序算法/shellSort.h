#ifndef SHELLSORT_H
#define SHELLSORT_H

template<typename T>
void shellSort(T arr[], int n){

    // 计算 increment sequence: 1, 4, 13, 40, 121, 364, 1093...
    int h = 1;
    while( h < n/3 )
        h = 3 * h + 1;

    while( h >= 1 ){

        // h-sort the array
        for( int i = h ; i < n ; i ++ ){//注意i=h这里 确保了每个gap=h 

            // 对 arr[i], arr[i-h], arr[i-2*h], arr[i-3*h]... 使用插入排序
            T cur = arr[i];
            int j;
            for( j = i ; j >= h && cur < arr[j-h] ; j -= h )
                arr[j] = arr[j-h];
            arr[j] = cur;
        }

        h /= 3;
    }
}
//参考 
template<typename T>
void insertionSort(T arr[],int l,int r){
	
	for(int i=l+1;i<=r;i++){	
		//寻找arr[i]合适的插入位置 
		T cur=arr[i];
		int j;
		//j=i 这里保证，每次遍历时候都找到最小 所以&&既可以了 
		for(j=i; j>l && arr[j-1]>cur;j--)//寻找合适的位置，不合适就往后挪位置 
			arr[j]=arr[j-1];
		arr[j]=cur;
			
	}
	
}
#endif
