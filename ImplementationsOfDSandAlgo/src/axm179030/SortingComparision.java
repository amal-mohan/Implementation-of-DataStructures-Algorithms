/** Author: Amal Mohan - axm179030
 Anki Chauhan - axc170043

 axm179030\SortingComparision.java consists of the implementation of sorting algorithms

 axm179030\report.txt has the analysis of runtime of the algorithms

 */

package axm179030;
import java.util.Random;

/**
 * Class to compare sorting algorithms
 */
public class SortingComparision {
	public static Random random = new Random();
	public static int numTrials = 100;

	/**
	 * main function that tests each sorting version
	 * @param args
	 */
	public static void main(String[] args) {
		int n = 10;  int choice = 1 + random.nextInt(4);
		if(args.length > 0) {
			n = Integer.parseInt(args[0]);
		}
		if(args.length > 1) {
			choice = Integer.parseInt(args[1]);
		}
		int[] arr = new int[n];
		for(int i=0; i<n; i++) {
			arr[i] = i;
		}
		Timer timer = new Timer();
		switch(choice) {
			case 1:
				Shuffle.shuffle(arr);
				numTrials = 1;
				insertionSort(arr);
				break;
			case 2:
				for(int i=0; i<numTrials; i++) {
					Shuffle.shuffle(arr);
					mergeSort1(arr);
				}
				break;
			case 3:
				for(int i=0; i<numTrials; i++) {
					Shuffle.shuffle(arr);
					mergeSort2(arr);
				}
				break;
			case 4:
				for(int i=0; i<numTrials; i++) {
					Shuffle.shuffle(arr);
					mergeSort3(arr);
				}
				break;
		}
		timer.end();
		timer.scale(numTrials);
		System.out.println("Choice: " + choice + "\n" + timer);
	}


	/**
	 * Sorts the given array using standard Insertion sort
	 * Running Time : O(n^2)
	 * @param arr
	 */
	public static void insertionSort(int[] arr) {
		insertionSort(arr,0,arr.length-1);
	}

	/**
	 * The insertion sort algorithm to sort array from start to end
	 * @param arr
	 * @param start
	 * @param end
	 */
	public static void insertionSort(int[] arr,int start, int end) {
		int key,j;
		for(int i=start+1;i<=end;i++){
			key=arr[i];
			j=i-1;
			while(j>=start && key<arr[j]){
				//swaps elements till a current element is bigger than array element
				arr[j+1]=arr[j];
				j--;
			}
			arr[j+1]=key;
		}
	}

	/**
	 * version 1 of merge sort
	 * @param arr
	 */
	public static void mergeSort1(int[] arr) {
		mergeSort(arr,0,arr.length-1);
	}

	/**
	 * sort array arr from p to r
	 * @param arr
	 * @param p
	 * @param r
	 */
	public static void mergeSort(int[] arr, int p, int r){
		if(p<r){
			int q = (p+r)/2;
			mergeSort(arr, p, q);
			mergeSort(arr, q+1, r);
			//merges two sorted array
			merge1(arr,p,q,r);
		}
	}

	/**
	 * merges array from p to q and q to r
	 * @param arr
	 * @param p
	 * @param q
	 * @param r
	 */
	public static void merge1(int[] arr, int p, int q, int r){
		int[] left=new int[q-p+1];
		int[] right=new int[r-q];
		//copies arr[p to q] to left and arr[q+1 to r] right array
		System.arraycopy(arr, p, left, 0, q-p+1);
		System.arraycopy(arr, q+1, right, 0, r-q);
		int i,j;
		i=j=0;
		for(int k=p;k<=r;k++){
			if(j>=r-q || (i<q-p+1 && (left[i]<=right[j])))
				arr[k]=left[i++];
			else
				arr[k]=right[j++];
		}
	}

	/**
	 * mergesort version 2
	 * @param arr
	 */
	public static void mergeSort2(int[] arr) {
		int[] arr_tmp=new int[arr.length];
		mergeSort1(arr,arr_tmp,0,arr.length-1, 8);
	}

	/**
	 * merge sort algorithm to sort from p to r
	 * @param arr
	 * @param arr_tmp
	 * @param p
	 * @param r
	 * @param T
	 */
	public static void mergeSort1(int[] arr,int[] arr_tmp, int p, int r, int T){
		//uses threshold to move to insertion sort to optimize performance
		if(r-p+1<T){
			insertionSort(arr,p,r);
		}
		else{
			int q = (p+r)/2;
			mergeSort1(arr,arr_tmp, p, q,T);
			mergeSort1(arr,arr_tmp, q+1, r,T);
			//merges array from p to r after sorting them respectively from p to q and q to r
			merge2(arr,arr_tmp,p,q,r);
		}
	}

