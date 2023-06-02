package hr.fer.oprpp1.custom.collections;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class ArrayIndexedCollectionTest {

	@Test
	public void testToArray() {
		Object[] a = new Object[3];
		a[0] = "String1";
		a[1] = "String2";
		a[2] = "String3";
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.add("String1");
		arr.add("String2");
		arr.add("String3");
		assertArrayEquals(a, arr.toArray());
	}
	
	@Test
	public void testDefaultConstructor() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		assertEquals(0, arr.size());
	}
	
	@Test
	public void testConstructorPassCollection() {
		Collection c = new ArrayIndexedCollection();
		c.add("String1");
		c.add("String2");
		ArrayIndexedCollection arr = new ArrayIndexedCollection(c);
		assertArrayEquals(c.toArray(), arr.toArray());
	}
	
	@Test
	public void testConstructorInitalValueSmallerThanCollectionSize() {
		Collection c = new ArrayIndexedCollection();
		c.add("String1");
		c.add("String2");
		c.add("String3");
		ArrayIndexedCollection arr = new ArrayIndexedCollection(c, 0);
		assertEquals(arr.size(), 3);
	}
	
	@Test
	public void testConstructorPassNullCollectionException() {
		Collection c = null;
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(c));
	}
	
	@Test
	public void testConstructorInitalValueSmallerThanOneException() {
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(0));
	}
	
	@Test
	public void testSizeOfArray() {
		Integer a = Integer.valueOf(100), b = Integer.valueOf(100), c = Integer.valueOf(-100);
		ArrayIndexedCollection arr = new ArrayIndexedCollection(2);
		arr.add(a);
		arr.add(b);
		arr.add(c);
		arr.add(a);
		assertEquals(arr.size(), 4);
	}
	
	@Test
	public void testContainsElement() {
		Integer a = Integer.valueOf(100), b = Integer.valueOf(100);
		ArrayIndexedCollection arr = new ArrayIndexedCollection(2);
		arr.add(a);
		arr.add(b);
		assertTrue(arr.contains(b));
	}
	
	@Test
	public void testDoesntContainElement() {
		Integer a = Integer.valueOf(100), b = Integer.valueOf(100), c = Integer.valueOf(-100);
		ArrayIndexedCollection arr = new ArrayIndexedCollection(2);
		arr.add(a);
		arr.add(b);
		assertFalse(arr.contains(c));
	}
	
	@Test
	public void testDoesntContainNullInEmptyArray() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		assertFalse(arr.contains(null));
	}
	
	@Test
	public void testRemoveElement() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.add("String1");
		arr.add("String2");
		arr.remove("String1");
		assertArrayEquals(arr.toArray(), new String[] {"String2"});
	}
	
	@Test
	public void testRemoveElementFromArrayToBeEmpty() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.add("String1");
		arr.add("String2");
		arr.remove("String1");
		arr.remove("String2");
		assertArrayEquals(arr.toArray(), new Object[] {});
	}
	
	@Test
	public void testRemoveNullFromNullArray() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		assertFalse(arr.remove(null));
	}
	
	@Test
	public void testRemoveAt() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.add("String1");
		arr.add("String2");
		arr.add("String3");
		arr.remove(1);
		assertArrayEquals(arr.toArray(), new String[] {"String1", "String3"});
	}
	
	@Test
	public void testRemoveAtBeginning() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.add("String1");
		arr.add("String2");
		arr.add("String3");
		arr.remove(0);
		assertArrayEquals(arr.toArray(), new String[] {"String2", "String3"});
	}

	@Test
	public void testRemoveAtEnd() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.add("String1");
		arr.add("String2");
		arr.add("String3");
		arr.remove(arr.size()-1);
		assertArrayEquals(arr.toArray(), new String[] {"String1", "String2"});
	}
	
	@Test
	public void testRemoveAtOnlyElement() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.add("String1");
		arr.remove(0);
		assertArrayEquals(arr.toArray(), new String[] {});
	}
	
	@Test
	public void testRemoveAtIndexUnderArrayBoundException() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.add("String1");
		assertThrows(IndexOutOfBoundsException.class, () -> arr.remove(-1));
	}
	
	@Test
	public void testRemoveAtIndexOverArrayBoundException() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.add("String1");
		assertThrows(IndexOutOfBoundsException.class, () -> arr.remove(arr.size()));
	}

	@Test
	public void testRemoveAtNullArrayException() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> arr.remove(0));
	}

	@Test
	public void testAddNullValueException() {
		Object o = null;
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		assertThrows(NullPointerException.class, () -> arr.add(o));
	}

	@Test
	public void testCapacityAddToFullArray() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection(2);
		arr.add("String1");
		arr.add("String2");
		arr.add("String3");
		assertEquals(3, arr.size());
	}
	
	@Test
	public void testItemsAddToFullArray() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection(2);
		arr.add("String1");
		arr.add("String2");
		arr.add("String3");
		assertArrayEquals(arr.toArray(), new String[] {"String1", "String2", "String3"});
	}

	@Test
	public void testClearSizeOfArray() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.add("String1");
		arr.add("String2");
		arr.clear();
		assertEquals(0, arr.size());
	}
	
	@Test
	public void testClearArray() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.add("String1");
		arr.add("String2");
		arr.clear();
		assertArrayEquals(arr.toArray(), new String[] {});
	}
	
	@Test
	public void testClearRemoveException() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.add("String1");
		arr.add("String2");
		arr.clear();
		assertThrows(IndexOutOfBoundsException.class, () -> arr.remove(0));
	}

	@Test
	public void testClearRemoveNull() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.add("String");
		arr.clear();
		assertFalse(arr.remove(null));
	}

	@Test
	public void testIndexOfNullInEmptyArray() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		assertEquals(-1, arr.indexOf(null));
	}
	
	@Test
	public void testIndexOfNullInClearedArray() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.add("String");
		arr.add("String");
		arr.clear();
		assertEquals(-1, arr.indexOf(null));
	}
	
	@Test
	public void testIndexOfFirstOccuranceInArray() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.add("String1");
		arr.add("String");
		arr.add("String2");
		arr.add("String");
		assertEquals(1, arr.indexOf("String"));
	}
	
	@Test
	public void testIndexOfFirstElement() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.add("String1");
		arr.add("String2");
		assertEquals(0, arr.indexOf("String1"));
	}
	
	@Test
	public void testIndexOfLastElement() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.add("String1");
		arr.add("String2");
		assertEquals(arr.size()-1, arr.indexOf("String2"));
	}

	@Test
	public void testAddAllNullCollectionException() {
		Collection c = null;
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		assertThrows(NullPointerException.class, () -> arr.addAll(c));
	}
	
	@Test
	public void testAddAllToEmptyArray() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.add("String1");
		arr.add("String2");
		ArrayIndexedCollection arr2 = new ArrayIndexedCollection();
		arr2.addAll(arr);
		assertArrayEquals(arr.toArray(), arr2.toArray());
	}
	
	@Test
	public void testAddAllToArrayWithValues() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.add("String");
		arr.add("String");
		ArrayIndexedCollection arr2 = new ArrayIndexedCollection(arr);
		arr2.addAll(arr);
		assertArrayEquals(new String[] {"String", "String", "String", "String"}, arr2.toArray());
	}
	
	@Test
	public void testAddAllNullCollectionToArrayWithValuesException() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.add("String1");
		arr.add("String2");
		ArrayIndexedCollection arr2 = new ArrayIndexedCollection();
		arr2.addAll(arr);
		assertThrows(NullPointerException.class, () -> arr2.addAll(null));
	}
	
	@Test
	public void testInsertAtBeginningOfArray() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.insert("String", 0);
		arr.insert("Integer", 0);
		assertArrayEquals(arr.toArray(), new String[] {"Integer", "String"});
	}
	
	@Test
	public void testInsertAtEndOfArray() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.insert("String", arr.size());
		arr.insert("Integer", arr.size());
		assertArrayEquals(arr.toArray(), new String[] {"String", "Integer"});
	}
	
	@Test
	public void testInsertTooSmallIndexException() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> arr.insert("", -1));
	}

	@Test
	public void testInsertTooGreatIndexException() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		assertThrows(IndexOutOfBoundsException.class, () -> arr.insert("", arr.size()+1));
	}

	@Test
	public void testInsertNullException() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		assertThrows(NullPointerException.class, () -> arr.insert(null, arr.size()));
	}
	
	@Test
	public void testGetFirstElement() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.add("String");
		assertEquals("String", arr.get(0));
	}

	@Test
	public void testGetLastElement() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.add("String");
		assertEquals("String", arr.get(arr.size()-1));
	}

	@Test
	public void testGetElementTooSmallIndexException() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.add("String");
		assertThrows(IndexOutOfBoundsException.class, () -> arr.get(-1));
	}

	@Test
	public void testGetElementTooGreatIndexException() {
		ArrayIndexedCollection arr = new ArrayIndexedCollection();
		arr.add("String");
		assertThrows(IndexOutOfBoundsException.class, () -> arr.get(arr.size()));
	}
	
}
