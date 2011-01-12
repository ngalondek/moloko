lexer grammar RtmSmartFilterLexer;

options
{
   language=Java;
}

@header
{
   package dev.drsoran.moloko.grammar;

   import dev.drsoran.moloko.util.parsing.RtmDateTimeParsing;
   import dev.drsoran.moloko.util.parsing.RtmSmartFilterToken;
   import dev.drsoran.provider.Rtm.Tasks;
   import dev.drsoran.provider.Rtm.Tags;
   import dev.drsoran.provider.Rtm.Notes;
   import dev.drsoran.provider.Rtm.Locations;

   import org.antlr.runtime.RecognitionException;

   import java.util.Calendar;
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
   
   public final static String OP_IS_SHARED_LIT = "isshared:";
   
   public final static String OP_SHARED_WITH_LIT = "sharedwith:";   

   public final static String COMPLETED_LIT = "completed";

   public final static String INCOMPLETE_LIT = "incomplete";

   public final static String TRUE_LIT = "true";

   public final static String FALSE_LIT = "false";

   public final static String L_PARENTH_LIT = "(";

   public final static String R_PARENTH_LIT = ")";

   public final static String AND_LIT = "and";

   public final static String OR_LIT = "or";

   public final static String NOT_LIT = "not";

   // END TOKEN LITERALS

   private final static String TAGS_QUERY_PREFIX
      = "(SELECT "  + Tags.TASKSERIES_ID + " FROM " + Tags.PATH
        + " WHERE " + Tags.TASKSERIES_ID + " = " + "subQuery." + Tasks.TASKSERIES_ID;

   // STATUS VARIABLES

   private StringBuffer result = new StringBuffer();

   private boolean hasStatusCompletedOp = false;

   private boolean error = false;
   
   private boolean op_not = false;
   
   private ArrayList< RtmSmartFilterToken > tokens;



   @Override
   public void reset()
   {
      super.reset();
		
      result.setLength( 0 );
      hasStatusCompletedOp = false;
      error = false;
      tokens = null;
   }



   @Override
   public void reportError( RecognitionException e )
   {
      super.reportError( e );
      error = true;
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
      final Calendar cal = RtmDateTimeParsing.parseDateTimeSpec( unquotify( param ) );

      if ( cal != null )
      {
         result.append( column );
         
      	// Check if we have 'NEVER'
      	if ( !cal.isSet( Calendar.DATE ) )
      	{      	   
            result.append( " IS NULL" );
      	}
      	
         // Check if we have an explicit time
         // given.
         else if ( cal.isSet( Calendar.HOUR_OF_DAY ) )
         {
            result.append( " == " );
            result.append( cal.getTimeInMillis() );
         }
         else
         {
            result.append( " >= " );
            result.append( cal.getTimeInMillis() );
            result.append( " AND " );

            cal.roll( Calendar.DAY_OF_YEAR, true );

            result.append( column );
            result.append( " < " );
            result.append( cal.getTimeInMillis() );
         }
      }
      else
         // Parser error
         error = true;
   }



   private void differsTimeParam( String column, String param, boolean before )
   {
      final Calendar cal = RtmDateTimeParsing.parseDateTimeSpec( unquotify( param ) );

      if ( cal != null )
      {
	      result.append( column );
	      
   	   // Check if we have 'NEVER'
      	if ( !cal.isSet( Calendar.DATE ) )
      	{      	   
            result.append( " IS NOT NULL" );
      	}
         else
         {
            result.append( ( before ) ? " < " : " > " );
            result.append( cal.getTimeInMillis() );
         }
      }
      else
         // Parser error
         error = true;
   }



   private void inTimeParamRange( String column, String param, boolean past )
   {
      final RtmDateTimeParsing.DateWithinReturn range = RtmDateTimeParsing.parseDateWithin( unquotify( param ), past );

      if ( range != null )
      {
         result.append( column );
         result.append( " >= " );
         result.append( !past ? range.startEpoch.getTimeInMillis() : range.endEpoch.getTimeInMillis() );
         result.append( " AND " );
         result.append( column );
         result.append( " < " );
         result.append( !past ? range.endEpoch.getTimeInMillis() : range.startEpoch.getTimeInMillis() );
      }
      else
        // Parser error
        error = true;
   }


   public String getResult( ArrayList< RtmSmartFilterToken > tokens ) throws RecognitionException
   {
      this.tokens = tokens;
      
      if ( !error && result.length() == 0 )
      {
         result.append( "( " );
         
         hasStatusCompletedOp = false;
         
         while ( !error && nextToken() != Token.EOF_TOKEN )
         {
         }

         result.append( " )" );
      }

      error = error || result.length() == 0;

      if ( error )
         throw new RecognitionException();

      return result.toString();
   }



   public boolean hasStatusCompletedOperator()
   {
      return hasStatusCompletedOp;
   }



   private final void addRtmToken( int type, String param )
   {
      if ( tokens != null )
         tokens.add( new RtmSmartFilterToken( type, unquotify( param ), op_not ) );
   }
}


