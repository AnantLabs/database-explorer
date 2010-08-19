/**
 * 
 */
package com.gs.dbex.integration.impl.castor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.ValidationException;
import org.xml.sax.InputSource;

import com.gs.dbex.integration.XmlReaderIntegration;

/**
 * @author sabuj.das
 * 
 */
public class CastorXmlReaderIntegration implements XmlReaderIntegration {

	public <T> T readByMapping(File inputXml, InputStream mappingStream)
			throws IOException, MappingException, MarshalException,
			ValidationException {
		T data = null;
		Mapping mapping = new Mapping();
		InputSource iso = new InputSource();
		iso.setByteStream(mappingStream);
		mapping.loadMapping(iso);
		Reader reader = new FileReader(inputXml);
		Unmarshaller unmarshaller = new Unmarshaller();
		unmarshaller.setMapping(mapping);

		data = (T) unmarshaller.unmarshal(reader);

		return data;
	}

	@Override
	public <T> T readByMapping(File inputXml, File mappingFile)
			throws MarshalException, ValidationException,
			FileNotFoundException, IOException, MappingException {
		return (T) readByMapping(inputXml, new FileInputStream(mappingFile));
	}

}
