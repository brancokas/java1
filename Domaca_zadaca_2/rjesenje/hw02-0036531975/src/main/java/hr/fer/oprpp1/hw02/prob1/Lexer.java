package hr.fer.oprpp1.hw02.prob1;

public class Lexer {

	private char[] data;      
    private Token token;
    private int currentIndex; 
    private int size;
    private LexerState state;
    
    public Lexer(String text) {
    	if (text == null) throw new NullPointerException();
    	
    	data = text.toCharArray();
    	size = data.length;
    	currentIndex = 0;
    	setState(LexerState.BASIC);
    }
    
    public void setState(LexerState state) {
    	if (state == null) throw new NullPointerException();
    	
    	this.state = state;
    }
    
    private boolean isLetter(char c) {
    	if (Character.isLetter(c) || c == '\\') return true;
    	return false;
    }
    
    private boolean isNumber(char c) {
    	if (c >= '0' && c <= '9') return true;
    	return false;
    }
    
    private boolean isBlank(char c) {
    	if (c == ' ' || c  == '\n' || c == '\t' || c == '\r') return true;
    	return false;
    }
    
    private boolean isEOF() {
    	if (this.currentIndex == size) return true;
    	return false;
    }
    
    private void deleteBlankSpace() {
    	while (!(this.isEOF()) && isBlank(data[currentIndex])) {
    		currentIndex++;
    	}
    }
    
    private boolean isGreaterThanLongMaxValue(String s) {
    	String longMax = String.valueOf(Long.MAX_VALUE);
    	
    	if (s.length() < longMax.length()) return false;
    	
    	if (s.length() > longMax.length()) return true;
    	
    	for (int i = 0, size = s.length(); i < size; i++) {
    		if (s.charAt(i) > longMax.charAt(i)) return true;
    		
    		if (s.charAt(i) < longMax.charAt(i)) return false;
    	}
    	return false;
    }
    
    private Token getNumber() {
    	StringBuilder sb = new StringBuilder();
    	
    	while(!(this.isEOF()) && isNumber(data[currentIndex])) {
    		sb.append(data[currentIndex++]);
    	}
    	
    	if (isGreaterThanLongMaxValue(sb.toString())) 
    		throw new LexerException("Greater than Long can storage.");
    	
    	return new Token(TokenType.NUMBER, Long.valueOf(sb.toString()));
    }
    
    private Token getWord() {
    	StringBuilder sb = new StringBuilder();
    	
    	while (!(isEOF()) && (isLetter(data[currentIndex]))) {
    		if (data[currentIndex] == '\\') {
    			currentIndex++;
    			if (isEOF() || !(isNumber(data[currentIndex]) || data[currentIndex] == '\\'))
    				throw new LexerException("Did not escape a valid character.");
    		}
    		sb.append(data[currentIndex++]);
    	}
    	
    	return new Token(TokenType.WORD, sb.toString());
    }
    
    private Token getSymbol() {
    	Character c = data[currentIndex++];
    	
    	return new Token(TokenType.SYMBOL, c);
    }
    
    private Token nextBasicToken() {
    	
    	if (isNumber(data[currentIndex])) {
    		return getNumber();
    	}
    	
    	if (isLetter(data[currentIndex])) {
    		return getWord();
    	}
    	
    	token = getSymbol();
    	
    	if (token.getValue().equals('#')) {
    		setState(LexerState.EXTENDED);
    	}
    	
    	return token;
    }
    
    private Token nextExtendedToken() {
    	if (data[currentIndex] == '#') {
    		setState(LexerState.BASIC);
    		return new Token(TokenType.SYMBOL, data[currentIndex++]);
    	}
    	
    	StringBuilder sb = new StringBuilder();
    	
    	while (!(isEOF()) && !(isBlank(data[currentIndex])) && (data[currentIndex] != '#')) {
    		sb.append(data[currentIndex++]);
    	}
    	
    	return new Token(TokenType.WORD, sb.toString());
    }
    
    public Token nextToken() {
    	if (this.currentIndex > size) throw new LexerException("Reached end of the file.");
    	
    	this.deleteBlankSpace();
    	
    	if (this.isEOF()) {
    		this.currentIndex++;
    		return token = new Token(TokenType.EOF, null);
    	}
    	
    	if (state == LexerState.BASIC) return token = nextBasicToken();
    	
    	return token = nextExtendedToken();
    	
    }
    
    public Token getToken() {
    	return token;
    }
	
}
