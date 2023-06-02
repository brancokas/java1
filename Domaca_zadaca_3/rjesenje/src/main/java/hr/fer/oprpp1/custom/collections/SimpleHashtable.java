package hr.fer.oprpp1.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;


/**
 * class {@link SimpleHashtable} is a parametrized class
 * used for storing key-value pairs. It implements Iterable
 * interface for iterating over elements stored in class.
 * Class is parametrized by two types K and V.
 * K is type of key, V is type of value.
 * Key is unique for every pair and cannot be null,
 * whereas value can be null and they don't have to be unique
 * Elements of {@link SimpleHashtable} are instances of nested class
 * {@link TableEntry}
 * 
 * @author bstankovic
 * */
public class SimpleHashtable<K,V> implements Iterable<SimpleHashtable.TableEntry<K, V>>{
	
	/**
	 * Static class {@link TableEntry} that holds key-value pair
	 * parametrized by two types K and V, where K is the type of the key 
	 * and V is the type of the value.
	 * Class is a List that points to the next instance of TableEntry
	 * @throws NullPointerException if key is null
	 * */
	public static class TableEntry<K,V> {
		private K key;
		private V value;
		private TableEntry<K, V> next;
		
		public TableEntry(K key, V value) {
			if (key == null) throw new NullPointerException("Key must not be null");
			
			this.key = key;
			this.setValue(value);
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}
		
		@Override
		public String toString() {
			return key + "=" + value;
		}

		@Override
		public int hashCode() {
			return Objects.hash(key, value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TableEntry<K,V> other = (TableEntry<K,V>) obj;
			return Objects.equals(key, other.key) && Objects.equals(value, other.value);
		}
		
		
	}
	
	private TableEntry<K, V>[] tableEntry;
	private int size;
	private int modificationCount;
	
	/**
	 * Initializes SimpleHashtable slots to the nearest 
	 * greater than or equal to power of two of the given
	 * initalValue
	 * */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int initalValue) {
		if (initalValue < 1) 
			throw new IllegalArgumentException("Inital value must be greater than 0");
		
		int size = 1;
		for (;size < initalValue; size *= 2) {}
		tableEntry = new TableEntry[size];
		this.size=0;
		this.modificationCount = 0;
	}
	
	/**
	 * Initializes SimpleHashtable to 16 slots
	 * */
	public SimpleHashtable() {
		this(16);
	}
	
	/**
	 * @return size of the number of
	 * 			TableEntries in this instance
	 * */
	public int size() {
		return size;
	}
	
	/**
	 * @return true if there are no
	 * 			TableEntries in instance
	 * */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * @return calculates the hash of given key
	 * */
	private int getIndexOfHash(Object key) {
		int index = key.hashCode() < 0 ? -key.hashCode() : key.hashCode();
		return index % tableEntry.length;
	}
	
	/**
	 * @return true if current capacity is 
	 * 			greater than or equal to 75% 
	 * */
	private boolean isFilled75Percent() {
		if (Double.valueOf(size) / Double.valueOf(tableEntry.length) - Double.valueOf(0.75) >= 0) 
			return true;
		return false;
	}
	
	/**
	 * doubles the capacity of slots
	 * and recalculates and reallocates new slots
	 * for old entries and adds new entry
	 * */
	@SuppressWarnings("unchecked")
	private void doubleCapacity(K key, V value) {
		TableEntry<K, V>[] array = toArray();
		tableEntry = new TableEntry[tableEntry.length * 2];
		this.size = 0;
	
		for (int i = 0, size = array.length; i < size; i++) {
			put(array[i].key, array[i].value);
			this.modificationCount--;
		}
		this.modificationCount -= array.length;
		put(key, value);
		
	}
	
