package com.gs.dbex.application.command
{
	import com.gs.dbex.application.delegate.QueryRunnerDelegate;
	import com.gs.dbex.application.event.QueryRunnerEvent;
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
		}
		
	}
}