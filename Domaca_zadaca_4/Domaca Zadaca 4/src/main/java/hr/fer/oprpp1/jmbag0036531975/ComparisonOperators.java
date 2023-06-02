package hr.fer.oprpp1.jmbag0036531975;

public class ComparisonOperators {
    public static final IComparisonOperator LESS = (val1, val2) -> (val1.compareTo(val2) < 0);
    public static final IComparisonOperator LESS_OR_EQUALS = (val1, val2) -> (val1.compareTo(val2) <= 0);
    public static final IComparisonOperator GREATER = (val1, val2) -> (val1.compareTo(val2) > 0);
    public static final IComparisonOperator GREATER_OR_EQUALS = (val1, val2) -> (val1.compareTo(val2) >= 0);
    public static final IComparisonOperator EQUALS = (val1, val2) -> (val1.compareTo(val2) == 0);
    public static final IComparisonOperator NOT_EQUALS = (val1, val2) -> (val1.compareTo(val2) != 0);
    public static final IComparisonOperator LIKE = (val1, val2) -> {
        boolean foundOperator = false;
        String firstPart = "", lastPart = "";
        for(int i = 0; i < val2.length(); i++) {
            if (val2.charAt(i) == '*') {
                if (foundOperator)
                    throw new IllegalArgumentException("LIKE can have at most one operator *");
                foundOperator = true;
            } else if (!foundOperator) {
                firstPart += val2.charAt(i);
            } else {
                lastPart += val2.charAt(i);
            }
        }
        if (foundOperator && firstPart.length() + lastPart.length() > val1.length()) return false;
        if (foundOperator && val1.startsWith(firstPart) && val1.endsWith(lastPart)) return true;
        if (!foundOperator && val1.equals(firstPart)) return true;
        return false;
    };
}
