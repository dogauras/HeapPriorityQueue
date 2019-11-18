/**
 * Array Heap implimentation of a priority queue
 * 
 * @author Lachlan Plant
 * 
 * Student: Doga Uras
 * Student No: 300062160
 */
public class HeapPriorityQueue<K extends Comparable, V> implements PriorityQueue<K, V> {

	private Entry[] storage, maxHeap;// The Heap itself in array form
	private Entry buffer;
	private int tail; // Index of last element in the heap

	/**
	 * Default constructor
	 */
	public HeapPriorityQueue() {
		this(100);
	}

	/**
	 * HeapPriorityQueue constructor with max storage of size elements
	 */
	public HeapPriorityQueue(int size) {
		storage = new Entry[size];
		maxHeap = new Entry[size];
		buffer = null;
		tail = -1;
	}

	/******************
	 *
	 * Priority Queue Methods
	 *
	 ******************/

	/**
	 * Returns the number of items in the priority queue. O(1)
	 * 
	 * @return number of items
	 */
	public int size() {
		if (buffer == null) {
			return (tail + 1) * 2;
		} else {
			return (tail + 1) * 2 + 1;
		}
	} /* size */

	/**
	 * Tests whether the priority queue is empty. O(1)
	 * 
	 * @return true if the priority queue is empty, false otherwise
	 */
	public boolean isEmpty() {
		return tail < 0;
	} /* isEmpty */

	/**
	 * Inserts a key-value pair and returns the entry created. O(log(n))
	 * 
	 * @param key   the key of the new entry
	 * @param value the associated value of the new entry
	 * @param ref   a reference to the associated entry in the othe heap
	 * @return the entry storing the new key-value pair
	 * @throws IllegalArgumentException if the heap is full
	 */
	public Entry<K, V> insert(K key, V value) throws IllegalArgumentException {
		if (tail == storage.length - 1)
			throw new IllegalArgumentException("Heap Overflow");
		if (buffer == null) {
			buffer = new Entry<>(key, value);
			return buffer;
		} else {
			Entry<K, V> e = new Entry<>(key, value);
			buffer.setAssociate(e);
			e.setAssociate(buffer);
			if (buffer.getKey().compareTo(e.getKey()) > 0) {
				maxHeap[++tail] = buffer;
				storage[tail] = e;
				storage[tail].setIndex(tail); // sets the index in minHeap to tail
				maxHeap[tail].setIndex(tail); // sets the index in maxHeap to tail
				buffer = null; //setting the buffer back to null
				minUpHeap(tail); // re-establishing the heap condition
				maxUpHeap(tail);
			} else {
				maxHeap[++tail] = e;
				storage[tail] = buffer;
				storage[tail].setIndex(tail);
				maxHeap[tail].setIndex(tail);
				buffer = null;
				minUpHeap(tail);
				maxUpHeap(tail);
			}
			return e;
		}

	} /* insert */

	/**
	 * Returns (but does not remove) an entry with minimal key. O(1)
	 * 
	 * @return entry having a minimal key (or null if empty)
	 */
	public Entry<K, V> min() {
		if (isEmpty())
			return null;
		return storage[0];
	} /* min */

	public Entry<K, V> max() {
		if (isEmpty())
			return null;
		return maxHeap[0];
	} /* min */

	/**
	 * Removes and returns an entry with minimal key. O(log(n))
	 * 
	 * @return the removed entry (or null if empty)
	 */
	public Entry<K, V> removeMin() {
		if (isEmpty())
			return null;

		Entry<K, V> ret = storage[0];
		Entry<K, V> associateMax = ret.getAssociate();

		if (buffer == null) {
			storage[0] = storage[tail];

			buffer = associateMax;
			if (associateMax.getIndex() == tail) {
				maxHeap[tail] = null;
			} else {
				maxSwap(associateMax.getIndex(), tail);
				maxHeap[tail] = null;
			}
			storage[tail--] = null;
		} else {
			if (ret.getKey().compareTo(buffer.getKey()) > 0) { // buffer is smaller than the min
				Entry<K, V> temp = buffer;
				buffer = null;
				return temp;
			} else { // buffer is greater than the min
				buffer.setAssociate(associateMax);
				associateMax.setAssociate(buffer);
				if (associateMax.getKey().compareTo(buffer.getKey()) > 0) { // buffer is smaller than associateMax
					storage[0] = buffer;
				} else { // buffer is greater than associateMax
					K keyy = associateMax.key;
					V value = associateMax.value;
					associateMax.key = (K) buffer.key;
					associateMax.value = (V) buffer.value;
					buffer.key = keyy;
					buffer.value = value;
					maxUpHeap(associateMax.getIndex());
					storage[0] = buffer;
				}
				buffer = null;
			}
		}
		minDownHeap(0);
		maxDownHeap(0);
		return ret;
	} /* removeMin */

