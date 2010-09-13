
package com.gs.dbex.application.sql.processor;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Set;

import com.gs.dbex.model.DatabaseReservedWordsUtil;


/**
 * @author Sabuj Das
 *
 */
public class SqlProcessor extends AbstractProcessor {

    private static final byte[] kind = new byte[128];
    private static final byte[] unikind = new byte[31];
    private static final DatabaseReservedWordsUtil RESERVED_WORDS_UTIL = DatabaseReservedWordsUtil.getInstance();
    private int charlength = 1;
    private int pair = 0;

    public SqlProcessor() {

        initKind();
        initUniKind();
    }
    
    public void installServiceKeywords(){
    	//initSymbolTable();
    	Set<String> schemaNames = RESERVED_WORDS_UTIL.getSchemaNames();
    	if(schemaNames != null){
    		for (String s : schemaNames) {
    			lookup(TokenType.SCHEMA_NAME, s);
    			Set<String> tableNames = RESERVED_WORDS_UTIL.getTableNames(s);
    	    	if(tableNames != null){
    	    		for (String t : tableNames) {
    					lookup(TokenType.TABLE_NAME, t);
    					Set<String> colNames = RESERVED_WORDS_UTIL.getColumnNames(t);
    					if(colNames != null){
    						for (String c : colNames) {
    							lookup(TokenType.COLUMN_NAME, c);
    						}
    					}
    				}
    	    	}
			}
    	}
    }

    public void installServiceKeywords(DatabaseMetaData metaData) throws SQLException {

        initSymbolTable();
        String wordSet = metaData.getSystemFunctions();
        StringTokenizer st = new StringTokenizer(wordSet, ",");
        while (st.hasMoreTokens()) {
            String nextToken = st.nextToken();
            lookup(TokenType.FUNCTION, nextToken);
            RESERVED_WORDS_UTIL.addFunctionName(nextToken, true);
        }

        wordSet = metaData.getNumericFunctions();
        st = new StringTokenizer(wordSet, ",");
        while (st.hasMoreTokens()) {
            String nextToken = st.nextToken();
            lookup(TokenType.FUNCTION, nextToken);
            RESERVED_WORDS_UTIL.addFunctionName(nextToken, true);
        }

        wordSet = metaData.getStringFunctions();
        st = new StringTokenizer(wordSet, ",");
        while (st.hasMoreTokens()) {
            String nextToken = st.nextToken();
            lookup(TokenType.FUNCTION, nextToken);
            RESERVED_WORDS_UTIL.addFunctionName(nextToken, true);
        }

        wordSet = metaData.getTimeDateFunctions();
        st = new StringTokenizer(wordSet, ",");
        while (st.hasMoreTokens()) {
            String nextToken = st.nextToken();
            lookup(TokenType.FUNCTION, nextToken);
            RESERVED_WORDS_UTIL.addFunctionName(nextToken, true);
        }

    }

    @Override
    protected TextSymbol lookup(TokenType type, String name) {

        if (type != TokenType.IDENTIFIER)
            return super.lookup(type, name);
        TextSymbol sym = symbolTable.get(name);
        if (sym != null)
            return sym;
        sym = symbolTable.get(name);
        if (sym != null)
            return sym;
        return super.lookup(type, name);
    }

