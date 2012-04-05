/**
 * 
 */
package com.gs.dbex.common.enums;


/**
 * @author sabuj.das
 *
 */
public enum QueryTypeEnum {
	SELECT("SELECT"), 
	UPDATE("UPDATE"), 
	INSERT("INSERT"), 
	CREATE("CREATE"), 
	ALTER("ALTER"),
	TRUNCATE("TRUNCATE"),
	DROP("DROP"),
	COMMENT("COMMENT");
	
	
	private String code;

	private QueryTypeEnum(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	
	public static QueryTypeEnum getQueryTypeEnum(String value){
		if(SELECT.getCode().equalsIgnoreCase(value)){
			return SELECT;
		}
		else if(UPDATE.getCode().equalsIgnoreCase(value)){
			return UPDATE;
		}
		else if(INSERT.getCode().equalsIgnoreCase(value)){
			return INSERT;
		}
		else if(CREATE.getCode().equalsIgnoreCase(value)){
			return CREATE;
		}
		else if(ALTER.getCode().equalsIgnoreCase(value)){
			return ALTER;
		}
		else if(TRUNCATE.getCode().equalsIgnoreCase(value)){
			return TRUNCATE;
		}
		else if(DROP.getCode().equalsIgnoreCase(value)){
			return DROP;
		}
		else if(COMMENT.getCode().equalsIgnoreCase(value)){
			return COMMENT;
		}
		return SELECT;
	}
}
