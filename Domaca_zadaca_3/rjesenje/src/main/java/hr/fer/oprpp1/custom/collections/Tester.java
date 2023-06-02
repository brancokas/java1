package hr.fer.oprpp1.custom.collections;


/**
 * Functional Interface for testing objects
 * if they pass given test than return boolean
 * 
 * @author bstankovic
 * @version 1.0
 * */
@FunctionalInterface
public interface Tester<T> {

	/**
	 * @return <code>true</code> if Object of type T passes certain test
	 * 			else <code>false</code>
	 */
	boolean test(T obj);
	
}
