package axm179030;

import java.util.Scanner;

public class BoundedStack<T>{

    private int size;
    private Object stack[];
    private int head;
    private int tail;

    BoundedStack(int size){
        this.size=size;
        stack=new Object[size];
        tail=-1;
    }

    public boolean push(T x){
        if((tail+1)==size){
            return false;
        }
        stack[++tail]=x;
        return true;
    }

    public T pop(){
        if(tail==-1){
            return null;
        }
        T element=(T)stack[tail];
        tail=tail-1;
        return element;
    }

    public void printStack(){
        if(tail==-1){
            return;
        }
        int counter=0;
        while(counter!=tail+1){
            System.out.print(stack[counter++]);
        }
    }

    public static void main(String args[]){
        Scanner in =new Scanner(System.in);
        BoundedStack<Integer> stack=new BoundedStack<>(5);
        whileloop:
        while (in.hasNext()){
            int x=in.nextInt();
            switch (x){
                case 1:
                    int element=in.nextInt();
                    boolean c=stack.push(element);
                    if(!c){
                        System.out.println("Full");
                    }
                    break;
                case 2:
                    int ele=stack.pop();
                    System.out.println(ele);
                    break;
                case 3: stack.printStack();
                    break ;
                    default:break whileloop;
            }

        }
    }


}
