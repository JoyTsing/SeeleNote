#ifndef SHELLSORT_H
#define SHELLSORT_H

template<typename T>
void shellSort(T arr[], int n){

    // ���� increment sequence: 1, 4, 13, 40, 121, 364, 1093...
    int h = 1;
    while( h < n/3 )
        h = 3 * h + 1;

    while( h >= 1 ){

        // h-sort the array
        for( int i = h ; i < n ; i ++ ){//ע��i=h���� ȷ����ÿ��gap=h 

            // �� arr[i], arr[i-h], arr[i-2*h], arr[i-3*h]... ʹ�ò�������
            T cur = arr[i];
            int j;
            for( j = i ; j >= h && cur < arr[j-h] ; j -= h )
                arr[j] = arr[j-h];
            arr[j] = cur;
        }

        h /= 3;
    }
}
//�ο� 
template<typename T>
void insertionSort(T arr[],int l,int r){
	
	for(int i=l+1;i<=r;i++){	
		//Ѱ��arr[i]���ʵĲ���λ�� 
		T cur=arr[i];
		int j;
		//j=i ���ﱣ֤��ÿ�α���ʱ���ҵ���С ����&&�ȿ����� 
		for(j=i; j>l && arr[j-1]>cur;j--)//Ѱ�Һ��ʵ�λ�ã������ʾ�����Ųλ�� 
			arr[j]=arr[j-1];
		arr[j]=cur;
			
	}
	
}
#endif