	/**
	 * version of merge which avoids multiple expensive copy and
	 * @param arr
	 * @param arr_tmp
	 * @param p
	 * @param q
	 * @param r
	 */
	public static void merge2(int[] arr,int[] arr_tmp, int p, int q, int r){
		System.arraycopy(arr, p, arr_tmp, p, r-p+1 );
		int i,j;
		i=p;j=q+1;
		//
		for(int k=p;k<=r;k++){
			if(j>r || (i<=q && (arr_tmp[i]<=arr_tmp[j])))
				arr[k]=arr_tmp[i++];
			else
				arr[k]=arr_tmp[j++];
		}
	}

	/**
	 * merge sort version 3 that avoids array copy recursion
	 * @param arr
	 */
	public static void mergeSort3(int[] arr) {
		int[] arr_tmp=new int[arr.length];
		System.arraycopy(arr, 0, arr_tmp, 0, arr.length );
		mergeSort(arr, arr_tmp,0,arr.length,8);
	}

	/**
	 * merge sort version 3 that sorts array
	 * @param arr
	 * @param arr_tmp
	 * @param left
	 * @param n
	 * @param T
	 */
	public static void mergeSort(int[] arr, int[] arr_tmp, int left, int n, int T) {
		//if n is less than threshold insertion sort is called to sort elements efficiently
		if(n<T){
			insertionSort(arr,left,left+n-1);
		}
		else{
			int mid_n=n/2;
			mergeSort(arr_tmp,arr,left,mid_n,T);
			mergeSort(arr_tmp,arr,left+mid_n,n-mid_n,T);
			//merge array form array left
			merge(arr,arr_tmp,left,left+mid_n-1,left+n-1);
		}

	}

	/**
	 * optimized merge version that efficiently sorts the array
	 * @param arr1
	 * @param arr2
	 * @param p
	 * @param q
	 * @param r
	 */
	public static void merge(int[] arr1, int[] arr2, int p, int q, int r){
		int i=p;
		int j=q+1;
		int k=p;
		while(i<=q && j<=r){
			if(arr2[i]<=arr2[j])
				arr1[k++]=arr2[i++];
			else
				arr1[k++]=arr2[j++];
		}
		while(i<=q){
			arr1[k++] = arr2[i++];
		}
		while(j<=r){
			arr1[k++] = arr2[j++];
		}
	}


	/** Timer class for roughly calculating running time of programs
	 *  @author rbk
	 *  Usage:  Timer timer = new Timer();
	 *          timer.start();
	 *          timer.end();
	 *          System.out.println(timer);  // output statistics
	 */

	public static class Timer {
		long startTime, endTime, elapsedTime, memAvailable, memUsed;
		boolean ready;

		public Timer() {
			startTime = System.currentTimeMillis();
			ready = false;
		}

		public void start() {
			startTime = System.currentTimeMillis();
			ready = false;
		}

		public Timer end() {
			endTime = System.currentTimeMillis();
			elapsedTime = endTime-startTime;
			memAvailable = Runtime.getRuntime().totalMemory();
			memUsed = memAvailable - Runtime.getRuntime().freeMemory();
			ready = true;
			return this;
		}

		public long duration() { if(!ready) { end(); }  return elapsedTime; }

		public long memory()   { if(!ready) { end(); }  return memUsed; }

		public void scale(int num) { elapsedTime /= num; }

		public String toString() {
			if(!ready) { end(); }
			return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed/1048576) + " MB / " + (memAvailable/1048576) + " MB.";
		}
	}

	/** @author rbk : based on algorithm described in a book
	 */


	/* Shuffle the elements of an array arr[from..to] randomly */
	public static class Shuffle {

		public static void shuffle(int[] arr) {
			shuffle(arr, 0, arr.length-1);
		}

		public static<T> void shuffle(T[] arr) {
			shuffle(arr, 0, arr.length-1);
		}

		public static void shuffle(int[] arr, int from, int to) {
			int n = to - from  + 1;
			for(int i=1; i<n; i++) {
				int j = random.nextInt(i);
				swap(arr, i+from, j+from);
			}
		}

		public static<T> void shuffle(T[] arr, int from, int to) {
			int n = to - from  + 1;
			Random random = new Random();
			for(int i=1; i<n; i++) {
				int j = random.nextInt(i);
				swap(arr, i+from, j+from);
			}
		}

		static void swap(int[] arr, int x, int y) {
			int tmp = arr[x];
			arr[x] = arr[y];
			arr[y] = tmp;
		}

		static<T> void swap(T[] arr, int x, int y) {
			T tmp = arr[x];
			arr[x] = arr[y];
			arr[y] = tmp;
		}

		public static<T> void printArray(T[] arr, String message) {
			printArray(arr, 0, arr.length-1, message);
		}

		public static<T> void printArray(T[] arr, int from, int to, String message) {
			System.out.print(message);
			for(int i=from; i<=to; i++) {
				System.out.print(" " + arr[i]);
			}
			System.out.println();
		}
	}
}

