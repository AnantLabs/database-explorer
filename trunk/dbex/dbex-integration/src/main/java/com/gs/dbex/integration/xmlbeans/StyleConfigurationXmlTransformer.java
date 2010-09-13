/**
 * 
 */
package com.gs.dbex.integration.xmlbeans;

import java.math.BigInteger;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.XmlBeans;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;

import com.gs.dbex.bod.FontStyleDocument.FontStyle;
import com.gs.dbex.bod.StyleColorDocument.StyleColor;
import com.gs.dbex.bod.StyleColorsDocument.StyleColors;
import com.gs.dbex.bod.StyleConfigurationDocument;
import com.gs.dbex.bod.SyntaxStyleDocument.SyntaxStyle;
import com.gs.dbex.bod.WordFontDocument.WordFont;
import com.gs.dbex.bod.WordStyleDocument.WordStyle;
import com.gs.dbex.bod.WordStylesDocument.WordStyles;
import com.gs.dbex.bod.impl.StyleConfigurationDocumentImpl;
import com.gs.dbex.model.syntax.StyleConfiguration;
import com.gs.utils.text.StringUtil;

/**
 * @author Sabuj Das
 *
 */
public class StyleConfigurationXmlTransformer {

	private static Logger logger = Logger.getLogger(StyleConfigurationXmlTransformer.class);
	
	private static final int DEFAULT_FONT_SIZE = 12;
	private static final String XML_TXT = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	
	public StyleConfiguration getStyleConfiguration(String xmlText){
		if(logger.isDebugEnabled()){
			logger.debug("Enter:: getAllStyleConfiguration()");
		}
		StyleConfiguration styleConfigurationModel = new StyleConfiguration();
		
		if(!StringUtil.hasValidContent(xmlText))
			return styleConfigurationModel;
		StyleConfigurationDocument styleConfigurationDocument = null;
		XmlOptions xmloptions = new XmlOptions();
		xmloptions.setDocumentType(XmlBeans.typeForClass(StyleConfigurationDocumentImpl.class));
		try {
			styleConfigurationDocument = StyleConfigurationDocument.Factory.parse(xmlText, xmloptions);
			if(null != styleConfigurationDocument){
				StyleConfigurationDocument.StyleConfiguration styleConfig = styleConfigurationDocument.getStyleConfiguration();
				if(null != styleConfig){
					SyntaxStyle[] syntaxStyles = styleConfig.getSyntaxStyleArray();
					if(null != syntaxStyles){
						for (SyntaxStyle syntaxStyle : syntaxStyles) {
							if(null != syntaxStyle){
								com.gs.dbex.model.syntax.SyntaxStyle syntaxStyleModel = getSyntaxStyleModel(syntaxStyle);
								if(logger.isDebugEnabled()){
									logger.debug("Found SyntaxStyle: " + syntaxStyleModel.getSyntaxStyleName());
								}
								styleConfigurationModel.getSyntaxStyleList().add(syntaxStyleModel);
							}
						}
					}
				}
				
			}
		} catch (XmlException e) {
			logger.error(e);
		}
		if(logger.isDebugEnabled()){
			logger.debug("Exit:: getAllStyleConfiguration()");
		}
		return styleConfigurationModel;
	}
	
