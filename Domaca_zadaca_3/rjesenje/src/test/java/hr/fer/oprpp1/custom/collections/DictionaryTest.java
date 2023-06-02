package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DictionaryTest {

	@Test
	public void emptyDictionaryTest() {
		Dictionary<Integer, String> dict = new Dictionary<>();
		assertTrue(dict.isEmpty());
	}
	
	@Test
	public void isEmptyTest() {
		Dictionary<Integer, String> dict = new Dictionary<>();
		dict.put(null, null);
		dict.put(null, "String");
		assertTrue(dict.isEmpty());
	}
	
	@Test
	public void isNotEmptyTest() {
		Dictionary<Integer, String> dict = new Dictionary<>();
		dict.put(12, null);
		dict.put(12, "String");
		assertFalse(dict.isEmpty());
	}
	
	@Test
	public void sizeOfEmptyDictionaryTest() {
		Dictionary<Integer, String> dict = new Dictionary<>();
		dict.put(null, null);
		dict.put(null, "String");
		assertEquals(dict.size(), 0);
	}
	
	@Test
	public void sizeOfNonEmptyDictionaryTest() {
		Dictionary<Integer, String> dict = new Dictionary<>();
		dict.put(12, null);
		dict.put(12, "String");
		dict.put(13, null);
		assertEquals(dict.size(), 2);
	}
	
	@Test
	public void clearEmptyDictionaryTest() {
		Dictionary<Integer, String> dict = new Dictionary<>();
		dict.clear();
		assertEquals(dict.size(), 0);
	}
	
	@Test
	public void sizeOfClearedDictionaryTest() {
		Dictionary<Integer, String> dict = new Dictionary<>();
		dict.put(13, null);
		dict.put(12, "String");
		dict.clear();
		assertEquals(dict.size(), 0);
	}
	
	@Test
	public void clearDictionaryTest() {
		Dictionary<Integer, String> dict = new Dictionary<>();
		dict.put(100, null);
		dict.put(200, "String");
		dict.put(200, null);
		dict.clear();
		assertEquals(dict.toString(), "");
	}
	
	@Test
	public void putNullTest() {
		Dictionary<Integer, String> dict = new Dictionary<>();
		dict.put(null, null);
		dict.put(null, "120");
		assertEquals(dict.size(), 0);
	}
	
	@Test
	public void putSameKeysTest() {
		Dictionary<Integer, String> dict = new Dictionary<>();
		dict.put(120, null);
		dict.put(120, "120");
		assertEquals(dict.get(120), "120");
	}
	
	@Test
	public void getNullKeyTest() {
		Dictionary<Integer, String> dict = new Dictionary<>();
		dict.put(null, "String");
		assertEquals(dict.get(null), null);
	}
	
	@Test
	public void getNonNullKeyTest() {
		Dictionary<Integer, String> dict = new Dictionary<>();
		dict.put(120, "String");
		dict.put(120, "Integer");
		assertEquals(dict.get(120), "Integer");
	}
	
	@Test
	public void removeNullKeyTest() {
		Dictionary<Integer, String> dict = new Dictionary<>();
		dict.put(null, "String");
		assertEquals(dict.remove(null), null);
	}
	
	@Test
	public void removeNonNullKeyTest() {
		Dictionary<Integer, String> dict = new Dictionary<>();
		dict.put(120, "String");
		dict.remove(120);
		assertEquals(dict.get(120), null);
	}
	
	@Test
	public void removeNonNullKeyValueTest() {
		Dictionary<Integer, String> dict = new Dictionary<>();
		dict.put(120, "String");
		assertEquals(dict.remove(120), "String");
	}
	
	@Test
	public void removeEmptyDictionaryTest() {
		Dictionary<Integer, String> dict = new Dictionary<>();
		dict.remove(120);
		assertEquals(dict.size(), 0);
	}
	
	@Test
	public void removeValueInsteadOfKeyTest() {
		Dictionary<Integer, Integer> dict = new Dictionary<>();
		dict.put(150, 120);
		dict.remove(120);
		assertEquals(dict.size(), 1);
	}
}
