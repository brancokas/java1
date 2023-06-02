package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.EmptyStackException;
import hr.fer.oprpp1.custom.collections.ObjectStack;

public class StackDemo {
	
	private static String[] split(String str) {
		int size = 0;
		
		char c;
		for (int i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			if((c >= '0' && c <= '9' || c == '-' || c == '+' || c == '*' ||
				c == '/' || c == '%')) {
				if (i == str.length()-1 || str.charAt(i+1) == ' ') {
					size++;
				}
			} else if (c != ' ') 
				throw new IllegalArgumentException("Wrong input charachter " + c + ".");
		}
		
		String[] newString = new String[size];
		size = 0;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			if((c >= '0' && c <= '9' || c == '-' || c == '+' || c == '*' ||
				c == '/' || c == '%')) {
				sb.append(c);
				if (str.length()-1 == i) {
					newString[size] = sb.toString();
				}
			} else if (c == ' ') {
				if (sb.length() != 0) {
					newString[size++] = sb.toString();
					sb.delete(0, sb.length());
				}
			}
		}
		
		return newString;
	}
	
	private static boolean isNumber(String str) {
		if (str.length() == 1 && (str.charAt(0) == '+' ||
			str.charAt(0) == '-' || str.charAt(0) == '*' ||
			str.charAt(0) == '/' || str.charAt(0) == '%')) return false;
		return true;
	}
	
	private static Integer mod(Integer prvi, Integer drugi) {
		if (prvi < 0 && drugi < 0) return -(prvi % drugi);
		if (prvi > 0 && drugi < 0) return -(prvi % drugi);
		return prvi % drugi;
	}
	
	private static Integer calculate(Object prvi, Object drugi, Object operator) {
		Integer prviInt = Integer.valueOf(String.valueOf(prvi));
		Integer drugiInt = Integer.valueOf(String.valueOf(drugi));
		String operatorStr = String.valueOf(operator);
		
		if (operatorStr.equals("+")) {
			return prviInt + drugiInt;
		}
		if (operatorStr.equals("-")) {
			return prviInt - drugiInt;
		}
		if (operatorStr.equals("*")) {
			return prviInt * drugiInt;
		}
		if (operatorStr.equals("/")) {
			if (drugiInt.compareTo(Integer.valueOf(0)) == 0) 
				throw new IllegalArgumentException("Can't divide by zero.");
			return prviInt / drugiInt;
		}
		if (drugiInt.compareTo(Integer.valueOf(0)) == 0) 
			throw new IllegalArgumentException("Can't mod by zero");
		return mod(prviInt,drugiInt);
		
	}
	
	
	public static void main(String[] args) {				
		try {
			
			if (args.length != 1) 
				throw new IllegalArgumentException("Input is not String[] of length 1.");
			
			ObjectStack stack = new ObjectStack();
		
			String[] elements = split(args[0]);
		
		
			for (int i = 0, size = elements.length; i < size; i++) {
				if (isNumber(elements[i])) {
					stack.push(elements[i]);
				} else {
					Object prvi, drugi;
					drugi = stack.pop();
					prvi = stack.pop();
					stack.push(calculate(prvi, drugi, elements[i]));
				}
			}
			if (stack.size() != 1) {
				System.out.println("Error stack is not valid.");
			} else {
				System.out.println("Expression evaluates to " + stack.pop());
			}
		} catch(IllegalArgumentException ex) {
			System.out.println("Error occured: " + ex.getMessage());
		} catch(EmptyStackException ex) {
			System.out.println("Error occured: Not enough operators on stack!");
		} catch(NullPointerException ex) {
			System.out.println("Error occured: Null cannot be added to stack!");
		} catch (Exception ex) {
			System.out.println("Error occured!" + ex.getMessage());
		}
	}
}
