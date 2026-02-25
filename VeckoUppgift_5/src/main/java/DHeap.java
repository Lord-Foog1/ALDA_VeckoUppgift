
public class DHeap<T extends Comparable<? super T>> {
    private static final int DEFAULT_CAPACITY = 10;
    private int currentSize;
    private T[] array;
    private int d;

    public DHeap() {
        this(2);
    }

    public DHeap(int d) {
        if(d < 2) {
            throw new IllegalArgumentException("d must be at least 2");
        }

        this.d = d;
        this.currentSize = 0;
        this.array = (T[]) new Comparable[DEFAULT_CAPACITY + 1];
    }

    public int parentIndex(int i) {
        if(i <= 1 ) throw new IllegalArgumentException();
        return (i + d - 2) / d;
    }

    public int firstChildIndex(int i) {
        if (i < 1) throw new IllegalArgumentException();
        return d * (i - 1) + 2;
    }

    public void insert(T x) {
        if (currentSize == array.length - 1)
            enlargeArray(array.length * 2 + 1);

        int hole = ++currentSize;
        // Percolate Up
        for (array[0] = x; hole > 1 && x.compareTo(array[parentIndex(hole)]) < 0; hole = parentIndex(hole)) {
            array[hole] = array[parentIndex(hole)];
        }
        array[hole] = x;
    }

    public T deleteMin() {
        if (isEmpty()) throw new RuntimeException("Underflow");

        T minItem = findMin();
        array[1] = array[currentSize--];
        percolateDown(1);

        return minItem;
    }

    private void percolateDown(int hole) {
        int child;
        T tmp = array[hole];

        for (; firstChildIndex(hole) <= currentSize; hole = child) {
            child = findBestChild(hole);
            if (array[child].compareTo(tmp) < 0) {
                array[hole] = array[child];
            } else {
                break;
            }
        }
        array[hole] = tmp;
    }

    private int findBestChild(int hole) {
        int firstChild = firstChildIndex(hole);
        int bestChild = firstChild;

        // Check all children (up to d children) and find the minimum
        for (int i = 1; i < d && (firstChild + i) <= currentSize; i++) {
            if (array[firstChild + i].compareTo(array[bestChild]) < 0) {
                bestChild = firstChild + i;
            }
        }
        return bestChild;
    }

    public int size() { return currentSize; }

    T get(int index) { return array[index]; }

    public T findMin() { return array[1]; }

    public boolean isEmpty() {
        return currentSize == 0;
    }

    @SuppressWarnings("unchecked")
    private void enlargeArray(int newSize) {
        T[] old = array;
        array = (T[]) new Comparable[newSize];
        for (int i = 0; i < old.length; i++) array[i] = old[i];
    }
}
