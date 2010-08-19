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

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
 * This class is primarily a copy Of the java.util.StringTokenizer with some extentsions.
 * <p>
 * Things you can do in this version and not in default JDK version.
 * <ul>
 * <li>This class also supports tokenizing with quotes.
 * <li>Get the index within the original text that the token was at.
 * </ul>
 * 
 * @author Mark A. Kobold &lt;mkobold at isqlviewer dot com&gt;
 * @version 1.0
 */
public class StringTokenizer implements Enumeration<String> {

    private int currentPosition;
    private int newPosition;
    private int maxPosition;
    private String str;
    private String delimiters;
    private boolean retDelims;
    private boolean delimsChanged;
    /**
     * maxDelimCodePoint stores the value of the delimiter character with the highest value. It is used to optimize the
     * detection of delimiter characters. It is unlikely to provide any optimization benefit in the hasSurrogates case
     * because most string characters will be smaller than the limit, but we keep it so that the two code paths remain
     * similar.
     */
    private int maxDelimCodePoint;
    private boolean quotesEnabled;

    /**
     * Set maxDelimCodePoint to the highest char in the delimiter set.
     */
    private void setMaxDelimCodePoint() {

        if (delimiters == null) {
            maxDelimCodePoint = 0;
            return;
        }

        int m = 0;
        int c;
        int count = 0;
        for (int i = 0; i < delimiters.length(); i += Character.charCount(c)) {
            c = delimiters.charAt(i);
            if (m < c)
                m = c;
            count++;
        }
        maxDelimCodePoint = m;
    }

    /**
     * Constructs a string tokenizer for the specified string. All characters in the <code>delim</code> argument are
     * the delimiters for separating tokens.
     * <p>
     * If the <code>returnDelims</code> flag is <code>true</code>, then the delimiter characters are also returned
     * as tokens. Each delimiter is returned as a string of length one. If the flag is <code>false</code>, the
     * delimiter characters are skipped and only serve as separators between tokens.
     * <p>
     * Note that if <tt>delim</tt> is <tt>null</tt>, this constructor does not throw an exception. However, trying
     * to invoke other methods on the resulting <tt>StringTokenizer</tt> may result in a <tt>NullPointerException</tt>.
     * 
     * @param str a string to be parsed.
     * @param delim the delimiters.
     * @param returnDelims flag indicating whether to return the delimiters as tokens.
     * @exception NullPointerException if str is <CODE>null</CODE>
     */
    public StringTokenizer(String str, String delim, boolean returnDelims) {

        currentPosition = 0;
        newPosition = -1;
        delimsChanged = false;
        this.str = str;
        maxPosition = str.length();
        delimiters = delim;
        retDelims = returnDelims;
        setMaxDelimCodePoint();
    }

    /**
     * Constructs a string tokenizer for the specified string. The characters in the <code>delim</code> argument are
     * the delimiters for separating tokens. Delimiter characters themselves will not be treated as tokens.
     * <p>
     * Note that if <tt>delim</tt> is <tt>null</tt>, this constructor does not throw an exception. However, trying
     * to invoke other methods on the resulting <tt>StringTokenizer</tt> may result in a <tt>NullPointerException</tt>.
     * 
     * @param str a string to be parsed.
     * @param delim the delimiters.
     * @exception NullPointerException if str is <CODE>null</CODE>
     */
    public StringTokenizer(String str, String delim) {

        this(str, delim, false);
    }

    /**
     * Constructs a string tokenizer for the specified string. The tokenizer uses the default delimiter set, which is
     * <code>"&nbsp;&#92;t&#92;n&#92;r&#92;f"</code>: the space character, the tab character, the newline character,
     * the carriage-return character, and the form-feed character. Delimiter characters themselves will not be treated
     * as tokens.
     * 
     * @param str a string to be parsed.
     * @exception NullPointerException if str is <CODE>null</CODE>
     */
    public StringTokenizer(String str) {

        this(str, " \t\n\r\f", false);
    }

    /**
     * Skips delimiters starting from the specified position. If retDelims is false, returns the index of the first
     * non-delimiter character at or after startPos. If retDelims is true, startPos is returned.
     */
    private int skipDelimiters(int startPos) {

        if (delimiters == null)
            throw new NullPointerException();

        int position = startPos;
        while (!retDelims && position < maxPosition) {
            char c = str.charAt(position);
            if ((c > maxDelimCodePoint) || (delimiters.indexOf(c) < 0))
                break;
            position++;
        }
        return position;
    }

    /**
     * Skips ahead from startPos and returns the index of the next delimiter character encountered, or maxPosition if no
     * such delimiter is found.
     */
    private int scanToken(int startPos) {

        int position = startPos;
        boolean withinLiteral = false;
        while (position < maxPosition) {
            char c = str.charAt(position);
            if (isQuotable(c) && quotesEnabled) {
                if (position > 0 && str.charAt(position - 1) != '\\') {
                    // quote was not escaped //
                    withinLiteral = !withinLiteral;
                }
            }
            if ((c <= maxDelimCodePoint) && (delimiters.indexOf(c) >= 0))
                if (!withinLiteral) {
                    break;
                }
            position++;
        }
        if (retDelims && (startPos == position)) {
            char c = str.charAt(position);
            if ((c <= maxDelimCodePoint) && (delimiters.indexOf(c) >= 0))
                position++;
        }
        return position;
    }

