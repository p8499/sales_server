package test.sales;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FilterConditionExpr implements FilterExpr {
    public static final String OP_EQUAL = "equal";
    public static final String OP_GREATER = "greater";
    public static final String OP_LESS = "less";
    public static final String OP_GREATEREQUAL = "greaterEqual";
    public static final String OP_LESSEQUAL = "lessEqual";
    public static final String OP_ISEMPTY = "isEmpty";
    public static final String OP_CONTAIN = "contain";
    public static final String OP_STARTWITH = "startWith";
    public static final String OP_ENDWITH = "endWith";
    public static final String OP_EXISTS = "exists";

    public String op = null;
    public List<FilterExpr> data = null;

    public FilterConditionExpr() {
        data = new ArrayList<FilterExpr>();
    }

    public FilterConditionExpr(String op, FilterExpr... data) {
        this.op = op;
        this.data = new ArrayList<FilterExpr>();
        for (FilterExpr datum : data)
            this.data.add(datum);
    }

    public static FilterConditionExpr equalsString(String field, String value) {
        FilterOperandExpr operand1 = new FilterOperandExpr(field, FilterOperandExpr.OP_STRING, true);
        FilterOperandExpr operand2 = new FilterOperandExpr(value, FilterOperandExpr.OP_STRING, false);
        return new FilterConditionExpr(FilterConditionExpr.OP_EQUAL, operand1, operand2);
    }

    public static FilterConditionExpr containsString(String field, String value) {
        FilterOperandExpr operand1 = new FilterOperandExpr(field, FilterOperandExpr.OP_STRING, true);
        FilterOperandExpr operand2 = new FilterOperandExpr(value, FilterOperandExpr.OP_STRING, false);
        return new FilterConditionExpr(FilterConditionExpr.OP_CONTAIN, operand1, operand2);
    }

    public static FilterConditionExpr startString(String field, String value) {
        FilterOperandExpr operand1 = new FilterOperandExpr(field, FilterOperandExpr.OP_STRING, true);
        FilterOperandExpr operand2 = new FilterOperandExpr(value, FilterOperandExpr.OP_STRING, false);
        return new FilterConditionExpr(FilterConditionExpr.OP_STARTWITH, operand1, operand2);
    }

    public static FilterConditionExpr endString(String field, String value) {
        FilterOperandExpr operand1 = new FilterOperandExpr(field, FilterOperandExpr.OP_STRING, true);
        FilterOperandExpr operand2 = new FilterOperandExpr(value, FilterOperandExpr.OP_STRING, false);
        return new FilterConditionExpr(FilterConditionExpr.OP_ENDWITH, operand1, operand2);
    }

    public static FilterConditionExpr gtDate(String field, Date value) {
        FilterOperandExpr operand1 = new FilterOperandExpr(field, FilterOperandExpr.OP_STRING, true);
        FilterOperandExpr operand2 = new FilterOperandExpr(DefaultDateFormatter.format(value), FilterOperandExpr.OP_DATE, false);
        return new FilterConditionExpr(FilterConditionExpr.OP_GREATER, operand1, operand2);
    }

    public static FilterConditionExpr geDate(String field, Date value) {
        FilterOperandExpr operand1 = new FilterOperandExpr(field, FilterOperandExpr.OP_STRING, true);
        FilterOperandExpr operand2 = new FilterOperandExpr(DefaultDateFormatter.format(value), FilterOperandExpr.OP_DATE, false);
        return new FilterConditionExpr(FilterConditionExpr.OP_GREATEREQUAL, operand1, operand2);
    }

    public static FilterConditionExpr ltDate(String field, Date value) {
        FilterOperandExpr operand1 = new FilterOperandExpr(field, FilterOperandExpr.OP_STRING, true);
        FilterOperandExpr operand2 = new FilterOperandExpr(DefaultDateFormatter.format(value), FilterOperandExpr.OP_DATE, false);
        return new FilterConditionExpr(FilterConditionExpr.OP_LESS, operand1, operand2);
    }

    public static FilterConditionExpr leDate(String field, Date value) {
        FilterOperandExpr operand1 = new FilterOperandExpr(field, FilterOperandExpr.OP_STRING, true);
        FilterOperandExpr operand2 = new FilterOperandExpr(DefaultDateFormatter.format(value), FilterOperandExpr.OP_DATE, false);
        return new FilterConditionExpr(FilterConditionExpr.OP_LESSEQUAL, operand1, operand2);
    }

    public static FilterConditionExpr equalsNumber(String field, Integer value) {
        FilterOperandExpr operand1 = new FilterOperandExpr(field, FilterOperandExpr.OP_STRING, true);
        FilterOperandExpr operand2 = new FilterOperandExpr(value.toString(), FilterOperandExpr.OP_NUMBER, false);
        return new FilterConditionExpr(FilterConditionExpr.OP_EQUAL, operand1, operand2);
    }

    public static FilterConditionExpr gtNumber(String field, Integer value) {
        FilterOperandExpr operand1 = new FilterOperandExpr(field, FilterOperandExpr.OP_STRING, true);
        FilterOperandExpr operand2 = new FilterOperandExpr(value.toString(), FilterOperandExpr.OP_NUMBER, false);
        return new FilterConditionExpr(FilterConditionExpr.OP_GREATER, operand1, operand2);
    }

    public static FilterConditionExpr geNumber(String field, Integer value) {
        FilterOperandExpr operand1 = new FilterOperandExpr(field, FilterOperandExpr.OP_STRING, true);
        FilterOperandExpr operand2 = new FilterOperandExpr(value.toString(), FilterOperandExpr.OP_NUMBER, false);
        return new FilterConditionExpr(FilterConditionExpr.OP_GREATEREQUAL, operand1, operand2);
    }

    public static FilterConditionExpr ltNumber(String field, Integer value) {
        FilterOperandExpr operand1 = new FilterOperandExpr(field, FilterOperandExpr.OP_STRING, true);
        FilterOperandExpr operand2 = new FilterOperandExpr(value.toString(), FilterOperandExpr.OP_NUMBER, false);
        return new FilterConditionExpr(FilterConditionExpr.OP_LESS, operand1, operand2);
    }

    public static FilterConditionExpr leNumber(String field, Integer value) {
        FilterOperandExpr operand1 = new FilterOperandExpr(field, FilterOperandExpr.OP_STRING, true);
        FilterOperandExpr operand2 = new FilterOperandExpr(value.toString(), FilterOperandExpr.OP_NUMBER, false);
        return new FilterConditionExpr(FilterConditionExpr.OP_LESSEQUAL, operand1, operand2);
    }

    public static FilterConditionExpr equalsField(String field1, String field2) {
        FilterOperandExpr operand1 = new FilterOperandExpr(field1, FilterOperandExpr.OP_STRING, true);
        FilterOperandExpr operand2 = new FilterOperandExpr(field2, FilterOperandExpr.OP_STRING, true);
        return new FilterConditionExpr(FilterConditionExpr.OP_EQUAL, operand1, operand2);
    }

    public static FilterConditionExpr existsObject(String obj, FilterLogicExpr expr) {
        FilterOperandExpr operand1 = new FilterOperandExpr(obj, FilterOperandExpr.OP_STRING, true);
        return new FilterConditionExpr(FilterConditionExpr.OP_EXISTS, operand1, expr);
    }

    public FilterLogicExpr not() {
        return new FilterLogicExpr(FilterLogicExpr.OP_NOT, this);
    }

    public String toStringPostgresql() {
        StringBuffer sb = new StringBuffer();
        switch (op) {
            case OP_EQUAL: {
                FilterOperandExpr datum0 = (FilterOperandExpr) data.get(0);
                FilterOperandExpr datum1 = (FilterOperandExpr) data.get(1);
                sb.append(datum0.toStringPostgresql());
                sb.append("=");
                sb.append(datum1.toStringPostgresql());
                break;
            }
            case OP_GREATER: {
                FilterOperandExpr datum0 = (FilterOperandExpr) data.get(0);
                FilterOperandExpr datum1 = (FilterOperandExpr) data.get(1);
                sb.append(datum0.toStringPostgresql());
                sb.append(">");
                sb.append(datum1.toStringPostgresql());
                break;
            }
            case OP_LESS: {
                FilterOperandExpr datum0 = (FilterOperandExpr) data.get(0);
                FilterOperandExpr datum1 = (FilterOperandExpr) data.get(1);
                sb.append(datum0.toStringPostgresql());
                sb.append("<");
                sb.append(datum1.toStringPostgresql());
                break;
            }
            case OP_GREATEREQUAL: {
                FilterOperandExpr datum0 = (FilterOperandExpr) data.get(0);
                FilterOperandExpr datum1 = (FilterOperandExpr) data.get(1);
                sb.append(datum0.toStringPostgresql());
                sb.append(">=");
                sb.append(datum1.toStringPostgresql());
                break;
            }
            case OP_LESSEQUAL: {
                FilterOperandExpr datum0 = (FilterOperandExpr) data.get(0);
                FilterOperandExpr datum1 = (FilterOperandExpr) data.get(1);
                sb.append(datum0.toStringPostgresql());
                sb.append("<=");
                sb.append(datum1.toStringPostgresql());
                break;
            }
            case OP_ISEMPTY: {
                FilterOperandExpr datum0 = (FilterOperandExpr) data.get(0);
                FilterOperandExpr datum1 = (FilterOperandExpr) data.get(1);
                sb.append(datum0.toStringPostgresql());
                sb.append(" IS NULL OR ");
                sb.append(datum1.toStringPostgresql());
                sb.append(" = ''");
                break;
            }
            case OP_CONTAIN: {
                FilterOperandExpr datum0 = (FilterOperandExpr) data.get(0);
                FilterOperandExpr datum1 = (FilterOperandExpr) data.get(1);
                sb.append(datum0.toStringPostgresql());
                sb.append(" LIKE ");
                sb.append(datum1.toEscapedContainSqlPostgresql());
                break;
            }
            case OP_STARTWITH: {
                FilterOperandExpr datum0 = (FilterOperandExpr) data.get(0);
                FilterOperandExpr datum1 = (FilterOperandExpr) data.get(1);
                sb.append(datum0.toStringPostgresql());
                sb.append(" LIKE ");
                sb.append(datum1.toEscapedStartWithSqlPostgresql());
                break;
            }
            case OP_ENDWITH: {
                FilterOperandExpr datum0 = (FilterOperandExpr) data.get(0);
                FilterOperandExpr datum1 = (FilterOperandExpr) data.get(1);
                sb.append(datum0.toStringPostgresql());
                sb.append(" LIKE ");
                sb.append(datum1.toEscapedEndWithSqlPostgresql());
                break;
            }
            case OP_EXISTS: {
                FilterOperandExpr datum0 = (FilterOperandExpr) data.get(0);
                FilterLogicExpr datum1 = (FilterLogicExpr) data.get(1);
                sb.append("EXISTS (SELECT * FROM ");
                sb.append(datum0.toStringPostgresql());
                sb.append(" WHERE ");
                sb.append(datum1.toStringPostgresql());
                sb.append(")");
            }
        }
        return sb.toString();
    }

    public String toStringOracle() {
        StringBuffer sb = new StringBuffer();
        switch (op) {
            case OP_EQUAL: {
                FilterOperandExpr datum0 = (FilterOperandExpr) data.get(0);
                FilterOperandExpr datum1 = (FilterOperandExpr) data.get(1);
                sb.append(datum0.toStringOracle());
                sb.append("=");
                sb.append(datum1.toStringOracle());
                break;
            }
            case OP_GREATER: {
                FilterOperandExpr datum0 = (FilterOperandExpr) data.get(0);
                FilterOperandExpr datum1 = (FilterOperandExpr) data.get(1);
                sb.append(datum0.toStringOracle());
                sb.append(">");
                sb.append(datum1.toStringOracle());
                break;
            }
            case OP_LESS: {
                FilterOperandExpr datum0 = (FilterOperandExpr) data.get(0);
                FilterOperandExpr datum1 = (FilterOperandExpr) data.get(1);
                sb.append(datum0.toStringOracle());
                sb.append("<");
                sb.append(datum1.toStringOracle());
                break;
            }
            case OP_GREATEREQUAL: {
                FilterOperandExpr datum0 = (FilterOperandExpr) data.get(0);
                FilterOperandExpr datum1 = (FilterOperandExpr) data.get(1);
                sb.append(datum0.toStringOracle());
                sb.append(">=");
                sb.append(datum1.toStringOracle());
                break;
            }
            case OP_LESSEQUAL: {
                FilterOperandExpr datum0 = (FilterOperandExpr) data.get(0);
                FilterOperandExpr datum1 = (FilterOperandExpr) data.get(1);
                sb.append(datum0.toStringOracle());
                sb.append("<=");
                sb.append(datum1.toStringOracle());
                break;
            }
            case OP_ISEMPTY: {
                FilterOperandExpr datum0 = (FilterOperandExpr) data.get(0);
                FilterOperandExpr datum1 = (FilterOperandExpr) data.get(1);
                sb.append(datum0.toStringOracle());
                sb.append(" IS NULL OR ");
                sb.append(datum1.toStringOracle());
                sb.append(" = ''");
                break;
            }
            case OP_CONTAIN: {
                FilterOperandExpr datum0 = (FilterOperandExpr) data.get(0);
                FilterOperandExpr datum1 = (FilterOperandExpr) data.get(1);
                sb.append(datum0.toStringOracle());
                sb.append(" LIKE ");
                sb.append(datum1.toEscapedContainSqlOracle());
                break;
            }
            case OP_STARTWITH: {
                FilterOperandExpr datum0 = (FilterOperandExpr) data.get(0);
                FilterOperandExpr datum1 = (FilterOperandExpr) data.get(1);
                sb.append(datum0.toStringOracle());
                sb.append(" LIKE ");
                sb.append(datum1.toEscapedStartWithSqlOracle());
                break;
            }
            case OP_ENDWITH: {
                FilterOperandExpr datum0 = (FilterOperandExpr) data.get(0);
                FilterOperandExpr datum1 = (FilterOperandExpr) data.get(1);
                sb.append(datum0.toStringOracle());
                sb.append(" LIKE ");
                sb.append(datum1.toEscapedEndWithSqlOracle());
                break;
            }
            case OP_EXISTS: {
                FilterOperandExpr datum0 = (FilterOperandExpr) data.get(0);
                FilterLogicExpr datum1 = (FilterLogicExpr) data.get(1);
                sb.append("EXISTS (SELECT * FROM ");
                sb.append(datum0.toStringOracle());
                sb.append(" WHERE ");
                sb.append(datum1.toStringOracle());
                sb.append(")");
            }
        }
        return sb.toString();
    }
}