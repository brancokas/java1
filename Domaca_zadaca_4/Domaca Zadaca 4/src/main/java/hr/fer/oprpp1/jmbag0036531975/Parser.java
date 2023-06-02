package hr.fer.oprpp1.jmbag0036531975;

public class Parser {
    private Lexer lexer;

    public Parser(String text) {
        lexer = new Lexer(text);
    }

    public boolean hasNextQuery() {
        return lexer.hasNextToken();
    }

    public ConditionalExpression getNextQuery() {
        IFieldValueGetter fieldValueGetter;
        IComparisonOperator comparisonOperator;
        String message = "Wrong query input.", literal;
        Token token = lexer.getNextToken();
        if (token != null && token.getTokenType() == TokenType.FIELD) {
            if (token.getValue().equals("firstName")) {
                fieldValueGetter = FieldValueGetters.FIRST_NAME;
            } else if (token.getValue().equals("lastName")) {
                fieldValueGetter = FieldValueGetters.LAST_NAME;
            } else if (token.getValue().equals("jmbag")) {
                fieldValueGetter = FieldValueGetters.JMBAG;
            } else {
                throw new IllegalArgumentException("Wrong filtering expression.");
            }
        } else {
            throw new IllegalArgumentException(message);
        }
        token = lexer.getNextToken();
        if (token != null && token.getTokenType() == TokenType.OPERATOR) {
            if (token.getValue().equals(">")) {
                comparisonOperator = ComparisonOperators.GREATER;
            } else if (token.getValue().equals(">=")) {
                comparisonOperator = ComparisonOperators.GREATER_OR_EQUALS;
            } else if (token.getValue().equals("<")) {
                comparisonOperator = ComparisonOperators.LESS;
            } else if (token.getValue().equals("<=")) {
                comparisonOperator = ComparisonOperators.LESS_OR_EQUALS;
            } else if (token.getValue().equals("=")) {
                comparisonOperator = ComparisonOperators.EQUALS;
            } else if (token.getValue().equals("!=")) {
                comparisonOperator = ComparisonOperators.NOT_EQUALS;
            } else if (token.getValue().equals("LIKE")) {
                comparisonOperator = ComparisonOperators.LIKE;
            } else {
                throw new IllegalArgumentException(message);
            }
        } else {
            throw new IllegalArgumentException(message);
        }
        token = lexer.getNextToken();
        if (token != null && token.getTokenType() == TokenType.LITERAL) {
            literal = token.getValue();
        } else {
            throw new IllegalArgumentException(message);
        }
        if (lexer.hasNextToken()) {
            if (lexer.getNextToken().getTokenType() != TokenType.AND) {
                throw new IllegalArgumentException(message);
            } else if (!lexer.hasNextToken()) {
                throw new IllegalArgumentException(message);
            }
        }
        return new ConditionalExpression(fieldValueGetter, literal, comparisonOperator);
    }
}
