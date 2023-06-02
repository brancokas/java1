package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.Tester;

/**
 * Class EvenIntegerTester is a wrapper class
 * that implements interface Tester
 * 
 * @author bstankovic
 * @version 1.0
 * */

class EvenIntegerTester implements Tester {

	/**
	 * @return <code>true</code> if obj is even
	 * else <code>false</code>
	 * */
	public boolean test(Object obj) {
	    if(!(obj instanceof Integer)) return false;
	    Integer i = (Integer)obj;
	    return i % 2 == 0;
	}
	  
}
