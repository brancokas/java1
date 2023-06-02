package hr.fer.oprpp1.jmbag0036531975;

public class ConditionalExpression {
    private IFieldValueGetter fieldValueGetter;
    private String filter;
    private IComparisonOperator comparisonOperator;

    public ConditionalExpression(IFieldValueGetter fieldValueGetter,
                                 String filter, IComparisonOperator comparisonOperator) {
        this.fieldValueGetter = fieldValueGetter;
        this.filter = filter;
        this.comparisonOperator = comparisonOperator;
    }

    public IFieldValueGetter getFieldGetter() {
        return fieldValueGetter;
    }

    public String getStringLiteral() {
        return filter;
    }

    public IComparisonOperator getComparisonOperator() {
        return comparisonOperator;
    }
}
