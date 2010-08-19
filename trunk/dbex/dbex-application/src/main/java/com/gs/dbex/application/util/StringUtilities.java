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
package com.gs.dbex.application.util;

import java.awt.Color;
import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Ref;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.crypto.Cipher;

/**
 * Generic String manipulation library for converting strings or working with text.
 * <p>
 * 
 * @author Mark A. Kobold &lt;mkobold at isqlviewer dot com&gt;
 * @version 1.0
 */
public final class StringUtilities {

    private static final String RESOURCE_BUNDLE = "org.isqlviewer.util.ResourceBundle";
    private static final String MD5_ALGORITHIM = "MD5";
    private static final String DELIM_START = "${";
    private static final char DELIM_STOP = '}';
    private static final int DELIM_START_LEN = 2;
    private static final int DELIM_STOP_LEN = 1;
    private static final LocalMessages messages = new LocalMessages(RESOURCE_BUNDLE);

    private StringUtilities() {

    }

    /**
     * Utility method for accessing a system property without a security permission.
     * <p>
     * This method will simply eat any security exception that could occur and return the default.
     * 
     * @param key of the system property to lookup.
     * @param def the default value to use if the property cannot be read.
     * @return the respective system property or the default if cannot be read.
     */
    public static String getSystemProperty(String key, String def) {

        try {
            return System.getProperty(key, def);
        } catch (Throwable e) {
            return def;
        }
    }

    /**
     * Performs variable string substitution with a set of named parameters.
     * <p>
     * In similar fashion the the JVM and Jakarta projects this will perform string substitution on a string based on a
     * set of properties using the names to create a dynamically formatted text, that is based on easy to create, as
     * well as read source string.
     * <p>
     * Using the source text of <code>'The quick brown ${fast animal} jumps over the lazy ${lazy animal}'</code> you
     * would then set properties in the give props of 'fast animal' and 'lazy animal' with the respective values that
     * you want to be used as a replacement to the ${} variables. such that is 'fast animal' = 'fox' and 'lazy animal' =
     * 'dog' the result would be the famous sentence of 'The quick brown fox jumps over the lazy dog'.
     * <p>
     * This method does support recursive variables such that a variable can reference another variable until either
     * there is a stack overflow or there are no variables left to substitute.
     * 
     * @param source string to be formatted using variable substitution.
     * @param props set of variable names to variable values to be used for the substitution process.
     * @return a formatted string with the variables properly substituted.
     * @throws ParseException if a variable declaration is started but is not closed.
     */
    public static String substituteVariables(String source, Properties props) throws ParseException {

        StringBuffer sbuf = new StringBuffer();
        int i = 0;
        int j, k;
        while (true) {
            j = source.indexOf(DELIM_START, i);
            if (j == -1) {
                // no more variables
                if (i == 0) { // this is a simple string
                    return source;
                }
                // add the tail string which contains no variables and return the result.
                sbuf.append(source.substring(i, source.length()));
                return sbuf.toString();
            }
            sbuf.append(source.substring(i, j));
            k = source.indexOf(DELIM_STOP, j);
            if (k == -1) {
                throw new ParseException(messages.format("StringUtilities.bad_variable_format"), j);
            }

            j += DELIM_START_LEN;
            String key = source.substring(j, k);
            // first try in System properties
            String replacement = props == null ? null : props.getProperty(key);
            // then try props parameter
            if (replacement == null) {
                replacement = getSystemProperty(key, null);
            }
            if (replacement != null) {
                // Do variable substitution on the replacement string
                // such that we can solve "Hello ${x2}" as "Hello p1"
                // the where the properties are
                // x1=p1
                // x2=${x1}
                String recursiveReplacement = substituteVariables(replacement, props);
                sbuf.append(recursiveReplacement);
            }
            i = k + DELIM_STOP_LEN;
        }
    }

    /**
     * Forces a string to be trimmed to a specified length.
     * <p>
     * Makes sure that the text is &lt;= maxLength. If text is null or blank the original text will be returned.
     * <p>
     * A trim() will occur before a substring is created, the substring is create iff the trimed version greater than
     * the maxLength.
     * 
     * @param text to strip.
     * @param maxLength maximum size to trim to.
     * @return the text parameter that does not exceed maxLength.
     */
    public static String trimToSize(String text, int maxLength) {

        if (text == null) {
            return text;
        }
        String temp = text.trim();
        if (temp.length() <= maxLength) {
            return temp;
        }
        return temp.substring(0, maxLength);
    }

