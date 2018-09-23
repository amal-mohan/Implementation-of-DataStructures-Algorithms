/**
 * Author: Amal Mohan - axm179030
 * 	Yash Ajay Madane - yxm172130
 *
 * The main() method in BinaryHeap.java is the driver for the program
 * The user can perform the following operations on the queue using the following keys:
 * 	a. Press 1 to add an element
 *         b. Press 2 to offer element
 *         c. Press 3 to remove element
 *         d. Press 4 to poll element
 *         e. Press 5 to peek element
 */

// Starter code for bounded-size Binary Heap implementation
// Changed signature of heapSort changing <T> to <T extends Comparable<? super T>>
// poll returns null if pq is empty (not false)

package axm179030;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;

public class BinaryHeap<T extends Comparable<? super T>> {
    T[] pq;
    Comparator<T> comp;
    private int size;

    // Constructor for building an empty priority queue using natural ordering of T
    public BinaryHeap(T[] q) {
        // Use a lambda expression to create comparator from compareTo
        this(q, (T a, T b) -> a.compareTo(b));
    }

    // Constructor for building an empty priority queue with custom comparator
    public BinaryHeap(T[] q, Comparator<T> c) {
        pq = q;
        size = 0;        //Size variable used to manage the length of the priority queue.
        comp = c;
    }

    /**
     * Build a priority queue with a given array q, using q[0..n-1].
     * It is not necessary that n == q.length.
     * Extra space available can be used to add new elements.
     * Implement this if solving optional problem.  To be called from heap sort.
     */
    private BinaryHeap(T[] q, Comparator<T> c, int n) {
        pq = q;
        comp = c;
        // You need to add more code here to build queue
    }

    /**a
     * Add function to add a new element to the priority queue
     * The new element is added to the end of the array and percolate up is called to maintain heap condition
     * @param x
     */
    public void add(T x) { /* throw exception if pq is full */
        if (pq.length == size) {
            throw new ArrayIndexOutOfBoundsException();
        }
        pq[size] = x;
        percolateUp(size);
        size++;
    }

    /**
     * performs the same operation as add function
     * returns false if priority queue is full
     * @param x
     * @return boolean
     */
    public boolean offer(T x) { /* return false if pq is full */
        if (pq.length == size) {
            return false;
        }
        pq[size] = x;
        percolateUp(size);
        size++;
        return true;
    }

    /**
     * removes and returns root element at priority queue(element with minimum priority
     * The priority queue is readjusted to maintain heap property
     * @return
     */
    public T remove() { /* throw exception if pq is empty */
        if (size == 0) {
            throw new IllegalStateException();
        }
        T min = pq[0];
        pq[0] = pq[size - 1];
        size--;
        percolateDown(0);
        return min;
    }

    /**
     * Implements the same functionality as remove
     * returns null if size is zero
     * @return
     */
    public T poll() { /* return null if pq is empty */
        if (size == 0) {
            return null;
        }
        T min = pq[0];
        pq[0] = pq[size - 1];
        size--;
        percolateDown(0);
        return min;
    }

    /**
     * returns the value with highest priority
     * @return
     */
    public T peek() { /* return null if pq is empty */
        if (size == 0) {
            return null;
        }
        return pq[0];
    }

    /**
     * percolate up checks the moves the element from a location upward till heap condition is achieved
     * pq[i] may violate heap order with parent
     */
    void percolateUp(int i) { /* to be implemented */
        T x = pq[i];
        //compare element and parent and moves parent down if it is larger
        while (comp.compare(x, pq[parent(i)]) == -1 && i > 0) {
            pq[i] = pq[parent(i)];
            i = parent(i);
        }
        pq[i] = x;
    }

