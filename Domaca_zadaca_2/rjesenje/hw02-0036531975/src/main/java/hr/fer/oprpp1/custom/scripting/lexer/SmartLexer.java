package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;
/**
 * Class SmartLexer expects non-text
 * and creates tokens out of text until 
 * it reaches EOF
 * Types of token that class generates
 * are of type: enum TokenType
 * 
 * @author bstankovic
 * @version 1.0
 * */
public class SmartLexer {
	private char[] data;
	private int currentIndex;
	private Token token;
	private SmartLexerState state;
	
	/**
	 * @throws SmartScriptParserException if text == null
	 * */
	public SmartLexer(String text) {
		if (text == null) throw new SmartScriptParserException("Text cannot be null");
		
		data = text.toCharArray();
		currentIndex = 0;
		state = SmartLexerState.TEXT;
		isStartTag();
	}
	
	/**
	 * delets blank space until 
	 * first apperance of a non-blank element
	 * or EOF
	 * */
	private void deleteBlankSpace() {
		while(isNotEOF() && isBlankSpace()) {
			currentIndex++;
		}
	}
	/**
	 * @return <code>true</code> if current char of text
	 * 			is whitespace tab or newline else
	 * 			<code>false</code>
	 * */
	private boolean isBlankSpace() {
		if ((data[currentIndex] == ' ' || data[currentIndex] == '\r' ||
				data[currentIndex] == '\n' || data[currentIndex] == '\t')) return true;
		
		return false;
	}
	
	/**
	 * @return <code>true</code> if EOF is reached
	 * 			else <code>false</code>
	 * */
	private boolean isEOF() {
		if (currentIndex == data.length) return true;
		return false;
	}
	
	/**
	 * @return (opposite)~isEOF
	 * */
	private boolean isNotEOF() {
		return !isEOF();
	}
	
	/**
	 * @return <code>true</code> if next chars 
	 * 			are {$ else <code>false</code>
	 * */
	private boolean isStartTag() {		
		if (currentIndex+1 >= data.length) return false;
		
		if (data[currentIndex] == '{' && data[currentIndex+1] == '$') {
			state = SmartLexerState.TAG;
			return true;
		};
		
		return false;
	}

	/**
	 * @return <code>true</code> if next chars 
	 * 			are $} else <code>false</code>
	 * */
	private boolean isEndTag() {
		if (currentIndex+1 >= data.length) return false;
		
		if (data[currentIndex] == '$' && data[currentIndex+1] == '}') return true;
		
		return false;
	}
	
	/**
	 * @return text element token
	 * @throws SmartScriptParserException if text is incorrectly escaped
	 * */
	private Token getTextElementToken() {
		StringBuilder sb = new StringBuilder();
		
		while(isNotEOF() && !isStartTag()) {
			if (data[currentIndex] == '\\' && (currentIndex+1 == data.length 
					|| data[currentIndex+1] != '{' && data[currentIndex+1] != '\\')) 
						throw new SmartScriptParserException("Didn't escape valid text.");
			if (data[currentIndex] == '\\') {
				sb.append(data[currentIndex++]);
			}
			sb.append(data[currentIndex++]);
		}
		
		return new Token(TokenType.TEXT, sb.toString());
	}
	
	/**
	 * @return <code>true</code> if char is a digit
	 * 			else <code>false</code>
	 * */
	private boolean isDigit(char c) {
		if (c >= '0' && c <= '9') return true;
		return false;
	}

	/**
	 * @return <code>true</code> if char is a letter
	 * 			else <code>false</code>
	 * */
	private boolean isLetter(char c) {
		if (Character.isLetter(c)) return true;
		return false;
	}

