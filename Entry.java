/*
 * Student: Doga Uras
 * Student No: 300062160
 * 
*/
public class Entry <K extends Comparable, V> {
	K key;            // the key
	V value;          // the value
	int index;        // position of the element in the array
	Entry associate;  // reference to the associate element in the other heap

	/**
	 * Returns the key stored in this entry.
	 * @return the entry's key
	 */
	K getKey() {
		return key;
	} /* getKey */


	/**
	 * Returns the value stored in this entry.
	 * @return the entry's value
	 */
	V getValue() {
		return value;
	} /* getValue */


	public Entry( K k, V v ) {
		key   = k;
		value = v;
	}
	
	public void setIndex(int i) {
	
	    index= i;
	}
	
	public int getIndex() {
	
		return index;
	}
	
	public void setAssociate(Entry e) {
	    
		associate= e;
	}
	
	public Entry getAssociate() {
	
	    return associate;
	}
}
