package hr.fer.oprpp1.custom.collections;


/**
 * Class Dictionary is parametrized by two values
 * K and V. K represents Type of key and
 * T represents Type of value that is stored in
 * Dictonary. Elements of Dictionary are stored as
 * key,value pairs where each key is unique compared 
 * to other keys in that instance of Dictionary.
 * Key mustn't be null whereas value can be null and
 * different keys can have same values. 
 * 
 * @author bstankovic
 * */
public class Dictionary<K,V> {
	/**
	 * private static class Pair that holds
	 * a key-value pair where key cannot be null
	 * @throws NullPointerException if key is null
	 * */
	private static class Pair<K,V> {
		private K key;
		private V value;
		
		public Pair(K key, V value) {
			if (key == null) throw new NullPointerException();
			
			this.setKey(key);
			this.setValue(value);
		}

		public void setKey(K key) {
			this.key = key;
		}

		public void setValue(V value) {
			this.value = value;
		}
	}
	
	private ArrayIndexedCollection<Pair<K,V>> array;
	
	public Dictionary() {
		array = new ArrayIndexedCollection<>();
	}
	
	/**
	 * @return true if dictionary is empty
	 * 			else false
	 * */
	public boolean isEmpty() {
		return array.isEmpty();
	}
	
	/**
	 * @return int size of elements
	 * 			stored in the dictionary
	 * */
	public int size() {
		return array.size();
	}
	
	/**
	 * removes every element in
	 * dictionary and sets the size
	 * to zero
	 * */
	public void clear() {
		array.clear();
	}
	
	/**
	 * 
	 * @param key of a dictionary
	 * @return if given key exist in dictionary
	 * 			returns it's value else
	 * 			returns null
	 * */
	public V get(Object key) {
		if (key == null) return null;
		
		for (int i = 0; i < array.size(); i++) {
			if (array.get(i).key.equals(key)) {
				return array.get(i).value;
			}
		}
		return null;
	}
	
	/**
	 * @param key stores the given value in dictionary 
	 * @return if key exists before than return it's previos value
	 * 			and update it with new one else return null
	 * */
	public V put(K key, V value) {
		if (key == null) return null;
		
		for (int i = 0; i < array.size(); i++) {
			if (array.get(i).key.equals(key)) {
				V returnValue = array.get(i).value;
				array.get(i).setValue(value);
				return returnValue;
			}
		}
		array.add(new Pair<K,V>(key, value));
		return null;
	}
	
	/**
	 * @param key of a dictionary
	 * @return if key exists in dictionary 
	 * 			it and it's value are removed from dictionary
	 * 			and it's value is returned else
	 * 			if key doesn't exist return null
	 * */
	public V remove(K key) {
		if (key == null) return null;
		
		for (int i = 0; i < array.size(); i++) {
			if (array.get(i).key.equals(key)) {
				V value = array.get(i).value;
				array.remove(i);
				return value;
			}
		}
		return null;
	}
	
	/**
	 * @return String of all the dictionaries pairs as
	 * 			"key: value\n"
	 * */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		array.forEach(new Processor<Pair<K,V>>() {
			@Override
			public void process(Pair<K, V> value) {
				sb.append(value.key + ": " + value.value + '\n');
			}
		});
		return sb.toString();
	}
	
}
