/**
 * created by Amal Mohan(axm179030),Esha Punjabi(EHP170000)
 */



package axm179030;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class DoublyLinkedList<T> extends SinglyLinkedList<T> {
    /**
     * Class Entry holds a single node of the list
     */
    static class Entry<E> extends SinglyLinkedList.Entry<E> {
        Entry<E> prev;

        Entry(E x, Entry<E> next, Entry<E> prev) {
            super(x, next);
            this.prev = prev;
        }
    }

    public DoublyLinkedList()
    {
        head=new Entry<>(null,null,null);
        tail=head;
    }


    public DoublyLinkedListIterator<T> dllIterator() { return new DLLIterator(); }

    //custom interface to hold all the methods of singly and doubly linked list
    public interface DoublyLinkedListIterator<T> {
        boolean hasNext();
        boolean hasPrev();
        T next();
        T prev();
        void add(T x);
        void remove();
    }

    //custom iterator class for doubly linked list
    protected class DLLIterator extends SLLIterator implements DoublyLinkedListIterator<T> {
        //check whether previous element of cursor exists or not
        public boolean hasPrev(){
            return prev!=head && prev!=null;
        }

        //return previous element of the cursor
        public T prev() {
            cursor= (Entry<T>) prev;
            prev=((Entry<T>) prev).prev;
            ready=true;
            return cursor.element;
        }

        // Remove can be called only if next or prev has been called and the element has not been removed
        //remove the element pointed by the cursor
        public void remove(){
            if(!ready){
                throw new NoSuchElementException();
            }
            if(cursor!=tail) {
                Entry<T> next = (Entry<T>) cursor.next;
                next.prev = (Entry<T>) prev;
            }
            super.remove();
            return;
        }

        //add an element before the element returned by cursor.next()
        public void add(Entry<T> x) {
            Entry<T> next= (Entry<T>) cursor.next;
            x.next=next;
            cursor.next=x;
            x.prev= (Entry<T>) cursor;
            prev=cursor;
            cursor=cursor.next;
            size++;
            ready=false;
            if(cursor.next==null) {
                tail=cursor;
                return;
            }
            next.prev=x;
        }

        //add an element before cursor.next()
        public void add(T x) {
            add(new Entry<>(x,null,null));
        }

    }//end of DLLIterator class


    //append an element at the end of the list
    public void add(T x) {
        add(new Entry<>(x, null,null));
    }

    //append an element at the end of the list
    public void add(Entry<T> ent)
    {
        ent.prev= (Entry<T>) tail;
        super.add(ent);
    }



    //driver
    public  static void main(String[] args) throws NoSuchElementException{
        int n=10;
        DoublyLinkedList<Integer> lst=new DoublyLinkedList<>();

        //append 1 to 10 in the list initially
        for(int i=1; i<=n; i++) {
            lst.add(Integer.valueOf(i));
        }

        lst.printList();

        DoublyLinkedListIterator<Integer> it = lst.dllIterator();

        Scanner in = new Scanner(System.in);

        whileloop:
        while(in.hasNext()) {
            int com = in.nextInt();
            switch(com) {
                case 1:  // Move to next element and print it
                    if (it.hasNext()) {
                        System.out.println(it.next());
                    } else {
                        break whileloop;
                    }
                    break;
                case 2:  // Remove element
                    it.remove();
                    lst.printList();
                    break;
                case 3://Move to previous element and print it
                    if (it.hasPrev()) {
                        System.out.println(it.prev());
                    } else {
                        break whileloop;
                    }
                    break;
                case 4: //add element at the position
                    //take an input from user and add it after the current element
                    Scanner in1 = new Scanner(System.in);
                    System.out.println("Add the element value:");
                    int num=in1.nextInt();
                    it.add(num);
                    lst.printList();
                    break;

                default:  // Exit loop
                    break whileloop;
            }
        }

    }

}



