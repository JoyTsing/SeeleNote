public class MaxHeap<E extends Comparable<E>> {
    private Array<E> data;

    public MaxHeap(int capacity) {
        data = new Array<>(capacity);
    }

    public MaxHeap(E[] arr){
         data=new Array<>(arr);
        for (int i = parent(arr.length-1); i >=0 ; i--) {
            shiftDown(i);
        }
    }

    public MaxHeap() {
        data = new Array<>();
    }

    public int size() {
        return data.getSize();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    private int parent(int index) {
        if (index <= 0)
            throw new IllegalArgumentException("Index ERROR");
        return (index - 1) / 2;
    }

    private int leftChild(int index) {
        return index * 2 + 1;
    }

    private int rightChild(int index) {
        return index * 2 + 2;
    }

    public void add(E e) {
        data.addLast(e);
        shiftUp(data.getSize() - 1);
    }

    private void shiftUp(int k) {
        while (k > 0 && data.get(k).compareTo(data.get(parent(k))) > 0) {
            data.swap(k, parent(k));
            k = parent(k);
        }
    }

    public E peek() {
        if (isEmpty())
            throw new IllegalArgumentException("Empty");
        return data.get(0);
    }

    public E extractMax() {
        E ret = peek();
        data.swap(0, data.getSize() - 1);
        data.removeLast();
        shiftDown(0);
        return ret;
    }

    private void shiftDown(int index) {

        while (leftChild(index) < data.getSize()) {
            int j = leftChild(index);
            if (j + 1 < data.getSize() &&
                    data.get(j + 1).compareTo(data.get(j))>0)
                j++;

            if(data.get(index).compareTo(data.get(j))>=0)
                break;

            data.swap(index,j);
            index=j;
        }
    }

    //将顶部元素换为输入的元素
    public E replace(E e){
        E ret=peek();
        data.set(0,e);
        shiftDown(0);
        return ret;
    }


}
