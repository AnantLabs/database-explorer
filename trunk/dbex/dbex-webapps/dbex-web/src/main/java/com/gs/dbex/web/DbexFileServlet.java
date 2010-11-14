package com.gs.dbex.web;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.gs.dbex.common.exception.DbexException;
import com.gs.dbex.integration.xmlbeans.ConnectionPropertiesXmlTransformer;
import com.gs.dbex.model.cfg.ConnectionProperties;
import com.gs.dbex.model.converter.ConnectionPropertiesVOConverter;
import com.gs.dbex.model.vo.cfg.ConnectionPropertiesVO;
import com.gs.dbex.serviceprovider.DatabaseConnectionDelegate;
import com.gs.utils.io.ByteArrayInput;

/**
 * @author Sabuj Das
 *
 */
public class DbexFileServlet extends HttpServlet {

	private static final Logger logger = Logger.getLogger(DbexFileServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("Handeling request.");
		// Create a factory for disk-based file items
		FileItemFactory factory = new DiskFileItemFactory();
		
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		// Parse the request
		try {
			List items = upload.parseRequest(request);
			// Process the uploaded items
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
			    FileItem item = (FileItem) iter.next();
                       
			    if (!item.isFormField()) {
			        try {
			        	List<ConnectionProperties> connectionPropsList = processUploadedFile(item);
			        	if(null != connectionPropsList && connectionPropsList.size() > 0){
			        		List<ConnectionPropertiesVO> connectionPropsVOList = ConnectionPropertiesVOConverter.convertModelsToVOList(connectionPropsList);
			        		DatabaseConnectionDelegate connectionDelegate = 
			        			(DatabaseConnectionDelegate)WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext())
			        				.getBean("databaseConnectionDelegate");
			        		connectionPropsVOList = connectionDelegate.saveAllConnectionProperties(connectionPropsVOList);
			        		String responseText = null;
				        	if(null != connectionPropsVOList && connectionPropsVOList.size() > 0){
								responseText = "" + connectionPropsVOList.size();
							}
							response.setContentType("text");
							response.getOutputStream().print(responseText);
			        	}
						response.getOutputStream().flush();
					} catch (DbexException e) {
						logger.error("Error during doPost() in UploadServlet ",e);
						throw new IOException();
					}
			       
			    }
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	}
	
	private List<ConnectionProperties> processUploadedFile(FileItem item) throws DbexException{
		//Process a file upload
	    InputStream uploadedStream;
	    List<ConnectionProperties> list = new ArrayList<ConnectionProperties>();
		try {
			uploadedStream = item.getInputStream();
			byte [] fileContent = new byte[uploadedStream.available()];
			uploadedStream.read(fileContent);
			ByteArrayInput input = new ByteArrayInput(fileContent);
			list = new ConnectionPropertiesXmlTransformer().readAllConnectionProperties(new String(fileContent));
		    uploadedStream.close();
		} catch (IOException e) {
			logger.error("Error during processUploadedFile() in UploadServlet ",e);
			throw new DbexException(e);
		}
		return list;
	}

	
	
}
