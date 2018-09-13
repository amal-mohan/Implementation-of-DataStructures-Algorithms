/** @author Amal Mohan - axm179030, Yash Ajay Madane - yxm172130
 * The main() method in BoundedQueue.java is the driver for the program
 * The user can perform the following operations on the queue using the following keys:
 *      a. Press '1' to offer an element to the queue
 *      b. Press '2' to poll an element from the queue
 *      c. Press '3' to peek an element from the front of queue
 *      d. Press '4' to display size of the queue
 *      e. Press '5' to check if queue is empty
 *      f. Press '6' to clear the queue
 *      g. Press '7' to convert queue to an array and display its contents
*/

package axm179030;

import java.util.Scanner;

public class BoundedQueue<T> {

    private Object[] queue;
    private int size;
    private int head;
    private int tail;

    //creates queue of Size: size
    // head and tail is -1 which indicates queue is empty
    BoundedQueue(int size){
        queue=new Object[size];
        this.size=size;
        head=-1;
        tail=-1;
    }

    //adds element to queue
    //returns false if tail is behind head(queue is full)
    boolean offer(T x){
        if((tail+1)%size==head){
            return false;
        }
        if(tail==-1){
            head++;
        }
        tail=(tail+1)%size;
        queue[tail]=x;
        return true;
    }

    //removes and returns element at the front of the queue
    //returns null if queue is empty(tail=-1)
    //if queue becomes empty after this operation, head and tail is reset to -1
    T poll(){
        if(tail==-1){
            return null;
        }
        T x=(T)queue[head];
        if(head==tail){
            head=-1;
            tail=-1;
        }
        else{
            head=(head+1)%size;
        }
        return x;
    }

    //returns element at the front of the queue
    T peek(){
        if(tail==-1){
            return null;
        }
        return (T)queue[head];
    }

    //returns size of the queue
    int size(){
        if(tail==-1){
            return 0;
        }
        if(tail>=head){
            return (tail-head)+1;
        }
        else{
            return (size-head)+tail+1;
        }
    }

    //checks if queue is empty or not
    boolean isEmpty(){
        return tail==-1;
    }

    //clears the queue
    //sets head and tail to -1
    void clear(){
        tail=-1;
        head=-1;
    }

    //converts queue to array
    //throws array out of bound exception if queue size is greater than array size
    void toArray(T[] a){
        if(tail==-1){
            return;
        }
        int queueSize=this.size();
        if(a.length<queueSize){
            throw new ArrayIndexOutOfBoundsException();
        }
        int counter=head;
        int i=0;
        while(counter%size!=tail){
            a[i++]=(T)queue[counter++];
        }
        a[i]=(T)queue[tail];
    }


    public static void main(String args[]) throws ArrayIndexOutOfBoundsException{
        BoundedQueue<Integer>  elements=new BoundedQueue<>(10);
        Scanner in = new Scanner(System.in);
        System.out.println("1. Offer element");
        System.out.println("2. Pop Element");
        System.out.println("3. Peek Element");
        System.out.println("4. Size of queue");
        System.out.println("5. Is empty");
        System.out.println("6. Clear");
        System.out.println("6. To Array");
        System.out.println("Enter Choice: ");

        whileloop:
        while(in.hasNext()){
            int input=in.nextInt();
            switch (input){
                case 1:
                    System.out.println("Enter an element");
                    if(in.hasNext()){
                        int element=in.nextInt();
                        boolean offered=elements.offer(element);
                        if(!offered){
                            System.out.println("Element could not be added");
                        }
                    }
                    break;
                case 2:
                    int element=elements.poll();
                    System.out.println("Popped element: "+element);
                    break;
                case 3:
                    int frontQueue=elements.peek();
                    System.out.println("Front of Queue: "+frontQueue);
                    break;
                case 4:
                    System.out.println("Queue Size: "+elements.size());
                    break;
                case 5:
                    boolean queueEmpty=elements.isEmpty();
                    if(queueEmpty){
                        System.out.println("Queue is empty");
                    }
                    else {
                        System.out.println("Queue not empty");
                    }
                    break;
                case 6:
                    elements.clear();
                    break ;
                case 7:
                    Integer []queueArray=new Integer[10];
                    elements.toArray(queueArray);
                    for(int i=0;i<queueArray.length;i++){
                        System.out.println(queueArray[i]);
                    }
                    break;
                    default:break whileloop;
            }
        }
    }
}
