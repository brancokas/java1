package hr.fer.oprpp1.jmbag0036531975;

public class Token {
    private TokenType tokenType;
    private String value;

    public Token(TokenType tokenType, String value) {
        this.tokenType = tokenType;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public TokenType getTokenType() {
        return tokenType;
    }
}