	public Entry<K, V> removeMax() {
		if (isEmpty())
			return null;

		Entry<K, V> ret = maxHeap[0];
		Entry<K, V> associateMin = ret.getAssociate();

		if (buffer == null) {
			maxHeap[0] = maxHeap[tail];
			buffer = associateMin;
			if (associateMin.getIndex() == tail) {
				storage[tail] = null;
			} else {
				minSwap(associateMin.getIndex(), tail);
				storage[tail] = null;
			}
			storage[tail--] = null;
		} else {
			if (ret.getKey().compareTo(buffer.getKey()) < 0) { // buffer is greater than max
				Entry<K, V> temp = buffer;
				buffer = null;
				return temp;
			} else { // buffer is smaller than max
				buffer.setAssociate(associateMin);
				associateMin.setAssociate(buffer);
				if (associateMin.getKey().compareTo(buffer.getKey()) < 0) { // buffer is greater than associateMin
					maxHeap[0] = buffer;
				} else { // buffer is smaller than associateMin
					K keyy = associateMin.key;
					V value = associateMin.value;
					associateMin.value = (V) buffer.value;
					associateMin.key = (K) buffer.key;
					buffer.key = keyy;
					buffer.value = value;
					minUpHeap(associateMin.getIndex());
					maxHeap[0] = buffer;

				}
				buffer = null;
			}
		}
		minDownHeap(0);
		maxDownHeap(0);
		return ret;
	} /* removeMax */

	/******************
	 *
	 * Methods for Heap Operations
	 *
	 ******************/

	/**
	 * Algorithm to place element after insertion at the tail. O(log(n))
	 */
	private void minUpHeap(int location) {
		if (location == 0)
			return;

		int parent = parent(location);

		if (storage[parent].key.compareTo(storage[location].key) > 0) {
			minSwap(location, parent);
			minUpHeap(parent);
		}
	} /* upHeap */

	private void maxUpHeap(int location) {
		if (location == 0)
			return;
		int parent = parent(location);
		if (maxHeap[parent].key.compareTo(maxHeap[location].key) < 0) {
			maxSwap(location, parent);
			maxUpHeap(parent);
		}
	} /* upHeap */

	/**
	 * Algorithm to place element after removal of root and tail element placed at
	 * root. O(log(n))
	 */
	private void minDownHeap(int location) {
		int left = (location * 2) + 1;
		int right = (location * 2) + 2;

		// Both children null or out of bound
		if (left > tail)
			return;

		// left in right out;
		if (left == tail) {
			if (storage[location].key.compareTo(storage[left].key) > 0)
				minSwap(location, left);
			return;
		}

		int toSwap = (storage[left].key.compareTo(storage[right].key) < 0) ? left : right;

		if (storage[location].key.compareTo(storage[toSwap].key) > 0) {
			minSwap(location, toSwap);

			minDownHeap(toSwap);
		}
	} /* downHeap */

	private void maxDownHeap(int location) {
		int left = (location * 2) + 1;
		int right = (location * 2) + 2;

		// Both children null or out of bound
		if (left > tail)
			return;

		// left in right out;
		if (left == tail) {
			if (maxHeap[location].key.compareTo(maxHeap[left].key) < 0)
				maxSwap(location, left);
			return;
		}

		int toSwap = (maxHeap[left].key.compareTo(maxHeap[right].key) > 0) ? left : right;

		if (maxHeap[location].key.compareTo(maxHeap[toSwap].key) < 0) {
			maxSwap(location, toSwap);
			maxDownHeap(toSwap);
		}
	} /* downHeap */

	/**
	 * Find parent of a given location, Parent of the root is the root O(1)
	 */
	private int parent(int location) {
		return (location - 1) / 2;
	} /* parent */

	/**
	 * Inplace swap of 2 elements, assumes locations are in array O(1)
	 */
	private void maxSwap(int location1, int location2) {
		Entry<K, V> temp = maxHeap[location1];
		maxHeap[location1] = maxHeap[location2];
		maxHeap[location2] = temp;
		maxHeap[location1].index = location1;
		maxHeap[location2].index = location2;
	} /* swap */

	private void minSwap(int location1, int location2) {
		Entry<K, V> temp = storage[location1];
		storage[location1] = storage[location2];
		storage[location2] = temp;
		storage[location1].index = location1;
		storage[location2].index = location2;
	} /* swap */

	// first prints the minHeap then the maxHeap
	public void print() {

		for (int i = 0; i < tail + 1; i++) {
			Entry<K, V> e = storage[i];
			System.out.println("(" + e.key.toString() + "," + e.value.toString() + ":" + e.index + "), ");
		}

		for (int i = 0; i < tail + 1; i++) {
			Entry<K, V> n = maxHeap[i];
			System.out.println("(" + n.key.toString() + "," + n.value.toString() + ":" + n.index + "), ");
		}
	}

}