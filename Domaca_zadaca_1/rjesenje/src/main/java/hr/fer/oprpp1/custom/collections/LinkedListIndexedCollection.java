package hr.fer.oprpp1.custom.collections;

/**
 * class <code>LinkedListIndexedCollection</code> implements resizable array-backed collection of objects
 * 
 * LinkedListIndexedCollection can have and store duplicate elements,
 * but storage of null references is not allowed
 * 
 * class supports various operations such as:
 * 	add Object, add Collection, contains Object,
 * 	size of List, remove Object, remove Object at given index,
 * 	get Object at given index, clear all Objects from List,
 * 	insert Object at given index, get index of an Object,
 * 	procces each element of List, get new Array of elements
 * 
 * 	class has two Constructors :
 * 	default Constructor initializes first and last to null,
 * 	Constructor with parametar Collection : copies elements of Collection to List
 * 
 * @author Branimir Stankovic
 * @version 1.0
 * */

public class LinkedListIndexedCollection extends Collection {
	
	/**
	 * Za privatne metode se ne pise javadoc jer to klijent ne vidi, no stavio sam za potrebe zadace
	 * Private class for storing references to
	 * next and previous Node that holds a Object non-null value  
	 */
	private static class ListNode {
		public Object value;
		public ListNode next;
		public ListNode previous;
		
		/**
		 * Constructor for storing value to Node 
		 * */
		public ListNode(Object value) {
			this.value = value;
			next = previous = null;
		}
	}
	
	private int size;
	private ListNode first;
	private ListNode last;
	
	/**
	 * default constructor initializes the internal variables
	 */
	public LinkedListIndexedCollection() {
		first = last = null;
		this.size = 0;
	}
	
	/**
	 * Add the elements of collection to List
	 * @throws NullPointerException if collection is null
	 */
	public LinkedListIndexedCollection(Collection collection) {
		if (collection == null) throw new NullPointerException();
		
		Object[] arr = collection.toArray();
		for (int i = 0, arrSize = arr.length; i < arrSize; i++) {
			this.add(arr[i]);
		}
	}
	
	/**
	 * Method never returns null
	 * @return Object[] new Array the size of the collection
	 * */
	@Override
	public Object[] toArray() {
		Object[] newArray = new Object[this.size];
		ListNode curr = this.first;
		
		int index = 0;
		while(curr != null) {
			newArray[index++] = curr.value;
			curr = curr.next;
		}
		
		return newArray;
	}
	
	/**
	 * Processes each elements of the collection
	 * @param Processor Processor with method procces to execute action upon each element
	 * */
	@Override
	public void forEach(Processor processor) {
		ListNode curr = this.first;
		while(curr != null) {
			processor.process(curr.value);
			curr = curr.next;
		}
		
	}
	
	/**
	 * @return size of the List
	 */
	@Override
	public int size() {
		return this.size;
	}
	
	/**
	 * List compares Object parametar with its elements with equals method 
	 * @param value value can be null
	 * @return <code>true</code> if List contains Object parametar 
	 * 			else return <code>false</code>
	 * */
	@Override
	public boolean contains(Object value) {
		ListNode current = first;
		while(current != null) {
			if (value.equals(current.value)) return true;
			current = current.next;
		}
		return false;
	}
	
	/** 
	 * Average complexity : O(n) - linear
	 * @param index : allowed values between 0 and size-1
	 * @return element at given index of collection  
	 * @throws IndexOutOfBoundsException if index is smaller than 0
	 * 			or greater than or equal to size of collection
	 *  */
	public Object get(int index) {
		if (index < 0 || index >= this.size) throw new IndexOutOfBoundsException();
		
		ListNode curr;
		if(index < this.size / 2) {
			curr = this.first;
			
			for(int i = 0; i < index; i++) {
				curr = curr.next;
			}
		} else {
			curr = this.last;
			
			for (int i = this.size-1; i > index; i--) {
				curr = curr.previous;
			}
		}
		return curr.value;
	}

