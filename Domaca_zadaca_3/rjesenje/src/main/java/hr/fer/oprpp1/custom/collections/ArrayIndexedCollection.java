package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;

/**
 * class <code>ArrayIndexedCollection</code> implements 
 * resizable array-backed collection of parameterized types
 * 
 * ArrayIndexedCollection can have and store duplicate elements,
 * but storage of null references is not allowed
 * 
 * class supports various operations such as:
 * 	add Object of type T, add Collection of type T, contains Object of type t,
 * 	size of Array, remove Object, remove Object at given index,
 * 	get Object at given index, clear all Objects from Array,
 * 	insert Object at given index, get index of an Object,
 * 	process each element of Array, get new Array of elements
 * 
 * 	class has four Constructors :
 * 	default Constructor initializes internal Array to 16,
 * 	Constructor with parameter initial value : sets internal Array to initial value
 * 	Constructor with parameter Collection : sets internal Array to size of given Collection,
 * 	and copies elements of Collection to internal Array
 * 	Constructor with parameters Collection initial value: sets internal Array 
 * 	to size of initial value, if it is greater than Collection size,
 * 	else sets internal size of Array to Collection size
 * 	and copies elements of Collection to internal Array
 * 
 * 
 * @author Branimir Stankovic
 * @version 1.0
 * */

public class ArrayIndexedCollection<T> implements List<T> {
	private int size;
	private Object[] elements;
	private long modificationCount = 0;

	/**
	 * 	default Constructor initializes internal Array to 16,
	 * */
	@SuppressWarnings("unchecked")
	public ArrayIndexedCollection() {
		this.elements = (T[]) new Object[16];
		this.size = 0;
	}
	
	/**
	 * 	Constructor with parameter initial value : sets internal Array to initial value
	 * */
	public ArrayIndexedCollection(int initialValue) {
		// If value is lesser than 1 throw  IllegalArgumentException()
		if (initialValue < 1) throw new IllegalArgumentException();
		
		this.elements = new Object[initialValue];
		this.size = 0;
	}
	
	/**
	 * Collection can be of Type T and type's derived from T
	 * 	Constructor with parameters Collection initial value: sets internal Array 
	 * 	to size of initial value, if it is greater than Collection size,
	 * 	else sets internal size of Array to Collection size
	 * 	and copies elements of Collection to internal Array
	 * */
	public ArrayIndexedCollection(Collection<T> collection, int initialVaule) {
		// If collection's size is greater than initalValue 
		// than allocate memory for collection.size, else for initalValue
		this((collection != null) && collection.size() > initialVaule ? collection.size() : initialVaule);

		// Check if collection is empty : throw NullPointerException
		if (collection == null) throw new NullPointerException();
		
		// Call .toArray() to instanciate elements reference to new Array
		this.elements = collection.toArray();
		
		// Update size to size of added collection
		this.size = collection.size();
	}
	
	/**
	 * Collection can be of Type T and type's derived from T
	 *  Constructor with parameter Collection : sets internal Array to size of given Collection,
	 * 	and copies elements of Collection to internal Array
	 * */
	public ArrayIndexedCollection(Collection<T> collection) {
		this(collection, collection.size());
	}
	
	/**
	 * @return int size of the Array
	 * */
	@Override
	public int size() {
		return this.size;
	}
	
	/**
	 * Array compares Object parameter with its elements with equals method 
	 * @param value value can be null
	 * @return <code>true</code> if Array contains Object parameter 
	 * 			else return <code>false</code>
	 * */
	@Override
	public boolean contains(Object value) {
		for (int i = 0; i < this.size; i++) {
			if (this.elements[i].equals(value)) return true;
		}
		return false;
	}
	
