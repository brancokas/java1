package hr.fer.oprpp1.custom.collections;

/**
 * Interface ElementsGetter enables 
 * getting element by element rather
 * than processing whole collection
 * 
 * @author bstankovic
 * @version 1.0
 * */
public interface ElementsGetter<T> {
	
	/**
	 * @return <code>true</code> if collection has nextElement
	 * 			else <code>false</code>
	 * */
	public boolean hasNextElement();
	
	/**
	 * @return next Element in collection
	 * */
	public T getNextElement();
	
	/**
	 * 
	 * default method processes every element from collection
	 * */
	default void processRemaining(Processor<? super T> p) {
		while(this.hasNextElement()) {
			p.process(this.getNextElement());
		}
	}
}
