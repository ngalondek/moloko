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
	import dev.drsoran.provider.Rtm.Notes;
	
	import java.util.Calendar;

	import org.antlr.runtime.CommonTokenStream;
	import org.antlr.runtime.RecognitionException;
	
	import dev.drsoran.moloko.grammar.DateTimeLexer;
	import dev.drsoran.moloko.grammar.TimeParser;
	import dev.drsoran.moloko.grammar.DateParser;
	
	import dev.drsoran.moloko.util.ANTLRNoCaseStringStream;
}


@members
{
	// BEGIN TOKEN LITERALS
	
	public final static String OP_LIST_LIT = "list:";
	
	public final static String OP_PRIORITY_LIT = "priority:";
	
	public final static String OP_STATUS_LIT = "status:";	
	
	public final static String OP_TAG_LIT = "tag:";

	public final static String OP_TAG_CONTAINS_LIT = "tagcontains:";
	
	public final static String OP_IS_TAGGED_LIT = "istagged:";
	
	public final static String OP_LOCATION_LIT = "location:";	
	
	public final static String OP_LOCATION_WITHIN_LIT = "locationwithin:";
	
	public final static String OP_IS_LOCATED_LIT = "islocated:";
	
	public final static String OP_IS_REPEATING_LIT = "isrepeating:";
	
	public final static String OP_NAME_LIT = "name:";
	
	public final static String OP_NOTE_CONTAINS_LIT = "notecontains:";
	
	public final static String OP_HAS_NOTES_LIT = "hasnotes:";
	
	public final static String OP_DUE_LIT = "due:";
	
	public final static String OP_DUE_AFTER_LIT = "dueafter:";
		
	public final static String OP_DUE_BEFORE_LIT = "duebefore:";
	
	public final static String OP_DUE_WITHIN_LIT = "duewithin:";
	
	public final static String OP_COMPLETED_LIT = "completed:";
	
	public final static String OP_COMPLETED_BEFORE_LIT = "completedbefore:";
	
	public final static String OP_COMPLETED_AFTER_LIT = "completedafter:";
	
	public final static String OP_COMPLETED_WITHIN_LIT = "completedwithin:";
	
	public final static String OP_ADDED_LIT = "added:";
	
	public final static String OP_ADDED_BEFORE_LIT = "addedbefore:";
	
	public final static String OP_ADDED_AFTER_LIT = "addedafter:";
	
	public final static String OP_ADDED_WITHIN_LIT = "addedwithin:";
	
	public final static String OP_TIME_ESTIMATE_LIT = "timeestimate:";
	
	public final static String OP_POSTPONED_LIT = "postponed:";
	
	public final static String	COMPLETED_LIT = "completed";
	
	public final static String	INCOMPLETE_LIT = "incomplete";
	
	public final static String	TRUE_LIT = "true";

	public final static String FALSE_LIT = "false";

	public final static String L_PARENTH_LIT = "(";
					
	public final static String R_PARENTH_LIT = ")";

	public final static String AND_LIT = "and";

	public final static String OR_LIT = "or";

	public final static String NOT_LIT = "not";
	
	// END TOKEN LITERALS
	
	private final static String TAGS_QUERY_PREFIX
   	= "(SELECT "  + Tags.TASKSERIES_ID + " FROM " + Tags.PATH
   	  + " WHERE " + Tags.TASKSERIES_ID + " = " + "subQuery." + Tasks._ID;
   
	private final StringBuffer result = new StringBuffer();
	
	private boolean hasStatusCompletedOp = false;
	
	private Calendar parseDateTimeSpec( String spec )
	{
      final DateTimeLexer lexer           = new DateTimeLexer( new ANTLRNoCaseStringStream( spec ) );
      final CommonTokenStream antlrTokens = new CommonTokenStream( lexer );
      final TimeParser timeParser         = new TimeParser( antlrTokens );
      
      final Calendar cal = TimeParser.getLocalizedCalendar();
      
      boolean finished = false;
      // first try to parse time
      try
      {
         // The parser can adjust the day of week
         // for times in the past.
         timeParser.parseTime( cal, true );
         finished = true;
      }
      catch ( RecognitionException e )
      {
      }
      
      if ( !finished )
      {
         antlrTokens.reset();
         
         final DateParser dateParser = new DateParser( antlrTokens );
         
         try
         {
            dateParser.parseDate( cal );
            
            try
            {
               // Check if there is a time trailing.
               // The parser can not adjust the day of week
               // for times in the past.
               timeParser.parseTime( cal, false );
            }
            catch ( RecognitionException re2 )
            {
            }
            finally
            {
               finished = true;
            }
         }
         catch ( RecognitionException e )
         {           
         }
      }
      
      return ( finished ) ? cal : null;
	}



	public static final String unquotify( String input )
	{
		return input.replaceAll( "(\"|')", "" );
	}


	
	public static final String quotify( String input )
	{
		return new StringBuffer( "\"").append( input ).append( "\"").toString();
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
		{
         hasStatusCompletedOp = false;

         while ( nextToken() != Token.EOF_TOKEN )
         {
         }
      }
      
      return result.toString();
	}
	
	
	
	public boolean hasStatusCompletedOperator()
	{
      return hasStatusCompletedOp;
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
						COMPLETED
						{
						   hasStatusCompletedOp = true;
							result.append(" IS NOT NULL");
						}
						|
                  INCOMPLETE
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

OP_TAG_CONTAINS : 'tagcontains:' ( s=STRING | s=Q_STRING )
					   {
					     result.append( TAGS_QUERY_PREFIX )
					           .append( " AND " )
					           .append( Tags.TAG );
						  containsStringParam( $s.getText() );
						  result.append( ")" );
				      };

OP_IS_TAGGED    : 'istagged:'
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
					
OP_NOTE_CONTAINS : 'notecontains:' ( s=STRING | s=Q_STRING )
						 {
						 	 result.append( " (SELECT " )
				                 .append( Notes.TASKSERIES_ID )
				                 .append( " FROM " )
				                 .append( Notes.PATH )
				                 .append( " WHERE " )
				                 .append( Notes.TASKSERIES_ID )
				                 .append( " = subQuery." )
				                 .append( Tasks._ID )
				                 .append( " AND " )
				                 .append( Notes.NOTE_TEXT );
				           containsStringParam( $s.getText() );
				           result.append( ")" );
						 };

OP_HAS_NOTES : 'hasnotes:'
					(
						TRUE
						{
							result.append(" num_notes > 0");	
						}
						|
						FALSE
						{
							result.append(" num_notes = 0");
						}
					);

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

COMPLETED   : 'completed';

INCOMPLETE  : 'incomplete';

TRUE			: 'true';

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
