package hr.fer.oprpp1.custom.collections;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class LinkedListIndexedCollectionTest {

	@Test
	public void testToArray() {
		Object[] a = new Object[3];
		a[0] = "String1";
		a[1] = "String2";
		a[2] = "String3";
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add("String1");
		arr.add("String2");
		arr.add("String3");
		assertArrayEquals(a, arr.toArray());
	}
	
	@Test
	public void testDefaultConstructor() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		assertEquals(0, arr.size());
	}
	
	@Test
	public void testConstructorPassCollection() {
		Collection c = new LinkedListIndexedCollection();
		c.add("String1");
		c.add("String2");
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection(c);
		assertArrayEquals(c.toArray(), arr.toArray());
	}
	
	@Test
	public void testConstructorPassNullCollectionException() {
		Collection c = null;
		assertThrows(NullPointerException.class, () -> new LinkedListIndexedCollection(c));
	}
	
	@Test
	public void testSizeOfList() {
		Integer a = Integer.valueOf(100), b = Integer.valueOf(100), c = Integer.valueOf(-100);
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add(a);
		arr.add(b);
		arr.add(c);
		arr.add(a);
		assertEquals(arr.size(), 4);
	}
	
	@Test
	public void testContainsElement() {
		Integer a = Integer.valueOf(100), b = Integer.valueOf(100);
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add(a);
		arr.add(b);
		assertTrue(arr.contains(b));
	}
	
	@Test
	public void testDoesntContainElement() {
		Integer a = Integer.valueOf(100), b = Integer.valueOf(100), c = Integer.valueOf(-100);
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add(a);
		arr.add(b);
		assertFalse(arr.contains(c));
	}
	
	@Test
	public void testDoesntContainNullInEmptyList() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		assertFalse(arr.contains(null));
	}
	
	@Test
	public void testRemoveElement() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add("String1");
		arr.add("String2");
		arr.remove("String1");
		assertArrayEquals(arr.toArray(), new String[] {"String2"});
	}
	
	@Test
	public void testRemoveElementFromListToBeEmpty() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add("String1");
		arr.add("String2");
		arr.remove("String1");
		arr.remove("String2");
		assertArrayEquals(arr.toArray(), new Object[] {});
	}
	
	@Test
	public void testRemoveNullFromNullList() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		assertFalse(arr.remove(null));
	}
	
	@Test
	public void testRemoveAt() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add("String1");
		arr.add("String2");
		arr.add("String3");
		arr.remove(1);
		assertArrayEquals(arr.toArray(), new String[] {"String1", "String3"});
	}
	
	@Test
	public void testRemoveAtBeginning() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add("String1");
		arr.add("String2");
		arr.add("String3");
		arr.remove(0);
		assertArrayEquals(arr.toArray(), new String[] {"String2", "String3"});
	}

	@Test
	public void testRemoveAtEnd() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add("String1");
		arr.add("String2");
		arr.add("String3");
		arr.remove(arr.size()-1);
		assertArrayEquals(arr.toArray(), new String[] {"String1", "String2"});
	}
	
	@Test
	public void testRemoveAtOnlyElement() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add("String1");
		arr.remove(0);
		assertArrayEquals(arr.toArray(), new String[] {});
	}
	
	@Test
	public void testRemoveAtIndexUnderListBoundException() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add("String1");
		assertThrows(IndexOutOfBoundsException.class, () -> arr.remove(-1));
	}
	
	@Test
	public void testRemoveAtIndexOverListBoundException() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add("String1");
		assertThrows(IndexOutOfBoundsException.class, () -> arr.remove(arr.size()));
	}

	@Test
	public void testRemoveAtNullListException() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> arr.remove(0));
	}

	@Test
	public void testAddNullValueException() {
		Object o = null;
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		assertThrows(NullPointerException.class, () -> arr.add(o));
	}

	@Test
	public void testClearListSize() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add("String1");
		arr.add("String2");
		arr.clear();
		assertEquals(0, arr.size());
	}
	
	@Test
	public void testClearList() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add("String1");
		arr.add("String2");
		arr.clear();
		assertArrayEquals(arr.toArray(), new String[] {});
	}
	
	@Test
	public void testClearRemoveException() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add("String1");
		arr.add("String2");
		arr.clear();
		assertThrows(IndexOutOfBoundsException.class, () -> arr.remove(0));
	}

	@Test
	public void testClearRemoveNull() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add("String");
		arr.clear();
		assertFalse(arr.remove(null));
	}

	@Test
	public void testIndexOfNullList() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		assertEquals(-1, arr.indexOf(null));
	}
	
	@Test
	public void testIndexOfNullInClearedList() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add("String");
		arr.add("String");
		arr.clear();
		assertEquals(-1, arr.indexOf(null));
	}
	
	@Test
	public void testIndexOfFirstOccuranceInList() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add("String1");
		arr.add("String");
		arr.add("String2");
		arr.add("String");
		assertEquals(1, arr.indexOf("String"));
	}
	
	@Test
	public void testIndexOfFirstElement() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add("String1");
		arr.add("String2");
		assertEquals(0, arr.indexOf("String1"));
	}
	
	@Test
	public void testIndexOfLastElement() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add("String1");
		arr.add("String2");
		assertEquals(arr.size()-1, arr.indexOf("String2"));
	}

	@Test
	public void testAddAllNullCollectionException() {
		Collection c = null;
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		assertThrows(NullPointerException.class, () -> arr.addAll(c));
	}
	
	@Test
	public void testAddAllToEmptyList() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add("String1");
		arr.add("String2");
		LinkedListIndexedCollection arr2 = new LinkedListIndexedCollection();
		arr2.addAll(arr);
		assertArrayEquals(arr.toArray(), arr2.toArray());
	}
	
	@Test
	public void testAddAllToListWithValues() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add("String");
		arr.add("String");
		LinkedListIndexedCollection arr2 = new LinkedListIndexedCollection(arr);
		arr2.addAll(arr);
		assertArrayEquals(new String[] {"String", "String", "String", "String"}, arr2.toArray());
	}
	
	@Test
	public void testAddAllNullCollectionToListWithValuesException() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add("String1");
		arr.add("String2");
		LinkedListIndexedCollection arr2 = new LinkedListIndexedCollection();
		arr2.addAll(arr);
		assertThrows(NullPointerException.class, () -> arr2.addAll(null));
	}
	

	@Test
	public void testInsertAtBeginningOfList() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.insert("String", 0);
		arr.insert("Integer", 0);
		assertArrayEquals(arr.toArray(), new String[] {"Integer", "String"});
	}
	
	@Test
	public void testInsertAtEndOfList() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.insert("String", arr.size());
		arr.insert("Integer", arr.size());
		assertArrayEquals(arr.toArray(), new String[] {"String", "Integer"});
	}
	
	@Test
	public void testInsertTooSmallIndexException() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> arr.insert("", -1));
	}

	@Test
	public void testInsertTooGreatIndexException() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> arr.insert("", arr.size()+1));
	}

	@Test
	public void testInsertNullException() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		assertThrows(NullPointerException.class, () -> arr.insert(null, arr.size()));
	}
	
	@Test
	public void testGetFirstElement() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add("String");
		assertEquals("String", arr.get(0));
	}

	@Test
	public void testGetLastElement() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add("String");
		assertEquals("String", arr.get(arr.size()-1));
	}

	@Test
	public void testGetElementTooSmallIndexException() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add("String");
		assertThrows(IndexOutOfBoundsException.class, () -> arr.get(-1));
	}

	@Test
	public void testGetElementTooGreatIndexException() {
		LinkedListIndexedCollection arr = new LinkedListIndexedCollection();
		arr.add("String");
		assertThrows(IndexOutOfBoundsException.class, () -> arr.get(arr.size()));
	}

}