    /**
     * Searches a string for a set of characters.
     * <p>
     * This will return the first occurrence of any character that exists in both s and chars. The return values is the
     * index &gt;=0 and &lt;= s.length()-1;
     * <p>
     * If either s or chars is null or empty the method will immediately return -1.
     * 
     * @param s string to search
     * @param chars set of character to look for.
     * @return first index of any character in chars within s.
     */
    public static int searchStringSet(String s, String chars) {

        if (s == null || chars == null) {
            return -1;
        }

        int mx = s.length();
        for (int i = 0; i < mx; i++) {
            char ch = s.charAt(i);
            if (chars.indexOf(ch) >= 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Encodes XML reserved characters to their respective entities.
     * <p>
     * This will encode/change the following raw characters:
     * <ul>
     * <li>&quot; = &amp;quot;</li>
     * <li>&gt; = &amp;gt;</li>
     * <li>&lt; = &amp;lt;</li>
     * <li>&amp; = &amp;amp;</li>
     * <li>&apos; = &amp;apos;</li>
     * </ul>
     * 
     * @param str to be encoded.
     * @return String with standard encoded entities.
     */
    public static String encodeXMLEntities(String str) {

        if (str == null) {
            return "";
        }

        int len = str.length();
        StringBuffer outBuffer = new StringBuffer(len * 2);
        for (int x = 0; x < len; x++) {
            char aChar = str.charAt(x);
            switch (aChar) {
                case '\"' :
                    outBuffer.append("&quot;");
                    break;
                case '>' :
                    outBuffer.append("&gt;");
                    break;
                case '<' :
                    outBuffer.append("&lt;");
                    break;
                case '&' :
                    outBuffer.append("&amp;");
                    break;
                case '\'' :
                    outBuffer.append("&apos;");
                    break;
                default :
                    if ((aChar < 0x0020) || (aChar > 0x007e)) {
                        outBuffer.append('&');
                        outBuffer.append("#x");
                        outBuffer.append(toHex((aChar >> 12) & 0xF));
                        outBuffer.append(toHex((aChar >> 8) & 0xF));
                        outBuffer.append(toHex((aChar >> 4) & 0xF));
                        outBuffer.append(toHex(aChar & 0xF));
                        outBuffer.append(';');
                    } else {
                        outBuffer.append(aChar);
                    }
            }
        }
        return outBuffer.toString();
    }

    /**
     * Encodes string safe for HTML.
     * <p>
     * If encoding into HTML various entities will be encoded for proper HTML formatting.
     * 
     * @param theString to be encoded
     * @return encoded string.
     */
    public static String encodeHTMLEntities(String theString) {

        if (theString == null) {
            return "";
        }

        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len * 2);
        for (int x = 0; x < len; x++) {
            char aChar = theString.charAt(x);
            switch (aChar) {
                case '&' :
                    outBuffer.append("&amp;");
                    break;
                case '\t' :
                    outBuffer.append("&nbsp;&nbsp;");
                    break;
                case '\"' :
                    outBuffer.append("&quot;");
                    break;
                case '>' :
                    outBuffer.append("&gt;");
                    break;
                case '`' :
                    outBuffer.append("&apos;");
                    break;
                case '<' :
                    outBuffer.append("&lt;");
                    break;
                default :
                    if ((aChar < 0x0020) || (aChar > 0x007e)) {
                        outBuffer.append('&');
                        outBuffer.append("#x");
                        outBuffer.append(toHex((aChar >> 12) & 0xF));
                        outBuffer.append(toHex((aChar >> 8) & 0xF));
                        outBuffer.append(toHex((aChar >> 4) & 0xF));
                        outBuffer.append(toHex(aChar & 0xF));
                        outBuffer.append(';');
                    } else {
                        outBuffer.append(aChar);
                    }
            }
        }
        return outBuffer.toString();
    }

    /**
     * Encode text using an MD5 algorithim.
     * <p>
     * 
     * @param text to encode.
     * @return MD5 checksum of the text given.
     */
    public static String encodeMD5(String text) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            baos.write(text == null ? new byte[0] : text.getBytes("UTF8"));
        } catch (IOException ioe) {
        }
        byte[] hash = null;
        MessageDigest digest = allocateDigestInstance(MD5_ALGORITHIM);
        hash = digest.digest(baos.toByteArray());
        return encodeMD5Data(hash);
    }

