package axm179030;

/** @author
 *  Binary search tree (starter code)
 **/

import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;


public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T> {
    static class Entry<T> {
        T element;
        Entry<T> left, right;

        public Entry(T x, Entry<T> left, Entry<T> right) {
            this.element = x;
            this.left = left;
            this.right = right;
        }
    }
    Stack <Entry> parent_stack = new Stack<>();

    Entry<T> root;
    int size;

    public BinarySearchTree() {
        root = null;
        size = 0;
    }


    /** TO DO: Is x contained in tree?
     */
    public boolean contains(T x) {
        Entry<T> node = find(x);
        if(node == null || node.element.compareTo(x)!=0)
            return false;
        else
            return true;
    }

    /** TO DO: Is there an element that is equal to x in the tree?
     *  Element in tree that is equal to x is returned, null otherwise.
     */
    public T get(T x) {
        Entry<T> node = find(x);
        if(node.element.compareTo(x)!=0)
            return null;
        else
            return (T)node.element;
    }

    /** TO DO: Add x to tree.
     *  If tree contains a node with same key, replace element by x.
     *  Returns true if x is a new element added to tree.
     */
    public boolean add(T x) {
        if(size == 0)
        {
            root = new Entry<>(x,null,null);
            size = 1;
            return true;

        }
        else
        {
            Entry<T> node=find(x);
            if(node.element.compareTo(x) == 0) {
                node.element = x;
                return false;
            } else if(x.compareTo(node.element) == -1)
            {
                node.left = new Entry(x,null,null);

            }
            else {
                node.right = new Entry(x,null,null);

            }
            size ++;
        }




        return true;
    }

    /** TO DO: Remove x from tree.
     *  Return x if found, otherwise return null
     */
    public T remove(T x) {
        if(root == null)
            return null;
        else{
            Entry<T> node = find(x);
            if(node.element.compareTo(x)!=0)
                return null;
            T result = (T)node.element;
            if(node.left == null || node.right == null)

                bypass(node);

            else {
                parent_stack.push(node);
                Entry min_right = find(node.right,x);
                node.element = (T)min_right.element;
                bypass(min_right);
                size--;


            }
            return result;
        }
    }

    public T min() {
        if(size == 0)
            return null;
        Entry node = root;
        while(node.left!=null){
            node = node.left;
        }
        return (T)node.element;
    }

    public T max() {
        if(size == 0)
            return null;
        Entry node = root;
        while(node.right!=null){
            node = node.right;
        }
        return (T)node.element;
    }

    public Entry<T> find(T x){
        parent_stack.push(null);
        return find(root,x);

    }
    public void bypass(Entry<T> node)
    {
        Entry parent = parent_stack.peek();
        Entry child = node.left == null ? node.right : node.left;
        if (parent == null)
            root = child;
        else if(parent.left == node)
        {
            parent.left = child;
        }
        else
            parent.right = child;
    }

    public Entry<T> find(Entry<T> node, T x)
    {
        if(node == null || node.element ==x)
            return node;
        while(true)
        {
            if(x.compareTo(node.element) == -1)
            {
                if(node.left == null)
                    break;
                else {
                    parent_stack.push(node);
                    node = node.left;
                }

            }
            else if(x.compareTo(node.element)==0)
                break;
            else{
                if(node.right == null)
                    break;
                else {
                    parent_stack.push(node);
                    node = node.right;
                }
            }
        }
        return node;

    }

    // TODO: Create an array with the elements using in-order traversal of tree
    public Comparable[] toArray() {
        Stack<Entry> nodes = new Stack<>();
        Comparable[] arr = new Comparable[size];
        if(root == null)
            return arr;
        else
        {
            int i =0;
            Entry cur = root;

            while (cur != null || nodes.size() > 0)
            {
                while (cur !=  null)
                {

                    nodes.push(cur);
                    cur = cur.left;
                }

                cur = nodes.pop();
                arr[i++] = (T)cur.element;



                cur = cur.right;
            }
        }
        return arr;
    }



// Start of Optional problem 2

    /** Optional problem 2: Iterate elements in sorted order of keys
     Solve this problem without creating an array using in-order traversal (toArray()).
     */
    public Iterator<T> iterator() {
        return null;
    }

    // Optional problem 2.  Find largest key that is no bigger than x.  Return null if there is no such key.
    public T floor(T x) {
        return null;
    }

    // Optional problem 2.  Find smallest key that is no smaller than x.  Return null if there is no such key.
    public T ceiling(T x) {
        return null;
    }

    // Optional problem 2.  Find predecessor of x.  If x is not in the tree, return floor(x).  Return null if there is no such key.
    public T predecessor(T x) {
        return null;
    }

    // Optional problem 2.  Find successor of x.  If x is not in the tree, return ceiling(x).  Return null if there is no such key.
    public T successor(T x) {
        return null;
    }

// End of Optional problem 2

    public static void main(String[] args) {
        BinarySearchTree<Integer> t = new BinarySearchTree<>();
        Scanner in = new Scanner(System.in);
        while(in.hasNext()) {
            int x = in.nextInt();
            if(x > 0) {
                System.out.print("Add " + x + " : ");
                t.add(x);
                t.printTree();
            } else if(x < 0) {
                System.out.print("Remove " + x + " : ");
                t.remove(-x);
                t.printTree();
            } else {
                Comparable[] arr = t.toArray();
                System.out.print("Final: ");
                for(int i=0; i<t.size; i++) {
                    System.out.print(arr[i] + " ");
                }
                System.out.println();
                return;
            }
        }
    }


    public void printTree() {
        System.out.print("[" + size + "]");
        printTree(root);
        System.out.println();
    }

    // Inorder traversal of tree
    void printTree(Entry<T> node) {
        if(node != null) {
            printTree(node.left);
            System.out.print(" " + node.element);
            printTree(node.right);
        }
    }

}
/*
Sample input:
1 3 5 7 9 2 4 6 8 10 -3 -6 -3 0

Output:
Add 1 : [1] 1
Add 3 : [2] 1 3
Add 5 : [3] 1 3 5
Add 7 : [4] 1 3 5 7
Add 9 : [5] 1 3 5 7 9
Add 2 : [6] 1 2 3 5 7 9
Add 4 : [7] 1 2 3 4 5 7 9
Add 6 : [8] 1 2 3 4 5 6 7 9
Add 8 : [9] 1 2 3 4 5 6 7 8 9
Add 10 : [10] 1 2 3 4 5 6 7 8 9 10
Remove -3 : [9] 1 2 4 5 6 7 8 9 10
Remove -6 : [8] 1 2 4 5 7 8 9 10
Remove -3 : [8] 1 2 4 5 7 8 9 10
Final: 1 2 4 5 7 8 9 10

*/