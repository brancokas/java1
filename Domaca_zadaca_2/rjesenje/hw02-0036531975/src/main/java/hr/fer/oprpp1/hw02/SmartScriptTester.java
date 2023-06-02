package hr.fer.oprpp1.hw02;

import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.parser.*;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

public class SmartScriptTester {
	public static void main(String[] args) {
		SmartScriptParser parser = null;
		
		try {
			String filepath = "src/test/resources/examples/doc1.txt";
			String docBody = new String(Files.readAllBytes(Paths.get(filepath)),
	                  StandardCharsets.UTF_8);
			parser = new SmartScriptParser(docBody);
			
			DocumentNode document = parser.getDocumentNode();
			String originalDocumentBody = document.toString();
			System.out.println(originalDocumentBody); 
			SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
			DocumentNode document2 = parser2.getDocumentNode();
			boolean same = document.equals(document2);
			System.out.println(same);
		} catch(SmartScriptParserException e) {
		  System.out.println("Unable to parse document!\n" + e.getMessage());
		  System.exit(-1);
		} catch(IOException e) {
			System.out.println("Wrong filepath");
		} catch(Exception e) {
		  System.out.println("If this line ever executes, you have failed this class!" + e.getMessage());
		  System.exit(-1);
		}
		
	}	
}