	/**
	 * Average complexity : O(N) - linear
	 * Increments the size
	 * Inserts element at given index, and moves each element one to the right
	 * from given index to size
	 * @param value, index: value cannot be null,
	 * 			index must be from 0 to size 
	 * @throws IndexOutOfBoundsException if index is smaller than 0 and greater than size
	 * @throws NullPointerException if value is null
	 * */
	public void insert(Object value, int position) {
		if (position < 0 || position > this.size) throw new IndexOutOfBoundsException();
		if (value == null) throw new NullPointerException();
		
		ListNode newNode = new ListNode(value);
		if (this.size == 0) {
			this.first = newNode;
			this.last = newNode;
		} else if (position == 0) {
			first.previous = newNode;
			newNode.next = this.first;
			this.first = newNode;
		} else if (position == this.size) {
			last.next = newNode;
			newNode.previous = last;
			this.last = newNode;
		} else {
			ListNode curr = first;
			for (int i = 0; i < position; i++) {
				curr = curr.next;
			}
			curr.previous.next = newNode;
			curr.previous = newNode;
		}
		
		this.size++;
	}
	
	/**
	 * Average complexity : O(N) - linear
	 * @param value can be null
	 * @return index of value's first occurance if found in collection
	 *  by method equal	else -1
	 */
	public int indexOf(Object value) {
		ListNode curr = first;
		
		int i = 0;
		while(curr != null) {
			if (value.equals(curr.value)) return i;
			curr = curr.next;
			i++;
		}
		return -1;
	}
	
	/**
	 * List's elements are moved so there are no null references in between the elements
	 * List compares Object parametar with its elements with equals method 
	 * @param index of Object to remove, allowed index between 0 and size-1
	 * @throws IndexOutOfBoundsException if parametar index is smaller than 0
	 * 			and greater than or equal to size of List
	 * */
	public void remove(int index) {
		if (index < 0 || index >= this.size) throw new IndexOutOfBoundsException();
		
		if (this.size == 1) {
			this.first = null;
			this.last = null;
		} else if (index == 0) {
			first = first.next;
			first.previous.next = null;
			first.previous = null;
		} else if (index == this.size - 1) {
			last = last.previous;
			last.next.previous = null;
			last.next = null;
		} else {
			ListNode curr = first;
			for (int i = 0; i < index; i++) {
				curr = curr.next;
			}
			curr.previous.next = curr.next;
			curr.next.previous = curr.previous;
			curr.next = curr.previous = null;
		}
		this.size--;
	}
	
	/**
	 * Average complexity : O(1) - constant
	 * Adds parametar Object to the end of collection and increments the size
	 * @throws NullPointerException if parametar value is null
	 * */  
	@Override
	public void add(Object value) {
		if (value == null) throw new NullPointerException();
		
		this.size++;
		ListNode newNode = new ListNode(value);
		
		if (first == last && last == null) {
			first = newNode;
			last = newNode;
			return;
		}
		
		last.next = newNode;
		newNode.previous = last;
		last = newNode;
	}
	
	/**
	 * Erases all elements from the collection
	 * and sets the size to 0
	 * */
	@Override
	public void clear() {
		this.size = 0;
		if (first == null) return;
		
		while(first.next != null) {
			if (first.previous != null) {
				first.previous.next = null;				
			}
			first.previous = null;
			first = first.next;
		}
		first.previous = null;
		this.first = null;
		this.last = null;
	}
	
	/**
	 * List's elements are moved so there are no null references in between the elements
	 * List compares Object parametar with its elements with equals method 
	 * @param value value can be null
	 * @return <code>true</code> if List removed first occurance 
	 * 			of Object parametar in collection 
	 * 			else return <code>false</code>
	 * */
	@Override
	public boolean remove(Object value) {		
		ListNode curr = first;
		int index = 0;
		while(curr != null) {
			if (value.equals(curr.value)) {
				this.remove(index);
				return true;
			}
		}
		return false;
	}
}
