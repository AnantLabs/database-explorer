package com.gs.dbex.model.sql;

import com.gs.dbex.common.enums.QueryTypeEnum;
import com.gs.utils.text.StringUtil;

/**
 * @author sabuj.das
 * 
 */
public class SqlQuery {
	private QueryTypeEnum queryType;
	private String query;

	public SqlQuery(String query) {
		this.query = query;
		String w = StringUtil.getFirstWord(query);
		queryType = QueryTypeEnum.getQueryTypeEnum(w);
	}

	public QueryTypeEnum getQueryType() {
		return queryType;
	}

	public void setQueryType(QueryTypeEnum queryType) {
		this.queryType = queryType;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

}