	/**
	 * Array's elements are moved so there are no null references in between the elements
	 * Array compares Object parameter with its elements with equals method 
	 * @param Object value can be null
	 * @return <code>true</code> if Array removed first occurance 
	 * 			of Object parametar in collection 
	 * 			else return <code>false</code>
	 * */
	@Override
	public boolean remove(Object value) {
		for (int i = 0; i < this.size; i++) {
			if (this.elements[i].equals(value)) {
				this.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * Array's elements are moved so there are no null references in between the elements
	 * Array compares Object parametar with its elements with equals method 
	 * @param index index of Object to remove, allowed index between 0 and size-1
	 * @throws IndexOutOfBoundsException if parametar index is smaller than 0
	 * 			and greater than or equal to size of Array
	 * */
	public void remove(int index) {
		if (index < 0 || index >= this.size) throw new IndexOutOfBoundsException();
		
		this.elements[index] = null;
		for (int i = index+1; i < this.size; i++) {
			this.elements[i-1] = this.elements[i];
			this.elements[i] = null;
		}
		
		this.size--;
		this.modificationCount++;
	}
	
	/**
	 * Method never returns null
	 * @return Object[] new Array the size of the collection
	 * */
	@Override
	public Object[] toArray() {
		Object[] arr = new Object[this.size];
		
		for (int i = 0; i < this.size; i++) {
			arr[i] = this.elements[i];
		}
		
		return arr;
	}
	
	/**
	 * Za privatne metode se ne pise javadoc jer to klijent ne vidi, no stavio sam za potrebe zadace
	 * @return <code>true</code> if size of elements in collection
	 * 			and length of internal array are equal
	 * 			else <code>false</code>
	 * */
	private boolean isFull() {
		return this.size == this.elements.length;
	}
	
	/**
	 * Za privatne metode se ne pise javadoc jer to klijent ne vidi, no stavio sam za potrebe zadace
	 * Doubles the size of internal array
	 */
	private void doubleTheArray() {
		Object[] newElements = new Object[this.elements.length * 2];
		
		for (int i = 0; i < this.size; i++) {
			newElements[i] = this.elements[i];
		}
		
		this.elements = newElements;
	}
	
	/**
	 * Average complexity : O(1) - constant
	 * Adds parametar Object to the end of collection and increments the size
	 * @throws NullPointerException if parametar value is null
	 * */  
	@Override
	public void add(T value) {
		if (value == null) throw new NullPointerException();
		
		if (this.isFull()) {
			this.doubleTheArray();
		}
		
		this.elements[this.size++] = value;
		this.modificationCount++;
	}
	
	/** 
	 * Average complexity : O(1) - constant
	 * @param int index : allowed values between 0 and size-1
	 * @return Object element at given index of collection  
	 * @throws IndexOutOfBoundsException if index is smaller than 0
	 * 			or greater than or equal to size of collection
	 *  */
	@SuppressWarnings("unchecked")
	public T get(int index) {
		if (index < 0 || index >= this.size) throw new IndexOutOfBoundsException();
		
		return (T) this.elements[index];
	}
	
	/**
	 * Erases all elements from the collection
	 * and sets the size to 0
	 * */
	public void clear() {
		for (int i = 0; i < this.size; i++) {
			this.elements[i] = null;
		}
		
		this.size = 0;
		this.modificationCount++;
	}
	
	/**
	 * Average complexity : O(N) - linear
	 * Increments the size
	 * Inserts element at given index, and moves each element one to the right
	 * from given index to size
	 * @param Object value, int index: value cannot be null,
	 * 			index must be from 0 to size 
	 * @throws IndexOutOfBoundsException if index is smaller than 0 and greater than size
	 * @throws NullPointerException if value is null
	 * */
	public void insert(T value, int index) {
		if (index < 0 || index > this.size) throw new IndexOutOfBoundsException();
		if (value == null) throw new NullPointerException();
		
		if (this.isFull()) this.doubleTheArray();
		
		for (int i = this.size-1; i >= index; i--) {
			this.elements[i+1] = this.elements[i];
		}
		
		this.elements[index] = value;
		this.size++;
		this.modificationCount++;
		
	}
	
	/**
	 * Average complexity : O(N) - linear
	 * @param value can be null
	 * @return index of value if found in collection by method equal
	 * 			else -1
	 */
	public int indexOf(Object value) {
		for (int i = 0; i < this.size; i++) {
			if(this.elements[i].equals(value)) return i;
		}
		return -1;
	}

	@Override
	public ElementsGetter<T> createElementsGetter() {
		return new ElementsGetterClass<T>(this, this.modificationCount);
	}
	
	
	private static class ElementsGetterClass<T> implements ElementsGetter<T> {
		private ArrayIndexedCollection<T> arr;
		private int lastIndex;
		private long savedModificationCount;
		
		public ElementsGetterClass(ArrayIndexedCollection<T> arr, long modificationCount) {
			lastIndex = 0;
			this.arr = arr;
			this.savedModificationCount = modificationCount;
		}
		
		@Override
		public boolean hasNextElement() {
			if (this.savedModificationCount != arr.modificationCount) 
				throw new ConcurrentModificationException();
			
			return lastIndex < arr.size ? true : false;

		}
		
		@SuppressWarnings("unchecked")
		@Override
		public T getNextElement() throws UnsupportedOperationException {
			if (!this.hasNextElement()) throw new UnsupportedOperationException();
			
			return (T) arr.elements[lastIndex++];
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		forEach(value -> sb.append(value.toString() + ' '));
		
		return sb.toString();
	}
}
