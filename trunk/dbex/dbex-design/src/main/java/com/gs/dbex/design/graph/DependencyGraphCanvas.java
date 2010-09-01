/**
 * 
 */
package com.gs.dbex.design.graph;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import com.gs.dbex.design.model.BaseDbShape;

/**
 * @author Sabuj.das
 * 
 */
public class DependencyGraphCanvas<T extends BaseDbShape> extends JPanel implements MouseListener,
		MouseMotionListener, MouseWheelListener, KeyListener  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1175314071365190993L;
	
	int x1, y1, x2, y2;
	
	private T dbShape;
	private T selectedShape;
	private JPanel parentPanel;
	private Graphics bufferGraphics; 
	private Image offscreenImage; 
	private Dimension currentSize;
	private Point currentPoint;
	private Rectangle bindingRectangle;
	
	public DependencyGraphCanvas(JPanel parentPanel, T dbShape) {
		this.dbShape = dbShape;
		this.parentPanel = parentPanel;
		setBackground(Color.WHITE);
		setMinimumSize(new Dimension(100, 100));
		setPreferredSize(getMinimumSize());
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		addKeyListener(this);
		
		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,0,true), this);
		
		setBorder(BorderFactory.createLineBorder(Color.MAGENTA));
	}

	
	
	public void initCanvas(){
		currentSize = getSize();
		
		
		offscreenImage = createImage(currentSize.width,currentSize.height); 
		bufferGraphics = offscreenImage.getGraphics(); 
		//if(null == dbShape.getGraphics())
			dbShape.setGraphics(bufferGraphics);
		dbShape.populateGraphicsContent(bufferGraphics, currentSize);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		initCanvas();
		//bufferGraphics.clearRect(0,0,currentSize.width,currentSize.height); 
		dbShape.drawShape();
		if(bindingRectangle != null){
			drawHighlightSquares(bufferGraphics, bindingRectangle);
		}
		g.drawImage(offscreenImage,0,0,this); 
	}

	@Override
	public void update(Graphics g) {
		paintComponent(g);
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println("keyTyped:"+e.getKeyCode());
		if(null != selectedShape){
			if(KeyEvent.VK_DOWN == e.getKeyCode()){
				dbShape.setX(dbShape.getX());
				dbShape.setY(dbShape.getY() + 1);
			}
			repaint();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("keyPressed:: " + e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println("keyReleased:: " + e.getKeyCode());
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		System.out.println("mouseWheelMoved:"+e.getWheelRotation());
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(dbShape.contains(e.getX(), e.getY())){
			x2 = e.getX();
	        y2 = e.getY();
			bindingRectangle = null;
			selectedShape = dbShape;
			bindingRectangle = null;
			dbShape.setX(dbShape.getX() + x2 - x1);
			dbShape.setY(dbShape.getY() + y2 - y1);
			x1 = x2;
	        y1 = y2;
		} 
		x1 = e.getX();
		y1 = e.getY();
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(null == currentPoint){
			currentPoint = new Point(e.getX(), e.getY());
		} else {
			currentPoint.x = e.getX();
			currentPoint.y = e.getY();
		}
		setToolTipText(dbShape.tooltipText(currentPoint));
		if(dbShape.contains(e.getX(), e.getY())){
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		} else {
			setCursor(Cursor.getDefaultCursor());
		}
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(dbShape.contains(e.getX(), e.getY())){
			selectedShape = dbShape;
			bindingRectangle = dbShape.getBounds();
		} else {
			if(bindingRectangle != null){
				bindingRectangle = null;
			}
		}
		repaint();	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(dbShape.contains(e.getX(), e.getY())){
			setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
			selectedShape = dbShape;
			if(bindingRectangle != null)
				bindingRectangle = dbShape.getBounds();
		} else {
			bindingRectangle = null;
		}
		
		x1 = e.getX();
		y1 = e.getY();
		repaint();
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(dbShape.contains(e.getX(), e.getY())){
			bindingRectangle = dbShape.getBounds();
			selectedShape = dbShape;
			setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		} else {
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
		repaint();	
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void drawHighlightSquares(Graphics g, Rectangle r) {
		if(null == r)
			return;
		Graphics2D g2D = (Graphics2D) g;
		double x = r.getX();
		double y = r.getY();
		double w = r.getWidth();
		double h = r.getHeight();
		g2D.setColor(Color.RED);

		g2D.fill(new Rectangle.Double(x - 3.0, y - 3.0, 6.0, 6.0));
		g2D.fill(new Rectangle.Double(x + w * 0.5 - 3.0, y - 3.0, 6.0, 6.0));
		g2D.fill(new Rectangle.Double(x + w - 3.0, y - 3.0, 6.0, 6.0));
		g2D.fill(new Rectangle.Double(x - 3.0, y + h * 0.5 - 3.0, 6.0, 6.0));
		g2D.fill(new Rectangle.Double(x + w - 3.0, y + h * 0.5 - 3.0, 6.0, 6.0));
		g2D.fill(new Rectangle.Double(x - 3.0, y + h - 3.0, 6.0, 6.0));
		g2D.fill(new Rectangle.Double(x + w * 0.5 - 3.0, y + h - 3.0, 6.0, 6.0));
		g2D.fill(new Rectangle.Double(x + w - 3.0, y + h - 3.0, 6.0, 6.0));
	}
}
