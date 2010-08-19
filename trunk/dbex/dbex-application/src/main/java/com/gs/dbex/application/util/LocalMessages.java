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

import java.text.MessageFormat;
import java.util.Hashtable;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Create localized formated/unformatted messages from ResourceBundles.
 * <p>
 * This class is mainly designed to take alot of the grunt work for making an application localized while still taking
 * advantage of the MessageFormat object as much as possible.
 * <p>
 * This object implements a caching mechanisim at a global level as well as local level so that effeciency is maximized
 * for resuing message formats whenever possible.
 * 
 * @author Markus A. Kobold.
 * @version 1.0
 */
public final class LocalMessages {

    private static Hashtable<String, ResourceBundle> bundleCache = new Hashtable<String, ResourceBundle>();
    private static Hashtable<String, Hashtable<String, MessageFormat>> formatCache = new Hashtable<String, Hashtable<String, MessageFormat>>();
    private static Locale systemLocale;
    private ResourceBundle bundle;
    private Hashtable<String, MessageFormat> compiledFormats;
    private Locale preferredLocale;
    private String bundleIdentifier;
    private ClassLoader classLoader;
    static {
        setSystemLocale(Locale.getDefault());
    }

    public static void setSystemLocale(Locale locale) {

        synchronized (LocalMessages.class) {
            systemLocale = locale;
            if (bundleCache != null) {
                bundleCache.clear();
            }
            if (formatCache != null) {
                formatCache.clear();
            }
            bundleCache = new Hashtable<String, ResourceBundle>();
            formatCache = new Hashtable<String, Hashtable<String, MessageFormat>>();
        }
    }

    public LocalMessages(String resourceName, Class clazz) {

        this(resourceName, systemLocale, clazz.getClassLoader());
    }

    public LocalMessages(String resourceName) {

        this(resourceName, systemLocale, Thread.currentThread().getContextClassLoader());
    }

    public LocalMessages(String resourceName, ClassLoader classLoader) {

        this(resourceName, systemLocale, classLoader);
    }

    /**
     * Creates a new Message object with a given resource bundle name.
     * <p>
     * This will initialize the local caches and detect if the ResourceBundle has already been loaded. If a cached
     * ResourceBundle is detected all cached MessageFormats are used as well.
     * <p>
     * The resourceName parameters will have the text '.properties' appended to be used as a fulle bundle name.
     * <p>
     * For instance: <br/><code>
     *   new Message(&quot;com.solutionary.util.resources.LocalMessages&quot;);     * 
     * </code> There
     * would have to be a resource bundle located in '/com/solutionary/util/resource/LocalMessages.properties'
     * 
     * @param resourceName common name for the resource bundle.
     * @param preferredLocale the default locale to use for this bundle.
     */

    public LocalMessages(String resourceName, Locale preferredLocale) {

        this(resourceName, preferredLocale, Thread.currentThread().getContextClassLoader());

    }

    public LocalMessages(String resourceName, Locale preferredLocale, ClassLoader classLoader) {

        synchronized (LocalMessages.class) {
            this.preferredLocale = preferredLocale;
            this.classLoader = classLoader;
            bundleIdentifier = resourceName;
            ResourceBundle rb = bundleCache.get(getFullBundleIdentifier(bundleIdentifier));
            if (rb == null) {
                init();
            } else {
                bundle = rb;
                compiledFormats = formatCache.get(getFullBundleIdentifier(bundleIdentifier));
            }
        }
    }

    /**
     * Gets a localized message that does not require formatting.
     * <p>
     * 
     * @param message the bundle key to get the localized message.
     * @return localized message.
     */
    public String format(String message) {

        return getMessage(message);
    }

    /**
     * Formats a message from the local bundle with variant number of arguments.
     * <p>
     * This is the heart of this object for formatting localized messages. The locales are first synchronized to ensure
     * that there is no contention between differnt locales.
     * <p>
     * Afterwards the MessgeFormat objects that have been pre-compiled in a matter of speaking are queried based on the
     * message key, if a cached format is found then that instance is used, otherwise a new one is created to format the
     * local message and return it and then cache the format object.
     * 
     * @param message resource bundle key to format.
     * @param args list of object to be used in the formatting pattern.
     * @return formatted localized string.
     * @see MessageFormat#format(java.lang.String, java.lang.Object[])
     */
    public String format(String message, Object... args) {

        if (preferredLocale != systemLocale) {
            synchronized (LocalMessages.class) {
                init();
            }
        }
        MessageFormat mf;
        String msg;
        try {
            mf = compiledFormats.get(message);
            if (mf == null) {
                synchronized (compiledFormats) {
                    try {
                        msg = bundle.getString(message);
                    } catch (MissingResourceException except) {
                        return '!' + message + '!';
                    }
                    mf = new MessageFormat(msg);
                    compiledFormats.put(message, mf);
                }
            }
            return mf.format(args);
        } catch (Exception except) {
            return "An internal error occured while processing message " + message;
        }
    }

    /**
     * Gets a localized message that does not require formatting.
     * <p>
     * 
     * @param message the bundle key to get the localized message.
     * @return localized message.
     */
    public String getMessage(String message) {

        if (preferredLocale != systemLocale) {
            synchronized (LocalMessages.class) {
                init();
            }
        }

        try {
            return bundle.getString(message);
        } catch (MissingResourceException except) {
            return message;
        }
    }

    /**
     * This will initialize caches and actually attempt to load the bundle.
     */
    protected void init() {

        try {
            if (preferredLocale == null) {
                bundle = ResourceBundle.getBundle(bundleIdentifier, systemLocale, classLoader);
            } else {
                bundle = ResourceBundle.getBundle(bundleIdentifier, preferredLocale, classLoader);
            }
        } catch (Exception except) {
            throw new MissingResourceException(bundleIdentifier, bundleIdentifier + "_" + preferredLocale, "");
        }
        bundleIdentifier = getFullBundleIdentifier(bundleIdentifier);
        compiledFormats = new Hashtable<String, MessageFormat>();
        bundleCache.put(bundleIdentifier, bundle);
        formatCache.put(bundleIdentifier, compiledFormats);
    }

    // this is a copy from the java.util.ResourceBundle. //
    private String getFullBundleIdentifier(final String baseName) {

        String localeSuffix = preferredLocale.toString();
        String bundleId = baseName;
        if (localeSuffix.length() > 0) {
            bundleId += "_" + localeSuffix;
        } else if (preferredLocale.getVariant().length() > 0) {
            bundleId += "___" + preferredLocale.getVariant();
        }
        return bundleId;
    }
}
