import java.util.ArrayList;

public class AVLTree<K extends Comparable<K>, V> {

    private class Node {
        public K key;
        public V value;
        public Node left, right;

        public int height;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            height = 1;
            left = null;
            right = null;
        }
    }

    private Node root;
    private int size;

    public AVLTree() {
        root = null;
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    //判断是否是二分搜索树
    public boolean isBST() {
        ArrayList<K> keys = new ArrayList<>();
        //中序遍历出来的结果是顺序的
        inOrder(root, keys);
        for (int i = 1; i < keys.size(); i++) {
            if (keys.get(i - 1).compareTo(keys.get(i)) > 0)
                return false;
        }
        return true;
    }

    private void inOrder(Node root, ArrayList<K> keys) {
        if (root == null)
            return;
        inOrder(root.left, keys);
        keys.add(root.key);
        inOrder(root.right, keys);
    }


    public boolean isBalanced() {
        return isBalanced(root);
    }


    /**
     * @param node
     * @return 判断是否平衡
     */
    private boolean isBalanced(Node node) {
        if (node == null)
            return true;

        int balanceFactor = getBalanceFactor(node);
        if (Math.abs(balanceFactor) > 1)
            return false;
        return isBalanced(node.left) && isBalanced(node.right);
    }

    private int getHeight(Node node) {
        if (node == null)
            return 0;
        return node.height;
    }

    private int getBalanceFactor(Node node) {
        if (node == null)
            return 0;
        return getHeight(node.left) - getHeight(node.right);
    }

    // 向二分搜索树中添加新的元素(key, value)
    public void add(K key, V value) {
        root = add(root, key, value);
    }

    // 向以node为根的二分搜索树中插入元素(key, value)，递归算法
    // 返回插入新节点后二分搜索树的根
    // 加入结点后沿着节点向上维护平衡性,注意是递归调用的
    private Node add(Node node, K key, V value) {

        if (node == null) {
            size++;
            return new Node(key, value);
        }

        if (key.compareTo(node.key) < 0)
            node.left = add(node.left, key, value);
        else if (key.compareTo(node.key) > 0)
            node.right = add(node.right, key, value);
        else // key.compareTo(node.key) == 0
            node.value = value;

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        int balanceFactor = getBalanceFactor(node);
        //LL
        if (balanceFactor > 1 && getBalanceFactor(node.left) > 0)
            return rightRotate(node);
        //RR
        if (balanceFactor < -1 && getBalanceFactor(node.right) < 0)
            return leftRotate(node);
        //LR
        if (balanceFactor > 1 && getBalanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        //RL
        if (balanceFactor < -1 && getBalanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }


        return node;
    }

    private Node rightRotate(Node node) {
        Node leftChild = node.left;
        node.left = leftChild.right;
        leftChild.right = node;

        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        leftChild.height = Math.max(getHeight(leftChild.left), getHeight(leftChild.right)) + 1;

        return leftChild;
    }

    private Node leftRotate(Node node) {
        Node rightChild = node.right;
        node.right = rightChild.left;
        rightChild.left = node;

        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
        rightChild.height = Math.max(getHeight(rightChild.left), getHeight(rightChild.right)) + 1;

        return rightChild;
    }

    // 返回以node为根节点的二分搜索树中，key所在的节点
    private Node getNode(Node node, K key) {

        if (node == null)
            return null;

        if (key.equals(node.key))
            return node;
        else if (key.compareTo(node.key) < 0)
            return getNode(node.left, key);
        else // if(key.compareTo(node.key) > 0)
            return getNode(node.right, key);
    }

    public boolean contains(K key) {
        return getNode(root, key) != null;
    }

    public V get(K key) {

        Node node = getNode(root, key);
        return node == null ? null : node.value;
    }

    public void set(K key, V newValue) {
        Node node = getNode(root, key);
        if (node == null)
            throw new IllegalArgumentException(key + " doesn't exist!");

        node.value = newValue;
    }

    // 返回以node为根的二分搜索树的最小值所在的节点
    private Node minimum(Node node) {
        if (node.left == null)
            return node;
        return minimum(node.left);
    }

    // 从二分搜索树中删除键为key的节点
    public V remove(K key) {

        Node node = getNode(root, key);
        if (node != null) {
            root = remove(root, key);
            return node.value;
        }
        return null;
    }

    private Node remove(Node node, K key) {

        if (node == null)
            return null;

        Node retNode;

        if (key.compareTo(node.key) < 0) {
            node.left = remove(node.left, key);
            retNode = node;
        } else if (key.compareTo(node.key) > 0) {
            node.right = remove(node.right, key);
            retNode = node;
        } else {   // key.compareTo(node.key) == 0

            // 待删除节点左子树为空的情况
            if (node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                size--;
                retNode = rightNode;
            }

            // 待删除节点右子树为空的情况
            else if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                size--;
                retNode = leftNode;
            } else {

                // 待删除节点左右子树均不为空的情况

                // 找到比待删除节点大的最小节点, 即待删除节点右子树的最小节点
                // 用这个节点顶替待删除节点的位置
                Node successor = minimum(node.right);
                successor.right = remove(node.right, successor.key);
                successor.left = node.left;

                node.left = node.right = null;

                retNode = successor;
            }

        }

        if (retNode == null)
            return null;

        //维护
        retNode.height = 1 + Math.max(getHeight(retNode.left), getHeight(retNode.right));

        int balanceFactor = getBalanceFactor(retNode);

        /**
         * 这里需要判断平衡因子等于0时候的情况
         * 因为刚刚删除的时候并未更新平衡因子
         * 就存在被删除后平衡被打破的情况
         * 两个等于0的情况画一下就可以了
         * LL为例，删除的节点为右子树的节点
         * 删除后经过旋转树的高度恢复为原来的高度
         */

        //LL
        if (balanceFactor > 1 && getBalanceFactor(retNode.left) >= 0)
            return rightRotate(retNode);
        //RR
        if (balanceFactor < -1 && getBalanceFactor(retNode.right) <= 0)
            return leftRotate(retNode);
        //LR
        if (balanceFactor > 1 && getBalanceFactor(retNode.left) < 0) {
            retNode.left = leftRotate(retNode.left);
            return rightRotate(retNode);
        }
        //RL
        if (balanceFactor < -1 && getBalanceFactor(retNode.right) > 0) {
            retNode.right = rightRotate(retNode.right);
            return leftRotate(retNode);
        }

        return retNode;
    }

    public static void main(String[] args) {
        System.out.println("Pride and Prejudice");

        ArrayList<String> words = new ArrayList<>();
        if (FileOperation.readFile("pride-and-prejudice.txt", words)) {
            System.out.println("Total words: " + words.size());

            AVLTree<String, Integer> map = new AVLTree<>();
            for (String word : words) {
                if (map.contains(word))
                    map.set(word, map.get(word) + 1);
                else
                    map.add(word, 1);
            }

            System.out.println("Total different words: " + map.getSize());
            System.out.println("Frequency of PRIDE: " + map.get("pride"));
            System.out.println("Frequency of PREJUDICE: " + map.get("prejudice"));

            System.out.println("is BST : " + map.isBST());
            System.out.println("is Balanced : " + map.isBalanced());

            for (String word : words) {
                map.remove(word);
                if (!map.isBST() || !map.isBalanced())
                    throw new RuntimeException("Error");
            }
        }

        System.out.println();

    }
}