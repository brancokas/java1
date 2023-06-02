package hr.fer.oprpp1.jmbag0036531975;

public class Lexer {
    private Token token;
    private String text;
    private int index;
    private TokenType type;

    public Lexer(String text) {
        if (text == null) throw new NullPointerException("Text cannot be null.");
        this.text = text;
        type = TokenType.FIELD;
    }

    public boolean hasNextToken() {
        clearBlank();
        return !isEOF();
    }


    public Token getNextToken() {
        clearBlank();
        //obrada argumenta[jmbag,firstName,lastName]
        if (type == TokenType.FIELD) {
            String field = "";
            while (!isEOF() && !isBlank() && !isOperator()) {
                field += text.charAt(index++);
            }
            if (field.equals("jmbag") || field.equals("firstName") || field.equals("lastName")) {
                token = new Token(type, field);
            } else {
                throw new IllegalArgumentException("Wrong input for arguments");
            }
            type = TokenType.OPERATOR;
        } else if (type == TokenType.OPERATOR) {
            if (isOperator()) {
                String operator = "";
                while (!isEOF() && (text.charAt(index) == '=' || text.charAt(index) == '>' || text.charAt(index) == '<' ||
                       text.charAt(index) == '!' || text.charAt(index) == 'L' || text.charAt(index) == 'I' ||
                        text.charAt(index) == 'K' || text.charAt(index) == 'E')) {
                    operator += text.charAt(index++);
                }
                token = new Token(type, operator);
            } else {
                throw new IllegalArgumentException("Wrong input for operator");
            }
            type = TokenType.LITERAL;
        } else if (type == TokenType.LITERAL) {
            int navodnici = 0;
            String literal = "";
            while (!isEOF() && navodnici < 2) {
                if (text.charAt(index) == '"') {
                    navodnici++;
                } else {
                    literal += text.charAt(index);
                }
                index++;
            }
            if (navodnici < 2) throw new IllegalArgumentException("Wrong input for literal");
            token = new Token(type, literal);
            type = TokenType.AND;
        } else {
            String and = "";
            while (!isEOF() && !isBlank()) {
                and += text.charAt(index++);
            }
            if (and.toLowerCase().equals("and")) {
                token = new Token(type, and);
            } else {
                throw new IllegalArgumentException("Wrong input for and");
            }
            type = TokenType.FIELD;
        }
        return token;
    }

    private boolean isOperator() {
        int tmpIndex = index;
        String operator = "";
        while (!isEOF() && (text.charAt(tmpIndex) == '=' || text.charAt(tmpIndex) == '>' || text.charAt(tmpIndex) == '<' ||
                text.charAt(tmpIndex) == '!' || text.charAt(tmpIndex) == 'L' || text.charAt(tmpIndex) == 'I' ||
                text.charAt(tmpIndex) == 'K' || text.charAt(tmpIndex) == 'E')) {
            operator += text.charAt(tmpIndex++);
        }
        if (operator.equals("=") || operator.equals(">=") || operator.equals("<=") || operator.equals(">") ||
               operator.equals("!=") || operator.equals("<") || operator.equals("LIKE")) return true;
        return false;
    }

    private boolean isEOF() {
        return index >= text.length();
    }

    private void clearBlank() {
        while (isBlank()) {
            index++;
        }
    }

    private boolean isBlank() {
        if (isEOF()) return false;
        char charAt = text.charAt(index);
        if (charAt == ' ' || charAt == '\n' || charAt == '\t') return true;
        return false;
    }
}
