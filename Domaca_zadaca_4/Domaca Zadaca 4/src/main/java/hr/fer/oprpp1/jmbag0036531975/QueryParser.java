package hr.fer.oprpp1.jmbag0036531975;

import java.util.ArrayList;
import java.util.List;

public class QueryParser {
    private List<ConditionalExpression> queries;

    public QueryParser(String text) {
        Parser parser = new Parser(text);
        queries = new ArrayList<>();
        while (parser.hasNextQuery()) {
            queries.add(parser.getNextQuery());
        }
    }

    public boolean isDirectQuery() {
        if (queries.size() != 1) return false;
        if (queries.get(0).getFieldGetter().equals(FieldValueGetters.JMBAG) &&
            queries.get(0).getComparisonOperator().equals(ComparisonOperators.EQUALS)) return true;
        return false;
    }

    public String getQueriedJMBAG() {
        if (!isDirectQuery()) throw new IllegalStateException("Only for direct queris.");
        return queries.get(0).getStringLiteral();
    }

    public List<ConditionalExpression> getQuery() {
        return queries;
    }
}
