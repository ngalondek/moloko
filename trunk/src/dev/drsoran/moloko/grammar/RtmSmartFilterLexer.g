lexer grammar RtmSmartFilterLexer;

options
{
	language=Java;
}

@header
{
	package dev.drsoran.moloko.grammar;
	
	import dev.drsoran.provider.Rtm.Tasks;
}

@members
{
	private final StringBuffer result = new StringBuffer();
		
	private String unquotify( String input )
	{
		return input.replaceAll( "(\"|')", "" );
	}
	
	private void equalsStringParam( String param )
	{
		result.append( " = '" );
		result.append( unquotify( param ) );
		result.append( "'" );
	}
	
	public String getResult()
	{
		return result.toString();
	}
}

OP_LIST 		: 'list:' ( s=STRING | s=Q_STRING )
{
	result.append( Tasks.LIST_NAME );
	equalsStringParam( $s.getText() );
};

OP_PRIORITY : 'priority:' s=STRING
{
	result.append( "priority = " + unquotify( $s.getText() ) );
};

OP_NAME		: 'name:' ( s=STRING | s=Q_STRING )
{
	result.append( Tasks.TASKSERIES_NAME );
	equalsStringParam( $s.getText() );
};

L_PARENTH   : '('
{
	result.append( "( " );
};

R_PARENTH   : ')'
{
	result.append( " )" );
};

AND 		   : 'AND'
{
	result.append( " AND " );
};

OR  			: 'OR'
{
	result.append( " OR " );
};

NOT 			: 'NOT'
{
	result.append( " NOT " );
};

WS  			:   ( ' '
					  | '\t'
			        | '\r'
			        | '\n'
			        ) { skip(); }
			    ;

fragment
Q_STRING 	: '"' ~('"')* '"';

fragment
STRING 		: ~('"' | ' ' | '(' | ')')+;
