/**
 * 
 */
package com.gs.dbex.application.button;

import java.sql.Blob;

import javax.swing.ImageIcon;
import javax.swing.JButton;



/**
 * @author sabuj.das
 *
 */
public class BlobButton extends JButton {

	private Blob blobData;
	
	public BlobButton() {
		this(null);
		/*setIcon(new ImageIcon(getClass()
				.getResource(OracleGuiConstants.IMAGE_PATH
						+ "view_blob_clob.png")));
		setActionCommand(GuiCommandConstants.VIEW_BLOB_TEXT_ACT_CMD);*/
	}

	public BlobButton(Blob blobData) {
		this.blobData = blobData;
	}

	public Blob getBlobData() {
		return blobData;
	}

	public void setBlobData(Blob blobData) {
		this.blobData = blobData;
	}
	
	
	
}