    @Override
    protected void initSymbolTable() {

        symbolTable.clear();
        lookup(TokenType.KEYWORD, "ABSOLUTE");
        lookup(TokenType.KEYWORD, "ACTION");
        lookup(TokenType.KEYWORD, "ADD");
        lookup(TokenType.KEYWORD, "ADMIN");
        lookup(TokenType.KEYWORD, "AFTER");
        lookup(TokenType.KEYWORD, "AGGREGATE");
        lookup(TokenType.KEYWORD, "ALIAS");
        lookup(TokenType.KEYWORD, "ALL");
        lookup(TokenType.KEYWORD, "ALLOCATE");
        lookup(TokenType.KEYWORD, "ALTER");
        lookup(TokenType.KEYWORD, "AND");
        lookup(TokenType.KEYWORD, "ANY");
        lookup(TokenType.KEYWORD, "ARE");
        lookup(TokenType.KEYWORD, "ARRAY");
        lookup(TokenType.KEYWORD, "AS");
        lookup(TokenType.KEYWORD, "ASC");
        lookup(TokenType.KEYWORD, "ASSERTION");
        lookup(TokenType.KEYWORD, "AT");
        lookup(TokenType.KEYWORD, "AUTHORIZATION");
        lookup(TokenType.KEYWORD, "BEFORE");
        lookup(TokenType.KEYWORD, "BETWEEN");
        lookup(TokenType.KEYWORD, "BEGIN");
        lookup(TokenType.KEYWORD, "BINARY");
        lookup(TokenType.KEYWORD, "BIT");
        lookup(TokenType.KEYWORD, "BLOB");
        lookup(TokenType.KEYWORD, "BOOLEAN");
        lookup(TokenType.KEYWORD, "BOTH");
        lookup(TokenType.KEYWORD, "BREADTH");
        lookup(TokenType.KEYWORD, "BY");
        lookup(TokenType.KEYWORD, "CALL");
        lookup(TokenType.KEYWORD, "CASCADE");
        lookup(TokenType.KEYWORD, "CASCADED");
        lookup(TokenType.KEYWORD, "CASE");
        lookup(TokenType.KEYWORD, "CAST");
        lookup(TokenType.KEYWORD, "CATALOG");
        lookup(TokenType.KEYWORD, "CHAR");
        lookup(TokenType.KEYWORD, "CHARACTER");
        lookup(TokenType.KEYWORD, "CHECK");
        lookup(TokenType.KEYWORD, "CLASS");
        lookup(TokenType.KEYWORD, "CLOB");
        lookup(TokenType.KEYWORD, "CLOSE");
        lookup(TokenType.KEYWORD, "COLLATE");
        lookup(TokenType.KEYWORD, "COLLATION");
        lookup(TokenType.KEYWORD, "COLUMN");
        lookup(TokenType.KEYWORD, "COMMIT");
        lookup(TokenType.KEYWORD, "COMPLETION");
        lookup(TokenType.KEYWORD, "CONDITION");
        lookup(TokenType.KEYWORD, "CONNECT");
        lookup(TokenType.KEYWORD, "CONNECTION");
        lookup(TokenType.KEYWORD, "CONSTRAINT");
        lookup(TokenType.KEYWORD, "CONSTRAINTS");
        lookup(TokenType.KEYWORD, "CONSTRUCTOR");
        lookup(TokenType.KEYWORD, "CONTAINS");
        lookup(TokenType.KEYWORD, "CONTINUE");
        lookup(TokenType.KEYWORD, "CORRESPONDING");
        lookup(TokenType.KEYWORD, "CREATE");
        lookup(TokenType.KEYWORD, "CROSS");
        lookup(TokenType.KEYWORD, "CUBE");
        lookup(TokenType.KEYWORD, "CURRENT");
        lookup(TokenType.KEYWORD, "CURRENT_DATE");
        lookup(TokenType.KEYWORD, "CURRENT_PATH");
        lookup(TokenType.KEYWORD, "CURRENT_ROLE");
        lookup(TokenType.KEYWORD, "CURRENT_TIME");
        lookup(TokenType.KEYWORD, "CURRENT_TIMESTAMP");
        lookup(TokenType.KEYWORD, "CURRENT_USER");
        lookup(TokenType.KEYWORD, "CURSOR");
        lookup(TokenType.KEYWORD, "CYCLE");
        lookup(TokenType.KEYWORD, "DATA");
        lookup(TokenType.KEYWORD, "DATALINK");
        lookup(TokenType.KEYWORD, "DATE");
        lookup(TokenType.KEYWORD, "DAY");
        lookup(TokenType.KEYWORD, "DEALLOCATE");
        lookup(TokenType.KEYWORD, "DEC");
        lookup(TokenType.KEYWORD, "DECIMAL");
        lookup(TokenType.KEYWORD, "DECLARE");
        lookup(TokenType.KEYWORD, "DEFAULT");
        lookup(TokenType.KEYWORD, "DEFERRABLE");
        lookup(TokenType.KEYWORD, "DELETE");
        lookup(TokenType.KEYWORD, "DEPTH");
        lookup(TokenType.KEYWORD, "DEREF");
        lookup(TokenType.KEYWORD, "DESC");
        lookup(TokenType.KEYWORD, "DESCRIPTOR");
        lookup(TokenType.KEYWORD, "DIAGNOSTICS");
        lookup(TokenType.KEYWORD, "DICTIONARY");
        lookup(TokenType.KEYWORD, "DISCONNECT");
        lookup(TokenType.KEYWORD, "DO");
        lookup(TokenType.KEYWORD, "DOMAIN");
        lookup(TokenType.KEYWORD, "DOUBLE");
        lookup(TokenType.KEYWORD, "DROP");
        lookup(TokenType.KEYWORD, "END-EXEC");
        lookup(TokenType.KEYWORD, "EQUALS");
        lookup(TokenType.KEYWORD, "ESCAPE");
        lookup(TokenType.KEYWORD, "EXCEPT");
        lookup(TokenType.KEYWORD, "EXCEPTION");
        lookup(TokenType.KEYWORD, "EXECUTE");
        lookup(TokenType.KEYWORD, "EXIT");
        lookup(TokenType.KEYWORD, "EXPAND");
        lookup(TokenType.KEYWORD, "EXPANDING");
        lookup(TokenType.KEYWORD, "FALSE");
        lookup(TokenType.KEYWORD, "FIRST");
        lookup(TokenType.KEYWORD, "FLOAT");
        lookup(TokenType.KEYWORD, "FOR");
        lookup(TokenType.KEYWORD, "FOREIGN");
        lookup(TokenType.KEYWORD, "FREE");
        lookup(TokenType.KEYWORD, "FROM");
        lookup(TokenType.KEYWORD, "FUNCTION");
        lookup(TokenType.KEYWORD, "GENERAL");
        lookup(TokenType.KEYWORD, "GET");
        lookup(TokenType.KEYWORD, "GLOBAL");
        lookup(TokenType.KEYWORD, "GOTO");
        lookup(TokenType.KEYWORD, "GROUP");
        lookup(TokenType.KEYWORD, "GROUPING");
        lookup(TokenType.KEYWORD, "HANDLER");
        lookup(TokenType.KEYWORD, "HASH");
        lookup(TokenType.KEYWORD, "HOUR");
        lookup(TokenType.KEYWORD, "IDENTITY");
        lookup(TokenType.KEYWORD, "IF");
        lookup(TokenType.KEYWORD, "IGNORE");
        lookup(TokenType.KEYWORD, "IMMEDUATE");
        lookup(TokenType.KEYWORD, "IN");
        lookup(TokenType.KEYWORD, "INDICATOR");
        lookup(TokenType.KEYWORD, "INITIALIZE");
        lookup(TokenType.KEYWORD, "INITALLY");
        lookup(TokenType.KEYWORD, "INNER");
        lookup(TokenType.KEYWORD, "INOUT");
        lookup(TokenType.KEYWORD, "INPUT");
        lookup(TokenType.KEYWORD, "INSERT");
        lookup(TokenType.KEYWORD, "INT");
        lookup(TokenType.KEYWORD, "INTEGER");
        lookup(TokenType.KEYWORD, "INTERSECT");
        lookup(TokenType.KEYWORD, "INTERVAL");
        lookup(TokenType.KEYWORD, "INTO");
        lookup(TokenType.KEYWORD, "IS");
        lookup(TokenType.KEYWORD, "ISOLATION");
        lookup(TokenType.KEYWORD, "ITERATE");
        lookup(TokenType.KEYWORD, "JOIN");
        lookup(TokenType.KEYWORD, "KEY");
        lookup(TokenType.KEYWORD, "LANGUAGE");
        lookup(TokenType.KEYWORD, "LARGE");
        lookup(TokenType.KEYWORD, "LAST");
        lookup(TokenType.KEYWORD, "LATERAL");
        lookup(TokenType.KEYWORD, "LEADING");
        lookup(TokenType.KEYWORD, "LEAVE");
        lookup(TokenType.KEYWORD, "LEFT");
        lookup(TokenType.KEYWORD, "LESS");
        lookup(TokenType.KEYWORD, "LEVEL");
        lookup(TokenType.KEYWORD, "LIKE");
        lookup(TokenType.KEYWORD, "LIMIT");
        lookup(TokenType.KEYWORD, "LOCAL");
        lookup(TokenType.KEYWORD, "LOCALTIME");
        lookup(TokenType.KEYWORD, "LOCALTIME-");
        lookup(TokenType.KEYWORD, "LOCATOR");
        lookup(TokenType.KEYWORD, "LOOP");
        lookup(TokenType.KEYWORD, "MATCH");
        lookup(TokenType.KEYWORD, "MEETS");
        lookup(TokenType.KEYWORD, "MINUTE");
        lookup(TokenType.KEYWORD, "MODIFIES");
        lookup(TokenType.KEYWORD, "MODIFY");
        lookup(TokenType.KEYWORD, "MODULE");
        lookup(TokenType.KEYWORD, "MONTH");
        lookup(TokenType.KEYWORD, "NAMES");
        lookup(TokenType.KEYWORD, "NATIONAL");
        lookup(TokenType.KEYWORD, "NATURAL");
        lookup(TokenType.KEYWORD, "NCHAR");
        lookup(TokenType.KEYWORD, "NCLOB");
        lookup(TokenType.KEYWORD, "NEW");
        lookup(TokenType.KEYWORD, "NEXT");
        lookup(TokenType.KEYWORD, "NO");
        lookup(TokenType.KEYWORD, "NONE");
        lookup(TokenType.KEYWORD, "NORMALIZE");
        lookup(TokenType.KEYWORD, "NOT");
        lookup(TokenType.KEYWORD, "NULL");
        lookup(TokenType.KEYWORD, "NUMERIC");
        lookup(TokenType.KEYWORD, "OBJECT");
        lookup(TokenType.KEYWORD, "OF");
        lookup(TokenType.KEYWORD, "OFF");
        lookup(TokenType.KEYWORD, "OLD");
        lookup(TokenType.KEYWORD, "ON");
        lookup(TokenType.KEYWORD, "ONLY");
        lookup(TokenType.KEYWORD, "OPEN");
        lookup(TokenType.KEYWORD, "OPERATION");
        lookup(TokenType.KEYWORD, "OPTION");
        lookup(TokenType.KEYWORD, "OR");
        lookup(TokenType.KEYWORD, "ORDER");
        lookup(TokenType.KEYWORD, "ORDINALITY");
        lookup(TokenType.KEYWORD, "OUT");
        lookup(TokenType.KEYWORD, "OUTER");
        lookup(TokenType.KEYWORD, "OUTPUT");
        lookup(TokenType.KEYWORD, "PAD");
        lookup(TokenType.KEYWORD, "PARAMETER");
        lookup(TokenType.KEYWORD, "PARAMETERS");
        lookup(TokenType.KEYWORD, "PARTIAL");
        lookup(TokenType.KEYWORD, "PATH");
        lookup(TokenType.KEYWORD, "PERIOD");
        lookup(TokenType.KEYWORD, "POSTFIX");
        lookup(TokenType.KEYWORD, "PRECEDES");
        lookup(TokenType.KEYWORD, "PRECISION");
        lookup(TokenType.KEYWORD, "PREFIX");
        lookup(TokenType.KEYWORD, "PREORDER");
        lookup(TokenType.KEYWORD, "PREPARE");
        lookup(TokenType.KEYWORD, "PRESERVE");
        lookup(TokenType.KEYWORD, "PRIMARY");
        lookup(TokenType.KEYWORD, "PRIOR");
        lookup(TokenType.KEYWORD, "PRIVILEGES");
        lookup(TokenType.KEYWORD, "PROCEDURE");
        lookup(TokenType.KEYWORD, "PUBLIC");
        lookup(TokenType.KEYWORD, "READ");
        lookup(TokenType.KEYWORD, "READS");
        lookup(TokenType.KEYWORD, "REAL");
        lookup(TokenType.KEYWORD, "RECURSIVE");
        lookup(TokenType.KEYWORD, "REDO");
        lookup(TokenType.KEYWORD, "REF");
        lookup(TokenType.KEYWORD, "REFRENCES");
        lookup(TokenType.KEYWORD, "REFRENCING");
        lookup(TokenType.KEYWORD, "RELATIVE");
        lookup(TokenType.KEYWORD, "REPEAT");
        lookup(TokenType.KEYWORD, "RESIGNAL");
        lookup(TokenType.KEYWORD, "RESTRICT");
        lookup(TokenType.KEYWORD, "RESULT");
        lookup(TokenType.KEYWORD, "RETURN");
        lookup(TokenType.KEYWORD, "RETURNS");
        lookup(TokenType.KEYWORD, "REVOKE");
        lookup(TokenType.KEYWORD, "RIGHT");
        lookup(TokenType.KEYWORD, "ROLE");
        lookup(TokenType.KEYWORD, "ROLLBACK");
        lookup(TokenType.KEYWORD, "ROLLUP");
        lookup(TokenType.KEYWORD, "ROUTINE");
        lookup(TokenType.KEYWORD, "ROW");
        lookup(TokenType.KEYWORD, "ROWS");
        lookup(TokenType.KEYWORD, "SAVEPOINT");
        lookup(TokenType.KEYWORD, "SCHEMA");
        lookup(TokenType.KEYWORD, "SCROLL");
        lookup(TokenType.KEYWORD, "SEARCH");
        lookup(TokenType.KEYWORD, "SECOND");
        lookup(TokenType.KEYWORD, "SECTION");
        lookup(TokenType.KEYWORD, "SELECT");
        lookup(TokenType.KEYWORD, "SEQUENCE");
        lookup(TokenType.KEYWORD, "SESSION");
        lookup(TokenType.KEYWORD, "SESSION_USER");
        lookup(TokenType.KEYWORD, "SET");
        lookup(TokenType.KEYWORD, "SETS");
        lookup(TokenType.KEYWORD, "SIGNAL");
        lookup(TokenType.KEYWORD, "SIZE");
        lookup(TokenType.KEYWORD, "SMALLINT");
        lookup(TokenType.KEYWORD, "SPECIFIC");
        lookup(TokenType.KEYWORD, "SPECIFICTYPE");
        lookup(TokenType.KEYWORD, "SQL");
        lookup(TokenType.KEYWORD, "SQLEXCEPTION");
        lookup(TokenType.KEYWORD, "SQLSTATE");
        lookup(TokenType.KEYWORD, "SQLWARNING");
        lookup(TokenType.KEYWORD, "START");
        lookup(TokenType.KEYWORD, "STATE");
        lookup(TokenType.KEYWORD, "STATIC");
        lookup(TokenType.KEYWORD, "STRUCTURE");
        lookup(TokenType.KEYWORD, "SUCCEEDS");
        lookup(TokenType.KEYWORD, "SUM");
        lookup(TokenType.KEYWORD, "SYSTEM_USER");
        lookup(TokenType.KEYWORD, "TABLE");
        lookup(TokenType.KEYWORD, "TEMPORARY");
        lookup(TokenType.KEYWORD, "TERMINATE");
        lookup(TokenType.KEYWORD, "THAN");
        lookup(TokenType.KEYWORD, "THEN");
        lookup(TokenType.KEYWORD, "TIME");
        lookup(TokenType.KEYWORD, "TIMESTAMP");
        lookup(TokenType.KEYWORD, "TIMEZONE_HOUR");
        lookup(TokenType.KEYWORD, "TIMEZONE_MINUTE");
        lookup(TokenType.KEYWORD, "TO");
        lookup(TokenType.KEYWORD, "TRAILING");
        lookup(TokenType.KEYWORD, "TRANSACTION");
        lookup(TokenType.KEYWORD, "TRANSLATION");
        lookup(TokenType.KEYWORD, "TREAT");
        lookup(TokenType.KEYWORD, "TRIGGER");
        lookup(TokenType.KEYWORD, "TRUE");
        lookup(TokenType.KEYWORD, "UNDER");
        lookup(TokenType.KEYWORD, "UNDO");
        lookup(TokenType.KEYWORD, "UNION");
        lookup(TokenType.KEYWORD, "UNIQUE");
        lookup(TokenType.KEYWORD, "UNKNOWN");
        lookup(TokenType.KEYWORD, "UNTIL");
        lookup(TokenType.KEYWORD, "UPDATE");
        lookup(TokenType.KEYWORD, "USAGE");
        lookup(TokenType.KEYWORD, "USER");
        lookup(TokenType.KEYWORD, "USING");
        lookup(TokenType.KEYWORD, "VALUE");
        lookup(TokenType.KEYWORD, "VALUES");
        lookup(TokenType.KEYWORD, "VARIABLE");
        lookup(TokenType.KEYWORD, "VARYING");
        lookup(TokenType.KEYWORD, "VIEW");
        lookup(TokenType.KEYWORD, "WHEN");
        lookup(TokenType.KEYWORD, "WHENEVER");
        lookup(TokenType.KEYWORD, "WHERE");
        lookup(TokenType.KEYWORD, "WHILE");
        lookup(TokenType.KEYWORD, "WITH");
        lookup(TokenType.KEYWORD, "WRITE");
        lookup(TokenType.KEYWORD, "YEAR");
        lookup(TokenType.KEYWORD, "ZONE");

        lookup(TokenType.FUNCTION, "MAX");
        lookup(TokenType.FUNCTION, "MIN");
        lookup(TokenType.FUNCTION, "AVG");
        lookup(TokenType.FUNCTION, "COUNT");
    }