    private boolean isQuotable(char character) {

        return character == '\'' || character == '\"';
    }

    /**
     * Tests if there are more tokens available from this tokenizer's string. If this method returns <tt>true</tt>,
     * then a subsequent call to <tt>nextToken</tt> with no argument will successfully return a token.
     * 
     * @return <code>true</code> if and only if there is at least one token in the string after the current position;
     *         <code>false</code> otherwise.
     */
    public boolean hasMoreTokens() {

        /*
         * Temporarily store this position and use it in the following nextToken() method only if the delimiters haven't
         * been changed in that nextToken() invocation.
         */
        newPosition = skipDelimiters(currentPosition);
        return (newPosition < maxPosition);
    }

    /**
     * Returns the next token from this string tokenizer.
     * 
     * @return the next token from this string tokenizer.
     * @exception NoSuchElementException if there are no more tokens in this tokenizer's string.
     */
    public String nextToken() {

        /*
         * If next position already computed in hasMoreElements() and delimiters have changed between the computation
         * and this invocation, then use the computed value.
         */

        currentPosition = (newPosition >= 0 && !delimsChanged) ? newPosition : skipDelimiters(currentPosition);

        /* Reset these anyway */
        delimsChanged = false;
        newPosition = -1;

        if (currentPosition >= maxPosition)
            throw new NoSuchElementException();
        int start = currentPosition;
        currentPosition = scanToken(currentPosition);
        return str.substring(start, currentPosition);
    }

    /**
     * Returns the next token in this string tokenizer's string. First, the set of characters considered to be
     * delimiters by this <tt>StringTokenizer</tt> object is changed to be the characters in the string <tt>delim</tt>.
     * Then the next token in the string after the current position is returned. The current position is advanced beyond
     * the recognized token. The new delimiter set remains the default after this call.
     * 
     * @param delim the new delimiters.
     * @return the next token, after switching to the new delimiter set.
     * @exception NoSuchElementException if there are no more tokens in this tokenizer's string.
     * @exception NullPointerException if delim is <CODE>null</CODE>
     */
    public String nextToken(String delim) {

        delimiters = delim;

        /* delimiter string specified, so set the appropriate flag. */
        delimsChanged = true;

        setMaxDelimCodePoint();
        return nextToken();
    }

    /**
     * Returns the same value as the <code>hasMoreTokens</code> method. It exists so that this class can implement the
     * <code>Enumeration</code> interface.
     * 
     * @return <code>true</code> if there are more tokens; <code>false</code> otherwise.
     * @see java.util.Enumeration
     * @see java.util.StringTokenizer#hasMoreTokens()
     */
    public boolean hasMoreElements() {

        return hasMoreTokens();
    }

    /**
     * Returns the same value as the <code>nextToken</code> method, except that its declared return value is
     * <code>Object</code> rather than <code>String</code>. It exists so that this class can implement the
     * <code>Enumeration</code> interface.
     * 
     * @return the next token in the string.
     * @exception NoSuchElementException if there are no more tokens in this tokenizer's string.
     * @see java.util.Enumeration
     * @see java.util.StringTokenizer#nextToken()
     */
    public String nextElement() {

        return nextToken();
    }

    /**
     * Calculates the number of times that this tokenizer's <code>nextToken</code> method can be called before it
     * generates an exception. The current position is not advanced.
     * 
     * @return the number of tokens remaining in the string using the current delimiter set.
     * @see java.util.StringTokenizer#nextToken()
     */
    public int countTokens() {

        int count = 0;
        int currpos = currentPosition;
        while (currpos < maxPosition) {
            currpos = skipDelimiters(currpos);
            if (currpos >= maxPosition)
                break;
            currpos = scanToken(currpos);
            count++;
        }
        return count;
    }

    public int getTokenIndex() {

        return currentPosition;
    }

    /**
     * Sets quotes enabled for determining tokenzation.
     * <p>
     * 
     * @param quotesEnabled flag to enable/disable quote sensitivity during tokenization.
     */
    public void setQuotesEnabled(boolean quotesEnabled) {

        this.quotesEnabled = quotesEnabled;
    }

    /**
     * Determines if this tokenizer is sensitive to quoted strings.
     * <p>
     * 
     * @return <tt>true</tt> if this instance is sensitive to quotes for each token.
     */
    public boolean isQuotesEnabled() {

        return quotesEnabled;
    }

    public String[] toArray() {

        ArrayList<String> tokens = new ArrayList<String>();
        // reset internal variables so we get all tokens.
        currentPosition = 0;
        newPosition = -1;
        delimsChanged = false;

        while (hasMoreTokens()) {
            tokens.add(nextToken());
        }

        // reset internal variables so it can stil be used normally.
        currentPosition = 0;
        newPosition = -1;
        delimsChanged = false;
        return tokens.toArray(new String[tokens.size()]);
    }
}