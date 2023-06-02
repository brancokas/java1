package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;
/**
 * class Token stores
 * value and type of a piece of text
 * that has some semantic value
 * 
 * @author bstankovic
 * @version 1.0
 * */
public class Token {
	private Object value;
	private TokenType type;
	
	/**
	 * @throws SmartScriptParserException if type is null
	 * */
	public Token(TokenType type, Object value) {
		if (type == null) throw new SmartScriptParserException("Null pointer exception");
		this.value = value;
		this.type = type;
	}

	public Object getElement() {
		return value;
	}
	
	/**
	 * @return type can't be null
	 * */
	public TokenType getType() {
		return type;
	}
	
	/**
	 * @return String type "(value, type)"
	 * */
	@Override
	public String toString() {
		if (value == null) return "(null, " + type + ')';
		return '(' + value.toString() + ", " + type + ')'; 
	}
}