    /** Override the read method from the Scanner class. */
    @Override
    protected TokenType read() {

        TokenType type = TokenType.UNRECOGNIZED;

        if (start >= end)
            return TokenType.WHITESPACE;

        switch (state) {
            case START_VARIABLE :
                type = readVariable(TokenType.START_VARIABLE);
                if (type == TokenType.END_COMMENT) {
                    state = TokenType.WHITESPACE;
                } else {
                    state = TokenType.MID_COMMENT;
                }
                return type;
            case MID_COMMENT :
            case END_COMMENT :
                type = readComment(TokenType.MID_COMMENT);
                if (type == TokenType.END_COMMENT)
                    state = TokenType.WHITESPACE;
                else
                    state = TokenType.MID_COMMENT;
                return type;
            default :
                char c = buffer[start];
                if (c == '\\')
                    c = next();
                if (c < 128)
                    type = TokenType.forByte(kind[c]);
                else
                    type = TokenType.forByte(unikind[Character.getType(c)]);
                switch (type) {
                    case WHITESPACE :
                        start = start + charlength;
                        charlength = 1;
                        while (start < end) {
                            c = buffer[start];
                            if (c == '\\')
                                c = next();
                            int k;
                            if (c < 128)
                                k = kind[c];
                            else
                                k = unikind[Character.getType(c)];
                            if (k != TokenType.WHITESPACE.ordinal())
                                break;
                            start = start + charlength;
                            charlength = 1;
                        }
                        break;
                    case UNRECOGNIZED :
                    case BRACKET :
                    case SEPARATOR :
                        start = start + charlength;
                        charlength = 1;
                        break;
                    case OPERATOR :
                        start = start + charlength;
                        charlength = 1;
                        type = readOperator(c);
                        break;
                    case CHARACTER :
                        start = start + charlength;
                        charlength = 1;
                        type = readCharLiteral();
                        break;
                    case STRING :
                        start = start + charlength;
                        charlength = 1;
                        type = readStringLiteral();
                        break;
                    case IDENTIFIER :
                        start = start + charlength;
                        charlength = 1;
                        while (start < end) {
                            c = buffer[start];
                            if (c == '\\')
                                c = next();
                            int k;
                            if (c < 128)
                                k = kind[c];
                            else
                                k = unikind[Character.getType(c)];
                            if (k != TokenType.IDENTIFIER.ordinal() && k != TokenType.NUMBER.ordinal())
                                break;
                            start = start + charlength;
                            charlength = 1;
                        }
                        break;
                    case NUMBER :
                        start = start + charlength;
                        charlength = 1;
                        type = readNumber(c);
                        break;
                    case PUNCTUATION :
                        start = start + charlength;
                        charlength = 1;
                        type = readDot();
                        break;
                    case VARIABLE :
                        start = start + charlength;
                        charlength = 1;
                        type = readVariable(TokenType.START_VARIABLE);
                        if (type == TokenType.START_VARIABLE)
                            state = TokenType.UNRECOGNIZED;
                        break;
                    case COMMENT :
                        start = start + charlength;
                        charlength = 1;
                        type = readSlash();
                        if (type == TokenType.START_COMMENT)
                            state = TokenType.MID_COMMENT;
                        break;
                    default :
                        break;
                }
        }
        return type;
    }

