/**
 * 
 */
package com.gs.dbex.design.graph;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.gs.dbex.design.model.BaseDbShape;
import com.gs.dbex.design.model.TableDbShape;
import com.gs.dbex.model.db.Column;
import com.gs.dbex.model.db.Table;

/**
 * @author Sabuj.das
 * 
 */
public class DependencyGraphCanvas<T extends BaseDbShape> extends Canvas implements MouseListener,
		MouseMotionListener, MouseWheelListener, KeyListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1175314071365190993L;
	
	
	private T dbShape;
	
	Rectangle rect = new Rectangle(0, 0, 100, 50);
	BufferedImage bi;
	Graphics2D big;

	// Holds the coordinates of the user's last mousePressed event.
	int last_x, last_y;
	boolean firstTime = true;
	TexturePaint fillPolka, strokePolka;
	Rectangle area;

	// True if the user pressed, dragged or released the mouse outside of the
	// rectangle; false otherwise.
	boolean pressOut = false;

	public DependencyGraphCanvas() {
		
		Graphics g = getGraphics();
		setBackground(Color.white);
		addMouseMotionListener(this);
		addMouseListener(this);

		/*// Creates the fill texture paint pattern.
		bi = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);
		big = bi.createGraphics();
		big.setColor(Color.pink);
		big.fillRect(0, 0, 7, 7);
		big.setColor(Color.cyan);
		big.fillOval(0, 0, 3, 3);
		Rectangle r = new Rectangle(0, 0, 5, 5);
		fillPolka = new TexturePaint(bi, r);
		big.dispose();

		// Creates the stroke texture paint pattern.
		bi = new BufferedImage(5, 5, BufferedImage.TYPE_INT_RGB);
		big = bi.createGraphics();
		big.setColor(Color.cyan);
		big.fillRect(0, 0, 7, 7);
		big.setColor(Color.pink);
		big.fillOval(0, 0, 3, 3);
		r = new Rectangle(0, 0, 5, 5);
		strokePolka = new TexturePaint(bi, r);
		big.dispose();*/

	}

	
	public T getDbShape() {
		return dbShape;
	}


	public void setDbShape(T dbShape) {
		this.dbShape = dbShape;
	}


	// Handles the event of the user pressing down the mouse button.
	public void mousePressed(MouseEvent e) {
		last_x = rect.x - e.getX();
		last_y = rect.y - e.getY();

		// Checks whether or not the cursor is inside of the rectangle while the
		// user is pressing the mouse.
		if (rect.contains(e.getX(), e.getY()))
			updateLocation(e);
		else {

			pressOut = true;
		}
	}

	// Handles the event of a user dragging the mouse while holding down the
	// mouse button.
	public void mouseDragged(MouseEvent e) {
		if (!pressOut)
			updateLocation(e);

	}

	// Handles the event of a user releasing the mouse button.
	public void mouseReleased(MouseEvent e) {

		// Checks whether or not the cursor is inside of the rectangle when the
		// user releases the mouse button.
		if (rect.contains(e.getX(), e.getY()))
			updateLocation(e);
		else {

			pressOut = false;
		}
	}

	// This method required by MouseListener.
	public void mouseMoved(MouseEvent e) {
	}

	// These methods are required by MouseMotionListener.
	public void mouseClicked(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	// Updates the coordinates representing the location of the current
	// rectangle.
	public void updateLocation(MouseEvent e) {
		rect.setLocation(last_x + e.getX(), last_y + e.getY());
		/*
		 * Updates the label to reflect the location of the current rectangle if
		 * checkRect returns true; otherwise, returns error message.
		 */
		if (checkRect()) {

		} else {

		}

		repaint();
	}
	private Table populateData() {
		Table t = new Table();
		t.setModelName("TABLE_001");
		for(int i=0; i<5; i++){
			Column c = new Column(t);
			if(i==0)
				c.setPrimaryKey(true);
			else
				c.setForeignKey(true);
			c.setModelName("COLUMN_" + (i+1));
			t.getColumnlist().add(c);
		}
		
		return t;
	}
	public void paint(Graphics g) {
		if (g instanceof Graphics2D) {
			Graphics2D g2d = (Graphics2D) g;

			// for antialising geometric shapes
			g2d.addRenderingHints(new RenderingHints(
					RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON));
			// for antialiasing text
			g2d.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			// to go for quality over speed
		    g2d.getRenderingHints().put( RenderingHints.KEY_RENDERING,
		            RenderingHints.VALUE_RENDER_QUALITY );

			g = g2d;
		}
		super.paint(g);

		try {
			Font tf = Font.createFont(Font.TRUETYPE_FONT, 
					getClass().getResourceAsStream("/fonts/TAHOMA.TTF"));
			Font tahomaFont = new Font(tf.getFontName(),
					java.awt.Font.PLAIN, 12);
			g.setFont(tahomaFont);
			TableDbShape dbShape = new TableDbShape(g, populateData());
			dbShape.drawShape();
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//dbShape.setX(100);
		//dbShape.setY(100);
		
		//update(g);
	}

	public void update(Graphics g) {
		super.update(g);
		/*Graphics2D g2 = (Graphics2D) g;
		Dimension dim = getSize();
		int w = (int) dim.getWidth();
		int h = (int) dim.getHeight();
		g2.setStroke(new BasicStroke(8.0f));

		if (firstTime) {
			area = new Rectangle(dim);
			rect.setLocation(w / 2 - 50, h / 2 - 25);
			firstTime = false;
		}

		// Clears the rectangle that was previously drawn.
		g2.setPaint(Color.white);
		g2.fillRect(0, 0, w, h);

		// Draws and fills the newly positioned rectangle.
		g2.setPaint(strokePolka);
		g2.draw(rect);
		g2.setPaint(fillPolka);
		g2.fill(rect);*/

	}

	/*
	 * Checks if the rectangle is contained within the applet window. If the
	 * rectangle is not contained withing the applet window, it is redrawn so
	 * that it is adjacent to the edge of the window and just inside the window.
	 */
	boolean checkRect() {

		if (area == null) {
			return false;
		}

		if (area.contains(rect.x, rect.y, 100, 50)) {
			return true;
		}
		int new_x = rect.x;
		int new_y = rect.y;

		if ((rect.x + 100) > area.getWidth()) {
			new_x = (int) area.getWidth() - 99;
		}
		if (rect.x < 0) {
			new_x = -1;
		}
		if ((rect.y + 50) > area.getHeight()) {
			new_y = (int) area.getHeight() - 49;
		}
		if (rect.y < 0) {
			new_y = -1;
		}
		rect.setLocation(new_x, new_y);
		return false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		
	}

	

}