	private com.gs.dbex.model.syntax.SyntaxStyle getSyntaxStyleModel( SyntaxStyle syntaxStyle) {
		com.gs.dbex.model.syntax.SyntaxStyle syntaxStyleModel = new com.gs.dbex.model.syntax.SyntaxStyle();
		syntaxStyleModel.setSyntaxStyleName(syntaxStyle.getName());
		syntaxStyleModel.setLanguage(syntaxStyle.getLanguage());
		syntaxStyleModel.setDisplayOrder((syntaxStyle.getDisplayOrder() != null) ? syntaxStyle.getDisplayOrder().intValue() : 0);
		StyleColor styleColor = syntaxStyle.getStyleColor();
		if(null != styleColor){
			com.gs.dbex.model.syntax.StyleColor styleColorModel = getStyleColorModel(styleColor);
			syntaxStyleModel.setBodyBackgroundColor(styleColorModel);
		}
		WordStyles wordStyles = syntaxStyle.getWordStyles();
		if(null != wordStyles){
			com.gs.dbex.model.syntax.WordStyles wordStylesModel = new com.gs.dbex.model.syntax.WordStyles();
			WordStyle[] wordStyleArray = wordStyles.getWordStyleArray();
			if(null != wordStyleArray){
				for (WordStyle wordStyle : wordStyleArray) {
					com.gs.dbex.model.syntax.WordStyle wordStyleModel = getWordStyleModel(wordStyle);
					wordStylesModel.getWordStyleList().add(wordStyleModel);
				}
			}
			syntaxStyleModel.setWordStyleList(wordStylesModel);
		}
		return syntaxStyleModel;
	}
	private void addSyntaxStyle(com.gs.dbex.bod.StyleConfigurationDocument.StyleConfiguration styleConfiguration, 
			com.gs.dbex.model.syntax.SyntaxStyle syntaxStyleModel){
		if(null == syntaxStyleModel){
			return;
		}
		SyntaxStyle syntaxStyle = styleConfiguration.addNewSyntaxStyle();
		syntaxStyle.setName(syntaxStyleModel.getSyntaxStyleName());
		syntaxStyle.setDisplayOrder(toBigInteger(syntaxStyleModel.getDisplayOrder()));
		syntaxStyle.setLanguage(syntaxStyleModel.getLanguage());
		if(null != syntaxStyleModel.getBodyBackgroundColor()){
			addStyleColor(syntaxStyle, syntaxStyleModel.getBodyBackgroundColor());
		}
		if(null != syntaxStyleModel.getWordStyleList()){
			WordStyles wordStyles = syntaxStyle.addNewWordStyles();
			if(null != syntaxStyleModel.getWordStyleList().getWordStyleList()){
				for(com.gs.dbex.model.syntax.WordStyle wordStyleModel : syntaxStyleModel.getWordStyleList().getWordStyleList()){
					addWordStyle(wordStyles, wordStyleModel);
				}
			}
		}
	}

	private com.gs.dbex.model.syntax.WordStyle getWordStyleModel( WordStyle wordStyle) {
		com.gs.dbex.model.syntax.WordStyle wordStyleModel = new com.gs.dbex.model.syntax.WordStyle();
		if(null != wordStyle){
			wordStyleModel.setWordType(wordStyle.getType());
			WordFont wordFont = wordStyle.getWordFont();
			com.gs.dbex.model.syntax.WordFont wordFontModel = getWordFontModel(wordFont); 
			wordStyleModel.setWordFont(wordFontModel);
			StyleColors styleColors = wordStyle.getStyleColors();
			if(null != styleColors){
				com.gs.dbex.model.syntax.StyleColors styleColorsModel = new com.gs.dbex.model.syntax.StyleColors();
				StyleColor[] styleColorsArray = styleColors.getStyleColorArray();
				if(null != styleColorsArray){
					for (StyleColor styleColor : styleColorsArray) {
						com.gs.dbex.model.syntax.StyleColor styleColorModel = getStyleColorModel(styleColor);
						styleColorsModel.getColorList().add(styleColorModel);
					}
				}
				wordStyleModel.setWordColorList(styleColorsModel);
			}
		}
		return wordStyleModel;
	}
	private void addWordStyle(WordStyles wordStyles, com.gs.dbex.model.syntax.WordStyle wordStyleModel){
		if(null == wordStyleModel){
			return;
		}
		WordStyle wordStyle = wordStyles.addNewWordStyle();
		wordStyle.setType(wordStyleModel.getWordType());
		addWordFont(wordStyle, wordStyleModel.getWordFont());
		if(null != wordStyleModel.getWordColorList()){
			StyleColors styleColors = wordStyle.addNewStyleColors();
			if(null != wordStyleModel.getWordColorList().getColorList()){
				for (com.gs.dbex.model.syntax.StyleColor styleColorModel : wordStyleModel.getWordColorList().getColorList()) {
					addStyleColor(styleColors, styleColorModel);
				}
			}
		}
	}