    private TokenType readOperator(char c) {

        if (start >= end)
            return TokenType.OPERATOR;
        char c2;

        switch (c) {
            case '~' :
            case '?' :
            case ':' :
                break;
            case '+' :
            case '-' :
            case '&' :
            case '|' :
                c2 = buffer[start];
                if (c2 == '\\')
                    c2 = next();
                if (c2 != c && c2 != '=')
                    break;
                start = start + charlength;
                charlength = 1;
                break;
            case '=' :
            case '*' :
            case '!' :
            case '^' :
            case '%' :
            case '/' :
                c2 = buffer[start];
                if (c2 == '\\')
                    c2 = next();
                if (c2 != '=')
                    break;
                start = start + charlength;
                charlength = 1;
                break;
            case '<' :
            case '>' :
                c2 = buffer[start];
                if (c2 == '\\')
                    c2 = next();
                if (c2 == '=') {
                    start = start + charlength;
                    charlength = 1;
                } else if (c2 == c) {
                    start = start + charlength;
                    charlength = 1;
                    if (start >= end)
                        break;
                    char c3 = buffer[start];
                    if (c3 == '\\')
                        c3 = next();
                    if (c3 == '=') {
                        start = start + charlength;
                        charlength = 1;
                    } else if (c == '>' && c3 == '>') // >>>
                    {
                        start = start + charlength;
                        charlength = 1;
                        if (start >= end)
                            break;
                        char c4 = buffer[start];
                        if (c4 == '\\')
                            c4 = next();
                        if (c4 != '=')
                            break;
                        start = start + charlength;
                        charlength = 1;
                    }
                }
                break;
        }
        return TokenType.OPERATOR;
    }

