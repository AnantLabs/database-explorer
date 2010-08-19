
package com.gs.dbex.application.sql;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.IOException;
import java.io.Reader;

import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Segment;
import javax.swing.text.Style;
import javax.swing.text.StyledDocument;

import com.gs.dbex.application.sql.processor.SqlProcessor;
import com.gs.dbex.application.sql.processor.Token;
import com.gs.dbex.application.sql.processor.TokenType;
import com.gs.dbex.application.sql.text.WrapEditorKit;



/**
 * Display text with syntax highlighting. Highlighting is done with full accuracy, using a given language scanner. Large
 * amounts of re-highlighting are done in small bursts to make sure the user interface doesn't freeze.
 */
public class SyntaxHighlighter extends JTextPane implements DocumentListener {

    private static final long serialVersionUID = -5305919966146189023L;
    private StyledDocument document;
    private SqlProcessor scanner;
    private SyntaxStylizer stylelizer;
    private Segment text = new Segment();
    private int firstRehighlightToken;
    private int smallAmount = 100;
    private int height, width;

    /**
     * Create a graphics component which displays text with syntax highlighting. Provide a width and height, in
     * characters, and a language scanner.
     */
    public SyntaxHighlighter(int height, int width, SqlProcessor scanner) {

        super(new DefaultStyledDocument());
        setEditorKit(new WrapEditorKit());
        document = (StyledDocument) getDocument();
        this.height = height;
        this.width = width;
        this.scanner = scanner;
        this.stylelizer = new SyntaxStylizer(this);
        document.addDocumentListener(this);
    }

    @Override
    public void setFont(Font font) {

        int borderOfJTextPane = 3;
        super.setFont(font);
        FontMetrics metrics = getFontMetrics(font);
        int paneWidth = width * metrics.charWidth('W') + 2 * borderOfJTextPane;
        int paneHeight = height * metrics.getHeight() + 2 * borderOfJTextPane;
        Dimension size = new Dimension(paneWidth, paneHeight);
        setMinimumSize(size);
        setPreferredSize(size);
        invalidate();
    }

    @Override
    public void read(Reader in, Object desc) throws IOException {

        int oldLength = getDocument().getLength();
        document.removeDocumentListener(this);
        super.read(in, desc);
        document = (StyledDocument) getDocument();
        document.addDocumentListener(this);
        int newLength = getDocument().getLength();
        firstRehighlightToken = scanner.change(0, oldLength, newLength);
        repaint();
    }

    public void insertUpdate(DocumentEvent e) {

        int offset = e.getOffset();
        int length = e.getLength();
        firstRehighlightToken = scanner.change(offset, 0, length);
        repaint();
    }

    public void removeUpdate(DocumentEvent e) {

        int offset = e.getOffset();
        int length = e.getLength();
        firstRehighlightToken = scanner.change(offset, length, 0);
        repaint();
    }

    public void changedUpdate(DocumentEvent e) {

    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);
        int offset = scanner.position();
        if (offset < 0)
            return;

        int tokensToRedo = 0;
        int amount = smallAmount;
        while (tokensToRedo == 0 && offset >= 0) {
            int length = document.getLength() - offset;
            if (length > amount)
                length = amount;
            try {
                document.getText(offset, length, text);
            } catch (BadLocationException e) {
                return;
            }
            tokensToRedo = scanner.scan(text.array, text.offset, text.count);
            offset = scanner.position();
            amount = 2 * amount;
        }
        for (int i = 0; i < tokensToRedo; i++) {
            Token t = scanner.getToken(firstRehighlightToken + i);
            int length = t.symbol.name.length();
            int type = t.symbol.type.ordinal();
            if (type < 0) {
                type = TokenType.UNRECOGNIZED.ordinal();
            }
            Style style = stylelizer.styleForWord(t.symbol.type);
            if (style == null) {
                style = stylelizer.styleForWord(TokenType.WHITESPACE);
            }
            document.setCharacterAttributes(t.position, length, style, true);
        }
        firstRehighlightToken += tokensToRedo;
        if (offset >= 0) {
            repaint(2);
        }
    }


    
    
}
