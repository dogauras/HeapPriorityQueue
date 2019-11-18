import java.util.*;

/**
 * Basic Test for assignment 2 - F2019
 * 
 * @author Robert Laganiere
 * 
 * Student: Doga Uras
 * Student No: 300062160
 */
public class HeapPriorityQueueTest {

	static String alpha = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static void main(String[] args) {

		Random rng = new Random(12345); // do not change the seed

		// A min-heap
		HeapPriorityQueue<Integer, Character> pq = new HeapPriorityQueue<>(500);

		// Insert elements in heap
		for (int i = 0; i < 100; i++) {
			Entry<Integer, Character> e = pq.insert(rng.nextInt(10000), alpha.charAt(rng.nextInt(52)));
		}

		System.out.println();
		System.out.println();

		// removeMin
		for (int i = 0; i < 8; i++) {
			Entry<Integer, Character> e = pq.removeMin();
			System.out.print("(" + e.key.toString() + "," + e.value.toString() + ":" + e.index + "), ");
		}

		System.out.println();
		System.out.println();

		// removeMax

		for (int i = 0; i < 8; i++) {
			Entry<Integer, Character> e = pq.removeMax();
			System.out.print("(" + e.key.toString() + "," + e.value.toString() + ":" + e.index + "), ");
		}

		System.out.println();
		System.out.println();

		// removeMin
		for (int i = 0; i < 8; i++) {
			Entry<Integer, Character> e = pq.removeMin();
			System.out.print("(" + e.key.toString() + "," + e.value.toString() + ":" + e.index + "), ");
		}

		System.out.println();
		System.out.println();

		// removeMax

		for (int i = 0; i < 8; i++) {
			Entry<Integer, Character> e = pq.removeMax();
			System.out.print("(" + e.key.toString() + "," + e.value.toString() + ":" + e.index + "), ");
		}

	}
}