    private TokenType readCharLiteral() {

        if (start >= end)
            return bad();
        char c = buffer[start];
        if (c == '\\')
            c = next();

        while (c != '\"') {
            switch (c) {
                case '\\' :
                    start = start + charlength;
                    charlength = 1;
                    boolean ok = readEscapeSequence();
                    if (!ok)
                        return bad();
                    break;
                case '\n' :
                    return bad();
                default :
                    start = start + charlength;
                    charlength = 1;
                    if (start >= end)
                        return bad();
                    break;
            }
            c = buffer[start];
            if (c == '\\')
                c = next();
        }
        if (c != '\"') {
            return bad();
        }
        start = start + charlength;
        charlength = 1;
        return TokenType.CHARACTER;
    }

    private TokenType readStringLiteral() {

        if (start >= end)
            return bad();
        char c = buffer[start];
        if (c == '\\')
            c = next();

        while (c != '\'') {
            switch (c) {
                case '\\' :
                    start = start + charlength;
                    charlength = 1;
                    boolean ok = readEscapeSequence();
                    if (!ok)
                        return bad();
                    break;
                case '\n' :
                    return bad();
                default :
                    start = start + charlength;
                    charlength = 1;
                    if (start >= end)
                        return bad();
                    break;
            }
            c = buffer[start];
            if (c == '\\')
                c = next();
        }
        if (c != '\'') {
            return bad();
        }
        start = start + charlength;
        charlength = 1;
        return TokenType.STRING;
    }

