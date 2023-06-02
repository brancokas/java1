package hr.fer.oprpp1.custom.collections;


/**
 * Class Collection represents a general collection of objects of type T.
 * 
 * Because of its protected constructor class Collection is only 
 * available to the members of this package and to its children
 * that extend it.
 * 
 * @author Branimir Stankovic
 * @version 1.0
 * */

public interface Collection<T> {
	
	/**
	 * @return size of collection
	 */
	public int size();
	
	/**
	 * @return <code>true</code>if collection is empty
	 * 			else <code>false</code>
	 */
	default public boolean isEmpty() {
		return this.size() == 0;
	}
	
	/**
	 * adds value to collection
	 */
	public void add(T value);
	
	/**
	 * @return <code>true</code> if collection contains value
	 * 			else <code>false</code>
	 */
	public boolean contains(Object value);
	
	/**
	 * @return <code>true</code> if value is removed 
	 * 			from collection else <code>false</code>
	 */
	public boolean remove(Object value);
	
	/**
	 * @return a new array of collection's elements
	 */
	public Object[] toArray();
	
	/**
	 * @param process each element of collection
	 */
	default public void forEach(Processor<? super T> processor) {
		createElementsGetter().processRemaining(processor);
	}
	
	/**
	 * Adds the whole collection other to called collection
	 * @param other mustn't be null
	 * @throws NullPointerException if other is null
	 */
	default void addAll(Collection<? extends T> other) {
		if (other == null) throw new NullPointerException();
		
		other.forEach(value -> add(value));
		
	}
	
	/**
	 * Removes all elements from collection
	 */
	public void clear();
	
	public ElementsGetter<? extends T> createElementsGetter();
	
	default void addAllSatisfying(Collection<? extends T> col, Tester<? super T> tester) {
		if (col == null) throw new NullPointerException();
		
		col.forEach(value -> {
			if (tester.test(value)) {
				add(value);
			}
		});
	}
	
}
