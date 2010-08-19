package com.gs.dbex.core.metadata.enums;

public enum SchemaMetadataEnum {

	TABLE_SCHEM("TABLE_SCHEM"),
	TABLE_CATALOG("TABLE_CATALOG");
	
	private String code;

	private SchemaMetadataEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
