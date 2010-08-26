/**
 * 
 */
package com.gs.dbex.design;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

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
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.WHITE);
		DependencyGraphCanvas<TableDbShape> canvas = new DependencyGraphCanvas<TableDbShape>();
		
		panel.add(canvas, BorderLayout.CENTER);
		
		getContentPane().add(panel, BorderLayout.CENTER);
	}
}
