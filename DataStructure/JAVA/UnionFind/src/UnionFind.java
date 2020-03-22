public class UnionFind implements UF {

    private int[] parent; // parent[i]表示第i个元素所指向的父节点

    // 构造函数
    public UnionFind(int size) {

        parent = new int[size];

        // 初始化, 每一个parent[i]指向自己, 表示每一个元素自己自成一个集合
        for (int i = 0; i < size; i++)
            parent[i] = i;

    }

    @Override
    public int getSize() {
        return parent.length;
    }

    // 查找过程, 查找元素p所对应的集合编号
    // O(h)复杂度, h为树的高度
    public int find(int x) {
        if (x < 0 || x >= parent.length)
            throw new IllegalArgumentException("p is out of bound.");

        if (parent[x] == x)
            return x;
        else
            return parent[x] = find(parent[x]);

    }

    // 查看元素p和元素q是否所属一个集合
    // O(h)复杂度, h为树的高度
    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    // 合并元素p和元素q所属的集合
    // O(h)复杂度, h为树的高度
    @Override
    public void unionElements(int p, int q) {

        int pRoot = find(p);
        int qRoot = find(q);

        if (pRoot == qRoot)
            return;
        else {
            parent[q] = parent[p];
        }

    }
}