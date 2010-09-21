package com.gs.dbex.integration.impl;

import java.io.StringWriter;

import org.apache.commons.sql.ddl.DDLBuilder;
import org.apache.commons.sql.ddl.DDLBuilderFactory;
import org.apache.commons.sql.util.JdbcSupport;

import com.gs.dbex.integration.DDLGenerationIntegration;

/**
 * @author Sabuj Das
 *
 */
public abstract class DDLGenerationIntegrationImpl extends JdbcSupport implements DDLGenerationIntegration{

	public String ddl(){
		 

		    DDLBuilder builder = DDLBuilderFactory.newDDLBuilder(provider, version);

		    if (builder == null) {
		      throw new Exception("Database provider/version unknown: " + provider + "/" + version);
		    }

		    StringWriter buffer = new StringWriter();
		    builder.setWriter(buffer);
		    if (create)
		      builder.createDatabase(database, false);
		    else {
		      builder.dropDatabase(database);
		    }
		    buffer.flush();
		    System.out.println(buffer.toString());
	}
}
