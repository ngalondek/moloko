lexer grammar RtmSmartFilterLexer;

options
{
	language=Java;
}

@header
{
	package dev.drsoran.moloko.grammar;
	
	import dev.drsoran.provider.Rtm.Tasks;
	import dev.drsoran.provider.Rtm.Tags;
	
	import java.util.Calendar;

	import org.antlr.runtime.CommonTokenStream;
	import org.antlr.runtime.RecognitionException;
	
	import dev.drsoran.moloko.grammar.TimeSpecLexer;
	import dev.drsoran.moloko.grammar.TimeSpecParser;
	
	import dev.drsoran.moloko.util.ANTLRNoCaseStringStream;
}

@members
{
	public final static String OP_DEFAULT = "name:";
	
	private final static String TAGS_QUERY_PREFIX
   	= "(SELECT "  + Tags.TASKSERIES_ID + " FROM " + Tags.PATH
   	  + " WHERE " + Tags.TASKSERIES_ID + " = " + Tasks.PATH + "." + Tasks._ID;
   
	private final StringBuffer result = new StringBuffer();



	private static Calendar parseDateTimeSpec( String spec )
	{
	   final TimeSpecLexer     lexer       = new TimeSpecLexer( new ANTLRNoCaseStringStream( spec ) );
      final CommonTokenStream antlrTokens = new CommonTokenStream( lexer );
      final TimeSpecParser    parser      = new TimeSpecParser( antlrTokens );
      
      final Calendar cal = TimeSpecParser.getLocalizedCalendar();
      
      // first try to parse time
      try
      {
         parser.time_spec( cal );
         return cal;
      }
      catch( RecognitionException e )
      {
      	// try to parse date and time
      	try
      	{
      		// TODO: Read the dayFirst parameter from settings and
      		// remove the true.
            parser.parseDateTime( cal, true );
            return cal;
         }
         catch( RecognitionException e1 )
         {
            return null;
         }
      }
	}



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



	private void containsStringParam( String param )
	{
		result.append( " like '\%" );
		result.append( unquotify( param ) );
		result.append( "\%'" );
	}


	
	private void equalsIntParam( String param )
	{
		result.append( " = " );
		result.append( unquotify( param ) );
	}		



   private void equalsTimeParam( String column, String param )
   {
   	final Calendar cal = parseDateTimeSpec( unquotify( param ) );
						
		if ( cal != null )
		{
			// Check if we have an explicit time
		   // given.
		   if ( cal.isSet( Calendar.HOUR_OF_DAY ) )
		   {
		      result.append( column );
            result.append( " == " );
            result.append( cal.getTimeInMillis() );
		   }
		   else
		   {
				result.append( column );
				result.append( " >= " );
				result.append( cal.getTimeInMillis() );
				result.append( " AND " );
				
				cal.roll( Calendar.DAY_OF_YEAR, true );
						
				result.append( column );
				result.append( " < " );
				result.append( cal.getTimeInMillis() );
		   }
      }
   }



	private void differsTimeParam( String column, String param, boolean before )
   {
   	final Calendar cal = parseDateTimeSpec( unquotify( param ) );
						
		if ( cal != null )
		{
			result.append( column );
         result.append( ( before ) ? " < " : " > " );
         result.append( cal.getTimeInMillis() );
		}
   }



	public String getResult() throws RecognitionException
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
				   
OP_TAG      : 'tag:' ( s=STRING | s=Q_STRING )
				  {
				     result.append( TAGS_QUERY_PREFIX )
				           .append( " AND " )
				           .append( Tags.TAG );
					  equalsStringParam( $s.getText() );
					  result.append( ")" );
				  };

OP_TAG_CONTAINS : 'tagContains:' ( s=STRING | s=Q_STRING )
					   {
					     result.append( TAGS_QUERY_PREFIX )
					           .append( " AND " )
					           .append( Tags.TAG );
						  containsStringParam( $s.getText() );
						  result.append( ")" );
				      };

OP_IS_TAGGED    : 'isTagged:'
						(
							TRUE
							{
							   result.append( TAGS_QUERY_PREFIX );
								result.append( ")" );	
							}
							|
							FALSE
							{
							   result.append( " NOT EXISTS " );
							   result.append( TAGS_QUERY_PREFIX );
								result.append( ")" );	
							}
						); 

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
						containsStringParam( $s.getText() );
					};
					
// OP_NOTE_CONTAINS

// OP_HAS_NOTES

OP_DUE      :  'due:' ( s=STRING | s=Q_STRING )
					{
						equalsTimeParam( Tasks.DUE_DATE, $s.getText() );
					};

OP_DUE_AFTER : 'dueafter:' ( s=STRING | s=Q_STRING )
					{
						differsTimeParam( Tasks.DUE_DATE, $s.getText(), false );
					};
					
OP_DUE_BEFORE : 'duebefore:' ( s=STRING | s=Q_STRING )
					{
						differsTimeParam( Tasks.DUE_DATE, $s.getText(), true );
					};

// OP_DUE_WITHIN

OP_COMPLETED : 'completed:' ( s=STRING | s=Q_STRING )
					{
						equalsTimeParam( Tasks.COMPLETED_DATE, $s.getText() );
					};

OP_COMPLETED_BEFORE : 'completedbefore:' ( s=STRING | s=Q_STRING )
					{
						differsTimeParam( Tasks.COMPLETED_DATE, $s.getText(), true );
					};

OP_COMPLETED_AFTER : 'completedafter:' ( s=STRING | s=Q_STRING )
					{
						differsTimeParam( Tasks.COMPLETED_DATE, $s.getText(), false );
					};

// OP_COMPLETED_WITHIN

OP_ADDED     : 'added:' ( s=STRING | s=Q_STRING )
					{
						equalsTimeParam( Tasks.ADDED_DATE, $s.getText() );
					};

OP_ADDED_BEFORE : 'addedbefore:' ( s=STRING | s=Q_STRING )
					 {
						differsTimeParam( Tasks.ADDED_DATE, $s.getText(), true );
					 };

OP_ADDED_AFTER : 'addedafter:' ( s=STRING | s=Q_STRING )
					{
						differsTimeParam( Tasks.ADDED_DATE, $s.getText(), false );
					};

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

AND 		   : 'and'
					{
						result.append( " AND " );
					};

OR  			: 'or'
					{
						result.append( " OR " );
					};

NOT 			: 'not'
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
