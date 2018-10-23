/**
 * Author: Amal Mohan - axm179030
 * 	Anki Chauhan - axc170043
 *
 * --The driver of the program is HashingDriver.java which performs the following tasks
 * * Performance: compares the performance of java hashset and cuckoo hashing and prints the time taken and memory usage
 * * distinctElements: generates array of random integers and compares the performance of java Hashset and CuckooHashing
 *
 */

package axm179030;

import java.util.*;

/**
 * Driver class to test performance of cuckoo hashing implementation
 */
public class HashingDriver {	

    static Random random = new Random();
    static Timer timer = new Timer();

	public static void main(String[] args) {
		performanceRun();
		distinctElements(100);
		distinctElements(1000000);
	}

    /**
     * compares performance of java hashset and cuckoo hashing
     */
	public static void performanceRun(){
		//Generate a million numbers and shuffle
        List<Integer> data = new ArrayList<Integer>();
        for(int i =1;i<1000001;i++) {
			data.add(i);
		}
		Collections.shuffle(data);
//        System.out.println(data.size());
        int count=0;
        // Test with HashSet
        HashSet<Integer> hashSet = new HashSet<>();
        timer.start();
        data.stream().forEach(i->{
        	// pick a random operation from add,contains,remove
        	int val = random.nextInt(2);
        	switch(val){
        	case 0:
        		hashSet.add(i);
        		break;
        	case 1:
        		hashSet.contains(i);
        		break;
        	case 2: 
        		hashSet.remove(i);
        		break;
        		default: break;
        	}
        });
        timer.end();
//        System.out.println("Hashset size " + hashSet.size());
        System.out.println("Java HashSet");
        System.out.println(timer);
        
        // Test With Cuckoo Hashing
        CuckooHashing<Integer> hashCuckoo = new CuckooHashing<>();
        timer.start();
        data.stream().forEach(i->{
        	// pick a random operation from add,contains,remove
        	int val = random.nextInt(2);
        	switch(val){
        	case 0:
        		hashCuckoo.add(i);
        		break;
        	case 1:
        		hashCuckoo.contains(i);
        		break;
        	case 2: 
        		hashCuckoo.remove(i);
        		break;
        		default: break;
        	}
        });
        timer.end();
        System.out.println();
        System.out.println("Cuckoo Hashing");
  //      System.out.println("hashCuckoo size " + hashCuckoo.size());
        System.out.println(timer);

	}


    /**
     * compares hashset and cuckoo hashing and finds unique elements in the array
      * @param n number of elements to be randomly generated
     */
	public static void distinctElements(int n){
		
		int[] randomNum = new int[n];
		for(int i=0;i<n;i++){
			randomNum[i] = random.nextInt(n);
		}
		
		//Distinct Elements via HashSet.
		HashSet<Integer> hashSet = new HashSet<>();
		timer.start();
		for(int i : randomNum)
			hashSet.add(i);
		timer.end();
        System.out.println();
		System.out.println("HashSet Distinct elements count : "+ hashSet.size());
		System.out.println(timer);
		//Distinct Elements via Hashing with Cuckoo.
		CuckooHashing<Integer> hashWithCuckoo = new CuckooHashing<>();
		timer.start();
		for(int i : randomNum)
			hashWithCuckoo.add(i);
		timer.end();
        System.out.println();
		System.out.println("Hashing With Cuckoo Distinct elements count: "+ hashWithCuckoo.size());
		System.out.println(timer);
	}

}