/** Operators **/

OP_LIST     :  'list:' ( s=STRING | s=Q_STRING )
               {
                  result.append( Tasks.LIST_NAME );
                  equalsStringParam( $s.getText() );
                  
                  addRtmToken( OP_LIST, $s.getText() );
               };

OP_PRIORITY :  'priority:' s=STRING
               {
                  result.append( Tasks.PRIORITY );
                  equalsStringParam( firstCharOf( unquotify( $s.getText() ) ) );
                  
                  addRtmToken( OP_PRIORITY, $s.getText() );
               };

OP_STATUS   :  'status:'
               {
                  result.append( Tasks.COMPLETED_DATE );
               }
               (
                  COMPLETED
                  {
                     result.append(" IS NOT NULL");
                     hasStatusCompletedOp = true;
                     addRtmToken( OP_STATUS, COMPLETED_LIT );
                  }
                  |
                  INCOMPLETE
                  {
                     result.append(" IS NULL");
                     addRtmToken( OP_STATUS, INCOMPLETE_LIT );
                  }
               );

OP_TAG      : 'tag:' ( s=STRING | s=Q_STRING )
              {
                 result.append( TAGS_QUERY_PREFIX )
                       .append( " AND " )
                       .append( Tags.TAG );
                 equalsStringParam( $s.getText() );
                 result.append( ")" );
                 
                 addRtmToken( OP_TAG, $s.getText() );
              };

OP_TAG_CONTAINS : 'tagcontains:' ( s=STRING | s=Q_STRING )
                  {
                    result.append( TAGS_QUERY_PREFIX )
                          .append( " AND " )
                          .append( Tags.TAG );
                    containsStringParam( $s.getText() );
                    result.append( ")" );
                    
                    addRtmToken( OP_TAG_CONTAINS, $s.getText() );
                  };

OP_IS_TAGGED    : 'istagged:'
                  (
                     TRUE
                     {
                        result.append( TAGS_QUERY_PREFIX );
                        result.append( ")" );
                        addRtmToken( OP_IS_TAGGED, TRUE_LIT );
                     }
                     |
                     FALSE
                     {
                        result.append( " NOT EXISTS " );
                        result.append( TAGS_QUERY_PREFIX );
                        result.append( ")" );
                        addRtmToken( OP_IS_TAGGED, FALSE_LIT );
                     }
                  );

OP_LOCATION : 'location:' ( s=STRING | s=Q_STRING )
               {
                  result.append( Tasks.LOCATION_NAME );
                  containsStringParam( $s.getText() );
                  addRtmToken( OP_LOCATION, $s.getText() );
               };

// OP_LOCATED_WITHIN

OP_ISLOCATED : 'islocated:'
               {
                  result.append( Tasks.LOCATION_ID );
               }
               (
                  TRUE
                  {
                     // Handle the case that shared tasks have a location
                     // ID but not from our DB.
                     result.append(" IS NOT NULL AND ");
                     result.append( Tasks.LOCATION_ID );
                     result.append(" IN ( SELECT ");
                     result.append( Locations._ID );
                     result.append(" FROM ");
                     result.append( Locations.PATH );
                     result.append(" )");
                     addRtmToken( OP_ISLOCATED, TRUE_LIT );
                  }
                  |
                  FALSE
                  {
                     result.append(" IS NULL");
                     addRtmToken( OP_ISLOCATED, FALSE_LIT );
                  }
               );

