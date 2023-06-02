package hr.fer.oprpp1.jmbag0036531975;

import java.util.List;

public class QueryFilter implements IFilter{
    private List<ConditionalExpression> expressions;

    public QueryFilter(List<ConditionalExpression> expressions) {
        this.expressions = expressions;
    }
    @Override
    public boolean accepts(StudentRecord record) {
        for (var expression : expressions) {
            if (!expression.getComparisonOperator().satisfied(
                    expression.getFieldGetter().get(record),
                    expression.getStringLiteral()
            )) return false;
        }
        return true;
    }
}
