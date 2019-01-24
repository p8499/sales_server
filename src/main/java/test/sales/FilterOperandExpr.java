package test.sales;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class FilterOperandExpr implements FilterExpr {
    public static final String OP_STRING = "string";
    public static final String OP_NUMBER = "number";
    public static final String OP_DATE = "date";

    public String op = null;
    public String data = null;
    public Boolean isCol = null;

    public FilterOperandExpr() {
    }

    public FilterOperandExpr(String data, String op, Boolean isCol) {
        this.op = op;
        this.data = data;
        this.isCol = isCol;
    }

    public String toStringPostgresql() {
        if (!isCol && OP_STRING.equals(op)) return String.format("'%s'", parseEscapedStringPostgresql(data));
        else if (!isCol && OP_DATE.equals(op)) {
            return String.format("to_date('%s','yyyymmddhh24miss')", data);
        } else return data;
    }

    public String toStringOracle() {
        if (!isCol && OP_STRING.equals(op)) return String.format("'%s'", parseEscapedStringOracle(data));
        else if (!isCol && OP_DATE.equals(op)) {
            return String.format("to_date('%s','yyyymmddhh24miss')", data);
        } else return data;
    }

    public static String parseEscapedStringPostgresql(String raw) {
        //String escaped=raw.replaceAll("\0","\\0").replaceAll("\n","\\n").replaceAll("\t","\\t").replaceAll("\r","\\r").replaceAll("\b","\\b").replaceAll("'","\\'").replaceAll("\\","\\\\");
        String escaped = raw.replace("'", "\\\\'").replaceAll("\\\\", "\\\\\\\\");
        return escaped;
    }

    public static String parseEscapedStringOracle(String raw) {
        //String escaped=raw.replaceAll("\0","\\0").replaceAll("\n","\\n").replaceAll("\t","\\t").replaceAll("\r","\\r").replaceAll("\b","\\b").replaceAll("'","\\'").replaceAll("\\","\\\\");
        String escaped = raw.replace("'", "''");
        return escaped;
    }

    public static String parseWildEscapedStringPostgresql(String raw) {
        //String escaped=raw.replaceAll("\0","\\0").replaceAll("\n","\\n").replaceAll("\t","\\t").replaceAll("\r","\\r").replaceAll("\b","\\b").replaceAll("'","\\'").replaceAll("\\","\\\\").replaceAll("%","\\%").replaceAll("_","\\_");
        String escaped =
                raw.replace("'", "\\\\'")
                        .replaceAll("\\\\", "\\\\\\\\")
                        .replaceAll("%", "\\\\%")
                        .replaceAll("_", "\\\\_");
        return escaped;
    }

    public static String parseWildEscapedStringOracle(String raw) {
        //String escaped=raw.replaceAll("\0","\\0").replaceAll("\n","\\n").replaceAll("\t","\\t").replaceAll("\r","\\r").replaceAll("\b","\\b").replaceAll("'","\\'").replaceAll("\\","\\\\").replaceAll("%","\\%").replaceAll("_","\\_");
        String escaped =
                raw.replace("'", "''")
                        .replaceAll("\\\\", "\\\\\\\\")
                        .replaceAll("%", "\\\\%")
                        .replaceAll("_", "\\\\_");
        return escaped;
    }

    public boolean isEscapedOracle() {
        return data.contains("%") || data.contains("\\") || data.contains("_");
    }

    public String toEscapedStartWithSqlPostgresql() {
        StringBuffer sb = new StringBuffer();
        if (!isCol && OP_STRING.equals(op)) {
            sb.append("'");
            sb.append(parseWildEscapedStringPostgresql(data));
            sb.append("%'");
        }
        return sb.toString();
    }

    public String toEscapedStartWithSqlOracle() {
        StringBuffer sb = new StringBuffer();
        if (!isCol && OP_STRING.equals(op)) {
            sb.append("'");
            sb.append(parseWildEscapedStringOracle(data));
            sb.append("%'");
            if (isEscapedOracle())
                sb.append(" escape '\\'");
        }
        return sb.toString();
    }

    public String toEscapedEndWithSqlPostgresql() {
        StringBuffer sb = new StringBuffer();
        if (!isCol && OP_STRING.equals(op)) {
            sb.append("'%");
            sb.append(parseWildEscapedStringPostgresql(data));
            sb.append("'");
        }
        return sb.toString();
    }

    public String toEscapedEndWithSqlOracle() {
        StringBuffer sb = new StringBuffer();
        if (!isCol && OP_STRING.equals(op)) {
            sb.append("'%");
            sb.append(parseWildEscapedStringOracle(data));
            sb.append("'");
            if (isEscapedOracle())
                sb.append(" escape '\\'");
        }
        return sb.toString();
    }

    public String toEscapedContainSqlPostgresql() {
        StringBuffer sb = new StringBuffer();
        if (!isCol && OP_STRING.equals(op)) {
            sb.append("'%");
            sb.append(parseWildEscapedStringPostgresql(data));
            sb.append("%'");
        }
        return sb.toString();
    }

    public String toEscapedContainSqlOracle() {
        StringBuffer sb = new StringBuffer();
        if (!isCol && OP_STRING.equals(op)) {
            sb.append("'%");
            sb.append(parseWildEscapedStringOracle(data));
            sb.append("%'");
            if (isEscapedOracle())
                sb.append(" escape '\\'");
        }
        return sb.toString();
    }
}