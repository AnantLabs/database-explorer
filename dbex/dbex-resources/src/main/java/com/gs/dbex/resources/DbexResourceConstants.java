/**
 * 
 */
package com.gs.dbex.resources;

import java.awt.Font;

/**
 * @author sabuj.das
 *
 */
public final class DbexResourceConstants {

	public static final String RESOURCES_LOCATION = "/com/gs/dbex/resources";
	
	public static final class DEFAULT_FONT{
		public static final String DEFAULT_FONT_LOCATION = RESOURCES_LOCATION + "/fonts";
		
		public static final Font MONOSPACED_FONT;
		
		public static final Font CONSTANTIA_NORMAL_FONT;
		public static final Font CONSTANTIA_BOLD_FONT;
		public static final Font CONSTANTIA_BOLD_ITALIC_FONT;
		
		public static final Font _NORMAL_FONT;
		public static final Font _BOLD_FONT;
		public static final Font _BOLD_ITALIC_FONT;
		
		public static final Font _NORMAL_FONT;
		public static final Font _BOLD_FONT;
		public static final Font _BOLD_ITALIC_FONT;
		
		public static final Font _NORMAL_FONT;
		public static final Font _BOLD_FONT;
		public static final Font _BOLD_ITALIC_FONT;
		
		public static final Font _NORMAL_FONT;
		public static final Font _BOLD_FONT;
		public static final Font _BOLD_ITALIC_FONT;
		
		public static final Font _NORMAL_FONT;
		public static final Font _BOLD_FONT;
		public static final Font _BOLD_ITALIC_FONT;
		
		public static final Font _NORMAL_FONT;
		public static final Font _BOLD_FONT;
		public static final Font _BOLD_ITALIC_FONT;
		
		static{
			MONOSPACED_FONT =  new Font(Font.MONOSPACED, Font.PLAIN, 12);
			try {
				bitstreamFont = Font.createFont(Font.TRUETYPE_FONT, 
						getClass().getResourceAsStream("/fonts/VeraMono.ttf"));
				bitstreamFont = new Font(bitstreamFont.getFontName(),
	            Font.PLAIN, 12);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (FontFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static final class PROPERTIE_KEYS{
		
		public static final class FONT{
			public static final String application_font
		}
	}
}
