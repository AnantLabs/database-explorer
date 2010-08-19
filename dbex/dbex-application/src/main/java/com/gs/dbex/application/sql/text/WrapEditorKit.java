/**
 * 
 */
package com.gs.dbex.application.sql.text;

import javax.swing.text.*;

/**
 * @author sabuj.das
 *
 */
public class WrapEditorKit extends StyledEditorKit {
	public static final String LINE_BREAK_ATTRIBUTE_NAME = "line_break_attribute";
	
    ViewFactory defaultFactory=new WrapColumnFactory();
    
    public ViewFactory getViewFactory() {
        return defaultFactory;
    }

    public MutableAttributeSet getInputAttributes() {
        MutableAttributeSet mAttrs = super.getInputAttributes();
        mAttrs.removeAttribute(LINE_BREAK_ATTRIBUTE_NAME);
        return mAttrs;
    }
}