OP_IS_REPEATING : 'isrepeating:'
                  {
                     result.append( Tasks.RECURRENCE );
                  }
                  (
                     TRUE
                     {
                        result.append(" IS NOT NULL");
                        addRtmToken( OP_IS_REPEATING, TRUE_LIT );
                     }
                     |
                     FALSE
                     {
                        result.append(" IS NULL");
                        addRtmToken( OP_IS_REPEATING, FALSE_LIT );
                     }
                  );

OP_NAME      : 'name:' ( s=STRING | s=Q_STRING )
               {
                  result.append( Tasks.TASKSERIES_NAME );
                  containsStringParam( $s.getText() );
                  addRtmToken( OP_NAME, $s.getText() );
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
                            .append( Tasks.TASKSERIES_ID )
                            .append( " AND " )
                            .append( Notes.NOTE_TITLE );
                      containsStringParam( s.getText() );
                      result.append( " OR " ).append( Notes.NOTE_TEXT );
                      containsStringParam( s.getText() );
                      result.append( ")" );
                      
                      addRtmToken( OP_NOTE_CONTAINS, $s.getText() );
                   };

OP_HAS_NOTES : 'hasnotes:'
               (
                  TRUE
                  {
                     result.append( Tasks.NUM_NOTES + " > 0");
                     addRtmToken( OP_HAS_NOTES, TRUE_LIT );
                  }
                  |
                  FALSE
                  {
                     result.append( Tasks.NUM_NOTES + " = 0");
                     addRtmToken( OP_HAS_NOTES, FALSE_LIT );
                  }
               );

OP_DUE      :  'due:' ( s=STRING | s=Q_STRING )
               {
                  equalsTimeParam( Tasks.DUE_DATE, $s.getText() );
                  addRtmToken( OP_DUE, $s.getText() );
               };

OP_DUE_AFTER : 'dueafter:' ( s=STRING | s=Q_STRING )
               {
                  differsTimeParam( Tasks.DUE_DATE, $s.getText(), false );
                  addRtmToken( OP_DUE_AFTER, $s.getText() );
               };

OP_DUE_BEFORE : 'duebefore:' ( s=STRING | s=Q_STRING )
                {
                   differsTimeParam( Tasks.DUE_DATE, $s.getText(), true );
                   addRtmToken( OP_DUE_BEFORE, $s.getText() );
                };

OP_DUE_WITHIN : 'duewithin:' s=Q_STRING
                {
                   inTimeParamRange( Tasks.DUE_DATE, $s.getText(), false );
                   addRtmToken( OP_DUE_WITHIN, $s.getText() );
                };

OP_COMPLETED : 'completed:' ( s=STRING | s=Q_STRING )
               {
                  equalsTimeParam( Tasks.COMPLETED_DATE, $s.getText() );
                  hasStatusCompletedOp = true;
                  addRtmToken( OP_COMPLETED, $s.getText() );
               };

OP_COMPLETED_BEFORE : 'completedbefore:' ( s=STRING | s=Q_STRING )
                      {
                          differsTimeParam( Tasks.COMPLETED_DATE, $s.getText(), true );
                          hasStatusCompletedOp = true;
                          addRtmToken( OP_COMPLETED_BEFORE, $s.getText() );
                      };

OP_COMPLETED_AFTER : 'completedafter:' ( s=STRING | s=Q_STRING )
                     {
                        differsTimeParam( Tasks.COMPLETED_DATE, $s.getText(), false );
                        hasStatusCompletedOp = true;
                        addRtmToken( OP_COMPLETED_AFTER, $s.getText() );
                     };

OP_COMPLETED_WITHIN : 'completedwithin:' s=Q_STRING
                      {
                         inTimeParamRange( Tasks.COMPLETED_DATE, $s.getText(), true );
                         hasStatusCompletedOp = true;
                         addRtmToken( OP_COMPLETED_WITHIN, $s.getText() );
                      };