	/**
	 * @return <code>true</code> if token in the making has
	 * 			reached the end by last double qoutes
	 * 			 else <code>false</code>
	 * @throws SmartScriptParserException if wrong chars were escaped
	 * */
	private boolean isStringMade(StringBuilder sb) {		
		if (sb.charAt(sb.length()-1) == '\\') {
			if (data[currentIndex] != '\\' && data[currentIndex] != '"' && 
					data[currentIndex] != 'n' && data[currentIndex] != 't' &&
					data[currentIndex] != 'r') 
				throw new SmartScriptParserException("Wrong use of escpaing in String");
			sb.append(data[currentIndex++]);
			return false;
		} 	
		
		if (sb.length() != 1 && sb.charAt(sb.length()-1) == '"') {
			return true;
		}
			
		return false;
	}
	

	/**
	 * @return <code>true</code> if token in the making has
	 * 			reached the end 
	 * 			 else <code>false</code>
	 * @throws SmartScriptParserException if double contains more than one dot
	 * */
	private boolean isDoubleMade(StringBuilder sb) {
		if (isDigit(data[currentIndex])) {
			return false;
		}
		if (data[currentIndex] == '.') 
			throw new SmartScriptParserException("Wrong input for Double");
		return true;
	}

	/**
	 * @return <code>true</code> if token in the making has
	 * 			reached the end 
	 * 			 else <code>false</code>
	 * */
	private boolean isIntegerMade(StringBuilder sb) {
		if (isDigit(data[currentIndex]) || data[currentIndex] == '.') return false;
		return true;
	}

	/**
	 * @return <code>true</code> if token in the making has
	 * 			reached the end 
	 * 			 else <code>false</code>
	 * */
	private boolean isVariableMade(StringBuilder sb) {
		if (data[currentIndex] == '_' || isDigit(data[currentIndex]) ||
				isLetter(data[currentIndex])) return false;
		return true;
	}

	/**
	 * @return <code>true</code> if token in the making has
	 * 			reached the end 
	 * 			 else <code>false</code>
	 * @throws SmartScriptParserException if function doesn't have letter in second space of string
	 * */
	private boolean isFunctionMade(StringBuilder sb) {
		if (sb.length() == 1 && !isLetter(data[currentIndex])) 
			throw new SmartScriptParserException("Wrong input for Function");
		
		if (isDigit(data[currentIndex]) || isLetter(data[currentIndex]) ||
				data[currentIndex] == '_') return false;
		return true;
	}

	/**
	 * @return <code>true</code> if token in the making has
	 * 			reached the end 
	 * 			 else <code>false</code>
	 * @throws SmartScriptParserException if chars can't make a part of a token
	 * */
	private boolean isElementMade(StringBuilder sb) {
		if (isEOF() || isBlankSpace() || isEndTag()) return true;
		
		//keywords
		if (sb.toString().toUpperCase().equals("FOR") && 
				!(isDigit(data[currentIndex]) || isLetter(data[currentIndex]) ||
						data[currentIndex] == '_')) {
			return true;
		}
		if (sb.toString().equals("=")) {
			return true;
		}
		if (sb.toString().toUpperCase().equals("END") && 
				!(isDigit(data[currentIndex]) || isLetter(data[currentIndex]) ||
						data[currentIndex] == '_')) {
			return true;
		}
		
		//Operator
		if (sb.charAt(0) == '+' || (sb.charAt(0) == '-' && sb.length()==1
				&& !(isDigit(data[currentIndex]))) || sb.charAt(0) == '/' 
				|| sb.charAt(0) == '*' || sb.charAt(0) == '^') {
			return true;
		}
		
		//Double
		if (isDigit(sb.charAt(0)) || sb.charAt(0) == '-') {
			for (int i = 1; i < sb.length(); i++) {
				if (sb.charAt(i) == '.') {
					return isDoubleMade(sb);
				} 
			}
			return isIntegerMade(sb);
		}
		
		//Variable
		if (isLetter(sb.charAt(0))) {
			return isVariableMade(sb);
		}
		
		//Function
		if (sb.charAt(0) == '@') {
			return isFunctionMade(sb);
		}
		
		throw new SmartScriptParserException("Wrong input in tag.");
	}
	
