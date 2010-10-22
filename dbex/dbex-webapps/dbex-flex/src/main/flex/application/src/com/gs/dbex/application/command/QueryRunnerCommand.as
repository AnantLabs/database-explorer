package com.gs.dbex.application.command
{
	import com.gs.dbex.application.delegate.QueryRunnerDelegate;
	import com.gs.dbex.application.event.QueryRunnerEvent;
	import com.gs.dbex.application.responder.CurrentPageTableDataResponder;
	import com.gs.dbex.application.responder.FilteredPaginationTableDataResponder;
	import com.gs.dbex.application.responder.PaginationTableDataResponder;
	import com.gs.dbex.application.responder.SingleQueryRunnerResponder;
	import com.gs.dbex.common.business.BaseResponder;
	import com.gs.dbex.common.command.BaseCommand;
	import com.gs.dbex.common.event.DbexBaseEvent;
	import com.gs.dbex.common.factory.DbexResponderFactory;

	public class QueryRunnerCommand implements BaseCommand
	{
		
		public function QueryRunnerCommand()
		{
		}

		public function execute(event:DbexBaseEvent):void
		{
			var responder : BaseResponder = null;
			var queryRunnerEvent:QueryRunnerEvent = event as QueryRunnerEvent;
			if(QueryRunnerEvent.RUN_SINGLE_SQL_EVENT == queryRunnerEvent.type){
				responder = DbexResponderFactory.getInstance().getResponder(SingleQueryRunnerResponder);
				var delegate:QueryRunnerDelegate = new QueryRunnerDelegate(responder);
				delegate.runSingleQuery(queryRunnerEvent.singleSql, queryRunnerEvent.connectionProperties.connectionName);
			} 
			else if(QueryRunnerEvent.GET_PAGINATED_TABLE_DATA_EVENT == queryRunnerEvent.type){
				responder = DbexResponderFactory.getInstance().getResponder(PaginationTableDataResponder);
				var delegate:QueryRunnerDelegate = new QueryRunnerDelegate(responder);
				delegate.getPaginatedTableData(queryRunnerEvent.paginationResult, queryRunnerEvent.selectedTable, queryRunnerEvent.connectionProperties.connectionName);
			} 
			else if(QueryRunnerEvent.GET_CURRENT_PAGE_EVENT == queryRunnerEvent.type){
				responder = DbexResponderFactory.getInstance().getResponder(CurrentPageTableDataResponder);
				var delegate:QueryRunnerDelegate = new QueryRunnerDelegate(responder);
				delegate.getPaginatedTableData(queryRunnerEvent.paginationResult, queryRunnerEvent.selectedTable, queryRunnerEvent.connectionProperties.connectionName);
			} 
			else if(QueryRunnerEvent.GET_FILTERED_PAGINATED_TABLE_DATA_EVENT == queryRunnerEvent.type){
				responder = DbexResponderFactory.getInstance().getResponder(FilteredPaginationTableDataResponder);
				var delegate:QueryRunnerDelegate = new QueryRunnerDelegate(responder);
				delegate.getFilteredPaginatedTableData(queryRunnerEvent.selectedTable, queryRunnerEvent.connectionProperties.connectionName, queryRunnerEvent.filterSubQuery);
			} 
		}
		
	}
}