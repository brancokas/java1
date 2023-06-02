package hr.fer.oprpp1.custom.scripting.nodes;

import java.util.Arrays;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;
/**
 * class EchoNode represents
 * all the elements where tag starts
 * with =
 * There can be arbitrary number of elements
 * and can be empty
 * 
 * @author bstankovic
 * @version 1.0
 * */
public class EchoNode extends Node {
	private Element[] elements;
	
	/**
	 * constructor that arbitrary numerous number of elements
	 * */
	public EchoNode(Element[] elements) {
		if (elements == null) throw new SmartScriptParserException("Wrong input in EQUAL-tag");
		this.elements = elements;
	}
	
	// {$= ...elements $}
	/**
	 * @return all the elements of the tag
	 * */
	public Element[] elements() {
		return elements;
	}
	
	/**
	 * @return String of echo node of all the elements
	 * 			that are indented in respect to other Nodes
	 * */
	@Override
	public String toStringDocumentIndented() {
		StringBuilder sb = new StringBuilder("Echo Node:");
		
		for (int i = 0, size = elements.length; i < size; i++) {
			if (elements[i] == null) break;
			sb.append(" ");
			sb.append(elements[i].asText());
		}
		return sb.toString();
	}
	
	/**
	 * @return record that has been parsed from the original text 
	 * */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{$=");
		
		for (int i = 0, size = elements.length; i < size; i++) {
			if (elements[i] == null) break;
			sb.append(" ");
			sb.append(elements[i].asText());
		}
		sb.append("$}");
		return sb.toString();
	}

	/**
	 * equal if every element is equal to others every element
	 * */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EchoNode other = (EchoNode) obj;
		return Arrays.equals(elements, other.elements);
	}
	
	
}
