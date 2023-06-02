package hr.fer.oprpp1.custom.collections;

/**
 * Interface Processor has a method
 * process which task is
 * to process a given Object value of type T
 * depending of its implementation
 * 
 * @author Branimir Stankovic
 * @version 1.0
 */

@FunctionalInterface
public interface Processor<T> {
	
	/**
	 * Processes the given value
	 */
	public void process(T value);
}
