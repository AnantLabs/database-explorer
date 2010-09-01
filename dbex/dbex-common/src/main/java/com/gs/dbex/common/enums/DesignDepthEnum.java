/**
 * 
 */
package com.gs.dbex.common.enums;

/**
 * @author Sabuj Das
 *
 */
public enum DesignDepthEnum {

	COMPLETE_SHAPE("COMPLETE_SHAPE", "COMPLETE_SHAPE"),
	NAME_ONLY("NAME_ONLY", "NAME_ONLY"),
	IMPACTED_RELATION_ONLY("IMPACTED_RELATION_ONLY", "IMPACTED_RELATION_ONLY");
	
	private final String code;
	private final String descriprion;
	
	private DesignDepthEnum(String code, String descriprion) {
		this.code = code;
		this.descriprion = descriprion;
	}

	public String getCode() {
		return code;
	}

	public String getDescriprion() {
		return descriprion;
	}

	public static DesignDepthEnum getDesignDepthEnum(String code){
		if(COMPLETE_SHAPE.getCode().equals(code)){
			return COMPLETE_SHAPE;
		} else if(IMPACTED_RELATION_ONLY.getCode().equals(code)){
			return IMPACTED_RELATION_ONLY;
		}
		return NAME_ONLY;
	}
	
}
