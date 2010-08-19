/**
 * 
 */
package com.gs.dbex.application.sql.text;

/**
 * @author sabuj.das
 *
 */
import javax.swing.text.*;

public class NoWrapParagraphView extends ParagraphView {
    public NoWrapParagraphView(Element elem) {
        super(elem);
    }

    public void layout(int width, int height) {
        super.layout(Short.MAX_VALUE, height);
    }

    public float getMinimumSpan(int axis) {
        return super.getPreferredSpan(axis);
    }
}