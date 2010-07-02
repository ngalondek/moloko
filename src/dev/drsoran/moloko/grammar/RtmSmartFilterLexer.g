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
		
	private static final String unquotify( String input )
	{
		return input.replaceAll( "(\"|')", "" );
	}
	
	@SuppressWarnings( "unused" )
	private static final String quotify( String input )
	{
		return new StringBuffer( "'").append( input ).append( "'").toString();
	}
	
	private static final String firstCharOf( String input )
	{
		return input.length() > 0 ? input.substring( 0, 1 ) : "";
	}	
	
	private void equalsStringParam( String param )
	{
		result.append( " = '" );
		result.append( unquotify( param ) );
		result.append( "'" );
	}
	
	private void equalsIntParam( String param )
	{
		result.append( " = " );
		result.append( unquotify( param ) );
	}		
	
	public String getResult()
	{
		if ( result.length() == 0 )
         while ( nextToken() != Token.EOF_TOKEN )
         {
         }
      
      return result.toString();
	}
}

/** Operators **/

OP_LIST     :  'list:' ( s=STRING | s=Q_STRING )
					{
						result.append( Tasks.LIST_NAME );
						equalsStringParam( $s.getText() );
					};

OP_PRIORITY :  'priority:' s=STRING
					{
						result.append( Tasks.PRIORITY );
						equalsStringParam( firstCharOf( unquotify( $s.getText() ) ) );
					};

OP_STATUS 	:  'status:' 
					{
						result.append( Tasks.COMPLETED_DATE );
					}
					(  
						'completed'
						{
							result.append(" IS NOT NULL");
						}
						|
						'incomplete'
						{
							result.append(" IS NULL");
						}						
				   );
				   
// OP_TAG

// OP_TAG_CONTAINS

// OP_IS_TAGGED

OP_LOCATION : 'location:' ( s=STRING | s=Q_STRING )
					{
						result.append( Tasks.LOCATION_NAME );
						equalsStringParam( $s.getText() );
					};
					
// OP_LOCATED_WITHIN

OP_ISLOCATED : 'isLocated:'
					{
						result.append( Tasks.LOCATION_ID );
					}
					(
						TRUE
						{
							result.append(" IS NOT NULL");	
						}
						|
						FALSE
						{
							result.append(" IS NULL");
						}
					);

// OP_IS_REPEATING

OP_NAME		:  'name:' ( s=STRING | s=Q_STRING )
					{
						result.append( Tasks.TASKSERIES_NAME );
						equalsStringParam( $s.getText() );
					};
					
// OP_NOTE_CONTAINS

// OP_HAS_NOTES

// OP_DUE

// OP_DUE_AFTER

// OP_DUE_WITHIN

// OP_COMPLETED

// OP_COMPLETED_BEFORE

// OP_COMPLETED_AFTER

// OP_COMPLETED_WITHIN

// OP_ADDED

// OP_ADDED_BEFORE

// OP_ADDED_AFTER

// OP_ADDED_WITHIN

// OP_TIME_ESTIMATE

OP_POSTPONED : 'postponed:'
					{
						result.append( Tasks.POSTPONED );
					}
					( 
					  s=STRING
					  {
					  		equalsIntParam( $s.getText() );
					  }
					  | 
					  s=Q_STRING 
					  {
					  		result.append( unquotify( $s.getText() ) );
					  }
					);

// OP_IS_SHARED

// OP_SHARED_WITH

// OP_IS_RECEIVED

// OP_TO

// OP_FROM

// OP_INCLUDE_ARCHIVED


/** other tokens **/

fragment
TRUE			: 'true';

fragment
FALSE			: 'false';

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
