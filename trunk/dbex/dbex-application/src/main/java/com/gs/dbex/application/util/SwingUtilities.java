/*
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 * 
 * The Original Code is iSQL-Viewer, A Mutli-Platform Database Tool.
 *
 * The Initial Developer of the Original Code is iSQL-Viewer, A Mutli-Platform Database Tool.
 * Portions created by Mark A. Kobold are Copyright (C) 2000-2007. All Rights Reserved.
 *
 * Contributor(s): 
 *  Mark A. Kobold [mkobold <at> isqlviewer <dot> com].
 *  
 * If you didn't download this code from the following link, you should check
 * if you aren't using an obsolete version: http://www.isqlviewer.com
 */
package com.gs.dbex.application.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;
import javax.swing.RootPaneContainer;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.Utilities;

import com.gs.dbex.application.comps.ExtensionFileFilter;


/**
 * Customized swing utilities for iSQL-Viewer.
 * <p>
 * 
 * @author Mark A. Kobold
 * @version 1.0
 */
public final class SwingUtilities {

    public static final int MENU_SHORTCUT_MASK = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
    public static final String WINDOW_MODIFIED = "windowModified";
    
    /*
     * // see getKeyStroke (String)
     */
    private static ModifierKeyword[] modifierKeywords = {
            new ModifierKeyword("shift", InputEvent.SHIFT_MASK), new ModifierKeyword("control", InputEvent.CTRL_MASK),
            new ModifierKeyword("ctrl", InputEvent.CTRL_MASK), new ModifierKeyword("meta", MENU_SHORTCUT_MASK),
            new ModifierKeyword("alt", InputEvent.ALT_MASK), new ModifierKeyword("button1", InputEvent.BUTTON1_MASK),
            new ModifierKeyword("button2", InputEvent.BUTTON2_MASK),
            new ModifierKeyword("button3", InputEvent.BUTTON3_MASK)};

    private SwingUtilities() {

    }

    /**
     * 
     */
    public static void beep() {

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        toolkit.beep();
    }

    public static boolean isMacOS() {

        String lcOSName = System.getProperty("os.name").toLowerCase();
        return lcOSName.startsWith("mac os x");
    }

    public static void setFrameModified(RootPaneContainer frame, Boolean flag) {

        synchronized (frame) {
            JRootPane rootPane = frame.getRootPane();
            rootPane.putClientProperty(WINDOW_MODIFIED, flag);
        }
    }

    public static void setFrameModified(RootPaneContainer frame, boolean flag) {

        setFrameModified(frame, Boolean.valueOf(flag));
    }

    public static void replaceDocumentContent(Document document, String content) {

        try {
            document.remove(0, document.getLength());
            document.insertString(0, content, null);
        } catch (BadLocationException error) {

        }
    }

