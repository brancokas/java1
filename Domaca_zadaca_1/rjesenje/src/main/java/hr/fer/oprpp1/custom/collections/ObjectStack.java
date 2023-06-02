package hr.fer.oprpp1.custom.collections;

/**
 * Class ObjectStack is a class that implements a stack structure
 * Class has various methods:
 * 		stack is empty, size of stack,
 * 		push element to the top of the stack,
 * 		pop the element on top from stack,
 * 		peek the top element on stack,
 * 		clear the stack
 * 
 * @author Branimir Stankovic
 * @version 1.0
 */

public class ObjectStack {
	private ArrayIndexedCollection stackArray;
	
	/**
	 * Initialize an empty stack
	 */
	public ObjectStack() {
		this.stackArray = new ArrayIndexedCollection();
	}
	
	/**
	 * @return <code>true</code> if stack is empty
	 * 			else <code>false</code>
	 */
	public boolean isEmpty() {
		return this.stackArray.isEmpty();
	}
	
	/**
	 * @return size of the stack
	 */
	public int size() {
		return this.stackArray.size();
	}
	
	/**
	 * Average complexity : O(1) - constant
	 * @param value value mustn't be null
	 * @throws NullPointerException if value is null
	 */
	public void push(Object value) {
		if (value == null) throw new NullPointerException();
		
		this.stackArray.add(value);
	}
	
	/**
	 * Average complexity : O(1) - constant
	 * @return top element of stack and remove it from stack
	 * @throws EmptyStackException if stack is empty and pop is called
	 */
	public Object pop() {
		if (this.isEmpty()) throw new EmptyStackException();
		
		Object value = this.stackArray.get(this.size()-1);
		this.stackArray.remove(this.size()-1);
		return value;
	}
	
	/**
	 * Average complexity : O(1) - constant
	 * @return top of stack
	 * @throws EmptyStackException if stack is empty and peek is called
	 */
	public Object peek() {
		if (this.isEmpty()) throw new EmptyStackException();
		
		return this.stackArray.get(this.size()-1);
	}
	
	/**
	 * Remove all elements from stack
	 */
	public void clear() {
		this.stackArray.clear();
	}
}
