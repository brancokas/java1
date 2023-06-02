package hr.fer.oprpp1.hw02.prob1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import hr.fer.oprpp1.custom.scripting.lexer.Token;
import hr.fer.oprpp1.custom.scripting.lexer.SmartLexer;
import hr.fer.oprpp1.custom.scripting.lexer.TokenType;
import hr.fer.oprpp1.custom.scripting.parser.SmartScriptParserException;

public class LexerTest {
	
	@Test
	public void testPrimjer1() {
		String text = readExtra(1);
		SmartLexer lexer = new SmartLexer(text);
		assertEquals(1, numberOfSameTypes(TokenType.TEXT, lexer));
	}
	@Test
	public void testPrimjer2() {
		String text = readExtra(2);
		SmartLexer lexer = new SmartLexer(text);
		assertEquals(1, numberOfSameTypes(TokenType.TEXT, lexer));
		
	}
	@Test
	public void testPrimjer3() {
		String text = readExtra(3);
		SmartLexer lexer = new SmartLexer(text);
		assertEquals(1, numberOfSameTypes(TokenType.TEXT, lexer));
	}
	@Test
	public void testPrimjer4() {
		String text = readExtra(4);
		SmartLexer lexer = new SmartLexer(text);
		assertThrows(SmartScriptParserException.class 
				, () -> numberOfSameTypes(TokenType.TEXT, lexer));
	}
	@Test
	public void testPrimjer5() {
		String text = readExtra(5);
		SmartLexer lexer = new SmartLexer(text);
		assertThrows(SmartScriptParserException.class 
				, () -> numberOfSameTypes(TokenType.TEXT, lexer));	}
	@Test
	public void testPrimjer6() {
		String text = readExtra(6);
		SmartLexer lexer = new SmartLexer(text);
		assertEquals(1, numberOfSameTypes(TokenType.TEXT, lexer));
	}
	@Test
	public void testPrimjer7() {
		String text = readExtra(7);
		SmartLexer lexer = new SmartLexer(text);
		assertEquals(1, numberOfSameTypes(TokenType.TEXT, lexer));
	}
	@Test
	public void testPrimjer8() {
		String text = readExtra(8);
		SmartLexer lexer = new SmartLexer(text);
		assertThrows(SmartScriptParserException.class 
				, () -> numberOfSameTypes(TokenType.TEXT, lexer));	}
	@Test
	public void testPrimjer9() {
		String text = readExtra(9);
		SmartLexer lexer = new SmartLexer(text);
		assertThrows(SmartScriptParserException.class 
				, () -> numberOfSameTypes(TokenType.TEXT, lexer));	}
	
	private int numberOfSameTypes(TokenType type, SmartLexer lexer) {
		Token token = lexer.getNextToken();
		int counter = 0;
		while (token.getType() != TokenType.EOF) {
			if (token.getType() == type) {
				counter++;
			}
			token = lexer.getNextToken();
		}
		return counter;
	}
	

	private String readExtra(int n) {
		  try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer"+n+".txt")) {
		    if(is==null) throw new RuntimeException("Datoteka extra/primjer"+n+".txt je nedostupna.");
		    byte[] data = is.readAllBytes();
		    String text = new String(data, StandardCharsets.UTF_8);
		    return text;
		  } catch(IOException ex) {
		    throw new RuntimeException("Greška pri čitanju datoteke.", ex);
		  }
		}
	
}
