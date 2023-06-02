package hr.fer.oprpp1.custom.collections;

/**
 * Interface Processor has a method
 * process which task is
 * to process a given Object value
 * depending of its implementaion
 * 
 * @author Branimir Stankovic
 * @version 1.0
 */

@FunctionalInterface
public interface Processor {
	
	/**
	 * Processes the given value
	 */
	public void process(Object value);
}
