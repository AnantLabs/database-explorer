package com.gs.dbex.application.text;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.*;
import java.awt.*;

public class AutoTextComplete extends JWindow implements KeyListener,
		FocusListener, ActionListener {
	JTextComponent parent = null;
	JList lst = null;
	java.util.TreeSet<String> val = new java.util.TreeSet<String>();
	java.util.Vector<String> tempVector = new java.util.Vector<String>(1, 1);
	// declarations for table
	JTable tableParent = null;
	int activeColumn = 0;
	StringBuffer typed = new StringBuffer();

	public AutoTextComplete() {
		lst = new JList();
		lst.addKeyListener(this);
		lst.addFocusListener(this);
		this.getContentPane().add(new JScrollPane(lst), BorderLayout.CENTER);
		setButtons();
		((JPanel) this.getContentPane()).setBorder(BorderFactory
				.createLineBorder(Color.GRAY, 2));
		lst.setToolTipText("Press UP/DOWN arrow to navigate, ENTER to select.");
	}

	public AutoTextComplete(JComponent jc) {
		this();
		if (jc instanceof JTable)
			tableParent = (JTable) jc;
		else
			parent = (JTextComponent) jc;
		jc.addFocusListener(this);
		jc.addKeyListener(this);
	}

	public AutoTextComplete(JTable t, int col) {
		this(t);
		activeColumn = col;
	}

	public void setActiveColumn(int col) {
		activeColumn = col;
	}

	private void setButtons() {
		JButton b[] = { new JButton("Up"), new JButton("Down"),
				new JButton("Select") };
		char c[] = { 'U', 'D', 'S' };
		String txt[] = { "Move Up (UP Arrow)", "Move Down (DOWN Arrow)", "Select (ENTER)" };
		JPanel p = new JPanel(new FlowLayout());
		for (int i = 0; i < b.length; i++) {
			b[i].addActionListener(this);
			b[i].setMnemonic(c[i]);
			b[i].setToolTipText(txt[i]);
			p.add(b[i]);
		}
		this.getContentPane().add(p, BorderLayout.SOUTH);
	}

	public void addItems(String it[]) {
		val.addAll(java.util.Arrays.asList(it));
	}

	public void addItems(java.util.List<String> init) {
		val.addAll(init);
	}

	public void setItems(String it[]) {
		val.clear();
		val.addAll(java.util.Arrays.asList(it));
	}

	public void setItems(java.util.List<String> init) {
		val.clear();
		val.addAll(init);
	}

	private void moveUp() {
		if (!this.isVisible())
			return;
		int index = lst.getSelectedIndex();
		lst.setSelectedIndex(index = index > 0 ? index - 1
				: tempVector.size() - 1);
		lst.validate();
		lst.scrollRectToVisible(lst.getCellBounds(index, index - 1 < 0 ? 0
				: index - 1));
	}

	private void moveDown() {
		if (!this.isVisible())
			return;
		int index = lst.getSelectedIndex();
		lst.setSelectedIndex(index = index < tempVector.size() - 1 ? index + 1
				: 0);
		lst.validate();
		if (index == 0)
			lst.scrollRectToVisible(lst.getCellBounds(0, 1));
		else
			lst.scrollRectToVisible(lst.getCellBounds(index + 1 < tempVector
					.size() - 1 ? index + 1 : index, index + 1 < tempVector
					.size() - 1 ? index + 1 : index));
	}

	private void select(boolean enterPressed) {
		if (!this.isVisible())
			return;
		if (parent == null && tableParent != null) {
			tableSelection();
			return;
		}
		if (parent instanceof JTextArea) {
			String txt = parent.getText();
			int i = 0, n = parent.getCaretPosition() - 1, count = 0;
			char c;
			for (i = n; i >= 0; i--) {
				if (!(((c = txt.charAt(i)) >= 'a' && c <= 'z') || (c >= '0' && c <= '9'))
						&& count != 0)
					break;
				else
					count++;
			}
			if (count > 0 && this.isVisible()) {
				parent.setSelectionStart(i + 1);
				parent.setSelectionEnd(n + 1);
				if (i < n && !lst.isSelectionEmpty())
					parent.replaceSelection((String) lst.getSelectedValue());
			}
		} else
			parent.setText((String) lst.getSelectedValue());
		this.setVisible(false);
	}

	private void tableSelection() {
		int r = tableParent.getSelectedRow(), c = tableParent
				.getSelectedColumn();
		try {
			tableParent.getCellEditor(r, c).stopCellEditing();
		} catch (Exception ex) {
		}
		lst.validate();
		tableParent.setValueAt(lst.getSelectedValue(), r, c);
		typed.setLength(0);
		this.setVisible(false);
		tableParent.repaint();
		typed.setLength(0);
	}

	public void actionPerformed(ActionEvent ae) {
		String com = ae.getActionCommand().toLowerCase();
		if (com.equals("up"))
			moveUp();
		else if (com.equals("down"))
			moveDown();
		else if (com.equals("select"))
			select(false);
	}

	public void focusLost(FocusEvent fe) {
		this.setVisible(false);
		typed.setLength(0);
	}

	public void focusGained(FocusEvent fe) {
		if (fe.getSource() == lst)
			parent.requestFocus();
		else if (fe.getSource() == parent
				|| (fe.getSource() == tableParent && tableParent
						.getSelectedColumn() == activeColumn))
			populateList();
	}

	public void keyPressed(KeyEvent ke) {
		int kc = ke.getKeyCode();
		// if ESCAPE is pressed... remove the popup
		if(KeyEvent.VK_ESCAPE == kc){
			this.setVisible(false);
			typed.setLength(0);
			return;
		}
		int index = lst.getSelectedIndex();
		if (kc == KeyEvent.VK_UP
				|| (parent instanceof JTextField && kc == KeyEvent.VK_F5))
			moveUp();
		else if (kc == KeyEvent.VK_DOWN
				|| (parent instanceof JTextField && kc == KeyEvent.VK_F6))
			moveDown();
	}

	public void keyReleased(KeyEvent ke) {
		if (tableParent != null) {
			int r = tableParent.getSelectedRow(), c = tableParent
					.getSelectedColumn();
			this.setVisible(c == activeColumn);
			if (r < 0 || c < 0 || c != activeColumn)
				return;
		}
		int kc = ke.getKeyCode();
		// if ESCAPE is pressed... remove the popup
		if(KeyEvent.VK_ESCAPE == kc){
			this.setVisible(false);
			typed.setLength(0);
			return;
		}
		if (kc == KeyEvent.VK_ENTER
				|| (parent instanceof JTextField && kc == KeyEvent.VK_F7))
			select(true);
		else if (parent != null
				|| tableParent.getSelectedColumn() == activeColumn)
			populateList();
		else {
			this.setVisible(false);
			typed.setLength(0);
		}
	}

	public void keyTyped(KeyEvent ke) {
		if(KeyEvent.VK_ESCAPE == ke.getKeyCode()){
			this.setVisible(false);
			typed.setLength(0);
			return;
		}
		char c = ke.getKeyChar();
		if (c != '\r' && c != '\n' && c != '\t' && c != '\b')
			typed.append(ke.getKeyChar());
		else
			typed.setLength(0);
		if (c == '\b')
			typed.setLength(typed.length() > 0 ? typed.length() - 1 : 0);
	}

	private void populateList() {
		String txt = "";
		int r = 0, c = 0;
		if (tableParent != null) {
			r = tableParent.getSelectedRow();
			c = tableParent.getSelectedColumn();
			if (r < 0 || c < 0 || c != activeColumn)
				return;
			txt = tableParent.getValueAt(r, c) != null ? (String) tableParent
					.getValueAt(r, c) : "";
			txt += typed.toString();
			if (txt != null)
				txt = txt.toLowerCase();
			else
				txt = "";
		} else if (parent instanceof JTextArea) {
			txt = parent.getText().toLowerCase();
			int i = 0, n = parent.getCaretPosition() - 1, count = 0;
			char ch;
			for (i = n; i >= 0; i--) {
				if (!(((ch = txt.charAt(i)) >= 'a' && ch <= 'z') || (ch >= '0' && ch <= '9'))
						&& count != 0)
					break;
				else
					count++;
			}
			txt = txt.substring(i + 1, n + 1);
		} else
			txt = parent.getText().toLowerCase();
		tempVector.clear();
		int index = lst.getSelectedIndex();
		for (java.util.Iterator<String> i = val.iterator(); i.hasNext();) {
			String s = i.next();
			if (s != null && !s.equals("") && s.toLowerCase().startsWith(txt))
				tempVector.add(s);
		}
		lst.setListData(tempVector);
		lst.validate();
		if (tempVector.size() == 0) {
			this.setVisible(false);
			return;
		}
		this.pack();
		Point p = new Point(0, 0);
		if (parent != null) {
			try {
				p = parent.modelToView(parent.getCaretPosition()).getLocation();
			} catch (Exception ex) {
			}
			Point loc = parent.getLocationOnScreen();
			p = new Point(p.x + loc.x, p.y + loc.y);
		} else
			p = tableParent.getLocationOnScreen();

		if (tableParent != null) {
			Rectangle rect = tableParent.getCellRect(r, c, true);
			p.x += rect.x;
			p.y += rect.y;
		}
		Rectangle bound = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getMaximumWindowBounds();
		if (p.y - this.getHeight() > 0)
			this.setLocation(p.x, p.y - this.getHeight());
		else if (p.y + this.getHeight() > bound.y + bound.height)
			this.setLocation(p.x, bound.y + bound.height - this.getHeight());
		else
			this.setLocation(p.x, p.y);

		if (!this.isVisible())
			this.setVisible(true);
		lst.setSelectedIndex(index >= tempVector.size() || index < 0 ? 0
				: index);
	}
}