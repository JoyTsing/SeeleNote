import java.util.Objects;

//封装一个关于JAVA中数组的类 具有增删改查的功能
public class Array<E> {

    private E[] data;
    private int size;

    public Array(int capacity) {
        data = (E[]) new Object[capacity];
        size = 0;
    }

    public  Array(E[] arr){
        data=(E[])new Object[arr.length];
        for (int i = 0; i <arr.length ; i++) {
            data[i]=arr[i];
        }
        size=arr.length;
    }

    public Array() {
        this(10);
    }

    public int getSize() {
        return size;
    }

    public int getCapacity() {
        return data.length;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addLast(E e) {
        add(size, e);
    }

    public void addFirst(E e) {
        add(0, e);
    }

    public void add(int index, E e) {

        if (index < 0 || index > size)
            throw new IllegalArgumentException("Add failed");

        if (size == data.length)
            resize(2 * data.length);

        for (int i = size - 1; i >= index; i--)
            data[i + 1] = data[i];

        data[index] = e;
        size++;
    }

    public E get(int index) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Get failed.Index is illegal");
        return data[index];
    }

    public E getLast() {
        return get(size - 1);
    }

    public E getFirst() {
        return get(0);
    }

    public void set(int index, E e) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Set failed.Index is illegal");
        data[index] = e;
    }

    public boolean contains(E e) {
        for (int i = 1; i < size; i++)
            if (data[i].equals(e))
                return true;
        return false;
    }

    public E remove(int index) {
        if (index < 0 || index >= size)
            throw new IllegalArgumentException("Remove failed.Index is illegal");
        E ret = data[index];
        for (int i = index + 1; i < size; i++) {
            data[i - 1] = data[i];
        }
        size--;
        data[size] = null;

        if (size == data.length / 4 && data.length != 2)
            resize(data.length / 2);
        return ret;
    }

    public E removeFirst() {
        return remove(0);
    }

    public E removeLast() {
        return remove(size - 1);
    }

    public void removeElement(E e) {
        int index = find(e);
        if (index != -1)
            remove(index);
    }

    public int find(E e) {
        for (int i = 0; i < size; i++)
            if (data[i].equals(e))
                return i;

        return -1;
    }

    public void swap(int i,int j){
        if(i<0||i>=size ||j<0||j>=size)
            throw new IllegalArgumentException("Index Error");

        E t=data[i];
        data[i]=data[j];
        data[j]=t;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(String.format("Array:size=%d , capacity=%d\n", size, data.length));
        res.append('[');

        for (int i = 0; i < size; i++) {
            res.append(data[i]);
            if (i != size - 1)
                res.append(", ");
        }

        res.append(']');
        return res.toString();
    }

    private void resize(int newCapacity) {
        E[] newData = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData;

    }
}