    /**
     * percolate down moves the element down till the heap condition is achieved
     * pq[i] may violate heap order with children
     */
    void percolateDown(int i) { /* to be implemented */
        T x = pq[i];
        int c = 2 * i + 1;
        while (c <= size - 1) {
            //choose smaller of two children
            if (c < size - 1 && comp.compare(pq[c], pq[c + 1]) > 1) {
                c = c + 1;
            }
            //compare the child with the parent and swap if parent is greater
            if (comp.compare(x, pq[c]) == -1) {
                break;
            }
            pq[i] = pq[c];
            i = c;
            c = 2 * i + 1;
        }
        pq[i] = x;
    }

    // Assign x to pq[i].  Indexed heap will override this method
    void move(int i, T x) {
        pq[i] = x;
    }

    int parent(int i) {
        return (i - 1) / 2;
    }

    int leftChild(int i) {
        return 2 * i + 1;
    }

// end of functions for team project


// start of optional problem: heap sort (Q2)

    /**
     * Create a heap.  Precondition: none.
     * Implement this ifsolving optional problem
     */
    void buildHeap() {
    }

    /* sort array arr[].
       Sorted order depends on comparator used to buid heap.
       min heap ==> descending order
       max heap ==> ascending order
       Implement this for optional problem
     */
    public static <T extends Comparable<? super T>> void heapSort(T[] arr, Comparator<T> c) { /* to be implemented */
    }

    // Sort array using natural ordering
    public static <T extends Comparable<? super T>> void heapSort(T[] arr) {
        heapSort(arr, (T a, T b) -> a.compareTo(b));
    }
// end of optional problem: heap sort (Q2)


// start of optional problem: kth largest element (Q3)

    public void replace(T x) {
	/* TO DO.  Replaces root of binary heap by x if x has higher priority
	     (smaller) than root, and restore heap order.  Otherwise do nothing.
	   This operation is used in finding largest k elements in a stream.
	   Implement this if solving optional problem.
	 */
    }

    /**
     * Return the kth largest element of stream using custom comparator.
     * Assume that k is small enough to fit in memory, but the stream is arbitrarily large.
     * If stream has less than k elements return null.
     */
    public static <T extends Comparable<? super T>> T kthLargest(Iterator<T> stream, int k, Comparator<T> c) {
        return null;
    }

    /**
     * Return the kth largest element of stream using natural ordering.
     * Assume that k is small enough to fit in memory, but the stream is arbitrarily large.
     * If stream has less than k elements return null.
     */
    public static <T extends Comparable<? super T>> T kthLargest(Iterator<T> stream, int k) {
        return kthLargest(stream, k, (T a, T b) -> a.compareTo(b));
    }

    // end of optional problem: kth largest element (Q3)
    public static void main(String[] args) throws IllegalStateException,ArrayIndexOutOfBoundsException{
        Integer[] int_arr = new Integer[10];
        BinaryHeap<Integer> bh = new BinaryHeap<Integer>(int_arr);
        System.out.println("1. Add Element");
        System.out.println("2. Offer element");
        System.out.println("3. Remove Element");
        System.out.println("4. Poll Element");
        System.out.println("5. Peek Element");

        Scanner in = new Scanner(System.in);

        whileloop:
        while (in.hasNext()) {
            int com = in.nextInt();
            switch (com) {
                case 1:  // Move to next element and print it
                    Scanner in1 = new Scanner(System.in);
                    System.out.println("Provide the element value:");
                    int num = in1.nextInt();
                    bh.add(num);
                    break;
                case 2:
                    Scanner in2 = new Scanner(System.in);
                    System.out.println("Provide the element value:");
                    int num2 = in2.nextInt();
                    bh.add(num2);
                    break;
                case 3:
                    Integer x = bh.remove();
                    System.out.println("Removed element is: "+x);
                    break;
                case 4:
                    Integer x1 = bh.poll();
                    System.out.println("Removed element is: "+x1);
                    break;
                case 5:
                    Integer top = bh.peek();
                    System.out.println("Element with highest priority is: "+top);
                    break;

                default:
                    break whileloop;
            }
        }
    }
}
