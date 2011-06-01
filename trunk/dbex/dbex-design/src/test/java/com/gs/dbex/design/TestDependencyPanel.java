/**
 * 
 */
package com.gs.dbex.design;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import com.gs.dbex.design.graph.DependencyGraphCanvas;
import com.gs.dbex.design.model.TableDbShape;
import com.gs.dbex.model.db.Column;
import com.gs.dbex.model.db.Table;

/**
 * @author Sabuj Das
 *
 */
public class TestDependencyPanel extends JFrame {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestDependencyPanel frame = new TestDependencyPanel();
		frame.setVisible(true);
	}

	public TestDependencyPanel() {
		setSize(640, 480);
		setTitle("TestDependencyPanel");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		draw();
	}

	private Table populateData() {
		Table t = new Table();
		t.setModelName("TABLE");
		for(int i=0; i<5; i++){
			Column c = new Column(t);
			c.setModelName("COLUMN_" + (i+1));
			t.getColumnlist().add(c);
			t.getExportedKeys();
		}
		
		return t;
	}
	
	private Table getDemoTable(){
		Table t = new Table();
		t.setModelName("TABLE");
		for(int i=0; i<5; i++){
			Column c = new Column(t);
			c.setModelName("COLUMN_" + (i+1));
			t.getColumnlist().add(c);
		}
		
		return t;
	}
	
	public void draw(){
		Table t = populateData();
		getContentPane().setLayout(new BorderLayout());
		JPanel panel = new JPanel();


		panel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createCompoundBorder(BorderFactory
						.createLineBorder(new Color(0, 0, 0)), BorderFactory
						.createMatteBorder(1, 25, 1, 25, new Color(207, 218,
								231))), new LineBorder(
						new Color(153, 153, 255), 1, true)));
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.WHITE);
		TableDbShape s = new TableDbShape(t);
		DependencyGraphCanvas<TableDbShape> canvas = new DependencyGraphCanvas<TableDbShape>(panel,s);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(canvas);
		panel.add(scrollPane, BorderLayout.CENTER);
		
		getContentPane().add(panel, BorderLayout.CENTER);
	}
}