	/**
	 * 
	 * if current capacity of table is greater than or equal
	 * to 75% table doubles it's size and reallocates it's elements
	 * in new table
	 * @param key stores the given value in Hashtable in 
	 * 		slot determined by key's hash
	 * @return if key exists before than return it's previos value
	 * 			and update it with new one else return null
	 * */
	public V put(K key, V value) {
		if (key == null) return null;
		int index = getIndexOfHash(key);
		
		TableEntry<K, V> currNode = tableEntry[index], prevNode = null;
		if (currNode == null) {
			tableEntry[index] = new TableEntry<>(key, value);
			this.size++;
			return null;
		}
		while(currNode != null) {
			if (currNode.key.equals(key)) {
				V returnValue = currNode.value;
				currNode.value = value;
				return returnValue;
			}
			prevNode = currNode;
			currNode = currNode.next;
		}
		
		if (isFilled75Percent()) {
			doubleCapacity(key,value);
			return null;
		} 
		
		this.modificationCount++;
		prevNode.next = new TableEntry<>(key, value);			
		this.size++;
		return null;
	}
	
	/**
	 * 
	 * @param key of a table
	 * @return if given key exist in dictionary
	 * 			returns it's value else
	 * 			returns null
	 * */
	public V get(Object key) {
		if (key == null) return null;
		int index = getIndexOfHash(key);
		
		TableEntry<K, V> currNode = tableEntry[index];
		while (currNode != null) {
			if (currNode.key.equals(key)) {
				return currNode.value;
			}
			currNode = currNode.next;
		}
		return null;
	}
	
	/**
	 * @param Object that is stored as a key of a table
	 * @return true if given key exists in table
	 * 			else return false
	 * */
	public boolean containsKey(Object key) {
		if (key == null) return false; 
		int index = getIndexOfHash(key);
		
		TableEntry<K, V> currNode = tableEntry[index];
		while (currNode != null) {
			if (currNode.key.equals(key)) 
				return true;
			currNode = currNode.next;
		}
		return false;
		
	}
	
	/**
	 * @param Object that is stored as a value in table
	 * @return true if value exists in table
	 * 			else return false
	 * */
	public boolean containsValue(Object value) {
		for (int i = 0, length = tableEntry.length; i < length; i++) {
			TableEntry<K, V> currNode = tableEntry[i];
			while(currNode != null) {
				if (currNode.value == null) {
					if (currNode.value == value) return true;
				} else if (currNode.value.equals(value)) 
					return true;
				currNode = currNode.next;
			}
		}
		return false;
	}

	/**
	 * @param key of a table stored in a table to be removed with it's value
	 * @return if key exists in table
	 * 			it and it's value are removed from table
	 * 			and it's value is returned else
	 * 			if key doesn't exist return null
	 * */
	public V remove(Object key) {
		if (key == null) return null;
		int index = getIndexOfHash(key);
		
		if (tableEntry[index] == null) return null;
		
		TableEntry<K, V> currNode = tableEntry[index];
		V value = null;
		if (currNode.key.equals(key)) {
			value = currNode.value;
			tableEntry[index] = currNode.next;
			this.size--;
			this.modificationCount++;
		} else {
			TableEntry<K, V> prevNode = tableEntry[index];
			currNode = currNode.next;
			while(currNode != null) {
				if (currNode.key.equals(key)) {
					value = currNode.value;
					prevNode.next = currNode.next;
					this.size--;
					this.modificationCount++;
					return value;
				}
				prevNode = currNode;
				currNode = currNode.next;
			}
		}
		return value;
	}
	
	/**
	 * @return array of parametrized {@link TableEntry}
	 * 			in order they are stored in slots
	 * */
	@SuppressWarnings("unchecked")
	public TableEntry<K,V>[] toArray() {
		TableEntry<K, V>[] newTableEntry = new TableEntry[this.size];
		int index = 0;
		
		for (int i = 0, length = tableEntry.length; i < length; i++) {
			TableEntry<K, V> currNode = tableEntry[i];
			while (currNode != null) {
				newTableEntry[index++] = currNode;
				currNode = currNode.next;
			}
		}
		
		return newTableEntry;
	}
	
	/**
	 * @return String of all the table entries as
	 * 			"[key1=value1,..., keyn=valuen]"
	 * */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		TableEntry<K, V>[] thisTableEntry = toArray();
		
