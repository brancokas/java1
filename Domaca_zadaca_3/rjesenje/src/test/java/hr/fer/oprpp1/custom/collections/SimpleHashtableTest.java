package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.collections.SimpleHashtable.TableEntry;

public class SimpleHashtableTest {

	@Test
	public void initalCapacityLessThanOneExceptionTest() {
		assertThrows(IllegalArgumentException.class, 
				() -> new SimpleHashtable<Integer, Integer>(0));
	}
	
	@Test
	public void getNullKeyTest() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		map.put(null, 120);
		assertEquals(map.get(null), null);
	}
	
	@Test
	public void getNonNullKeyTest() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		map.put(120, 120);
		map.put(120, -120);
		assertEquals(map.get(120), -120);
	}
	
	@Test
	public void getNonNullKeyNullValueTest() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		map.put(120, 120);
		map.put(120, null);
		assertEquals(map.get(120), null);
	}
	
	@Test
	public void sizeOfEmptyTest() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		assertEquals(map.size(), 0);
	}
	
	@Test
	public void sizeOfNullKeyEntrieTest() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		map.put(null, null);
		map.put(null, 120);
		assertEquals(map.size(), 0);
	}
	
	@Test
	public void containsNullKeyTest() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		map.put(null, 120);
		map.put(null, null);
		assertFalse(map.containsKey(null));
	}
	
	@Test
	public void containsNullKeyNullValueTest() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		map.put(null, 120);
		map.put(null, null);
		assertFalse(map.containsValue(null));
	}
	
	@Test
	public void containsNonNullKeyTest() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		map.put(120, 120);
		map.put(120, null);
		assertTrue(map.containsKey(120));
	}
	
	@Test
	public void containsNonNullKeyNullValueTest() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		map.put(120, 120);
		map.put(120, null);
		assertTrue(map.containsValue(null));
	}
	
	@Test
	public void containsOverwrittenValueTest() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		map.put(120, 120);
		map.put(120, null);
		assertFalse(map.containsValue(120));
	}
	
	@Test
	public void removeNullKeyTest() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		map.put(null, 120);
		assertEquals(map.remove(null), null);
	}
	
	@Test
	public void removeNonNullKeyTest() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		map.put(100, 120);
		assertEquals(map.remove(100), 120);
	}
	
	@Test
	public void removeNonNullKeySizeTest() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		map.put(210, 120);
		map.remove(210);
		assertEquals(map.size(), 0);
	}
	
	@Test
	public void removeNonNullKeyTwiceTest() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		map.put(210, 120);
		map.remove(210);
		assertEquals(map.remove(210), null);
	}
	
	@Test
	public void isEmptyTest() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		assertTrue(map.isEmpty());
	}
	
	@Test
	public void isEmptyNullKeyEntriesTest() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		map.put(null, null);
		map.put(null, 120);
		assertTrue(map.isEmpty());
	}
	
	@Test
	public void isEmptyNullValueEntriesTest() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		map.put(120, null);
		map.put(1200, null);
		assertFalse(map.isEmpty());
	}
	
	@Test
	public void toStringTest() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		map.put(100, null);
		map.put(200, 120);
		assertEquals(map.toString(), "[100=null, 200=120]");
	}
	
	@Test
	public void toStringEmptyHashtableTest() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		map.put(null, null);
		map.put(null, 120);
		assertEquals(map.toString(), "[]");
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void toArrayTest() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		map.put(100, null);
		map.put(200, 120);
		SimpleHashtable.TableEntry<Integer, Integer>[] array = new TableEntry[2];
		array[0] = new SimpleHashtable.TableEntry<>(100,null);
		array[1] = new SimpleHashtable.TableEntry<>(200,120);
		assertArrayEquals(map.toArray(), array);
	}
	
	@Test
	public void clearHashtableTest() {
		SimpleHashtable<Integer, Integer> map = new SimpleHashtable<>();
		map.put(100, null);
		map.put(1000, null);
		map.clear();
		assertEquals(map.size(), 0);
	}
	
	@Test
	public void iteratorTest() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		StringBuilder sb = new StringBuilder();
		for(SimpleHashtable.TableEntry<String,Integer> pair : examMarks) {
			sb.append(pair.getKey() + " => " + pair.getValue() + '\n'); 
		}
		String s = "Ante => 2\nIvana => 5\nJasna => 2\nKristina => 5\n";
		assertEquals(sb.toString(), s);
	}
	
	@Test
	public void iteratorHasNextTest() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator(); 
		assertFalse(iter.hasNext());
	}
	
	@Test
	public void iteratorRemoveExceptionTest() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator(); 
		while(iter.hasNext()) {
		      SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
		      if(pair.getKey().equals("Ivana")) {
		            iter.remove();
		            assertThrows(IllegalStateException.class, () -> iter.remove());
		      }
		}
	}
	
	@Test
	public void iteratorRemoveTest() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		
		SimpleHashtable<String,Integer> examMarks2 = new SimpleHashtable<>(2);
		examMarks2.put("Ivana", 2);
		examMarks2.put("Ante", 2);
		examMarks2.put("Jasna", 2);
		examMarks2.put("Kristina", 5);
		examMarks2.put("Ivana", 5);
		examMarks2.remove("Ivana");
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator(); 
		while(iter.hasNext()) {
		      SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
		      if(pair.getKey().equals("Ivana")) {
		            iter.remove();
		      }
		}
		assertArrayEquals(examMarks.toArray(), examMarks2.toArray());
	}
	
	@Test
	public void iteratorRemoveBeforeNextExceptionTest() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator(); 
		assertThrows(IllegalStateException.class, () -> iter.remove());
	}
	
	@Test
	public void iteratorNextEmptyHashtableExceptionTest() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator(); 
		assertThrows(NoSuchElementException.class, () -> iter.next());
	}
	
	@Test
	public void iteratorRemoveEmptyHashtableExceptionTest() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator(); 
		assertThrows(IllegalStateException.class, () -> iter.remove());
	}
	
	@Test
	public void iteratorRemoveFromOutsideExceptionTest() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator(); 
		try {
			while(iter.hasNext()) {
		      	SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
		      	if(pair.getKey().equals("Ivana")) {
		    	  	examMarks.remove("Ivana"); 
		      	}
			}
		} catch (Exception ex) {
			assertEquals(ex.getMessage(), "Can't change table from outside");
		}
	}
	
	@Test
	public void iteratorAddFromOutsideExceptionTest() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator(); 
		try {
			while(iter.hasNext()) {
		      	SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
		      	if(pair.getKey().equals("Ivana")) {
		    	  	examMarks.put("Ivanovic", 10); 
		      	}
			}
		} catch (Exception ex) {
			assertEquals(ex.getMessage(), "Can't change table from outside");
		}
	}
	
	@Test
	public void iteratorClearFromOutsideExceptionTest() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator(); 
		try {
			while(iter.hasNext()) {
		      	SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
		      	if(pair.getKey().equals("Ivana")) {
		    	  	examMarks.clear();
		      	}
			}
		} catch (Exception ex) {
			assertEquals(ex.getMessage(), "Can't change table from outside");
		}
	}
	
	@Test
	public void iteratorAlterKeyValueFromOutsideTest() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator(); 
		while(iter.hasNext()) {
			SimpleHashtable.TableEntry<String,Integer> pair = iter.next();
			if(pair.getKey().equals("Ivana")) {
				examMarks.put("Ivana", 100); 
			}
		}
		assertEquals(examMarks.get("Ivana"), 100);	
	}
	
	@Test
	public void iteratorRemoveAllItemsSizeTest() {
		SimpleHashtable<String,Integer> examMarks = new SimpleHashtable<>(2);
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5);
		
		Iterator<SimpleHashtable.TableEntry<String,Integer>> iter = examMarks.iterator(); 
		while(iter.hasNext()) {
			iter.next();
			iter.remove();
		}
		assertEquals(examMarks.size(), 0);
		
	}
}
