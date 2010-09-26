/**
 * 
 */
package com.gs.dbex.resources;

import java.awt.Font;

import org.apache.log4j.Logger;

/**
 * @author sabuj.das
 *
 */
public final class DbexResourceConstants {

	private static final Logger LOGGER = Logger.getLogger(DbexResourceConstants.class);
	
	public static final String RESOURCES_LOCATION = "/com/gs/dbex/resources";
	
	
	public static final class DEFAULT_FONT{
		public static final String DEFAULT_FONT_LOCATION = RESOURCES_LOCATION + "/fonts";
		public static final int FONT_SIZE = 12;
		public static final int FONT_STYLE = Font.PLAIN;
		
		public static Font MONOSPACED_FONT;
		public static  Font CONSTANTIA_FONT;
		public static  Font COURIOR_FONT;
		public static  Font LIBRI_FONT;
		public static  Font TAHOMA_FONT;
		public static  Font VERA_MONO_FONT;
		
		
		static{
			LOGGER.info("Loading Default Fonts...");
			try{
				MONOSPACED_FONT =  new Font(Font.MONOSPACED, Font.PLAIN, 12);
				Font font = Font.createFont(Font.TRUETYPE_FONT, 
						DbexResourceConstants.class.getResourceAsStream(DEFAULT_FONT_LOCATION + "/VeraMono.ttf"));
				CONSTANTIA_FONT = new Font(font.getFontName(), FONT_STYLE, FONT_SIZE);
				font = Font.createFont(Font.TRUETYPE_FONT, 
						DbexResourceConstants.class.getResourceAsStream(DEFAULT_FONT_LOCATION + "/VeraMono.ttf"));
				COURIOR_FONT = new Font(font.getFontName(), FONT_STYLE, FONT_SIZE);
				font = Font.createFont(Font.TRUETYPE_FONT, 
						DbexResourceConstants.class.getResourceAsStream(DEFAULT_FONT_LOCATION + "/VeraMono.ttf"));
				LIBRI_FONT = new Font(font.getFontName(), FONT_STYLE, FONT_SIZE);
				font = Font.createFont(Font.TRUETYPE_FONT, 
						DbexResourceConstants.class.getResourceAsStream(DEFAULT_FONT_LOCATION + "/VeraMono.ttf"));
				TAHOMA_FONT = new Font(font.getFontName(), FONT_STYLE, FONT_SIZE);
				font = Font.createFont(Font.TRUETYPE_FONT, 
						DbexResourceConstants.class.getResourceAsStream(DEFAULT_FONT_LOCATION + "/VeraMono.ttf"));
				VERA_MONO_FONT = new Font(font.getFontName(), FONT_STYLE, FONT_SIZE);
			}catch(Exception e){
				
			}
		}
	}
	
	public static final class PROPERTIE_KEYS{
		
		public static final class FONT{
			
		}
	}
}
