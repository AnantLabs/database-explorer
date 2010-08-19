/**
 * 
 */
package com.gs.dbex.core.metadata.enums;

/**
 * @author Green Moon
 * 
 */
public enum ColumnMetaDataEnum {

	/**
	 * TABLE_CAT String => table catalog (may be null)
	 */
	TABLE_CAT("TABLE_CAT"),
	/**
	 * TABLE_SCHEM String => table schema (may be null)
	 */
	TABLE_SCHEM("TABLE_SCHEM"),
	/**
	 * TABLE_NAME String => table name
	 */
	TABLE_NAME("TABLE_NAME"),
	/**
	 * COLUMN_NAME String => column name
	 */
	COLUMN_NAME("COLUMN_NAME"),
	/**
	 * DATA_TYPE int => SQL type from java.sql.Types
	 */
	DATA_TYPE("DATA_TYPE"),
	/**
	 * TYPE_NAME String => Data source dependent type name, for a UDT the type
	 * name is fully qualified
	 */
	TYPE_NAME("TYPE_NAME"),
	/**
	 * COLUMN_SIZE int => column size.<br>
	 * The COLUMN_SIZE column the specified column size for the given column.
	 * For numeric data, this is the maximum precision. For character data, this
	 * is the length in characters. For datetime datatypes, this is the length
	 * in characters of the String representation (assuming the maximum allowed
	 * precision of the fractional seconds component). For binary data, this is
	 * the length in bytes. For the ROWID datatype, this is the length in bytes.
	 * Null is returned for data types where the column size is not applicable.
	 */
	COLUMN_SIZE("COLUMN_SIZE"),
	/**
	 * BUFFER_LENGTH is not used.
	 */
	BUFFER_LENGTH("BUFFER_LENGTH"),
	/**
	 * DECIMAL_DIGITS int => the number of fractional digits. Null is returned
	 * for data types where DECIMAL_DIGITS is not applicable.
	 */
	DECIMAL_DIGITS("DECIMAL_DIGITS"),
	/**
	 * NUM_PREC_RADIX int => Radix (typically either 10 or 2)
	 */
	NUM_PREC_RADIX("NUM_PREC_RADIX"),
	/**
	 * NULLABLE int => is NULL allowed.
	 * <ul>
	 * <li>columnNoNulls - might not allow NULL values</li>
	 * <li>columnNullable - definitely allows NULL values</li>
	 * <li>columnNullableUnknown - nullability unknown</li>
	 * </ul>
	 */
	NULLABLE("NULLABLE"),
	/**
	 * REMARKS String => comment describing column (may be null)
	 */
	REMARKS("REMARKS"),
	/**
	 * COLUMN_DEF String => default value for the column, which should be
	 * interpreted as a string when the value is enclosed in single quotes (may
	 * be null)
	 */
	COLUMN_DEF("COLUMN_DEF"),
	/**
	 * SQL_DATA_TYPE int => unused
	 */
	SQL_DATA_TYPE("SQL_DATA_TYPE"),
	/**
	 * SQL_DATETIME_SUB int => unused
	 */
	SQL_DATETIME_SUB("SQL_DATETIME_SUB"),
	/**
	 * CHAR_OCTET_LENGTH int => for char types the maximum number of bytes in
	 * the column
	 */
	CHAR_OCTET_LENGTH("CHAR_OCTET_LENGTH"),
	/**
	 * ORDINAL_POSITION int => index of column in table (starting at 1)
	 */
	ORDINAL_POSITION("ORDINAL_POSITION"),
	/**
	 * IS_NULLABLE String => ISO rules are used to determine the nullability for
	 * a column.
	 * <ul>
	 * <li>YES --- if the parameter can include NULLs</li>
	 * <li>NO --- if the parameter cannot include NULLs</li>
	 * <li>empty string --- if the nullability for the parameter is unknown</li>
	 * </ul>
	 */
	IS_NULLABLE("IS_NULLABLE"),
	IS_NULLABLE_YES("YES"),
	IS_NULLABLE_NO("NO"),
	/**
	 * IS_AUTOINCREMENT String => Indicates whether this column is auto
	 * incremented
	 * <ul>
	 * <li>YES --- if the column is auto incremented</li>
	 * <li>NO --- if the column is not auto incremented</li>
	 * <li>empty string --- if it cannot be determined whether the column is
	 * auto incremented parameter is unknown</li>
	 * </ul>
	 */
	IS_AUTOINCREMENT("IS_AUTOINCREMENT"),
	IS_AUTOINCREMENT_YES("YES"),
	IS_AUTOINCREMENT_NO("NO");

	private String code;

	private ColumnMetaDataEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
