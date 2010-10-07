/**
 * 
 */
package com.gs.dbex.model.converter;

import org.apache.log4j.Logger;

import com.gs.dbex.model.User;
import com.gs.dbex.model.vo.UserVO;

/**
 * @author Sabuj Das
 * 
 */
public class UserVoConverter {

	private static final Logger logger = Logger
			.getLogger(UserVoConverter.class);

	public static UserVO convertModelToVO(User userModel) {
		if (logger.isDebugEnabled()) {
			logger.debug("ENTER::- convertModelToVO()");
		}
		if (null == userModel)
			return null;
		UserVO userVO = new UserVO();
		userVO.setUserId(userModel.getUserId());
		userVO.setUserName(userModel.getUserName());
		userVO.setPassword(userModel.getPassword());
		userVO.setEmailAddress(userModel.getEmailAddress());
		userVO.setFullName(userModel.getFullName());
		userVO.setConnectionPropertiesVOs(ConnectionPropertiesVOConverter
				.convertModelsToVOs(userModel.getConnectionProperties()));
		if (logger.isDebugEnabled()) {
			logger.debug("EXIT::- convertModelToVO()");
		}
		return userVO;
	}
	
	public static User convertVoToModel(UserVO userVO){
		if (logger.isDebugEnabled()) {
			logger.debug("ENTER::- convertVoToModel()");
		}
		if (null == userVO)
			return null;
		User userModel = new User();
		Long id = userVO.getUserId();
		if(null != id){
			userModel.setUserId((id.longValue()==0L) ? null : id);
		} else{
			userModel.setUserId(null);
		}
		userModel.setUserName(userVO.getUserName());
		userModel.setPassword(userVO.getPassword());
		userModel.setEmailAddress(userVO.getEmailAddress());
		userModel.setFullName(userVO.getFullName());
		
		if (logger.isDebugEnabled()) {
			logger.debug("EXIT::- convertVoToModel()");
		}
		return userModel;
	}

}
