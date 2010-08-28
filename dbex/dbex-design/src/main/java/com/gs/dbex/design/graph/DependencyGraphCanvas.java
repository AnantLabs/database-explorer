/**
 * 
 */
package com.gs.dbex.design.graph;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
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

import javax.swing.JPanel;

import com.gs.dbex.design.model.BaseDbShape;
import com.gs.dbex.design.model.TableDbShape;
import com.gs.dbex.model.db.Column;
import com.gs.dbex.model.db.Table;

/**
 * @author Sabuj.das
 * 
 */
public class DependencyGraphCanvas<T extends BaseDbShape> extends Canvas implements MouseListener,
		MouseMotionListener, MouseWheelListener, KeyListener  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1175314071365190993L;
	
	
	private T dbShape;
	private JPanel parentPanel;
	private Graphics bufferGraphics; 
	private Image offscreenImage; 
	private Dimension currentSize;
	private Point currentPoint;
	
	public DependencyGraphCanvas(JPanel parentPanel, T dbShape) {
		this.dbShape = dbShape;
		this.parentPanel = parentPanel;
	}

	public void initCanvas(){
		currentSize = getSize();
		
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		addKeyListener(this);
		
		setBackground(Color.WHITE);
		offscreenImage = createImage(currentSize.width,currentSize.height); 
		bufferGraphics = offscreenImage.getGraphics(); 
		dbShape.setGraphics(bufferGraphics);
		dbShape.populateGraphicsContent(bufferGraphics, currentSize);
	}
	
	@Override
	public void paint(Graphics g) {
		initCanvas();
		bufferGraphics.clearRect(0,0,currentSize.width,currentSize.height); 
		dbShape.drawShape();
		g.drawImage(offscreenImage,0,0,this); 
	}

	@Override
	public void update(Graphics g) {
		paint(g);
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

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(null == currentPoint){
			currentPoint = new Point(e.getX(), e.getY());
		} else {
			currentPoint.x = e.getX();
			
		}
		parentPanel.setToolTipText("[ "+ dbShape.tooltipText(currentPoint) + " ]");
		repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
