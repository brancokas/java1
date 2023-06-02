package hr.fer.oprpp1.custom.collections;


/**
 * Functional Interface for testing objects
 * if they pass given test than return boolean
 * 
 * @author bstankovic
 * @version 1.0
 * */
@FunctionalInterface
public interface Tester {

	/**
	 * @return <code>true</code> if Object passes certain test
	 * 			else <code>false</code>
	 */
	boolean test(Object obj);
	
}
