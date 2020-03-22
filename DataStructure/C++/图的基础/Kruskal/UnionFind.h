#ifndef UNIONFIND_H
#define UNIONFIND_H

#include <iostream>
#include <cassert>

using namespace std;

// Quick Union + rank + path compression
class UnionFind{

private:
    // rank[i]��ʾ��iΪ���ļ�������ʾ�����Ĳ���
    // �ں����Ĵ�����, ���ǲ�����ά��rank������, Ҳ����rank��ֵ��·��ѹ���Ĺ�����, �п��ܲ��������Ĳ���ֵ
    // ��Ҳ�����ǵ�rank����height����depth��ԭ��, ��ֻ����Ϊ�Ƚϵ�һ����׼
    
    int* rank;
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

        // path compression 1
        while( p != parent[p] ){
            parent[p] = parent[parent[p]];
            p = parent[p];
        }
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

#endif