    /**
     * Encodes method into an ASCI compatible form.
     * <p>
     * This method has the result equivalent the native2ascii tool provided by the JDK.
     * <p>
     * The value returned here is similar to that found in the Java properties output.
     * 
     * @param theString to be encoded
     * @return encoded string.
     */
    public static String encodeASCII(String theString) {

        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len * 2);
        for (int x = 0; x < len; x++) {
            char aChar = theString.charAt(x);
            switch (aChar) {
                case ' ' :
                    outBuffer.append('\\');
                    outBuffer.append(' ');
                    break;
                case '\\' :
                    outBuffer.append('\\');
                    break;
                case '\t' :
                    outBuffer.append('\\');
                    outBuffer.append('t');
                    break;
                case '\"' :
                    outBuffer.append('\"');
                    break;
                case '>' :
                    outBuffer.append('>');
                    break;
                case '<' :
                    outBuffer.append('<');
                    break;
                case '\n' :
                    outBuffer.append('\\');
                    outBuffer.append('n');
                    break;
                case '\r' :
                    outBuffer.append('\\');
                    outBuffer.append('r');
                    break;
                case '\f' :
                    outBuffer.append('\\');
                    outBuffer.append('f');
                    break;
                default :
                    if ((aChar < 0x0020) || (aChar > 0x007e)) {
                        outBuffer.append('\\');
                        outBuffer.append('u');
                        outBuffer.append(toHex((aChar >> 12) & 0xF));
                        outBuffer.append(toHex((aChar >> 8) & 0xF));
                        outBuffer.append(toHex((aChar >> 4) & 0xF));
                        outBuffer.append(toHex(aChar & 0xF));
                    } else {
                        outBuffer.append(aChar);
                    }
            }
        }
        return outBuffer.toString();
    }

    /**
     * Method for decoding ASCII escaped character sequences for UNICODE characters.
     * <p>
     * This will decode text encoded in a fashion similar to the Java Properties encoding mechanisim. Such that \\uxxxx
     * are converted to the proper characters.
     * <p>
     * In fact most of this code came from that private method contained there.
     * 
     * @see #encodeASCII(String)
     * @param str to decode ASCII escaped character sequences from.
     * @return decoded string.
     */
    public static String decodeASCII(String str) {

        char aChar;
        int len = str.length();
        StringBuffer outBuffer = new StringBuffer(len);

        for (int x = 0; x < len;) {
            aChar = str.charAt(x++);
            if (aChar == '\\') {
                aChar = str.charAt(x++);
                if (aChar == 'u') {
                    int value = 0;
                    int maxLength = 4;
                    for (int i = 0; i < maxLength; i++) {
                        if (x >= str.length()) {
                            break;
                        }
                        aChar = str.charAt(x++);
                        switch (aChar) {
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
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a' :
                            case 'b' :
                            case 'c' :
                            case 'd' :
                            case 'e' :
                            case 'f' :
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A' :
                            case 'B' :
                            case 'C' :
                            case 'D' :
                            case 'E' :
                            case 'F' :
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default :
                                String chr = Character.toString((char) value);
                                String err = messages.format("stringutilities.malformedunicodecharacter", chr);
                                throw new IllegalArgumentException(err);
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    switch (aChar) {
                        case 't' :
                            aChar = '\t';
                            break;
                        case 'r' :
                            aChar = '\r';
                            break;
                        case 'n' :
                            aChar = '\n';
                            break;
                        case 'f' :
                            aChar = '\f';
                            break;
                        default :
                            break;
                    }
                    outBuffer.append(aChar);
                }
            } else {
                outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();
    }

    /**
     * Helper method to count number of given characters in a string.
     * <p>
     * This will return number of character instances in the given string. if the given string is null then a -1 will
     * returned.
     * <p>
     * For example if the given string is "XXNNXXnxXnXN" and the char is 'n' it will return a value of two as this
     * method is case sensitive.
     * <p>
     * Another example is if given the string 'X\nXxxNnN' with count escaped set to false will return 1 otherwise it
     * will return 2.
     * 
     * @param str to check characters for.
     * @param c character to count
     * @param countEscaped <tt>true</tt> will included escaped characters as part of the count`
     * @return number of occurrences of the character C in the given string.
     */
    public static int charCount(String str, char c, boolean countEscaped) {

        if (str == null)
            return -1;

        int cnt = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == c) {
                if (i >= 1) {
                    if (str.charAt(i - 1) == '\\') {
                        if (countEscaped) {
                            cnt++;
                        } else {
                            continue;
                        }
                    } else {
                        cnt++;
                    }
                }
            }
        }
        return cnt;
    }

    /**
     * Gets an SQL Type field string based on the given object.
     * <p>
     * Basically this method gets the field name that can be easily reflected back into the java.sql.Types object to get
     * the integer SQL type that should be used.
     * 
     * @see Types
     * @see #getTypeforValue(int)
     * @param o to test.
     * @return String that is a field name in the Types object.
     */
    public static String getTypeStringForObject(Object o) {

        if (o instanceof String)
            return "VARCHAR";
        if (o instanceof Byte)
            return "TINYINT";
        if (o instanceof Boolean)
            return "BOOLEAN";
        if (o instanceof Short)
            return "SMALLINT";
        if (o instanceof Integer)
            return "INTEGER";
        if (o instanceof Long || o instanceof BigInteger)
            return "BIGINT";
        if (o instanceof Double || o instanceof Float)
            return "DOUBLE";
        if (o instanceof BigDecimal)
            return "DECIMAL";
        if (o instanceof Timestamp)
            return "TIMESTAMP";
        if (o instanceof Time)
            return "TIME";
        if (o instanceof Ref)
            return "REF";
        if (o instanceof Date)
            return "DATE";
        if (o instanceof Clob)
            return "CLOB";
        if (o instanceof Blob)
            return "BLOB";
        if (o instanceof byte[] || o instanceof InputStream)
            return "LONGVARBINARY";
        if (o instanceof Reader)
            return "LONGVARCHAR";
        return "JAVA_OBJECT";
    }

    /**
     * Gets the Field name corresponding to the given type value.
     * <p>
     * Simple reflection to find the field name that has the same value given. If the value cannot be found the text
     * 'OTHER' will be returned.
     * <p>
     * This method will also return null if an error occurs during reflection.
     * 
     * @param type that corresponds to an SQL type.
     * @return field name based on the value type.
     * @see Types
     */
    public static String getTypeforValue(int type) {

        try {
            Class c = Types.class;
            Field[] fields = c.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                String name = fields[i].getName();
                int value = fields[i].getInt(null);
                if (value == type) {
                    return name;
                }
            }
            return "OTHER";
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates HTML friendly format for Java Color.
     * <p>
     * creates a string format of a Color object by taking the RGB color values and converting them to the hexadecimal
     * equivalent, for example Color.BLACK will be '#000000'
     * <p>
     * To get the color back from the value returned here, use the decode method on Color.
     * 
     * @see Color#decode(java.lang.String)
     * @param c to encode into HTML friendly format.
     * @return Hexadecimal representation of the color.
     */
    public static String getHTMLColor(Color c) {

        String red = Integer.toHexString(c.getRed());
        String blu = Integer.toHexString(c.getBlue());
        String gre = Integer.toHexString(c.getGreen());

        red = (red.length() == 1 ? '0' + red : red);
        blu = (blu.length() == 1 ? '0' + blu : blu);
        gre = (gre.length() == 1 ? '0' + gre : gre);
        return '#' + red + gre + blu;
    }

    /**
     * Converts a long number representing milliseconds into human readable format.
     * <p>
     * This value is mainly for formating milli-seconds into something a bit more readable much like the
     * getHumanReadableTime() method.
     * <p>
     * Instead of seeing large incoherent number of milli-seconds you can see something like '1.016m'= 1 minute 1 second
     * or '1.05h' = 1 hour 3 minutes.
     * 
     * @param milliseconds to format into a string.
     * @return human readable format for large milli-seconds intervals.
     */
    public static String getHumanReadableTime(long milliseconds) {

        long sc = 1000;
        long mn = 60 * sc;
        long hr = 60 * mn;
        long dy = 24 * hr;

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(3);
        double relSize = 0.0d;
        long abstime = Math.abs(milliseconds);

        String id = "";

        if ((abstime / dy) >= 1) {
            relSize = (double) abstime / (double) dy;
            id = messages.format("stringutilities.dayshorthand");
        } else if ((abstime / hr) >= 1) {
            relSize = (double) abstime / (double) hr;
            id = messages.format("stringutilities.hourshorthand");
        } else if ((abstime / mn) >= 1) {
            relSize = (double) abstime / (double) mn;
            id = messages.format("stringutilities.minuteshorthand");
        } else if ((abstime / sc) >= 1) {
            relSize = (double) abstime / (double) sc;
            id = messages.format("stringutilities.secondshorthand");
        } else {
            relSize = abstime;
            id = messages.format("stringutilities.millisecondshorthand");
        }
        return nf.format((milliseconds < 0 ? -1 : 1) * relSize) + id;
    }

    /**
     * Converts a long number representing milliseconds into human readable format.
     * <p>
     * This value is mainly for formating milli-seconds into something a bit more readable much like the
     * getHumanReadableTime() method.
     * <p>
     * Instead of seeing large incoherent number of milli-seconds you can see something like '1.016m'= 1 minute 1 second
     * or '1.05h' = 1 hour 3 minutes.
     * 
     * @param milliseconds to format into a string.
     * @return human readable format for large milli-seconds intervals.
     */
    public static String getFullHumanReadableTime(long milliseconds) {

        long sc = 1000;
        long mn = 60 * sc;
        long hr = 60 * mn;
        long dy = 24 * hr;

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(0);
        StringBuffer buff = new StringBuffer("");

        double relSize = 0.0d;
        boolean showseconds = true;
        boolean showmillis = true;

        long abstime = Math.abs(milliseconds);
        String id = null;

        while (abstime >= 1) {
            buff.append(" ");
            if ((abstime / dy) >= 1) {
                showmillis = false;
                relSize = (double) abstime / (double) dy;
                abstime -= dy * (long) relSize;
                showseconds = false;
                id = messages.format("stringutilities.days");
            } else if ((abstime / hr) >= 1) {
                showmillis = false;
                relSize = (double) abstime / (double) hr;
                id = messages.format("stringutilities.hours");
                abstime -= hr * (long) relSize;
            } else if ((abstime / mn) >= 1) {
                showmillis = false;
                relSize = (double) abstime / (double) mn;
                id = messages.format("stringutilities.minutes");
                abstime -= mn * (long) relSize;
            } else if ((abstime / sc) >= 1) {
                showmillis = false;
                relSize = (double) abstime / (double) sc;
                id = messages.format("stringutilities.seconds");
                abstime -= sc * (long) relSize;
                if (!showseconds) {
                    continue;
                }
            } else {
                if (showmillis) {
                    relSize = abstime;
                    id = messages.format("stringutilities.milliseconds");
                }
                abstime -= abstime;
                if (!showmillis) {
                    continue;
                }
            }

            Object[] p = {nf.format(Math.floor(relSize)), id};
            buff.append(MessageFormat.format("{0} {1}", p));
        }
        return buff.toString().trim();
    }

    /**
     * Converts a long number representing bytes into human readable format.
     * <p>
     * This value is mainly for formating values like a file size, memory size and so forth, so instead of seeing a
     * large incoherent number you can see something like '308.123KB' or '9.68MB'
     * 
     * @param bytes to format into a string.
     * @return human readable format for larger byte counts.
     */
    public static String getHumanReadableSize(long bytes) {

        long mb = (long) Math.pow(2, 20);
        long kb = (long) Math.pow(2, 10);
        long gb = (long) Math.pow(2, 30);

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(3);
        double relSize = 0.0d;
        long abytes = Math.abs(bytes);
        String id = "";

        if ((abytes / gb) >= 1) {
            relSize = (double) abytes / (double) gb;
            id = messages.format("stringutilities.gigabyte_abbreviation");
        } else if ((abytes / mb) >= 1) {
            relSize = (double) abytes / (double) mb;
            id = messages.format("stringutilities.megabyte_abbreviation");
        } else if ((abytes / kb) >= 1) {
            relSize = (double) abytes / (double) kb;
            id = messages.format("stringutilities.kilobyte_abbreviation");
        } else {
            relSize = abytes;
            id = messages.format("stringutilities.byte_abbreviation");
        }
        return nf.format((bytes < 0 ? -1 : 1) * relSize) + id;
    }

    /**
     * Removes a substring from a given string.
     * <p>
     * If source equals something like 'hello world' and the sub equals 'ell' the returned value will be 'ho world'.
     * Also it should be noted that this method is a case-insensitive operation, for example 'Hello World' with sub
     * equal to 'LO' would produce 'Hel World'.
     * 
     * @param source string to remove sub string from.
     * @param sub to remove from source value.
     * @return new string with sub removed from the source string.
     */
    public static String removeSubString(String source, String sub) {

        StringBuffer buff = new StringBuffer(source);
        String upcase = source.toUpperCase();

        int idx = upcase.indexOf(sub.toUpperCase());
        int end = (idx + sub.length());

        if (idx >= 0 && end <= source.length()) {
            buff.delete(idx, end);
        }
        return buff.toString();
    }

    /**
     * Utility method for wrapping text to a specific width.
     * <p>
     * This method allows you to pragmatically wrap long sections of text to a specified length. This can be help ful if
     * the string is too long to show on one line to the user.
     * <p>
     * This also allows for HTML style breaks or '\n' style line breaks.
     * 
     * @param length maximum line length to wrap lines to.
     * @param text to to format and wrap.
     * @param newLine this is text insert with each new line created i.e. '\t'.
     * @param htmlBreak use HTML style &lt;br/&gt; or '\n' line breaks.
     * @return formatted and wrapped text.
     */
    public static String formatBreak(int length, String text, String newLine, boolean htmlBreak) {

        if (text == null) {
            return "";
        }

        StringBuffer buff = new StringBuffer("");
        StringTokenizer st = new StringTokenizer(text, "\t\r\n ", false);
        int nicebreak = length;
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if (nicebreak < buff.length() + token.length() + 2) {
                buff.append((htmlBreak ? "<br>" : System.getProperty("line.seperator", "\n")));
                if (newLine != null) {
                    buff.append(newLine);
                }
                nicebreak = buff.length() + length;
            }
            buff.append(token);
            buff.append(" ");
        }
        return buff.toString();
    }

    /**
     * Standard base64 encoding mechanisim.
     * <p>
     * This uses the internal sun base64 encoder.
     * 
     * @param text to encode using base64.
     * @return encoded text using base64.
     */
    public static String encodeBase64(String text) {

        byte[] utf8 = encodeASCII(text).getBytes();
        return new sun.misc.BASE64Encoder().encode(utf8);
    }

    /**
     * Standard base64 decoding mechanisim.
     * <p>
     * This uses the internal sun base64 decoder.
     * 
     * @param text to decode using base64.
     * @return original text before base64 encoding.
     */
    public static String decodeBase64(String text) {

        byte[] dec;
        try {
            dec = new sun.misc.BASE64Decoder().decodeBuffer(text);
        } catch (IOException e) {
            return null;
        }
        try {
            return decodeASCII(new String(dec));
        } catch (Exception error) {
            // TODO log decode problem//
            return new String(dec);
        }
    }

    /**
     * Forces the single character to be stripped from both ends of the string.
     * <p>
     * This method is mainly useful for stripping off enclosing quotation marks and the like. this method will removes
     * the first and last character of text, and return the result.
     * <p>
     * if the text length is less than 2 it will simply return a blank string.
     * 
     * @param text to strip first and last character from.
     * @return stripped form of the text parameter.
     */
    public static String forceTrim(String text) {

        if (text.length() > 2) {
            return text.substring(1, text.length() - 1);
        }
        return "";
    }

    /**
     * Method for removing a set of characters from a source string.
     * <p>
     * Strips characters from the given set string from the source string s. for example the String 'XXJavaXxRuntime'
     * with the set parameter being 'Xa' will results in the following string 'JvxRuntime'
     * <p>
     * This method is very useful for removing various different characters at once from a string.
     * 
     * @param s String to strip characters from.
     * @param set collection of characters to strip.
     * @return new string that is s that contains no characters from set.
     */
    public static String stripCharacters(String s, String set) {

        if (s == null) {
            return "null";
        }

        StringBuffer outBuffer = new StringBuffer("");
        for (int x = 0; x < s.length(); x++) {
            char aChar = s.charAt(x);
            if (set.indexOf(aChar) < 0) {
                outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();
    }

    /**
     * Creates a comma seperated list of strings from a collection.
     * <p>
     * 
     * @param list of strings or objects to created csv string from.
     * @return a comma seperated list of values from the list.
     * @see #explode(Collection)
     */
    public static String explode(Object[] list) {

        return explode(Arrays.asList(list));
    }

    /**
     * Creates a comma seperated list of strings from a collection.
     * <p>
     * For every object in the list, this method relys on the toString() of Object to apped to, so it is allowable to
     * send a collection containing non-strings.
     * 
     * @param list of strings or objects to created csv string from.
     * @return a comma seperated list of values from the list.
     */
    public static String explode(Collection list) {

        return explode(list, ",");
    }

    /**
     * Creates a comma seperated list of strings from a collection.
     * <p>
     * For every object in the list, this method relys on the toString() of Object to apped to, so it is allowable to
     * send a collection containing non-strings.
     * 
     * @param list of strings or objects to created csv string from.
     * @param delimiter delimiter to explode the list by, if null a comma (,) is used.
     * @return a comma seperated list of values from the list.
     */
    public static String explode(Collection list, String delimiter) {

        StringBuffer buffer = new StringBuffer("");
        Iterator itr = list.iterator();

        while (itr.hasNext()) {
            buffer.append(itr.next());
            if (itr.hasNext()) {
                buffer.append(delimiter == null ? "," : delimiter);
            }

        }
        return buffer.toString();
    }

    /**
     * Collapses a comma delimted text to a collection of strings.
     * <p>
     * This method is the opposite of the explode methods in this class.
     * 
     * @see #explode(Collection)
     * @param list comma seperated list of values.
     * @param delimiter to used to distinguish the elements in the string list, for example ','.
     * @return collection of string from tokens in the list.
     */
    public static Collection<String> collapseToCollection(String list, String delimiter) {

        StringTokenizer st = new StringTokenizer(list, delimiter, false);
        Collection<String> collection = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            collection.add(st.nextToken());
        }
        return collection;
    }

    /**
     * Collapses a comma delimted text to an array of strings.
     * <p>
     * This method is the opposite of the explode methods in this class.
     * 
     * @see #explode(Collection)
     * @param list comma seperated list of values.
     * @param delimiter to used to distinguish the elements in the string list, for example ','.
     * @return collection of string from tokens in the list.
     */
    public static String[] collapseToArray(String list, String delimiter) {

        Collection<String> c = collapseToCollection(list, delimiter);
        return c.toArray(new String[c.size()]);
    }

    /**
     * Appends whitespace to a string to a sepecified length.
     * <p>
     * if leftFill("Bork",8) = 'Bork\ \ \ \ '
     * <p>
     * <em>spaces are escaped for clarity.</em>
     * 
     * @param original text to append data to.
     * @param maxLength maximum size the length the text grows to.
     * @return string right filled with white space to maxLength.
     * @see #leftFill(String, int)
     */
    public static String rightFill(String original, int maxLength) {

        StringBuffer buffer = new StringBuffer(original);
        while (buffer.length() < maxLength) {
            buffer.append(' ');
        }
        return buffer.toString();
    }

    /**
     * Prepends whitespace to a string to a sepecified length.
     * <p>
     * if leftFill("Bork",8) = '\ \ \ \ Bork'
     * <p>
     * <em>spaces are escaped for clarity.</em>
     * 
     * @param original text to prepend data to.
     * @param maxLength maximum size the length the text grows to.
     * @return string left filled with white space to maxLength.
     * @see #rightFill(String, int)
     */
    public static String leftFill(String original, int maxLength) {

        StringBuffer buffer = new StringBuffer(original);
        while (buffer.length() < maxLength) {
            buffer.insert(0, ' ');
        }
        return buffer.toString();
    }

    /**
     * Encodes MD5 Hashes into a conformed statisically unique identifier.
     * <p>
     * 
     * @param binaryData MD5 hash from the message digest.
     * @return encoded hash value.
     * @see java.security.MessageDigest#digest()
     */
    public static String encodeMD5Data(byte[] binaryData) {

        if (binaryData.length != 16) {
            return null;
        }

        char[] buffer = new char[32];
        for (int i = 0; i < 16; i++) {
            int low = (binaryData[i] & 0x0f);
            int high = ((binaryData[i] & 0xf0) >> 4);
            buffer[i * 2] = hexDigitLow[high];
            buffer[i * 2 + 1] = hexDigitLow[low];
        }
        return new String(buffer);
    }

    /**
     * Compares to strings together to see if they are equal.
     * <p>
     * What seperates this method from a standard equals is that both strings can be null and they are still compared
     * safely.
     * 
     * @param s1 first string to compare.
     * @param s2 second string to compare.
     * @return <tt>true</tt> if both of the given strings are equal.
     */
    public static boolean compare(String s1, String s2) {

        if (s1 == null && s2 == null) {
            return true;
        }

        String r1 = s1 == null ? "" : s1;
        String r2 = s2 == null ? "" : s2;
        return r1.compareTo(r2) == 0;
    }

    /**
     * Gets a normalized class name without a package namespace.
     * <p>
     * This will strip of the package name of a fully qualified class name that usually comes from
     * <tt>class.getName()</tt>
     * 
     * @param fqcn fully qualified class name to normalize.
     * @return class name only.
     */
    public static String getNormalizedClassName(String fqcn) {

        int idx = fqcn.lastIndexOf('.');
        if (idx >= 0) {
            return fqcn.substring(idx + 1);
        }
        return fqcn;
    }

    /**
     * Somewhat agressive number conversion method.
     * <p>
     * This will attempt to parse normal number, local currency, and or a percentage.
     * 
     * @param possibleNumber to change to a number.
     * @return <tt>null</tt> if the string cannot be interpreted as a number.
     */
    public static Number toNumber(String possibleNumber) {

        NumberFormat nf = NumberFormat.getNumberInstance();
        try {
            return nf.parse(possibleNumber);
        } catch (ParseException e) {
            // not a normal number....
        }

        nf = NumberFormat.getCurrencyInstance();
        try {
            return nf.parse(possibleNumber);
        } catch (ParseException e) {
            // not a currency ...
        }

        nf = NumberFormat.getPercentInstance();
        try {
            return nf.parse(possibleNumber);
        } catch (ParseException e) {
            // not a percentage
        }
        return null;
    }

    /**
     * Encrypts simple text using a key and specified transformation.
     * <p>
     * This will get an instance of the {@link Cipher} object using the specified transformation. With the given Cipher
     * the bytes will be transformed into a new string through the Cipher.doFinal() method.
     * <p>
     * If null or a blank string is given for text, the value is immediately returned as is.
     * 
     * @param text that needs to be encrypted.
     * @param key to lock the text with.
     * @param transformation the name of the transformation, e.g., DES/CBC/PKCS5Padding.
     * @return encrypted string of the original text parameter.
     * @throws GeneralSecurityException if an error occurs during encryption.
     * @see Cipher#getInstance(java.lang.String)
     */
    public static String encryptText(String text, Key key, String transformation) throws GeneralSecurityException {

        if (text == null || text.trim().length() == 0) {
            return text;
        }

        byte[] utf8 = null;
        try {
            utf8 = text.getBytes("UTF8");
        } catch (UnsupportedEncodingException e) {
            // shouldn't happen since UTF8 is internal to the JVM.
        }

        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] enc = cipher.doFinal(utf8);
        return new sun.misc.BASE64Encoder().encode(enc);
    }

    /**
     * Decrypts simple text using a key and specificed transformation.
     * <p>
     * This will get an instance of the {@link Cipher}object using the specified transformation. With the given Cipher
     * the bytes will be transformed into a new string through the Cipher.doFinal() method.
     * <p>
     * If null or a blank string is given for text, the value is immediately returned as is.
     * 
     * @param text that needs to be decrypted.
     * @param key to unlock the text with.
     * @param transformation the name of the transformation, e.g., DES/CBC/PKCS5Padding.
     * @return decrypted string of the original text parameter.
     * @throws GeneralSecurityException if an error occurs during decryption.
     * @see Cipher#getInstance(java.lang.String)
     */
    public static String decryptText(String text, Key key, String transformation) throws GeneralSecurityException {

        if (text == null || text.trim().length() == 0) {
            return text;
        }

        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] dec;
        try {
            dec = new sun.misc.BASE64Decoder().decodeBuffer(text);
        } catch (IOException e) {
            return null;
        }
        byte[] utf8 = cipher.doFinal(dec);
        try {
            return new String(utf8, "UTF8");
        } catch (UnsupportedEncodingException e) {
            // shouldn't happen since UTF8 is internal to the JVM.
            return null;
        }
    }

    /**
     * Parses a font string to a Font object.
     * <p>
     * Complimentary method for re-creating the font objects formatted to a string by the getFontString(Font) of this
     * object.
     * <p>
     * for information on the format of the font string see getFontString(Font)
     * 
     * @param s to parse for the font.
     * @return new Font object represented by the given string.
     */
    public static Font parseFontString(String s) {

        try {
            String name = s.substring(0, s.indexOf(","));
            int size = Integer.parseInt(s.substring(s.indexOf(",") + 1));
            return new Font(name, Font.PLAIN, size);
        } catch (Throwable t) {
            return null;
        }
    }

    private static MessageDigest allocateDigestInstance(String algorithim) {

        try {
            return MessageDigest.getInstance(algorithim);
        } catch (NoSuchAlgorithmException nsa) {
            throw new RuntimeException(nsa);
        }

    }

    private static char toHex(int nibble) {

        return hexDigit[(nibble & 0xF)];

    }

    /** A table of hex digits */

    private static final char[] hexDigitLow = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',

    'e', 'f'};

    /** A table of hex digits */

    private static final char[] hexDigit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',

    'F'};

}