    private TokenType readSlash() {

        if (start >= end)
            return TokenType.OPERATOR;
        char c = buffer[start];
        if (c == '\\') {
            c = next();
        }
        if (c == '-' && buffer[start - 1] == '-') {
            while (c != '\n') {
                start = start + charlength;
                charlength = 1;
                if (start >= end) {
                    return TokenType.COMMENT;
                }
                c = buffer[start];
                if (c == '\\') {
                    c = next();
                }
            }
            start = start + charlength;
            charlength = 1;
            return TokenType.COMMENT;
        } else if (c == '*') {
            start = start + charlength;
            charlength = 1;
            return readComment(TokenType.START_COMMENT);
        }
        return readOperator('/');
    }

    private TokenType readVariable(TokenType type) {

        if (start >= end) {
            return type;
        }

        char c = buffer[start];
        if (c == '\\') {
            c = next();
        }

        if (c == '{' && buffer[start - 1] == '$') {
            while (c != '}') {
                start = start + charlength;
                charlength = 1;
                if (start >= end) {
                    return bad();
                }
                c = buffer[start];
                if (c == '\\') {
                    c = next();
                }
            }
            start = start + charlength;
            charlength = 1;
            return TokenType.VARIABLE;
        }
        return readOperator('$');
    }

    // Read one line of a /*...*/ comment, given the expected type
    private TokenType readComment(TokenType type) {

        if (start >= end)
            return type;
        char c = buffer[start];
        if (c == '\\')
            c = next();

        while (true) {
            while (c != '*' && c != '\n') {
                start = start + charlength;
                charlength = 1;
                if (start >= end) {
                    return bad();
                }
                c = buffer[start];
                if (c == '\\') {
                    c = next();
                }
            }
            start = start + charlength;
            charlength = 1;
            if (c == '\n')
                return type;
            if (start >= end)
                return type;
            c = buffer[start];
            if (c == '\\')
                c = next();
            if (c == '/') {
                start = start + charlength;
                charlength = 1;
                if (type == TokenType.START_COMMENT) {
                    return TokenType.COMMENT;
                }
                return TokenType.END_COMMENT;
            }
        }
    }

    // Read a number, without checking whether it is out of range
    // Doesn't deal with e.g. 0777.9 or 07779f
    private TokenType readNumber(final char c) {

        char character = c;
        if (character == '0') {
            int saveStart = start, saveLength = charlength;
            start = start + charlength;
            charlength = 1;
            if (start >= end)
                return TokenType.NUMBER;
            char c2 = buffer[start];
            if (c2 == '\\')
                c2 = next();
            switch (c2) {
                case 'x' :
                case 'X' :
                    start = start + charlength;
                    charlength = 1;
                    boolean ok = readDigits(16);
                    if (!ok)
                        return bad();
                    readSuffix();
                    return TokenType.NUMBER;
                case 0 :
                case 1 :
                case 2 :
                case 3 :
                case 4 :
                case 5 :
                case 6 :
                case 7 :
                    readDigits(8);
                    readSuffix();
                    return TokenType.NUMBER;
                case '.' :
                case 'e' :
                case 'E' :
                    start = saveStart;
                    charlength = saveLength;
                    break;
                case 'f' :
                case 'F' :
                case 'd' :
                case 'D' :
                    start = start + charlength;
                    charlength = 1;
                    return TokenType.NUMBER;
                case 'l' :
                case 'L' :
                    start = start + charlength;
                    charlength = 1;
                    return TokenType.NUMBER;
            }
        }
        boolean hasDigits = false;
        if ('0' <= c && c <= '9') {
            hasDigits = true;
            readDigits(10);
            if (start >= end)
                return TokenType.NUMBER;
            character = buffer[start];
            if (character == '\\')
                character = next();
            if (character == 'l' || character == 'L') {
                start = start + charlength;
                charlength = 1;
                return TokenType.NUMBER;
            }
        }
        if (character == '.') {
            start = start + charlength;
            charlength = 1;
            if (start >= end)
                return TokenType.NUMBER;
            character = buffer[start];
            if (character == '\\')
                character = next();
            if ('0' <= c && c <= '9') {
                hasDigits = true;
                readDigits(10);
                if (start >= end)
                    return TokenType.NUMBER;
                character = buffer[start];
                if (character == '\\')
                    character = next();
            }
        }
        if (!hasDigits)
            return bad();
        switch (c) {
            case 'e' :
            case 'E' :
                start = start + charlength;
                charlength = 1;
                if (start >= end)
                    return bad();
                character = buffer[start];
                if (character == '\\')
                    character = next();
                if (c == '+' || c == '-') {
                    start = start + charlength;
                    charlength = 1;
                    if (start >= end) {
                        return bad();
                    }
                    character = buffer[start];
                    if (character == '\\') {
                        character = next();
                    }
                }
                readDigits(10);
                break;
            case 'f' :
            case 'F' :
            case 'd' :
            case 'D' :
                start = start + charlength;
                charlength = 1;
                return TokenType.NUMBER;
        }
        return TokenType.NUMBER;
    }

