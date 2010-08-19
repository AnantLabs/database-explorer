/*
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 * 
 * The Original Code is iSQL-Viewer, A Mutli-Platform Database Tool.
 *
 * The Initial Developer of the Original Code is iSQL-Viewer, A Mutli-Platform Database Tool.
 * Portions created by Mark A. Kobold are Copyright (C) 2000-2007. All Rights Reserved.
 *
 * Contributor(s): 
 *  Mark A. Kobold [mkobold <at> isqlviewer <dot> com].
 *  
 * If you didn't download this code from the following link, you should check
 * if you aren't using an obsolete version: http://www.isqlviewer.com
 */
package com.gs.dbex.application.sql.processor;

import java.util.TreeMap;

/**
 * TODO Add Scanner JavaDoc inforamation
 * <p>
 * 
 * @author Mark A. Kobold &lt;mkobold at isqlviewer dot com&gt;
 * @version 1.0
 */
public abstract class AbstractProcessor {

    /**
     * <p>
     * Read one token from the start of the current text buffer, given the start offset, end offset, and current scanner
     * state. The method moves the start offset past the token, updates the scanner state, and returns the type of the
     * token just scanned.
     * <p>
     * The scanner state is a representative token type. It is either the state left after the last call to read, or the
     * type of the old token at the same position if rescanning, or WHITESPACE if at the start of a document. The method
     * succeeds in all cases, returning whitespace or comment or error tokens where necessary. Each line of a multi-line
     * comment is treated as a separate token, to improve incremental rescanning. If the buffer does not extend to the
     * end of the document, the last token returned for the buffer may be incomplete and the caller must rescan it. The
     * read method can be overridden to implement different languages. The default version splits plain text into words,
     * numbers and punctuation.
     */
    protected TokenType read() {

        char c = buffer[start];
        TokenType type;
        // Ignore the state, since there is only one.
        if (Character.isWhitespace(c)) {
            type = TokenType.WHITESPACE;
            while (++start < end) {
                if (!Character.isWhitespace(buffer[start]))
                    break;
            }
        } else if (Character.isLetter(c)) {
            type = TokenType.WORD;
            while (++start < end) {
                c = buffer[start];
                if (Character.isLetter(c) || Character.isDigit(c))
                    continue;
                if (c == '-' || c == '\'' || c == '_')
                    continue;
                break;
            }
        } else if (Character.isDigit(c)) {
            type = TokenType.NUMBER;
            while (++start < end) {
                c = buffer[start];
                if (!Character.isDigit(c) && c != '.')
                    break;
            }
        } else if (c >= '!' || c <= '~') {
            type = TokenType.PUNCTUATION;
            start++;
        } else {
            type = TokenType.UNRECOGNIZED;
            start++;
        }

        // state = WHITESPACE;
        return type;
    }

    /**
     * The current buffer of text being scanned.
     */
    protected char[] buffer;

    /**
     * The current offset within the buffer, at which to scan the next token.
     */
    protected int start;

    /**
     * The end offset in the buffer.
     */
    protected int end;

    /**
     * The current scanner state, as a representative token type.
     */
    protected TokenType state = TokenType.WHITESPACE;

    // The array of tokens forms a gap buffer. The total length of the text is
    // tracked, and tokens after the gap have (negative) positions relative to
    // the end of the text. While scanning, the gap represents the area to be
    // scanned, no tokens after the gap can be taken as valid, and in particular
    // the end-of-text sentinel token is after the gap.

    private Token[] tokens;
    private int gap, endgap, textLength;
    private boolean scanning;
    private int position;

    /**
     * The symbol table can be accessed by <code>initSymbolTable</code> or <code>lookup</code>, if they are
     * overridden. Symbols are inserted with <code>symbolTable.put(sym,sym)</code> and extracted with
     * <code>symbolTable.get(sym)</code>.
     */
    protected TreeMap<String, TextSymbol> symbolTable;

    public/**
             * Create a new Scanner representing an empty text document. For non-incremental scanning, use change() to
             * report the document size, then pass the entire text to the scan() method in one go, or if coming from an
             * input stream, a bufferful at a time.
             */
    AbstractProcessor() {

        tokens = new Token[1];
        gap = 0;
        endgap = 0;
        textLength = 0;
        symbolTable = new TreeMap<String, TextSymbol>(String.CASE_INSENSITIVE_ORDER);
        initSymbolTable();
        TextSymbol endOfText = new TextSymbol(TokenType.WHITESPACE, "");
        tokens[0] = new Token(endOfText, 0);
        scanning = false;
        position = 0;
    }

    /**
     * Find the number of available valid tokens, not counting tokens in or after any area yet to be rescanned.
     */
    public int size() {

        if (scanning)
            return gap;
        return gap + tokens.length - endgap;
    }

    /**
     * Find the n'th token, or null if it is not currently valid.
     */
    public Token getToken(int n) {

        if (n < 0 || n >= gap && scanning)
            return null;
        if (n >= gap)
            moveGap(n + 1);
        return tokens[n];
    }

    /**
     * Find the index of the valid token starting before, but nearest to, text position p. This uses an O(log(n)) binary
     * chop search.
     */
    public int find(int p) {

        int firstIndex = 0, lastIndex, mid, midpos;
        if (!scanning) {
            moveGap(gap + tokens.length - endgap);
        }
        lastIndex = gap - 1;
        if (p > tokens[lastIndex].position) {
            return lastIndex;
        }
        while (lastIndex > firstIndex + 1) {
            mid = (firstIndex + lastIndex) / 2;
            midpos = tokens[mid].position;
            if (p > midpos) {
                firstIndex = mid;
            } else {
                lastIndex = mid;
            }
        }
        return firstIndex;
    }

