/**
 * 
 */
package com.gs.dbex.application.dlg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;

import com.gs.dbex.application.constants.ApplicationConstants;
import com.gs.dbex.application.mapping.FontStyle;
import com.gs.dbex.application.mapping.StyleColor;
import com.gs.dbex.application.mapping.StyleConfiguration;
import com.gs.dbex.application.mapping.SyntaxStyle;
import com.gs.dbex.application.mapping.WordFont;
import com.gs.dbex.application.mapping.WordStyle;
import com.gs.utils.io.IOUtil;
import com.gs.utils.xml.rw.XmlRWUtils;

/**
 * @author sabuj.das
 *
 */
public class StyleConfigurationDialog extends JDialog {

	public static String[] installedFontNames;

    private int selectedOption = ApplicationConstants.CANCEL_OPTION;
    private StyleConfiguration configuration;
    private FormListener formListener;
    
    static{
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        installedFontNames = ge.getAvailableFontFamilyNames();
    }
	
    public StyleConfigurationDialog(Frame parent, boolean modal) {
        super(parent, modal);
		
        initComponents();
        
        initValues();
    }

	private void initValues(){
		InputStream mappingInputStream = IOUtil.getResourceAsStream(ApplicationConstants.SQL_SYNTAX_MAPPING_FILE);
		//File dataFile = IOUtil.mkfile(ApplicationConstants.SYNTAX_DATA_FILE);
		File dataFile = new File(ApplicationConstants.SYNTAX_DATA_FILE);
		try {
			configuration = XmlRWUtils.readUsingCastor(dataFile, mappingInputStream);
		} catch (MarshalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(configuration != null ){
			configuration.loadStyleMap();
			List<SyntaxStyle> sl = configuration.getSyntaxStyleList();
			
			if(sl != null){
				final String[]  lanArra = new String[sl.size()];
				int i = 0;
				for (SyntaxStyle syntaxStyle : sl) {
					lanArra[i++] = syntaxStyle.getLanguage();
					List<WordStyle> wsl = syntaxStyle.getWordStyleList().getWordStyleList();
					if(wsl != null){
						final String[] styles = new String[wsl.size()];
						int j=0;
						for (WordStyle wordStyle : wsl) {
							styles[j++] = wordStyle.getWordType();
						}
						styleList.setModel(new AbstractListModel() {
				            String[] strings = styles;
				            public int getSize() { return strings.length; }
				            public Object getElementAt(int i) { return strings[i]; }
				        });
						//styleList.setSelectedIndex(0);
					}
				}
				languageList.setModel(new AbstractListModel() {
		            String[] strings = lanArra;
		            public int getSize() { return strings.length; }
		            public Object getElementAt(int i) { return strings[i]; }
		        });
				languageList.setSelectedIndex(0);
				
			}
			
		}
		
	}
	
    @SuppressWarnings("unchecked")
    private void initComponents() {
        GridBagConstraints gridBagConstraints;

        mainPanel = new JPanel();
        selectThemeLabel = new JLabel();
        selectThemeComboBox = new JComboBox();
        languagePanel = new JPanel();
        languageLabel = new JLabel();
        jScrollPane1 = new JScrollPane();
        languageList = new JList();
        styleLabel = new JLabel();
        jScrollPane2 = new JScrollPane();
        styleList = new JList();
        stylePanel = new JPanel();
        styleNameLabel = new JLabel();
        colorStylePanel = new JPanel();
        jLabel1 = new JLabel();
        foreColorPanel = new JPanel();
        jLabel2 = new JLabel();
        bgColorPanel = new JPanel();
        fontStylePanel = new JPanel();
        jLabel3 = new JLabel();
        fontNamesComboBox = new JComboBox();
        jPanel1 = new JPanel();
        boldCheckBox = new JCheckBox();
        italicCheckBox = new JCheckBox();
        underlinedCheckBox = new JCheckBox();
        jLabel4 = new JLabel();
        fontSizeTextField = new JTextField();
        cancelButton = new JButton();
        saveButton = new JButton();

        FormListener formListener = new FormListener();
        this.formListener = formListener;

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Style Configurator");

        mainPanel.setMinimumSize(new Dimension(620, 300));
        mainPanel.setPreferredSize(mainPanel.getMinimumSize());
        mainPanel.setLayout(new GridBagLayout());

        selectThemeLabel.setText("Select Theme");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        mainPanel.add(selectThemeLabel, gridBagConstraints);

        selectThemeComboBox.setModel(new DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        selectThemeComboBox.setMinimumSize(new Dimension(200, 20));
        selectThemeComboBox.setPreferredSize(new Dimension(200, 20));
        selectThemeComboBox.addActionListener(formListener);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        mainPanel.add(selectThemeComboBox, gridBagConstraints);

        languagePanel.setBorder(BorderFactory.createTitledBorder(" Language & Styles "));
        languagePanel.setMinimumSize(new Dimension(250, 100));
        languagePanel.setPreferredSize(new Dimension(250, 100));
        languagePanel.setLayout(new GridBagLayout());

        languageLabel.setText("Language :");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        languagePanel.add(languageLabel, gridBagConstraints);

        
        languageList.addListSelectionListener(formListener);
        jScrollPane1.setViewportView(languageList);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        languagePanel.add(jScrollPane1, gridBagConstraints);

        styleLabel.setText("Style : ");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        languagePanel.add(styleLabel, gridBagConstraints);

        
        styleList.addListSelectionListener(formListener);
        jScrollPane2.setViewportView(styleList);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        languagePanel.add(jScrollPane2, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        mainPanel.add(languagePanel, gridBagConstraints);

        stylePanel.setBorder(BorderFactory.createTitledBorder(" Style Setup "));
        stylePanel.setMinimumSize(new Dimension(303, 100));
        stylePanel.setPreferredSize(new Dimension(310, 100));
        stylePanel.setLayout(new GridBagLayout());

        styleNameLabel.setText("Some Style");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        stylePanel.add(styleNameLabel, gridBagConstraints);

        colorStylePanel.setBorder(BorderFactory.createTitledBorder(" Color Style "));
        colorStylePanel.setMaximumSize(new Dimension(150, 150));
        colorStylePanel.setMinimumSize(new Dimension(150, 150));
        colorStylePanel.setPreferredSize(new Dimension(150, 150));
        colorStylePanel.setLayout(new GridBagLayout());

        jLabel1.setText("Foreground");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        colorStylePanel.add(jLabel1, gridBagConstraints);

        foreColorPanel.setMaximumSize(new Dimension(25, 25));
        foreColorPanel.setMinimumSize(new Dimension(25, 25));
        foreColorPanel.setPreferredSize(new Dimension(25, 25));
        foreColorPanel.addMouseListener(formListener);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        colorStylePanel.add(foreColorPanel, gridBagConstraints);

        jLabel2.setText("Background");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        colorStylePanel.add(jLabel2, gridBagConstraints);

        bgColorPanel.setMaximumSize(new Dimension(25, 25));
        bgColorPanel.setMinimumSize(new Dimension(25, 25));
        bgColorPanel.setPreferredSize(new Dimension(25, 25));
        bgColorPanel.addMouseListener(formListener);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        colorStylePanel.add(bgColorPanel, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        stylePanel.add(colorStylePanel, gridBagConstraints);

        fontStylePanel.setBorder(BorderFactory.createTitledBorder(" Font Style "));
        fontStylePanel.setLayout(new GridBagLayout());

        jLabel3.setText("Font Name ");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        fontStylePanel.add(jLabel3, gridBagConstraints);

        fontNamesComboBox.setModel(new DefaultComboBoxModel(installedFontNames));
        fontNamesComboBox.addActionListener(formListener);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        fontStylePanel.add(fontNamesComboBox, gridBagConstraints);

        jPanel1.setLayout(new GridBagLayout());

        boldCheckBox.setText("Bold");
        boldCheckBox.addActionListener(formListener);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 10);
        jPanel1.add(boldCheckBox, gridBagConstraints);

        italicCheckBox.setText("Italic");
        italicCheckBox.addActionListener(formListener);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 10);
        jPanel1.add(italicCheckBox, gridBagConstraints);

        underlinedCheckBox.setText("Underlined");
        underlinedCheckBox.addActionListener(formListener);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 10);
        jPanel1.add(underlinedCheckBox, gridBagConstraints);

        jLabel4.setText("Size : ");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.insets = new Insets(2, 0, 2, 2);
        jPanel1.add(jLabel4, gridBagConstraints);

        fontSizeTextField.addKeyListener(formListener);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(2, 0, 2, 2);
        jPanel1.add(fontSizeTextField, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        fontStylePanel.add(jPanel1, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        stylePanel.add(fontStylePanel, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        mainPanel.add(stylePanel, gridBagConstraints);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(formListener);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        mainPanel.add(cancelButton, gridBagConstraints);

        saveButton.setText("Save & Close");
        saveButton.addActionListener(formListener);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.insets = new Insets(2, 2, 2, 2);
        mainPanel.add(saveButton, gridBagConstraints);

        getContentPane().add(mainPanel, BorderLayout.CENTER);

        pack();
    }


    private class FormListener implements ActionListener, KeyListener, MouseListener, ListSelectionListener {
        FormListener() {}
        public void actionPerformed(ActionEvent evt) {
            if (evt.getSource() == selectThemeComboBox) {
                StyleConfigurationDialog.this.selectThemeComboBoxActionPerformed(evt);
            }
            else if (evt.getSource() == fontNamesComboBox) {
                StyleConfigurationDialog.this.fontNamesComboBoxActionPerformed(evt);
            }
            else if (evt.getSource() == boldCheckBox) {
                StyleConfigurationDialog.this.boldCheckBoxActionPerformed(evt);
            }
            else if (evt.getSource() == italicCheckBox) {
                StyleConfigurationDialog.this.italicCheckBoxActionPerformed(evt);
            }
            else if (evt.getSource() == underlinedCheckBox) {
                StyleConfigurationDialog.this.underlinedCheckBoxActionPerformed(evt);
            }
            else if (evt.getSource() == saveButton) {
                StyleConfigurationDialog.this.saveButtonActionPerformed(evt);
            }
            else if (evt.getSource() == cancelButton) {
                StyleConfigurationDialog.this.cancelButtonActionPerformed(evt);
            }
        }

        public void keyPressed(KeyEvent evt) {
            
        }

        public void keyReleased(KeyEvent evt) {
        	if (evt.getSource() == fontSizeTextField) {
                StyleConfigurationDialog.this.fontSizeTextFieldKeyReleased(evt);
            }
        }

        public void keyTyped(KeyEvent evt) {
        }

        public void mouseClicked(MouseEvent evt) {
            if (evt.getSource() == foreColorPanel) {
            	if(foreColorPanel.isEnabled())
            		StyleConfigurationDialog.this.foreColorPanelMouseClicked(evt);
            }
            else if (evt.getSource() == bgColorPanel) {
            	if(bgColorPanel.isEnabled())
            		StyleConfigurationDialog.this.bgColorPanelMouseClicked(evt);
            }
        }

        public void mouseEntered(MouseEvent evt) {
        	if (evt.getSource() == foreColorPanel) {
        		foreColorPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            else if (evt.getSource() == bgColorPanel) {
                bgColorPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        }

        public void mouseExited(MouseEvent evt) {
        	if (evt.getSource() == foreColorPanel) {
                foreColorPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            else if (evt.getSource() == bgColorPanel) {
                bgColorPanel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        }

        public void mousePressed(MouseEvent evt) {
        }

        public void mouseReleased(MouseEvent evt) {
        }

        public void valueChanged(ListSelectionEvent evt) {
            if (evt.getSource() == languageList) {
                StyleConfigurationDialog.this.languageListValueChanged(evt);
            }
            else if (evt.getSource() == styleList) {
                StyleConfigurationDialog.this.styleListValueChanged(evt);
            }
        }
    }

    private void languageListValueChanged(ListSelectionEvent evt) {
        String language = languageList.getSelectedValue().toString();
        SyntaxStyle syntaxStyle = configuration.getStyleByLanguage(language);
        int i = 0;
        List<WordStyle> wsl = syntaxStyle.getWordStyleList().getWordStyleList();
		if(wsl != null){
			final String[] styles = new String[wsl.size()];
			int j=0;
			for (WordStyle wordStyle : wsl) {
				styles[j++] = wordStyle.getWordType();
			}
			styleList.setModel(new AbstractListModel() {
	            String[] strings = styles;
	            public int getSize() { return strings.length; }
	            public Object getElementAt(int i) { return strings[i]; }
	        });
			styleList.setSelectedIndex(0);
		}
    }

    private void styleListValueChanged(ListSelectionEvent evt) {
    	String language = languageList.getSelectedValue().toString();
        SyntaxStyle syntaxStyle = configuration.getStyleByLanguage(language);
        String keyWord = styleList.getSelectedValue().toString();
        WordStyle wordStyle = syntaxStyle.getWordStyleList().getStyleByType(keyWord);
        
        styleNameLabel.setText(language + " : " + keyWord);
        StyleColor foreColor = wordStyle.getWordColorList().getStyleByType(ApplicationConstants.FORE_GROUND);
        if(foreColor != null){
        	foreColorPanel.setBackground(new Color(foreColor.getRed(), foreColor.getGreen(), foreColor.getBlue()));
        	/*if(!foreColor.isEditable()){
        		foreColorPanel.removeMouseListener(formListener);
        	} else {
        		foreColorPanel.addMouseListener(formListener);
        	}*/
        	foreColorPanel.setEnabled(foreColor.isEditable());
        }
        StyleColor backColor = wordStyle.getWordColorList().getStyleByType(ApplicationConstants.BACK_GROUND);
        if(backColor != null){
        	bgColorPanel.setBackground(new Color(backColor.getRed(), backColor.getGreen(), backColor.getBlue()));
        	/*if(!backColor.isEditable()){
        		bgColorPanel.removeMouseListener(formListener);
        	} else {
        		bgColorPanel.addMouseListener(formListener);
        	}*/
        	bgColorPanel.setEnabled(backColor.isEditable());
        }
        WordFont wordFont = wordStyle.getWordFont();
        if(wordFont != null){
        	FontStyle fontStyle = wordFont.getFontStyle();
        	int index = -1;
        	for (int i = 0; i < installedFontNames.length; i++) {
        		String fontName = installedFontNames[i];
				if(fontName.equalsIgnoreCase(wordFont.getFontName())){
					index = i;
					break;
				}
			}
        	if(index > -1){
        		fontNamesComboBox.setSelectedIndex(index);
        	}
        	fontSizeTextField.setText(""+wordFont.getFontSize());
        	
        	if(fontStyle != null){
        		boldCheckBox.setSelected(fontStyle.isBold());
        		italicCheckBox.setSelected(fontStyle.isItalic());
        		underlinedCheckBox.setSelected(fontStyle.isUnderlined());
        	}
        	fontNamesComboBox.setEnabled(wordFont.isEditable());
    		fontSizeTextField.setEnabled(wordFont.isEditable());
    		boldCheckBox.setEnabled(wordFont.isEditable());
    		italicCheckBox.setEnabled(wordFont.isEditable());
    		underlinedCheckBox.setEnabled(wordFont.isEditable());
        }
    }

    private void foreColorPanelMouseClicked(MouseEvent evt) {
    	String language = languageList.getSelectedValue().toString();
    	String keyWord = styleList.getSelectedValue().toString();
    	SyntaxStyle syntaxStyle = configuration.getStyleByLanguage(language);
    	WordStyle wordStyle = syntaxStyle.getWordStyleList().getStyleByType(keyWord);
    	
    	// single left click
    	if(MouseEvent.BUTTON1 == evt.getButton()){
    		Color newColor = JColorChooser.showDialog(this, "Choose Color", foreColorPanel.getBackground());
    		StyleColor color = wordStyle.getWordColorList().getStyleByType(ApplicationConstants.FORE_GROUND);
    		foreColorPanel.setBackground(newColor);
    		color.setColor(newColor);
    	}
    	
    }

    private void bgColorPanelMouseClicked(MouseEvent evt) {
    	String language = languageList.getSelectedValue().toString();
    	String keyWord = styleList.getSelectedValue().toString();
    	SyntaxStyle syntaxStyle = configuration.getStyleByLanguage(language);
    	WordStyle wordStyle = syntaxStyle.getWordStyleList().getStyleByType(keyWord);
    	
    	// single left click
    	if(MouseEvent.BUTTON1 == evt.getButton()){
    		Color newColor = JColorChooser.showDialog(this, "Choose Color", bgColorPanel.getBackground());
    		StyleColor color = wordStyle.getWordColorList().getStyleByType(ApplicationConstants.BACK_GROUND);
    		bgColorPanel.setBackground(newColor);
    		color.setColor(newColor);
    	}
    }

    private void selectThemeComboBoxActionPerformed(ActionEvent evt) {
        
    }

    private void fontNamesComboBoxActionPerformed(ActionEvent evt) {
    	String language = languageList.getSelectedValue().toString();
    	String keyWord = styleList.getSelectedValue().toString();
    	SyntaxStyle syntaxStyle = configuration.getStyleByLanguage(language);
    	WordStyle wordStyle = syntaxStyle.getWordStyleList().getStyleByType(keyWord);
    	
    	String selectedFontName = fontNamesComboBox.getSelectedItem().toString();
    	WordFont wordFont = wordStyle.getWordFont();
        if(wordFont != null){
        	wordFont.setFontName(selectedFontName);
        }
    }

    private void boldCheckBoxActionPerformed(ActionEvent evt) {
    	String language = languageList.getSelectedValue().toString();
    	String keyWord = styleList.getSelectedValue().toString();
    	SyntaxStyle syntaxStyle = configuration.getStyleByLanguage(language);
    	WordStyle wordStyle = syntaxStyle.getWordStyleList().getStyleByType(keyWord);
    	
    	WordFont wordFont = wordStyle.getWordFont();
        if(wordFont != null){
        	FontStyle fontStyle = wordFont.getFontStyle();
        	if(fontStyle != null){
        		fontStyle.setBold(boldCheckBox.isSelected());
        	}
        }
    }

    private void italicCheckBoxActionPerformed(ActionEvent evt) {
    	String language = languageList.getSelectedValue().toString();
    	String keyWord = styleList.getSelectedValue().toString();
    	SyntaxStyle syntaxStyle = configuration.getStyleByLanguage(language);
    	WordStyle wordStyle = syntaxStyle.getWordStyleList().getStyleByType(keyWord);
    	
    	WordFont wordFont = wordStyle.getWordFont();
        if(wordFont != null){
        	FontStyle fontStyle = wordFont.getFontStyle();
        	if(fontStyle != null){
        		fontStyle.setItalic(italicCheckBox.isSelected());
        	}
        }
    }

    private void underlinedCheckBoxActionPerformed(ActionEvent evt) {
    	String language = languageList.getSelectedValue().toString();
    	String keyWord = styleList.getSelectedValue().toString();
    	SyntaxStyle syntaxStyle = configuration.getStyleByLanguage(language);
    	WordStyle wordStyle = syntaxStyle.getWordStyleList().getStyleByType(keyWord);
    	
    	WordFont wordFont = wordStyle.getWordFont();
        if(wordFont != null){
        	FontStyle fontStyle = wordFont.getFontStyle();
        	if(fontStyle != null){
        		fontStyle.setUnderlined(underlinedCheckBox.isSelected());
        	}
        }
    }

    private void fontSizeTextFieldKeyReleased(KeyEvent evt) {
    	String language = languageList.getSelectedValue().toString();
    	String keyWord = styleList.getSelectedValue().toString();
    	SyntaxStyle syntaxStyle = configuration.getStyleByLanguage(language);
    	WordStyle wordStyle = syntaxStyle.getWordStyleList().getStyleByType(keyWord);
    	
    	
    	WordFont wordFont = wordStyle.getWordFont();
        if(wordFont != null){
        	Integer size = wordFont.getFontSize();
        	try{
        		size = Integer.parseInt(fontSizeTextField.getText());
        	}catch(NumberFormatException e){
        		e.printStackTrace();
        	}
        	wordFont.setFontSize(size);
        }
    }

    private void saveButtonActionPerformed(ActionEvent evt) {
    	/*InputStream mappingInputStream = IOUtils.getResourceAsStream(ApplicationConstants.SQL_SYNTAX_MAPPING_FILE);
		File dataFile = IOUtils.mkfile(ApplicationConstants.SYNTAX_DATA_FILE);
		
		XmlRWUtils.writeUsingCastor(dataFile, mappingInputStream, configuration);*/
		dispose();
    }

    private void cancelButtonActionPerformed(ActionEvent evt) {
        dispose();
    }

    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                StyleConfigurationDialog dialog = new StyleConfigurationDialog(new JFrame(), true);
                dialog.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    private JPanel bgColorPanel;
    private JCheckBox boldCheckBox;
    private JButton cancelButton;
    private JPanel colorStylePanel;
    private JComboBox fontNamesComboBox;
    private JTextField fontSizeTextField;
    private JPanel fontStylePanel;
    private JPanel foreColorPanel;
    private JCheckBox italicCheckBox;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JPanel jPanel1;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JLabel languageLabel;
    private JList languageList;
    private JPanel languagePanel;
    private JPanel mainPanel;
    private JButton saveButton;
    private JComboBox selectThemeComboBox;
    private JLabel selectThemeLabel;
    private JLabel styleLabel;
    private JList styleList;
    private JLabel styleNameLabel;
    private JPanel stylePanel;
    private JCheckBox underlinedCheckBox;
  
}