    private boolean readDigits(int radix) {

        if (start >= end)
            return false;
        char c = buffer[start];
        if (c == '\\')
            c = next();
        if (Character.digit(c, radix) == -1)
            return false;
        while (Character.digit(c, radix) != -1) {
            start = start + charlength;
            charlength = 1;
            if (start >= end)
                return true;
            c = buffer[start];
            if (c == '\\')
                c = next();
        }
        return true;
    }

    private void readSuffix() {

        if (start >= end)
            return;
        char c = buffer[start];
        if (c == '\\')
            c = next();
        switch (c) {
            case 'f' :
            case 'F' :
            case 'd' :
            case 'D' :
            case 'l' :
            case 'L' :
                start = start + charlength;
                charlength = 1;
        }
    }

    private TokenType readDot() {

        if (start >= end)
            return TokenType.SEPARATOR;
        char c2 = buffer[start];
        if (c2 == '\\')
            c2 = next();
        if (Character.isDigit(c2)) {
            return readNumber('.');
        }
        if (start + 1 >= end)
            return TokenType.SEPARATOR;
        if (c2 != '.' || buffer[start + 1] != '.')
            return TokenType.SEPARATOR;
        start = start + 2;
        return TokenType.SEPARATOR;
    }

    private boolean readEscapeSequence() {

        if (start >= end)
            return false;
        char c2 = buffer[start];
        if (c2 == '\\')
            c2 = next();

        switch (c2) {
            case 'b' :
            case 't' :
            case 'n' :
            case 'f' :
            case 'r' :
            case '\"' :
            case '\'' :
            case '\\' :
                start = start + charlength;
                charlength = 1;
                return true;
            case '0' :
            case '1' :
            case '2' :
            case '3' :
                return readOctal(3);
            case '4' :
            case '5' :
            case '6' :
            case '7' :
                return readOctal(2);
            default :
                return false;
        }
    }

    private boolean readOctal(int maxlength) {

        if (start >= end)
            return false;
        char c = buffer[start];
        if (c == '\\')
            c = next();

        int i, val = 0;
        for (i = 0; i < maxlength; i++) {
            if (Character.digit(c, 8) != -1) {
                val = 8 * val + Character.digit(c, 8);
                start = start + charlength;
                charlength = 1;
                if (start >= end)
                    break;
                c = buffer[start];
                if (c == '\\')
                    c = next();
            } else
                break;
        }
        if ((i == 0) || (val > 0xFF))
            return false;
        return true;
    }

    // A malformed or incomplete token has a negative type
    private TokenType bad() {

        return TokenType.UNRECOGNIZED;
    }

    // Look ahead at the next character or unicode escape.
    // For efficiency, replace c = next(); with
    // c = buffer[start]; if (c == '\\') c = next();
    // To accept the character after looking at it, use:
    // start = start + charlength; charlength = 1;

    // Record the number of source code characters used up. To deal with an odd
    // or even number of backslashes preceding a unicode escape, whenever a
    // second backslash is coming up, mark its position as a pair.

    private char next() {

        if (start >= end)
            return 26; // EOF
        char c = buffer[start];
        if (c != '\\')
            return c;
        if (start == pair) {
            pair = 0;
            return '\\';
        }
        if (start + 1 >= end)
            return '\\';

        c = buffer[start + 1];
        if (c == '\\')
            pair = start + 1;
        if (c != 'u')
            return '\\';

        int pos = start + 2;
        while (pos < end && buffer[pos] == 'u')
            pos++;
        if (pos + 4 > end) {
            charlength = end - start;
            return '\0';
        }

        c = 0;
        for (int j = 0; j < 4; j++) {
            int d = Character.digit(buffer[pos + j], 16);
            if (d < 0) {
                charlength = pos + j - start;
                return '\0';
            }
            c = (char) (c * 16 + d);
        }
        charlength = pos + 4 - start;
        return c;
    }

