/**
 * 
 */
package com.gs.dbex.application.panel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import org.omg.CORBA.portable.ApplicationException;

import com.gs.dbex.application.comps.LineNumberedBorder;
import com.gs.dbex.application.constants.ApplicationConstants;
import com.gs.dbex.application.sql.SqlDocument;
import com.gs.dbex.model.cfg.ConnectionProperties;

/**
 * @author Green Moon
 *
 */
public class DDLGenerationPanel extends JPanel implements ActionListener,
		ApplicationConstants {

	public static final java.awt.Font DEFAULT_TEXT_FONT =
        new java.awt.Font(java.awt.Font.MONOSPACED,
            java.awt.Font.PLAIN, 12);

	private Font bitstreamFont;
	private JFrame parentFrame;
	//private QueryExecutionService queryExecutionService;
	
	private String tableName;
	private ConnectionProperties connectionProperties;
	
	private JToolBar ddlToolBar;
	private JButton generateDdlButton, refreshButton, copyButton, saveButton, clearButton;
	private JTextArea ddlTextArea;
	private JLabel statusLabel;

	private JToggleButton wrapToggleButton;
	
	public DDLGenerationPanel(JFrame frame, String tableName,
			ConnectionProperties connectionProperties) {
		this.parentFrame = frame;
		/*try {
			bitstreamFont = Font.createFont(Font.TRUETYPE_FONT, 
					getClass().getResourceAsStream("/fonts/VeraMono.ttf"));
			bitstreamFont = new Font(bitstreamFont.getFontName(),
            java.awt.Font.PLAIN, 12);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		bitstreamFont = DEFAULT_TEXT_FONT;
		this.tableName = tableName;
		this.connectionProperties = connectionProperties;
		//queryExecutionService = new QueryExecutionServiceImpl(connectionProperties);
		initComponents();
	}

	private void initComponents() {
		GridBagConstraints gridBagConstraints = null;
		Insets insets = null;
		
		setLayout(new GridBagLayout());
		ddlToolBar = new JToolBar();
		ddlToolBar.setFloatable(false);
		
		generateDdlButton = new JButton("Generate DDL");
		generateDdlButton.setIcon(new ImageIcon(getClass()
				.getResource(IMAGE_PATH
						+ "generateddl_right.gif")));
		generateDdlButton.setFocusable(false);
		generateDdlButton.addActionListener(this);
		ddlToolBar.add(generateDdlButton);
		ddlToolBar.add(new JToolBar.Separator());
		refreshButton = new JButton("");
		refreshButton.setFocusable(false);
		refreshButton.addActionListener(this);
		refreshButton.setIcon(new ImageIcon(getClass()
				.getResource(IMAGE_PATH
						+ "reload_green.png")));
		ddlToolBar.add(refreshButton);
		ddlToolBar.add(new JToolBar.Separator());
		copyButton = new JButton("");
		copyButton.setFocusable(false);
		copyButton.addActionListener(this);
		copyButton.setIcon(new ImageIcon(getClass()
				.getResource(IMAGE_PATH
						+ "copy_edit.gif")));
		ddlToolBar.add(copyButton);
		saveButton = new JButton("");
		saveButton.setFocusable(false);
		saveButton.addActionListener(this);
		saveButton.setIcon(new ImageIcon(getClass()
				.getResource(IMAGE_PATH
						+ "save_edit.gif")));
		ddlToolBar.add(saveButton);
		clearButton = new JButton("");
		clearButton.setFocusable(false);
		clearButton.addActionListener(this);
		clearButton.setIcon(new ImageIcon(getClass()
				.getResource(IMAGE_PATH
						+ "clear_co.gif")));
		ddlToolBar.add(clearButton);
		ddlToolBar.add(new JToolBar.Separator());
		wrapToggleButton = new JToggleButton();
		wrapToggleButton.setSelected(true);
		wrapToggleButton.setIcon(new ImageIcon(getClass()
				.getResource(IMAGE_PATH + "button-word-wrap.gif")));
		wrapToggleButton.addActionListener(this);
		wrapToggleButton.setFocusable(false);
		wrapToggleButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		wrapToggleButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		ddlToolBar.add(wrapToggleButton);
		statusLabel = new JLabel();
		statusLabel.setIcon(new ImageIcon(getClass()
				.getResource(IMAGE_PATH
						+ "loading.gif")));
		statusLabel.setVisible(false);
		ddlToolBar.add(statusLabel);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		gridBagConstraints.weightx = 1.0;
		add(ddlToolBar, gridBagConstraints);
		
		SqlDocument doc = new SqlDocument();
		ddlTextArea = new JTextArea(doc);
		ddlTextArea.setCursor(new Cursor(Cursor.TEXT_CURSOR));
		ddlTextArea.setEditable(false);
		doc.setTextArea(ddlTextArea);
		ddlTextArea.setColumns(20);
		ddlTextArea.setLineWrap(true);
		ddlTextArea.setRows(5);
		ddlTextArea.setTabSize(4);
		ddlTextArea.setMargin(new Insets(2,5,2,5));
		LineNumberedBorder lineNumberedBorder = new LineNumberedBorder(LineNumberedBorder.LEFT_SIDE,
				LineNumberedBorder.RIGHT_JUSTIFY);
		ddlTextArea.setFont((null != bitstreamFont) ? bitstreamFont : DEFAULT_TEXT_FONT);
		ddlTextArea.setBorder(
            BorderFactory.createCompoundBorder(
              BorderFactory.createEmptyBorder(1, 1, 1, 1),
              BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1),
                BorderFactory.createCompoundBorder(
                    lineNumberedBorder, BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.GRAY, 1),
                        BorderFactory.createEmptyBorder(0, 8, 0, 2)
              ))
              )
              )
              );

		JScrollPane jScrollPane1 = new JScrollPane(); 
		jScrollPane1.setViewportView(ddlTextArea);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1.0;
		gridBagConstraints.weighty = 1.0;
		add(jScrollPane1, gridBagConstraints);


	}

	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(generateDdlButton)){
			new Thread(
				new Runnable(){
					
					public void run() {
						generateDdlButton.setEnabled(false);
						statusLabel.setVisible(true);
						statusLabel.setText("Generating DDL, please wait...");
						String ddlString = "";
						/*try {
							ddlString = queryExecutionService.generateDdlForTable(tableName);
						} catch (ApplicationException e1) {
							ddlString = e1.getMessage();
						}*/
						ddlTextArea.setText(ddlString);
						statusLabel.setText("");
						statusLabel.setVisible(false);
						generateDdlButton.setEnabled(true);
					}
				}
			).start();
		}else if (e.getSource().equals(wrapToggleButton)) {
			if(wrapToggleButton.isSelected()){
				ddlTextArea.setLineWrap(true);
			}else{
				ddlTextArea.setLineWrap(false);
			}
		}
	}

	/**
	 * @return the parentFrame
	 */
	public JFrame getParentFrame() {
		return parentFrame;
	}

	/**
	 * @param parentFrame the parentFrame to set
	 */
	public void setParentFrame(JFrame parentFrame) {
		this.parentFrame = parentFrame;
	}

}