    public static void centerFrameOnScreen(JFrame frame) {

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        Point p = new Point((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        frame.setLocation(p);
    }

    public static Boolean isFrameModified(RootPaneContainer frame) {

        synchronized (frame) {
            JRootPane rootPane = frame.getRootPane();
            Object obj = rootPane.getClientProperty(WINDOW_MODIFIED);
            if (obj == null || !(obj instanceof Boolean)) {
                return Boolean.FALSE;
            }
            return (Boolean) obj;
        }
    }

    public static void setPreferredGeometry(JFrame frame) {

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension frameSize = frame.getPreferredSize();
        Dimension screenSize = tk.getScreenSize();

        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }

        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }

        Point p = new Point((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        Rectangle rect = new Rectangle(p, frameSize);
        frame.setBounds(rect);
    }

 

 

    /**
     * Creates a KeyStroke from a ResourceBundle string.
     * <p>
     * 
     * @see #getKeyStroke(String)
     * @param bundle to load the localized text from.
     * @param str ResourceBundle key.
     * @return KeyStroke for the resource string can be null if not a valid keystroke.
     */
    public static KeyStroke getLocalKeyStroke(ResourceBundle bundle, String str) {

        try {
            return getKeyStroke(bundle.getString(str));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Utility method for creating KeyStrokes.
     * <p>
     * Synonym for 'KeyStroke.getKeyStroke(vk, mask, false)'
     * 
     * @param VK constant for the Key
     * @param Mask standared input mask
     * @return KeyStroke based on the given parameters.
     */
    public static KeyStroke createKeyStroke(int VK, int Mask) {

        return KeyStroke.getKeyStroke(VK, Mask, false);
    }

    /**
     * Method getCaretCol.
     * 
     * @param idx
     * @param comp
     * @return int
     * @throws Exception
     */
    public static int getCaretCol(int idx, JTextComponent comp) throws BadLocationException {

        return Math.abs(Utilities.getRowStart(comp, idx) - idx);
    }

    /**
     * Method getCaretRow.
     * 
     * @param idx
     * @param comp
     * @return int
     * @throws Exception
     */
    public static int getCaretRow(int idx, JTextComponent comp) throws BadLocationException {

        return getLineOfOffset(idx, comp.getDocument());
    }

    /**
     * Consistent way to chosing multiple files to open with JFileChooser.
     * <p>
     * 
     * @see JFileChooser#setFileSelectionMode(int)
     * @see #getSystemFiles(Component, int)
     * @param owner to show the component relative to.
     * @param mode selection mode for the JFileChooser.
     * @return File[] based on the user selection can be null.
     */
    public static File[] getSystemFiles(Component owner, int mode, FileFilter[] filters) {

        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(mode);
        jfc.setFileHidingEnabled(true);
        jfc.setMultiSelectionEnabled(true);
        jfc.setAcceptAllFileFilterUsed(true);
        if (filters != null) {
            for (int i = 0; i < filters.length; i++) {
                jfc.addChoosableFileFilter(filters[i]);
            }

            if (filters.length >= 1) {
                jfc.setFileFilter(filters[0]);
            }
        }

        int result = jfc.showOpenDialog(owner);
        if (result == JFileChooser.APPROVE_OPTION) {
            return jfc.getSelectedFiles();
        }

        return new File[0];
    }

    public static File[] getSystemFiles(Component owner, int mode) {

        return getSystemFiles(owner, mode, null);
    }

    /**
     * Consistent way to chosing multiple files to save with JFileChooser.
     * <p>
     * 
     * @see #saveSystemFile(Component)
     * @param owner to show the component relative to.
     * @return File[] based on the user selection can be null.
     */
    public static File[] saveSystemFiles(Component owner) {

        JFileChooser jfc = new JFileChooser();
        jfc.setFileHidingEnabled(true);
        jfc.setMultiSelectionEnabled(true);
        int result = jfc.showSaveDialog(owner);

        if (result == JFileChooser.APPROVE_OPTION) {
            return jfc.getSelectedFiles();
        }

        return new File[0];
    }

    /**
     * Consistent way to chosing a file to open with JFileChooser.
     * <p>
     * 
     * @see JFileChooser#setFileSelectionMode(int)
     * @see #getSystemFiles(Component, int)
     * @param owner to show the component relative to.
     * @param mode selection mode for the JFileChooser.
     * @return File based on the user selection can be null.
     */
    public static File getSystemFile(Component owner, int mode, FileFilter[] filters) {

        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(mode);
        jfc.setFileHidingEnabled(true);
        jfc.setAcceptAllFileFilterUsed(true);
        if (filters != null) {
            for (int i = 0; i < filters.length; i++) {
                jfc.addChoosableFileFilter(filters[i]);
            }

            if (filters.length >= 1) {
                jfc.setFileFilter(filters[0]);
            }
        }

        int result = jfc.showOpenDialog(owner);

        if (result == JFileChooser.APPROVE_OPTION) {
            return jfc.getSelectedFile();
        }

        return null;
    }

    public static File getSystemFile(Component owner, int mode) {

        return getSystemFile(owner, mode, null);
    }

    /**
     * Consistent way to chosing a file to save to with JFileChooser.
     * <p>
     * 
     * @param owner to show the component relative to.
     * @return File based on the user selection can be null.
     */
    public static File saveSystemFile(Component owner, FileFilter[] filters) {

        JFileChooser jfc = new JFileChooser();
        jfc.setFileHidingEnabled(true);
        jfc.setAcceptAllFileFilterUsed(true);
        if (filters != null) {
            for (int i = 0; i < filters.length; i++) {
                jfc.addChoosableFileFilter(filters[i]);
            }

            if (filters.length >= 1) {
                jfc.setFileFilter(filters[0]);
            }
        }

        int result = jfc.showSaveDialog(owner);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selection = jfc.getSelectedFile();
            FileFilter filter = jfc.getFileFilter();
            if (filter != null && filter instanceof ExtensionFileFilter) {
                ExtensionFileFilter eff = (ExtensionFileFilter) filter;
                if (!filter.accept(selection)) {
                    selection = eff.applyExtension(selection);
                }
            }

            if (selection.exists()) {
                String msg = "File_Overwrite_Message" + selection.getName();
                String title = "Warning";
                int response = JOptionPane.showConfirmDialog(owner, msg, title, JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    return selection;
                }
            } else {
                try {
                    if (selection.createNewFile()) {
                        return selection;
                    }
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }

        }

        return null;
    }

    public static File saveSystemFile(Component owner) {

        return saveSystemFile(owner, null);
    }

    protected static int getLineOfOffset(int offset, Document doc) throws BadLocationException {

        if (offset < 0 || doc == null) {
            throw new BadLocationException("", -1);
        } else if (offset > doc.getLength()) {
            throw new BadLocationException("", doc.getLength() + 1);
        } else {
            Element map = doc.getDefaultRootElement();
            return map.getElementIndex(offset);
        }
    }

    /**
     * Keystroke parsing code.
     * <p>
     * This code is identical to the Java Keystroke parsing code with one small exception that it will interpet meta as
     * the the menu command mask e.g. command for Mac OS X, and CTRL for linux and windows.
     * 
     * @see KeyStroke#getKeyStroke(java.lang.String)
     */
    public static KeyStroke getKeyStroke(String s) {

        if (s == null || s.length() == 0) {
            return null;
        }
        StringTokenizer st = new StringTokenizer(s);
        String token;
        int mask = 0;
        boolean released = false;
        boolean typed = false;

        while ((token = st.nextToken()) != null) {
            int tokenMask = 0;

            /* if token matches a modifier keyword update mask and continue */

            for (int i = 0; (tokenMask == 0) && (i < modifierKeywords.length); i++) {
                tokenMask = modifierKeywords[i].getModifierMask(token);
            }

            if (tokenMask != 0) {
                mask |= tokenMask;
                continue;
            }

            if (token.equals("released")) {
                released = true;
                continue;
            }
            if (token.equals("pressed")) {
                continue;
            }
            if (token.equals("typed")) {
                typed = true;
                continue;
            }

            if (typed) {
                if (token.length() != 1) {
                    // bogus format, should really throw.
                    return null;
                }
                return KeyStroke.getKeyStroke(token.charAt(0));
            }

            /* otherwise token is the keycode name less the "VK_" prefix */

            String keycodeName = "VK_" + token;

            int keycode;
            try {
                keycode = KeyEvent.class.getField(keycodeName).getInt(KeyEvent.class);
            } catch (Throwable t) {
                return null;
            }

            return KeyStroke.getKeyStroke(keycode, mask, released);
        }
        return null;
    }

    private static URL getResourceURL(Class owner, String item) {

        return owner.getResource("/org/isqlviewer/resource/".concat(item));
    }

    private static class ModifierKeyword {

        final String keyword;
        final int mask;

        ModifierKeyword(String keyword, int mask) {

            this.keyword = keyword;
            this.mask = mask;
        }

        int getModifierMask(String s) {

            return (s.equals(keyword)) ? mask : 0;
        }
    }

}