    private void initKind() {

        for (char c = 0; c < 128; c++)
            kind[c] = -1;
        for (char c = 0; c < 128; c++)
            switch (c) {
                case 0 :
                case 1 :
                case 2 :
                case 3 :
                case 4 :
                case 5 :
                case 6 :
                case 7 :
                case 8 :
                case 11 :
                case 13 :
                case 14 :
                case 15 :
                case 16 :
                case 17 :
                case 18 :
                case 19 :
                case 20 :
                case 21 :
                case 22 :
                case 23 :
                case 24 :
                case 25 :
                case 27 :
                case 28 :
                case 29 :
                case 30 :
                case 31 :
                case 127 :
                case '#' :
                case '@' :
                case '`' :
                case '\\' :
                    kind[c] = (byte) TokenType.UNRECOGNIZED.ordinal();
                    break;
                case '\t' :
                case '\n' :
                case ' ' :
                case '\f' :
                case 26 :
                    kind[c] = (byte) TokenType.WHITESPACE.ordinal();
                    break;
                case '!' :
                case '%' :
                case '&' :
                case '*' :
                case '+' :
                case ':' :
                case '<' :
                case '=' :
                case '>' :
                case '?' :
                case '^' :
                case '|' :
                case '~' :
                    kind[c] = (byte) TokenType.OPERATOR.ordinal();
                    break;
                case '\'' :
                    kind[c] = (byte) TokenType.STRING.ordinal();
                    break;
                case '\"' :
                    kind[c] = (byte) TokenType.CHARACTER.ordinal();
                    break;
                case '.' :
                    kind[c] = (byte) TokenType.PUNCTUATION.ordinal();
                    break;
                case '/' :
                case '-' :
                    kind[c] = (byte) TokenType.COMMENT.ordinal();
                    break;
                case 'A' :
                case 'B' :
                case 'C' :
                case 'D' :
                case 'E' :
                case 'F' :
                case 'G' :
                case 'H' :
                case 'I' :
                case 'J' :
                case 'K' :
                case 'L' :
                case 'M' :
                case 'N' :
                case 'O' :
                case 'P' :
                case 'Q' :
                case 'R' :
                case 'S' :
                case 'T' :
                case 'U' :
                case 'V' :
                case 'W' :
                case 'X' :
                case 'Y' :
                case 'Z' :
                case '_' :
                case 'a' :
                case 'b' :
                case 'c' :
                case 'd' :
                case 'e' :
                case 'f' :
                case 'g' :
                case 'h' :
                case 'i' :
                case 'j' :
                case 'k' :
                case 'l' :
                case 'm' :
                case 'n' :
                case 'o' :
                case 'p' :
                case 'q' :
                case 'r' :
                case 's' :
                case 't' :
                case 'u' :
                case 'v' :
                case 'w' :
                case 'x' :
                case 'y' :
                case 'z' :
                    kind[c] = (byte) TokenType.IDENTIFIER.ordinal();
                    break;
                case '0' :
                case '1' :
                case '2' :
                case '3' :
                case '4' :
                case '5' :
                case '6' :
                case '7' :
                case '8' :
                case '9' :
                    kind[c] = (byte) TokenType.NUMBER.ordinal();
                    break;
                case '(' :
                case ')' :
                case '[' :
                case ']' :
                case '{' :
                case '}' :
                    kind[c] = (byte) TokenType.BRACKET.ordinal();
                    break;
                case ',' :
                case ';' :
                    kind[c] = (byte) TokenType.SEPARATOR.ordinal();
                    break;
                case '$' :
                    kind[c] = (byte) TokenType.VARIABLE.ordinal();
                    break;
            }
        for (char c = 0; c < 128; c++)
            if (kind[c] == -1)
                System.out.println("Char " + ((int) c) + " hasn't been classified");
    }

    private void initUniKind() {

        for (byte b = 0; b < 31; b++)
            unikind[b] = -1;
        for (byte b = 0; b < 31; b++)
            switch (b) {
                case Character.UNASSIGNED :
                case Character.ENCLOSING_MARK :
                case Character.OTHER_NUMBER :
                case Character.SPACE_SEPARATOR :
                case Character.LINE_SEPARATOR :
                case Character.PARAGRAPH_SEPARATOR :
                case Character.CONTROL :
                case 17 : // category 17 is unused
                case Character.PRIVATE_USE :
                case Character.SURROGATE :
                case Character.DASH_PUNCTUATION :
                case Character.START_PUNCTUATION :
                case Character.END_PUNCTUATION :
                case Character.OTHER_PUNCTUATION :
                case Character.MATH_SYMBOL :
                case Character.MODIFIER_SYMBOL :
                case Character.OTHER_SYMBOL :
                case Character.INITIAL_QUOTE_PUNCTUATION :
                case Character.FINAL_QUOTE_PUNCTUATION :
                    unikind[b] = (byte) TokenType.UNRECOGNIZED.ordinal();
                    break;
                case Character.UPPERCASE_LETTER :
                case Character.LOWERCASE_LETTER :
                case Character.TITLECASE_LETTER :
                case Character.MODIFIER_LETTER :
                case Character.OTHER_LETTER :
                case Character.LETTER_NUMBER :
                case Character.CONNECTOR_PUNCTUATION : // maybe NUMBER
                case Character.CURRENCY_SYMBOL :
                    // Characters where Other_ID_Start is true
                    unikind[b] = (byte) TokenType.IDENTIFIER.ordinal();
                    break;
                case Character.NON_SPACING_MARK :
                case Character.COMBINING_SPACING_MARK :
                case Character.DECIMAL_DIGIT_NUMBER :
                case Character.FORMAT :
                    unikind[b] = (byte) TokenType.NUMBER.ordinal();
                    break;
            }
        for (byte b = 0; b < 31; b++)
            if (unikind[b] == -1)
                System.out.println("Unicode cat " + b + " hasn't been classified");
    }

}
