/**
 * 
 */
package com.gs.dbex.integration;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;

/**
 * @author sabuj.das
 * 
 */
public interface XmlReaderIntegration {

	public <T> T readByMapping(File inputXml, InputStream mappingStream)
			throws IOException, MappingException, MarshalException,
			ValidationException;

	public <T> T readByMapping(File inputXml, File mappingFile)
			throws MarshalException, ValidationException,
			FileNotFoundException, IOException, MappingException;

}
