#ifndef UNIONFIND5_H
#define UNIONFIND5_H

#include<cassert>
#include<iostream>
using namespace std;

//·��ѹ�� 
namespace UF5{

    class UnionFind{

    private:
        int* rank;   // rank[i]��ʾ��iΪ���ļ�������ʾ�����Ĳ���
        int* parent; // parent[i]��ʾ��i��Ԫ����ָ��ĸ��ڵ�
        int count;   // ���ݸ���

    public:
        // ���캯��
        UnionFind(int count){
            parent = new int[count];
            rank = new int[count];
            this->count = count;
            for( int i = 0 ; i < count ; i ++ ){
                parent[i] = i;
                rank[i] = 1;
            }
        }

        // ��������
        ~UnionFind(){
            delete[] parent;
            delete[] rank;
        }

        // ���ҹ���, ����Ԫ��p����Ӧ�ļ��ϱ��
        // O(h)���Ӷ�, hΪ���ĸ߶�
        int find(int p){
            assert( p >= 0 && p < count );
            // ����ȥ��ѯ�Լ��ĸ��׽ڵ�, ֱ��������ڵ�
            // ���ڵ���ص�: parent[p] == p
            while( p != parent[p] ){
            	parent[p] =parent[parent[p]];
            	p = parent[p];
			}
             // path compression 2, �ݹ��㷨
//            if( p != parent[p] )
//                parent[p] = find( parent[p] );
//            return parent[p];  
            return p;
        }

        // �鿴Ԫ��p��Ԫ��q�Ƿ�����һ������
        // O(h)���Ӷ�, hΪ���ĸ߶�
        bool isConnected( int p , int q ){
            return find(p) == find(q);
        }

        // �ϲ�Ԫ��p��Ԫ��q�����ļ���
        // O(h)���Ӷ�, hΪ���ĸ߶�
        void unionElements(int p, int q){

            int pRoot = find(p);
            int qRoot = find(q);

            if( pRoot == qRoot )
                return;

            // ��������Ԫ����������Ԫ�ظ�����ͬ�жϺϲ�����
            // ��Ԫ�ظ����ٵļ��Ϻϲ���Ԫ�ظ�����ļ�����
            if( rank[pRoot] < rank[qRoot] ){
                parent[pRoot] = qRoot;
            }
            else if( rank[qRoot] < rank[pRoot]){
                parent[qRoot] = pRoot;
            }
            else{ // rank[pRoot] == rank[qRoot]
                parent[pRoot] = qRoot;
                rank[qRoot] += 1;   // ��ʱ, ��ά��rank��ֵ
            }
        }
    };
}

}

#endif
