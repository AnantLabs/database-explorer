/**
 * 
 */
package com.gs.dbex.application.tree;

/**
 * @author sabuj.das
 *
 */
public class CheckBoxTreeNode<T> {

	private String text;
	private T value;
	private String tooltip;
	private boolean selected;
	
	public CheckBoxTreeNode(String text, boolean selected) {
		this.text = text;
		this.selected = selected;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return the value
	 */
	public T getValue() {
		return value;
	}

	/**
	 * @return the tooltip
	 */
	public String getTooltip() {
		return tooltip;
	}

	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(T value) {
		this.value = value;
	}

	/**
	 * @param tooltip the tooltip to set
	 */
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public String toString() {
		return getClass().getName() + "[" + text + "/" + selected + "]";
	}
	
}
