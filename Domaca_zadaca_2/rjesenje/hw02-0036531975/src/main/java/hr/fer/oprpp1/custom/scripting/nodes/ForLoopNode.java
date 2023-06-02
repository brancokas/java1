package hr.fer.oprpp1.custom.scripting.nodes;

import hr.fer.oprpp1.custom.scripting.elems.Element;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;
/**
 * Class ForLoopNode that represents
 * all the elements in for tag
 * Class can have 3 or 4 elements
 * where first element must be a variable
 * and middle/stepexpression can be null
 * if there are 3 elements
 * 
 * @author bstankovic
 * @version 1.0
 * */
public class ForLoopNode extends Node {
	private ElementVariable variable;
	private Element startExpression;
	private Element stepExpression;
	private Element endExpression;

	/**
	 * constructor records elements of the for tag
	 * @throws SmartScriptParserException if first element isn't variable
	 * */
	public ForLoopNode(Element[] elements) {
		if (!(elements[0] instanceof ElementVariable)) 
			throw new SmartScriptParserException("First element in For-tag must be a variable");
		
		this.variable = (ElementVariable)elements[0];
		this.startExpression = (Element)elements[1];
		this.stepExpression = (Element)elements[2];
		if (elements[3] == null) {
			this.endExpression = this.stepExpression;
			this.stepExpression = null;
		} else
		this.endExpression = (Element)elements[3];
	}
	
	
	//has to exist
	/**
	 * @return variable of for tag
	 * */
	public ElementVariable variable() {
		return variable;
	}
	//has to exist Element:variable/number/string
	/**
	 * @return second element of for tag
	 * */
	public Element startExpression() {
		return startExpression;
	}
	//has to exist Element:variable/number/string
	/**
	 * @return third element of for tag
	 * */
	public Element endExpression() {
		return endExpression;
	}
	//can be null Element:variable/number/string
	/**
	 * @return fourth element of for tag,
	 * 			can be null if doesn't exist
	 * */
	public Element stepExpression() {
		return stepExpression;
	}
	
	/**
	 * @return String where each direct children of for tag
	 * 			is indented in respect to other nodes of the file
	 * Node is a child of a for tag until it isn't closed by its end tag
	 * */
	@Override
	public String toStringDocumentIndented() {
		StringBuilder sb = new StringBuilder("For loop Node: ");
		if (stepExpression == null) {
			sb.append(variable.asText() + ' ' + startExpression.asText() +
					' ' + endExpression.asText());
		} else
		sb.append(variable.asText() + ' ' + startExpression.asText() + ' '
				+ stepExpression + ' ' + endExpression.asText());
		if (this.numberOfChildren() > 0) sb.append('\n');
		this.incrementIndent();
		for (int i = 0, size = this.numberOfChildren(); i < size; i++) {
			if (i == size-1) {
				sb.append(this.getChild(i).toStringIndented(String.valueOf(i) + ": "));
			} else
			sb.append(this.getChild(i).toStringIndented(String.valueOf(i) + ": ") + '\n');
		}
		this.decrementIndent();
		return sb.toString();
	}
	
	/**
	 * @return String of for tag elements and all
	 * 		of its children as it is recorded originaly in text
	 * */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{$for ");
		if (stepExpression == null) {
			sb.append(variable.asText() + ' ' + startExpression.asText() +
					' ' + endExpression.asText());
		} else
		sb.append(variable.asText() + ' ' + startExpression.asText() + ' '
				+ stepExpression + ' ' + endExpression.asText());
		sb.append("$}");
		for (int i = 0, size = this.numberOfChildren(); i < size; i++) {
			sb.append(this.getChild(i).toString());
		}
		sb.append("{$end$}");
		return sb.toString();
	}
	
	/**
	 * @return <code>true</code> if for tag elements are equal to other's
	 * 			and if every child is equal to other's children
	 * */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (!(obj instanceof ForLoopNode)) return false;
		
		ForLoopNode node = (ForLoopNode)obj;
		
		if (node.numberOfChildren() != this.numberOfChildren()) return false;
		
		if (node.stepExpression == null && this.stepExpression != null ||
				node.stepExpression != null && this.stepExpression == null) return false;
		
		if (node.variable.equals(this.variable) && node.startExpression.equals(this.startExpression)
				&& node.endExpression.equals(this.endExpression) &&
				(node.stepExpression == null && this.stepExpression == null || 
				node.stepExpression.equals(this.stepExpression))) {
			
			for (int i = 0, size = this.numberOfChildren(); i < size; i++) {
				if (!(node.getChild(i).equals(this.getChild(i)))) return false;
			}
			return true;
		}
		return false;
		
	}
	
}

