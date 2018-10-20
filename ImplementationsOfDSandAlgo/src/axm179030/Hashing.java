package axm179030;

import java.util.ArrayList;
import java.util.HashSet;

enum Type{
    CUCKOO,HOPSCOTCH;
}

interface HashCodeGenerator{
    public int hashCode2(Object x);
    public int hashCode(Object x);
}



public class Hashing<T> implements HashCodeGenerator {

    private Object[][] table;
    private int size=256;
    private int load=0;
    private int threshold=size/2;
    private int k=2;

    Hashing(){
        table=new Object[256][2];
    }

    void rehash(){
        size=size*2;
        Object[][] temp=new Object[size][2];
        for(int i=0;i<size/2;i++)
        {
            for (int j=0;j<2;j++)
            {

                    temp[i][j]=table[i][j];
            }
        }
        table=temp;
        threshold=size/2;
    }

    public boolean place(T x,int hash,int pos)
    {
        if(table[hash][pos]==null){
            table[hash][pos]=x;
            return true;
        }
        return false;
    }

    public T exchange(T x,int hash,int pos){
        T temp=(T)table[hash][0];
        table[hash][0]=table[hash][1];
        table[hash][1]=x;
        return temp;
    }

    public void add(T x){
        if(load>=size/2){
            rehash();
        }
        int count=0,i=1;
        while (count++<threshold){
            int hash=hashI(x,i);
            if(place(x,hash,0)||place(x,hash,1))
            {
                load++;
                return;
            }
            else {
                x=exchange(x,hash,0);
            }
            i=i==k?1:i+1;
        }
    }

    public int hashI(T x,int i){
        return i==1?hashCode(x):hashCode2(x);
    }

    public int hashCode(Object x){
        return (hash(x.hashCode()) & size-1);
    }

    static int hash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }
    static int indexFor(int h, int length) {      // length = table.length is a power of 2
        return h & (length-1);
    }

    public int hashCode2(Object x){
        return this.hashCode(x);
    }

    public T get(int x,int y){
        return (T)table[x][y];
    }

    public boolean contains(T x){
            if(contains(x,0)!=-1||contains(x,1)!=-1){
                return true;
        }
        return false;
    }

    public int contains(T x,int pos){
        for(int i=1;i<=k;i++) {
            if (get(i, pos).equals(x)) {
                return i;
            }
        }
        return -1;
    }

    public T remove(T x){
        for(int i=0;i<2;i++){
            int hash=contains(x,i);
            if(hash!=-1){
                T result= (T) table[hash][i];
                table[hash][i]=null;
                return result;
            }
        }
        return null;
    }

    public static void main(String[] args){
        Hashing<Boolean> s=new Hashing<>();
        s.add(true);
        s.add(true);
        s.remove(true);
  //          s.rehash();
    }
}