		sb.append('[');
		for (int i = 0, size = thisTableEntry.length; i < size; i++) {
			if (thisTableEntry[i].value == null) {
				sb.append(thisTableEntry[i].key.toString() + "=null");
			} else 
				sb.append(thisTableEntry[i].key.toString() + "=" + thisTableEntry[i].value.toString());
			if (i < size-1) {
				sb.append(", ");
			}
		}
		sb.append(']');
		return sb.toString();
	}

	/**
	 * removes every element in
	 * table and sets the size
	 * to zero
	 * */
	public void clear() {
		this.modificationCount++;
		for (int i = 0, size = tableEntry.length; i < size; i++) {
			TableEntry<K, V> currNode = tableEntry[i], prevNode;
			tableEntry[i] = null;
			while(currNode != null) {
				prevNode = currNode;
				currNode = currNode.next;
				prevNode.next = null;
			}
		}
		this.size = 0;
	}

	/**
	 * @return new Iterator over instance
	 * 		of table of {@link TableEntry}
	 * */
	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}
	
	/**
	 * Class {@link IteratorImpl} implements {@link Iterator}
	 * that iterates over table of {@link TableEntry} where 
	 * iterator can get next element delete only once current element
	 * and return if there is next element
	 * 
	 * @throws ConcurrentModificationException if table is changed that its size is altered
	 * while iterating through it and iterating method is called afterwards
	 * @throws NoSuchElementException if iterator.next() is called and there is no more
	 * elements to iterate
	 * @throws IllegalStateException if the iterator.remove() is called twice or more on the
	 * same entry
	 * @author bstankovic
	 * */
	private class IteratorImpl implements Iterator<TableEntry<K, V>> {
		private int modificationCount;
		private int index;
		private TableEntry<K, V> currentNode;
		
		public IteratorImpl() {
			this.modificationCount = SimpleHashtable.this.modificationCount;
			index = 0;
		}
		
		/**
		 * @return true if table has more unprocessed entries
		 * 			else false
		 * @throws ConcurrentModificationException if table is changed that its size is altered
		 * while iterating through it and iterating method is called afterwards
		 * */
		@Override
		public boolean hasNext() {
			if (this.modificationCount != SimpleHashtable.this.modificationCount)
				throw new ConcurrentModificationException("Can't change table from outside");
			
			if (index >= SimpleHashtable.this.size)
				return false;
			return true;
		}

		/**
		 * @return first unprocessed entry in table
		 * @throws NoSuchElementException if there are no more entries in table
		 * @throws ConcurrentModificationException if table is changed that its size is altered
		 * while iterating through it and iterating method is called afterwards
		 * */
		@Override
		public TableEntry<K, V> next() {
			if (this.modificationCount != SimpleHashtable.this.modificationCount)
				throw new ConcurrentModificationException("Can't change table from outside");
			if (!(hasNext())) 
				throw new NoSuchElementException("Nema vise elemenata u tablici.");
			
for_loop:	for (int i = 0, size = tableEntry.length, index = 0; i < size; i++) {
				TableEntry<K, V> currNode = tableEntry[i];
				while (currNode != null) {
					if (index == this.index) {
						this.index++;
						this.currentNode = currNode;
						break for_loop;
					}
					currNode = currNode.next;
					index++;
				}
			}
			return currentNode;
		}
		
		/**
		 * Removes from table current entry that iterator is on
		 * @throws IllegalStateException if the same entry is removed more than once 
		 * @throws ConcurrentModificationException if table is changed that its size is altered
		 * while iterating through it and iterating method is called afterwards
		 * */
		public void remove() {
			if (this.modificationCount != SimpleHashtable.this.modificationCount)
				throw new ConcurrentModificationException("Can't change table from outside");
			
			if (currentNode == null || !(containsKey(currentNode.key))) 
				throw new IllegalStateException("Cannot remove twice the same entry.");
			
			SimpleHashtable.this.remove(currentNode.key);
			this.modificationCount++;
			index--;
			
		}
		
	}
	
}
