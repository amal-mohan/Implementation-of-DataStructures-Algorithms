/**
 * Author: Amal Mohan - axm179030
 * 	Anki Chauhan - axc170043
 *
 * -CuckooHashing is an implementation of HashSet which performs the following operations:
 * * add: adds an element to the hashset
 * * remove: removes an element from the hashset
 * * contains: checks if element is present in the hashset
 *
 */


package axm179030;

/**
 * Cuckoo Hashing class which does hashing of elements into a n X 2 array
 * An implementation of hashset
 * @param <T>
 */
public class CuckooHashing<T> {
    private Object[] table;//array storing the hashed values
    private int size=16;//initial size is set to 16 which increases when load is more than 50 %
    private int load=0;//keeps track of current load in the table
    private int threshold=size*2;//manages the number of iterations to check to add before stopping
    private int k=2;

    /**
     *constructor initializes the table with size
     */
    CuckooHashing(){
        table=new Object[size];
    }

    /**
     * When load becomes equal to 50% of size the hash table is resized to prevent collisions
     */
    void rehash(){
        size=size*2;
        Object[] temp=new Object[size]; //all elements are copied to a temp array
        for(int i=0;i<size/2;i++) {
            temp[i]=(T)table[i];
        }
        table=new Object[size]; //table is resized
        load=0;
        threshold=size*2;
        //elements copied to temp array are added again to hash table
        for(int i=0;i<size/2;i++) {
            if(temp[i]!=null)
                add((T)temp[i]);
        }
    }

    /**
     * helper function to place a value to hash table
     * @param x the value to be placed into hash table
     * @param hash the hash value of x which is basically index where the element is placed
     * @param pos the location 0/1 since it is a two place hash table
     * @return the status of plcaement of element
     */
    public boolean place(T x,int hash) {
        if(get(hash)==null){
            table[hash]=x;
            return true;
        }
        return false;
    }

    /**
     * Helper function to exchange values of hash table and input.
     * @param x the value to be exchanged into hash table
     * @param hash the hash value of x which is basically index where the element is placed
     * @return element exchanged with hash
     */
    public T exchange(T x,int hash){
        T temp= (T)table[hash];
        table[hash]=x;
        return temp;
    }

    /**
     * Function to add new elements to the hash table
     * @param x element to be added to the hash
     * @return status of addition of element
     */
    public boolean add(T x){
        if(contains(x)){
            return false;
        }

        //rehash if load is greater than  0.5
        if(load>=(int)(size*0.5)){
            rehash();
        }

        int count=0,i=1;//count to manage the threshold
        Element<T> object;//used to convert input element to element class to get hash codes
        while (count++<threshold){
            object=new Element<>(x);
            int hash=hashI(object,i); //calculate hash code 1 or 2
            if(place(x,hash)) {
                load++;
                return true; //return true if element is placed
            }
            else {
                x=exchange(x,hash); //exchange elements if the location is already occupied
            }
            i=i==k?1:i+1;
        }
        return false; //return false if element could not be placed
    }

    /**
     * Calculates the hash to place a new element to hash table
     * @param x the element to placed to the hash table
     * @param i indicator of which hash function to use
     * @return
     */
    public int hashI(Element x,int i){
        return i==1?indexFor(x.hashCode()):indexFor(x.hashCode2());
    }


    /**
     * is a method to constraint hash to array size
     * @param value value whose size is to be restricted
     * @return returns hash constrained to array size
     */
    public int indexFor(int value){
        return (hash(value) & size-1);
    }

    /**
     * standard method in java to calculate hash of an input
     * @param h value whose hash is to be calculated
     * @return hash of the input value
     */
    static int hash(int h) {
        h ^= (h >>> 20) ^ (h >>> 12);
        return h ^ (h >>> 7) ^ (h >>> 4);
    }

    /**
     * get the element in the hash table
     * @param x position in table
     * @param y 0/1
     * @return entry in hash table
     */
    public T get(int x){
        return (T)table[x];
    }

    /**
     * checks if x is present in hashtable
     * @param x element to be checked
     * @return true if element is present
     */
    public boolean contains(T x){
        Element<T> object=new Element<>(x);
        if(contains(object)!=-1){
            return true;
        }
        return false;
    }

    /**
     * helper function to find location of the element in the hash table
     * @param x element to be found
     * @param pos 0/1
     * @return location of element if found
     */
    public int contains(Element x){
        for(int i=1;i<=k;i++) {
            int hash=hashI(x,i);
            T res=get(hash);
            if (res!=null&&res.equals(x.getElement())) {
                return hash;
            }
        }
        return -1;
    }

    /**
     * removes an element from hash table
     * @param x element to be removed
     * @return the removed element is returned
     */
    public T remove(T x){
        Element<T> object=new Element<>(x);
        int hash=contains(object);
        if(hash!=-1){
            T result= (T) table[hash];
            table[hash]=null;
            load--;
            return result;
        }
        return null;
    }

    /**
     * returns current load on the hash table
     * @return load of the hash table
     */
    public int size() {
        return load;
    }
}