OP_ADDED     : 'added:' ( s=STRING | s=Q_STRING )
               {
                  equalsTimeParam( Tasks.ADDED_DATE, $s.getText() );
                  addRtmToken( OP_ADDED, $s.getText() );
               };

OP_ADDED_BEFORE : 'addedbefore:' ( s=STRING | s=Q_STRING )
                  {
                     differsTimeParam( Tasks.ADDED_DATE, $s.getText(), true );
                     addRtmToken( OP_ADDED_BEFORE, $s.getText() );
                  };

OP_ADDED_AFTER : 'addedafter:' ( s=STRING | s=Q_STRING )
                 {
                    differsTimeParam( Tasks.ADDED_DATE, $s.getText(), false );
                    addRtmToken( OP_ADDED_AFTER, $s.getText() );
                 };

OP_ADDED_WITHIN : 'addedwithin:' s=Q_STRING
                  {
                     inTimeParamRange( Tasks.ADDED_DATE, $s.getText(), true );
                     addRtmToken( OP_ADDED_WITHIN, $s.getText() );
                  };

OP_TIME_ESTIMATE : 'timeestimate:' s=Q_STRING
                   {
                      result.append( Tasks.ESTIMATE_MILLIS );

                      final String param = unquotify( s.getText() );

                      long estimatedMillis = -1;
                      final char chPos0 = param.charAt( 0 );

                      if ( chPos0 == '<' || chPos0 == '>' )
                      {
                         result.append( " > -1 AND " ).append( Tasks.ESTIMATE_MILLIS );
                         result.append( chPos0 );
                         estimatedMillis = RtmDateTimeParsing.parseEstimated( param.substring( 1 ) );
                      }
                      else
                      {
                         result.append( "=" );
                         estimatedMillis = RtmDateTimeParsing.parseEstimated( param );
                      }

                      // Parser error
                      if ( estimatedMillis == -1 )
                         error = true;
                      else
                         result.append( estimatedMillis );
                         
                      addRtmToken( OP_TIME_ESTIMATE, $s.getText() );
                   };

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
               )
               {
                  addRtmToken( OP_POSTPONED, $s.getText() );
               };

OP_IS_SHARED : 'isshared:'
               (
                  TRUE
                  {
                     result.append( Tasks.PARTICIPANT_IDS + " IS NOT NULL");
                     addRtmToken( OP_IS_SHARED, TRUE_LIT );
                  }
                  |
                  FALSE
                  {
                     result.append( Tasks.PARTICIPANT_IDS + " IS NULL");
                     addRtmToken( OP_IS_SHARED, FALSE_LIT );
                  }
               );

OP_SHARED_WITH : 'sharedwith:' ( s=STRING | s=Q_STRING )
                 {
                    result.append( Tasks.PARTICIPANT_FULLNAMES );
                    containsStringParam( $s.getText() );
                    
                    result.append( " OR " );
                    result.append( Tasks.PARTICIPANT_USERNAMES );
                    containsStringParam( $s.getText() );
                    
                    addRtmToken( OP_SHARED_WITH, $s.getText() );
                 };

// OP_IS_RECEIVED

// OP_TO

// OP_FROM

// OP_INCLUDE_ARCHIVED


/** other tokens **/

COMPLETED   : 'completed';

INCOMPLETE  : 'incomplete';

TRUE        : 'true';

FALSE       : 'false';

L_PARENTH   : '('
               {
                  result.append( "( " );
               };

R_PARENTH   : ')'
               {
                  result.append( " )" );
               };

AND         : 'and'
              {
                 result.append( " AND " );
                 op_not = false;
              };

OR          : 'or'
              {
                 result.append( " OR " );
                 op_not = false;
              };

NOT         : 'not'
              {
                 result.append( " NOT " );
                 op_not = true;
              };

WS          :   ( ' '
                 | '\t'
                 | '\r'
                 | '\n'
                 ) { skip(); };

fragment
Q_STRING    : '"' ~('"')* '"';

fragment
STRING      : ~('"' | ' ' | '(' | ')')+;
