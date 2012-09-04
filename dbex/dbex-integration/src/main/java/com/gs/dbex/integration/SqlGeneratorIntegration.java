/* ******************************************************************************
 * 	
 * 	Name	: SqlGeneratorIntegration.java
 * 	Type	: com.gs.dbex.integration.SqlGeneratorIntegration
 * 
 * 	Created	: Mar 25, 2012
 * 	
 * 	Author	: Sabuj Das [ mailto::sabuj.das@gmail.com ]
 * 
 * -----------------------------------------------------------------------------*
 * 																				*
 * Copyright © Sabuj Das 2010 All Rights Reserved. 								*
 * <br/>No part of this document may be reproduced without written 				*
 * consent from the author.														*
 * 																				*
 ****************************************************************************** */

package com.gs.dbex.integration;

import java.util.Map;


/**
 * @author sabuj.das
 * @MailTo sabuj.das@gmail.com
 * 
 */
public interface SqlGeneratorIntegration {

	String populateInsertValues(Map<String, Object> values);
	
}
