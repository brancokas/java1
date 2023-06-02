package hr.fer.oprpp1.custom.collections;


/**
 * Class Collection represents a general collection of objects.
 * 
 * Because of its protected constructor class Collection is only 
 * availabe to the memebers of this package and to its children
 * that extend it.
 * 
 * @author Branimir Stankovic
 * @version 1.0
 * */

public class Collection {
	
	protected Collection() {}
	
	/**
	 * @return size of collection
	 */
	public int size() {
		return 0;
	}
	
	/**
	 * @return <code>true</code>if collection is empty
	 * 			else <code>false</code>
	 */
	public boolean isEmpty() {
		return this.size() == 0;
	}
	
	/**
	 * adds value to collection
	 */
	public void add(Object value) {}
	
	/**
	 * @return <code>true</code> if collection contains value
	 * 			else <code>false</code>
	 */
	public boolean contains(Object value) {
		return false;
	}
	
	/**
	 * @return <code>true</code> if value is removed 
	 * 			from collection else <code>false</code>
	 */
	public boolean remove(Object value) {
		return false;
	}
	
	/**
	 * @return a new array of collection's elements
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * @param process each element of collection
	 */
	public void forEach(Processor processor) {
	}
	
	/**
	 * Adds the whole collection other to called collection
	 * @param other mustn't be null
	 * @throws NullPointerException if other is null
	 */
	public void addAll(Collection other) {
		if (other == null) throw new NullPointerException();
		
		class LocalProcessor extends Processor {	
			@Override
			public void process(Object value) {
				add(value);
			}
		}
		LocalProcessor localProcessor = new LocalProcessor();
		other.forEach(localProcessor);
		
	}
	
	/**
	 * Removes all elements from collection
	 */
	public void clear() {}
}