    /**
     * Report the position of an edit, the length of the text being replaced, and the length of the replacement text, to
     * prepare for rescanning. The call returns the index of the token at which rescanning will start.
     */
    public int change(int firstIndex, int len, int newLen) {

        if (firstIndex < 0 || len < 0 || newLen < 0 || firstIndex + len > textLength) {
            throw new Error("change(" + firstIndex + "," + len + "," + newLen + ")");
        }
        textLength += newLen - len;
        int currentEnd = firstIndex + newLen;
        if (scanning) {
            while (gap > 0 && tokens[gap - 1].position > firstIndex) {
                gap--;
            }
            if (gap > 0) {
                gap--;
            }
            if (gap > 0) {
                gap--;
                position = tokens[gap].position;
                state = tokens[gap].symbol.type;
            } else {
                position = 0;
                state = TokenType.WHITESPACE;
            }
            while (tokens[endgap].position + textLength < currentEnd) {
                endgap++;
            }
            return gap;
        }
        if (endgap == tokens.length) {
            moveGap(gap - 1);
        }
        scanning = true;
        while (tokens[endgap].position + textLength < firstIndex) {
            tokens[endgap].position += textLength;
            tokens[gap++] = tokens[endgap++];
        }
        while (gap > 0 && tokens[gap - 1].position > firstIndex) {
            tokens[--endgap] = tokens[--gap];
            tokens[endgap].position -= textLength;
        }
        if (gap > 0) {
            gap--;
        }
        if (gap > 0) {
            gap--;
            position = tokens[gap].position;
            state = tokens[gap].symbol.type;
        } else {
            position = 0;
            state = TokenType.WHITESPACE;
        }
        while (tokens[endgap].position + textLength < currentEnd) {
            endgap++;
        }
        return gap;
    }

    /**
     * Find out at what text position any remaining scanning work should start, or -1 if scanning is complete.
     */
    public int position() {

        if (!scanning)
            return -1;
        return position;
    }

    /**
     * Scan or rescan a given read-only segment of text. The segment is assumed to represent a portion of the document
     * starting at <code>position()</code>. Return the number of tokens successfully scanned, excluding any partial
     * token at the end of the text segment but not at the end of the document. If the result is 0, the call should be
     * retried with a longer segment.
     */
    public int scan(char[] array, int offset, int length) {

        if (!scanning) {
            throw new Error("scan called when not scanning");
        }
        if (position + length > textLength) {
            return -1;
        }
        boolean all = position + length == textLength;
        end = start + length;
        int startGap = gap;

        buffer = array;
        start = offset;
        end = start + length;
        while (start < end) {
            int tokenStart = start;
            TokenType type = read();
            if (start == end && !all)
                break;

            if (type != TokenType.WHITESPACE) {
                String name = new String(buffer, tokenStart, start - tokenStart);
                TextSymbol sym = lookup(type, name);
                Token t = new Token(sym, position);
                if (gap >= endgap) {
                    checkCapacity(gap + tokens.length - endgap + 1);
                }
                tokens[gap++] = t;
            }

            // Try to synchronise

            while (tokens[endgap].position + textLength < position)
                endgap++;
            if (position + start - tokenStart == textLength)
                scanning = false;
            else if (gap > 0 && tokens[endgap].position + textLength == position && tokens[endgap].symbol.type == type) {
                endgap++;
                scanning = false;
                break;
            }
            position += start - tokenStart;
        }
        checkCapacity(gap + tokens.length - endgap);
        return gap - startGap;
    }

    /**
     * Create the initial symbol table. This can be overridden to enter keywords, for example. The default
     * implementation does nothing.
     */
    protected abstract void initSymbolTable();

    /**
     * Lookup a symbol in the symbol table. This can be overridden to implement keyword detection, for example. The
     * default implementation just uses the table to ensure that there is only one shared occurrence of each symbol.
     */
    protected TextSymbol lookup(TokenType type, String name) {

        TextSymbol sym = symbolTable.get(name);
        if (sym != null) {
            return sym;
        }
        sym = new TextSymbol(type, name);
        symbolTable.put(name, sym);
        return sym;
    }

    // Change the size of the gap buffer, doubling it if it fills up, and
    // halving if it becomes less than a quarter full.
    private void checkCapacity(int capacity) {

        int oldCapacity = tokens.length;
        if (capacity <= oldCapacity && 4 * capacity >= oldCapacity)
            return;
        Token[] oldTokens = tokens;
        int newCapacity;
        if (capacity > oldCapacity) {
            newCapacity = oldCapacity * 2;
            if (newCapacity < capacity)
                newCapacity = capacity;
        } else {
            newCapacity = capacity * 2;
        }

        tokens = new Token[newCapacity];
        System.arraycopy(oldTokens, 0, tokens, 0, gap);
        int n = oldCapacity - endgap;
        System.arraycopy(oldTokens, endgap, tokens, newCapacity - n, n);
        endgap = newCapacity - n;
    }

    // Move the gap to a new index within the tokens array. When preparing to
    // pass a token back to a caller, this is used to ensure that the token's
    // position is relative to the start of the text and not the end.
    private void moveGap(int newgap) {

        if (scanning)
            throw new Error("moveGap called while scanning");
        if (newgap < 0 || newgap > gap + tokens.length - endgap) {
            throw new Error("bad argument to moveGap");
        }
        if (gap < newgap) {
            while (gap < newgap) {
                tokens[endgap].position += textLength;
                tokens[gap++] = tokens[endgap++];
            }
        } else if (gap > newgap) {
            while (gap > newgap) {
                tokens[--endgap] = tokens[--gap];
                tokens[endgap].position -= textLength;
            }
        }
    }
}
