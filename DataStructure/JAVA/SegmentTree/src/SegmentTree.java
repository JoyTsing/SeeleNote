import java.util.Arrays;

public class SegmentTree<E> {

    private E[] tree;
    private E[] data;

    //自定业务逻辑
    private Merger<E> merger;

    public SegmentTree(E[] arr, Merger<E> merger) {

        this.merger = merger;

        data = (E[]) new Object[arr.length];
        for (int i = 0; i < arr.length; i++)
            data[i] = arr[i];

        tree = (E[]) new Object[4 * arr.length];
        buildSegmentTree(0, 0, data.length - 1);
    }

    //在treeIndex的位置创建表示区间[l...r]的线段树
    private void buildSegmentTree(int treeIndex, int l, int r) {

        if (l == r) {
            tree[treeIndex] = data[l];
            return;
        }

        int leftTreeIndex = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);

        int mid = l + (r - l) / 2;
        //神似merge
        buildSegmentTree(leftTreeIndex, l, mid);
        buildSegmentTree(rightTreeIndex, mid + 1, r);

        //具体存储与实际相关
        tree[treeIndex] = merger.merge(tree[leftTreeIndex], tree[rightTreeIndex]);
    }

    public int getSize() {
        return data.length;
    }

    public E get(int index) {
        if (index < 0 || index >= data.length)
            throw new IllegalArgumentException("Index Error");

        return data[index];
    }

    private int leftChild(int index) {
        return 2 * index + 1;
    }

    private int rightChild(int index) {
        return 2 * index + 2;
    }

    //返回区间内的值
    public E query(int queryL, int queryR) {
        if (queryL < 0 || queryL >= data.length
                || queryL < 0 || queryR >= data.length || queryL > queryR)
            throw new IllegalArgumentException("Index Error");

        return query(0, 0, data.length - 1, queryL, queryR);
    }

    //在以treeID为根的线段树中[l...r]的范围里，搜索区间[queryL...queryR]的值
    //前三个参数可以整合
    //queryL和queryR是data那一组的，其通过树节点的变化与tree联系
    private E query(int root, int l, int r, int queryL, int queryR) {
        //查询操作
        if (l == queryL && r == queryR) {
            return tree[root];
        }

        int mid = l + (r - l) / 2;
        int leftTreeIndex = leftChild(root);
        int rightTreeIndex = rightChild(root);

        if (queryL >= mid + 1)
            return query(rightTreeIndex, mid + 1, r, queryL, queryR);
        else if (queryR <= mid)
            return query(leftTreeIndex, l, mid, queryL, queryR);

        E leftResult = query(leftTreeIndex, l, mid, queryL, mid);
        E rightResult = query(rightTreeIndex, mid + 1, r, mid + 1, queryR);
        return merger.merge(leftResult, rightResult);
    }

    public void set(int index, E e) {
        if (index < 0 || index >= data.length)
            throw new IllegalArgumentException("Index Error");
        data[index]=e;
        set(0,0,data.length-1,index,e);
    }

    //一个点更新
    private void set(int root,int l,int r,int index,E e){
        if(l==r){
            tree[root]=e;
            return;
        }
        int mid=l+(r-l)/2;
        int leftTreeIndex = leftChild(root);
        int rightTreeIndex = rightChild(root);

        if(index>=mid+1)
            set(rightTreeIndex,mid+1,r,index,e);
        else
            set(leftTreeIndex,l,mid,index,e);

        tree[root]=merger.merge(tree[leftTreeIndex],tree[rightTreeIndex]);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append('[');
        for (int i = 0; i < tree.length; i++) {
            if (tree[i] != null)
                res.append(tree[i]);
            else
                res.append("null");
            if (i != tree.length - 1)
                res.append(", ");
        }
        res.append(']');
        return res.toString();
    }
}