	/**
	 * ignores blank spaces between elements
	 * @return current token in the text that hasn't been processed
	 * 	@throws SmartScriptParserException if element doesn't fulfill tags requirement 
	 * */
	private Token getTagElementToken() {
		deleteBlankSpace();

		if (isStartTag()) {
			return new Token(TokenType.TAG_START,
					String.valueOf(data[currentIndex++]) + String.valueOf(data[currentIndex++]));
		} else if (isEndTag()) {
			state = SmartLexerState.TEXT;
			return new Token(TokenType.TAG_END,
					String.valueOf(data[currentIndex++]) + String.valueOf(data[currentIndex++]));
		}
		
		StringBuilder sb = new StringBuilder();
				
		if (data[currentIndex] == '"') {
			while((isNotEOF() && !isEndTag())) {
				sb.append(data[currentIndex++]);
				if (isStringMade(sb)) {
					break;
				}
			}
		} else 		
		while((isNotEOF() && !isEndTag() && !isBlankSpace())) {
			sb.append(data[currentIndex++]);
			if (isElementMade(sb)) {
				break;
			}
		}
		//keywords
		if (!(getCurrentToken().getType() == TokenType.TAG_START) && (
				sb.toString().toUpperCase().equals("FOR") ||
				sb.toString().toUpperCase().equals("END") ||
				sb.toString().toUpperCase().equals("="))) {
			throw new SmartScriptParserException("Cannot input keywords "
					+ "on other places in tags if not in first place");
		}
		if (sb.toString().toUpperCase().equals("FOR")) {
			return new Token(TokenType.FOR, sb.toString());
		}
		if (sb.toString().equals("=")) {
			return new Token(TokenType.EQUAL, sb.toString());
		}
		if (sb.toString().toUpperCase().equals("END")) {
			return new Token(TokenType.END, sb.toString());
		}
		//check if String is Valid
		if (sb.charAt(0) == '"') {
			if (sb.length() == 1) throw new SmartScriptParserException("String input missing second \"");
			if (sb.charAt(sb.length()-1) == '"') {
				return new Token(TokenType.STRING, sb.toString());
			}
			throw new SmartScriptParserException("Wrong input for String in the end");
		}
		//return operator
		if (sb.charAt(0) == '+' || (sb.charAt(0) == '-' && !(isDigit(data[currentIndex])))
				|| sb.charAt(0) == '/' || sb.charAt(0) == '*' || sb.charAt(0) == '^') {
			return new Token(TokenType.OPERATOR, sb.toString());
		}
		//check if it is a double or a integer
		if (isDigit(sb.charAt(0)) || sb.charAt(0) == '-') {
			for (int i = 1; i < sb.length(); i++) {
				if (sb.charAt(i) == '.') {
					return new Token(TokenType.DOUBLE, sb.toString());
				}
			}
			return new Token(TokenType.INTEGER, sb.toString());
		}
		//return variable
		if (isLetter(sb.charAt(0))) {
			return new Token(TokenType.VARIABLE, sb.toString());
		}
		//return function
		return new Token(TokenType.FUNCTION, sb.toString());
		
	}
	
	/**
	 * @return next unprocessed token in the text
	 * @throws SmartScriptParserException if client tries to read after EOF
	 * */
	public Token getNextToken() {
		if (this.currentIndex > this.data.length) 
			throw new SmartScriptParserException("Reached EOF.");
		
		if (isEOF()) {
			currentIndex++;
			return token = new Token(TokenType.EOF, null);
		}
		
		if (!isStartTag() && state == SmartLexerState.TEXT) {
			return token = getTextElementToken();
		}

		return token = getTagElementToken();
		
	}
	
	/**
	 * @return last processed token in the text
	 * */
	public Token getCurrentToken() {
		return token;
	}
}
