#ifndef UNIONFIND3_H
#define UNIONFIND3_H

#include <cassert>

using namespace std;

// ���ǵĵ�����Union-Find
namespace UF3{

    class UnionFind{

    private:
        int* parent; // parent[i]��ʾ��i��Ԫ����ָ��ĸ��ڵ�
        int* sz;     // sz[i]��ʾ��iΪ���ļ�����Ԫ�ظ���
        int count;   // ���ݸ���

    public:
        // ���캯��
        UnionFind(int count){
            parent = new int[count];
            sz = new int[count];
            this->count = count;
            for( int i = 0 ; i < count ; i ++ ){
                parent[i] = i;
                sz[i] = 1;
            }
        }

        // ��������
        ~UnionFind(){
            delete[] parent;
            delete[] sz;
        }

        // ���ҹ���, ����Ԫ��p����Ӧ�ļ��ϱ��
        // O(h)���Ӷ�, hΪ���ĸ߶�
        int find(int p){
            assert( p >= 0 && p < count );
            // ����ȥ��ѯ�Լ��ĸ��׽ڵ�, ֱ��������ڵ�
            // ���ڵ���ص�: parent[p] == p
            while( p != parent[p] )
                p = parent[p];
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
            if( sz[pRoot] < sz[qRoot] ){
                parent[pRoot] = qRoot;
                sz[qRoot] += sz[pRoot];
            }
            else{
                parent[qRoot] = pRoot;
                sz[pRoot] += sz[qRoot];
            }
        }
    };
}

#endif 