	private com.gs.dbex.model.syntax.WordFont getWordFontModel(WordFont wordFont) {
		com.gs.dbex.model.syntax.WordFont wordFontModel = new com.gs.dbex.model.syntax.WordFont();
		if(null != wordFont){
			wordFontModel.setEditable(wordFont.getEditable());
			wordFontModel.setFontName(wordFont.getName());
			wordFontModel.setFontSize((wordFont.getFontSize() != null) ? wordFont.getFontSize().intValue() : DEFAULT_FONT_SIZE);
			FontStyle fontStyle = wordFont.getFontStyle();
			if(null != fontStyle){
				com.gs.dbex.model.syntax.FontStyle fontStyleModel = new com.gs.dbex.model.syntax.FontStyle();
				fontStyle.setBold(fontStyle.getBold());
				fontStyle.setItalic(fontStyle.getItalic());
				fontStyle.setUnderlined(fontStyle.getUnderlined());
				wordFontModel.setFontStyle(fontStyleModel);
			}
		}
		return wordFontModel;
	}
	private void addWordFont(WordStyle wordStyle,com.gs.dbex.model.syntax.WordFont wordFontModel){
		if(null == wordFontModel){
			return;
		}
		WordFont wordFont = wordStyle.addNewWordFont();
		wordFont.setName(wordFontModel.getFontName());
		wordFont.setEditable(wordFontModel.isEditable());
		wordFont.setFontSize(toBigInteger(wordFontModel.getFontSize()));
		if(null != wordFontModel.getFontStyle()){
			FontStyle fontStyle = wordFont.addNewFontStyle();
			fontStyle.setBold(wordFontModel.getFontStyle().isBold());
			fontStyle.setItalic(wordFontModel.getFontStyle().isItalic());
			fontStyle.setUnderlined(wordFontModel.getFontStyle().isUnderlined());
		}
	}

	private com.gs.dbex.model.syntax.StyleColor getStyleColorModel(StyleColor styleColor){
		com.gs.dbex.model.syntax.StyleColor styleColorModel = new com.gs.dbex.model.syntax.StyleColor();
		if(null != styleColor){
			styleColorModel.setEditable(styleColor.getEditable());
			styleColorModel.setType(styleColor.getType());
			styleColorModel.setColorCode((styleColor.getCode() != null) ? styleColor.getCode().getStringValue() : "");
			styleColorModel.setRed((styleColor.getRed() != null) ? styleColor.getRed().intValue() : 0);
			styleColorModel.setGreen((styleColor.getGreen() != null) ? styleColor.getGreen().intValue() : 0);
			styleColorModel.setBlue((styleColor.getBlue() != null) ? styleColor.getBlue().intValue() : 0);
		}
		return styleColorModel;
	}
	
	private void addStyleColor(StyleColors styleColors, com.gs.dbex.model.syntax.StyleColor styleColorModel){
		if(null == styleColorModel)
			return;
		StyleColor styleColor = styleColors.addNewStyleColor();
		styleColor.setType(styleColorModel.getType());
		styleColor.setEditable(styleColorModel.isEditable());
		styleColor.setRed(toBigInteger(styleColorModel.getRed()));
		styleColor.setGreen(toBigInteger(styleColorModel.getGreen()));
		styleColor.setBlue(toBigInteger(styleColorModel.getBlue()));
	}
	
	private void addStyleColor(SyntaxStyle syntaxStyle, com.gs.dbex.model.syntax.StyleColor styleColorModel){
		if(null == styleColorModel)
			return;
		StyleColor styleColor = syntaxStyle.addNewStyleColor();
		styleColor.setType(styleColorModel.getType());
		styleColor.setEditable(styleColorModel.isEditable());
		styleColor.setRed(toBigInteger(styleColorModel.getRed()));
		styleColor.setGreen(toBigInteger(styleColorModel.getGreen()));
		styleColor.setBlue(toBigInteger(styleColorModel.getBlue()));
	}
	
	public static <T extends Number> BigInteger toBigInteger(T t){
		if(null == t)
			return BigInteger.valueOf(0L);
		return new BigInteger(t.toString());
	}
	
	public String generateStyleConfigurationXML(StyleConfiguration styleConfigurationModel){
		if(null == styleConfigurationModel){
			return null;
		}
		StyleConfigurationDocument styleConfigurationDocument = StyleConfigurationDocument.Factory.newInstance();
		if(null != styleConfigurationModel.getSyntaxStyleList()){
			for (com.gs.dbex.model.syntax.SyntaxStyle syntaxStyleModel : styleConfigurationModel.getSyntaxStyleList()) {
				com.gs.dbex.bod.StyleConfigurationDocument.StyleConfiguration styleConfiguration = styleConfigurationDocument.addNewStyleConfiguration();
				addSyntaxStyle(styleConfiguration, syntaxStyleModel);
			}
		}
		if(logger.isDebugEnabled()){
			logger.debug("Generated XML { \n" + styleConfigurationDocument.toString() + " }");
		}
		return XML_TXT + "\n" + styleConfigurationDocument.toString();
	}
}
