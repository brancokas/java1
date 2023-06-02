package hr.fer.oprpp1.custom.collections;


/**
 * Interface List enables 
 * basic functions in Collection
 * 
 * @author bstankovic
 * @version 1.0
 * */
public interface List<T> extends Collection<T> {

	/**
	 * @return get element at index [0...size-1]
	 * @throws IndexOutOfBoundsException 
	 * */
	T get(int index);
	
	/**
	 * inserts Object at given position and shifts each element 
	 * after position one place to the right
	 * @throws NullPointerException if value is null
	 * @throws IndexOutOfBoundsException if position not in [0...size>
	 * */
	void insert(T value, int position);
	
	/**
	 * Existance determined by equals method
	 * @return index of a given object if exists
	 * else return -1
	 * */
	int indexOf(Object value);
	
	/**
	 * remove element at given index and
	 * shifts each element after index one place to the left
	 * @throws IndexOutOfBoundsException if index not in [0...size>
	 * */
	void remove(int index);
	
}
