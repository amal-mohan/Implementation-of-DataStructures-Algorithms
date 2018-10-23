/**
 * Author: Amal Mohan - axm179030
 * 	Anki Chauhan - axc170043
 *
 * -Element class is a generic class used to find hashcode and hashcode2 of any object
 *
 */

package axm179030;



/**
 * standard interface to implement two hash codes of an element
 */
interface HashCodeGenerator{
    int hashCode2();
    int hashCode();
}

/**
 * generic class to implement two hash codes of a function
 * @param <T>
 */
class Element<T> implements HashCodeGenerator {
    private T element;

    /**
     * assigns element to the input value
     * @param x input value
     */
    Element(T x){
        element=x;
    }

    /**
     * get method
     * @return returns element
     */
    public T getElement(){
        return element;
    }

    /**
     * method to calculate second hash code
     * @return second hash code of the value
     */
    @Override
    public int hashCode2() {
        return element.toString().hashCode();
//        return ((Double)Math.pow(element.hashCode(),2)).hashCode();
        //  return ()Math.pow((Double) element,2).hashCode();
    }

    /**
     * method to calculate hash code
     * @return second hash code of the value
     */
    @Override
    public int hashCode() {
        return element.hashCode();
    }
}
