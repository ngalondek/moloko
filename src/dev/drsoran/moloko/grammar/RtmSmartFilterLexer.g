lexer grammar RtmSmartFilterLexer;

options
{
   language=Java;
}

@header
{
   package dev.drsoran.moloko.grammar;

   import dev.drsoran.moloko.grammar.datetime.IDateParser.ParseDateWithinReturn;
   import dev.drsoran.moloko.util.MolokoCalendar;
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
   
   public final static String PRIO_HIGH_LIT = "1";
   
   public final static String PRIO_MED_LIT = "2";
   
   public final static String PRIO_LOW_LIT = "3";
   
   public final static String PRIO_NONE_LIT = "n";

   public final static String L_PARENTH_LIT = "(";

   public final static String R_PARENTH_LIT = ")";

   public final static String AND_LIT = "and";

   public final static String OR_LIT = "or";

   public final static String NOT_LIT = "not";

   // END TOKEN LITERALS  

   private final static String DEFAULT_OPERATOR = AND_LIT;

   // STATUS VARIABLES   

   private StringBuilder result = new StringBuilder();

   private boolean hasStatusCompletedOp = false;

   private boolean error = false;
   
   private boolean lexedOperator = false;
   
   private boolean opNot = false;
   
   private ArrayList< RtmSmartFilterToken > tokens;
   
   private int numTokens;



   @Override
   public void reset()
   {
      super.reset();
		
      result.setLength( 0 );
      hasStatusCompletedOp = false;
      lexedOperator = false;
      opNot = false;
      error = false;
      tokens = null;
      numTokens = 0;
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
      return "\"" + input + "\"";
   }



   private static final String firstCharOf( String input )
   {
      return input.length() > 0 ? input.substring( 0, 1 ) : "";
   }


   private void likeStringParam( String param )
   {
      result.append( " like '" );
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
   	try
   	{
   	   final int val = Integer.parseInt( unquotify( param ) );
   	   
         result.append( " = " );
         result.append( val );
   	}
   	catch( NumberFormatException e )
   	{
   	   error = true;
   	}
   }



   private void equalsTimeParam( String column, String param )
   {
      final MolokoCalendar cal = RtmDateTimeParsing.parseDateTimeSpec( unquotify( param ) );

      if ( cal != null )
      {
         result.append( "(" );
         result.append( column );
         
      	// Check if we have 'NEVER'
      	if ( !cal.hasDate() )
      	{      	   
            result.append( " IS NULL" );
      	}
      	
         // Check if we have an explicit time
         // given.
         else if ( cal.hasTime() )
         {
            result.append( " == " );
            result.append( cal.getTimeInMillis() );
         }
         else
         {
            result.append( " >= " );
            result.append( cal.getTimeInMillis() );
            result.append( " AND " );

            cal.add( Calendar.DAY_OF_YEAR, 1 );

            result.append( column );
            result.append( " < " );
            result.append( cal.getTimeInMillis() );
         }
         
         result.append( ")" );
      }
      else
         // Parser error
         error = true;
   }



   private void differsTimeParam( String column, String param, boolean before )
   {
      final String unquotParam = unquotify( param );
      final MolokoCalendar cal = RtmDateTimeParsing.parseDateTimeSpec( unquotParam );
      
      if ( cal != null )
      {
         result.append( column );
         
         // Check if we have 'NEVER'
         if ( !cal.hasDate() )
         {
            result.append( " IS NOT NULL" );
         }
         else
         {
            result.append( ( before ) ? " < " : " > " );
            result.append( cal.getTimeInMillis() );
         }
      }
      
      // If simple time parsing failed, try parse date within
      else
      {
         final ParseDateWithinReturn dateWithinReturn = RtmDateTimeParsing.parseDateWithin( unquotParam,
                                                                                            before );
         
         result.append( column );
         
         if ( dateWithinReturn != null )
         {
            // Check if we have 'NEVER'
            if ( !dateWithinReturn.endEpoch.hasDate() )
            {
               result.append( " IS NOT NULL" );
            }
            
            else
            {
               if ( before )
               {
                  result.append( " < " );
               }
               else
               {
                  result.append( " > " );
               }
               
               result.append( dateWithinReturn.endEpoch.getTimeInMillis() );
            }
         }
         else
         {
            // Parser error
            error = true;
         }
      }
   }



   private void inTimeParamRange( String column, String param, boolean past )
   {
      final ParseDateWithinReturn range = RtmDateTimeParsing.parseDateWithin( unquotify( param ), past );

      if ( range != null )
      {
         result.append( "(" );
         result.append( column );
         result.append( " >= " );
         result.append( !past ? range.startEpoch.getTimeInMillis() : range.endEpoch.getTimeInMillis() );
         result.append( " AND " );
         result.append( column );
         result.append( " < " );
         result.append( !past ? range.endEpoch.getTimeInMillis() : range.startEpoch.getTimeInMillis() );
         result.append( ")" );
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
      ++numTokens;
      
      if ( tokens != null )
      {
         tokens.add( new RtmSmartFilterToken( type, unquotify( param ), opNot ) );
      }
   }
   
   private final void ensureOperator()
   {
      // We do not insert the default operatorfor the first, lexed token
      if (!lexedOperator && numTokens > 0)
      {
         result.append(" ")
               .append(DEFAULT_OPERATOR)
               .append(" ");
               
         lexedOperator = true;
      }
   }
}


/** Operators **/

OP_LIST     :  'list:' ( s=STRING | s=Q_STRING )
               {
                  ensureOperator();
                  
                  result.append( Tasks.LIST_NAME );
                  likeStringParam( $s.getText() );

                  addRtmToken( OP_LIST, $s.getText() );
                  lexedOperator = false;
               };

OP_PRIORITY :  'priority:' ( p=PRIO_HIGH | p=PRIO_MED | p=PRIO_LOW | p=PRIO_NONE )
               {
                  ensureOperator();
                  result.append( Tasks.PRIORITY );

                  likeStringParam( $p.getText() );

                  addRtmToken( OP_PRIORITY, $p.getText() );
                  lexedOperator = false;
               };

OP_STATUS   :  'status:'
               {
                  ensureOperator();
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
               )
               {
                  lexedOperator = false;
               };

OP_TAG      : 'tag:' ( s=STRING | s=Q_STRING )
              {
                 ensureOperator();
                 
                 final String unqString = unquotify( $s.getText() );
                 
                 result.append( "(" );
                 result.append( Tasks.TAGS )
                 // Exact match if only 1 tag
                       .append( " = '" )
                       .append( unqString )
                       .append( "' OR " )
                // match for the case tag, (prefix)
                       .append( Tasks.TAGS )
                       .append( " like '" )
                       .append( unqString )
                       .append( Tags.TAGS_SEPARATOR )
                       .append( "\%' OR " )
                // match for the case ,tag, (infix)
                       .append( Tasks.TAGS )
                       .append( " like '\%" )
                       .append( Tags.TAGS_SEPARATOR )                       
                       .append( unqString )
                       .append( Tags.TAGS_SEPARATOR )
                       .append( "\%' OR " )
                // match for the case ,tag (suffix)
                       .append( Tasks.TAGS )
                       .append( " like '\%" )
                       .append( Tags.TAGS_SEPARATOR )                       
                       .append( unqString )
                       .append( "'" );
                 result.append( ")" );
                       
                 addRtmToken( OP_TAG, $s.getText() );
                 lexedOperator = false;
              };

OP_TAG_CONTAINS : 'tagcontains:' ( s=STRING | s=Q_STRING )
                  {
                    ensureOperator();
                    
	                 result.append( Tasks.TAGS );
                    containsStringParam( $s.getText() );
                    
                    addRtmToken( OP_TAG_CONTAINS, $s.getText() );
                    lexedOperator = false;
                  };

OP_IS_TAGGED    : 'istagged:'
                  {
                     ensureOperator();
                     result.append( Tasks.TAGS );
                  }
                  (
                     TRUE
                     {
                        result.append( " IS NOT NULL" );
                        
                        addRtmToken( OP_IS_TAGGED, TRUE_LIT );
                     }
                     |
                     FALSE
                     {
                        result.append( " IS NULL" );
                        
                        addRtmToken( OP_IS_TAGGED, FALSE_LIT );
                     }
                  )
                  {
                     lexedOperator = false;
                  };

OP_LOCATION : 'location:' ( s=STRING | s=Q_STRING )
               {
                  ensureOperator();
                  result.append( Tasks.LOCATION_NAME );
                  likeStringParam( $s.getText() );
                  
                  addRtmToken( OP_LOCATION, $s.getText() );
                  lexedOperator = false;
               };

// OP_LOCATED_WITHIN

OP_ISLOCATED : 'islocated:'
               {
                  ensureOperator();
               }
               (
                  TRUE
                  {
                     result.append( "(" );
                     result.append( Tasks.LOCATION_ID );
                     // Handle the case that shared tasks have a location
                     // ID but not from our DB.
                     result.append(" IS NOT NULL AND ");
                     result.append( Tasks.LOCATION_ID );
                     result.append(" IN ( SELECT ");
                     result.append( Locations._ID );
                     result.append(" FROM ");
                     result.append( Locations.PATH );
                     result.append(" )");
                     result.append( ")" );
                     
                     addRtmToken( OP_ISLOCATED, TRUE_LIT );
                  }
                  |
                  FALSE
                  {
                     result.append( Tasks.LOCATION_ID );
                     result.append(" IS NULL");
                     
                     addRtmToken( OP_ISLOCATED, FALSE_LIT );
                  }
               )
               {
                  lexedOperator = false;
               };

OP_IS_REPEATING : 'isrepeating:'
                  {
                     ensureOperator();
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
                  )
                  {
                     lexedOperator = false;
                  };

OP_NAME      : 'name:' ( s=STRING | s=Q_STRING )
               {
                  ensureOperator();
                  
                  result.append( Tasks.TASKSERIES_NAME );
                  containsStringParam( $s.getText() );
                  
                  addRtmToken( OP_NAME, $s.getText() );
                  lexedOperator = false;
               };

OP_NOTE_CONTAINS : 'notecontains:' ( s=STRING | s=Q_STRING )
                   {
                      ensureOperator();
                      
                      result.append( "(" );
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
                      result.append( ")" );
                      
                      addRtmToken( OP_NOTE_CONTAINS, $s.getText() );
                      lexedOperator = false;
                   };

OP_HAS_NOTES : 'hasnotes:'
               {
                  ensureOperator();
               }
               (
                  TRUE
                  {
                     result.append( Tasks.NOTE_IDS + " NOT NULL");
                     addRtmToken( OP_HAS_NOTES, TRUE_LIT );
                  }
                  |
                  FALSE
                  {
                     result.append( Tasks.NOTE_IDS + " IS NULL");
                     addRtmToken( OP_HAS_NOTES, FALSE_LIT );
                  }
               )
               {
                  lexedOperator = false;
               };

OP_DUE      :  'due:' ( s=STRING | s=Q_STRING )
               {
                  ensureOperator();
                  
                  equalsTimeParam( Tasks.DUE_DATE, $s.getText() );
                  
                  addRtmToken( OP_DUE, $s.getText() );
                  lexedOperator = false;
               };

OP_DUE_AFTER : 'dueafter:' ( s=STRING | s=Q_STRING )
               {
                  ensureOperator();

                  differsTimeParam( Tasks.DUE_DATE, $s.getText(), false );
                  
                  addRtmToken( OP_DUE_AFTER, $s.getText() );
                  lexedOperator = false;
               };

OP_DUE_BEFORE : 'duebefore:' ( s=STRING | s=Q_STRING )
                {
                   ensureOperator();
                   
                   differsTimeParam( Tasks.DUE_DATE, $s.getText(), true );
                   
                   addRtmToken( OP_DUE_BEFORE, $s.getText() );
                   lexedOperator = false;
                };

OP_DUE_WITHIN : 'duewithin:' s=Q_STRING
                {
                   ensureOperator();
                   
                   inTimeParamRange( Tasks.DUE_DATE, $s.getText(), false );
                   
                   addRtmToken( OP_DUE_WITHIN, $s.getText() );
                   lexedOperator = false;
                };

OP_COMPLETED : 'completed:' ( s=STRING | s=Q_STRING )
               {
                  ensureOperator();
                  
                  equalsTimeParam( Tasks.COMPLETED_DATE, $s.getText() );
                  hasStatusCompletedOp = true;
                  
                  addRtmToken( OP_COMPLETED, $s.getText() );
                  lexedOperator = false;
               };

OP_COMPLETED_BEFORE : 'completedbefore:' ( s=STRING | s=Q_STRING )
                      {
                          ensureOperator();
                          
                          differsTimeParam( Tasks.COMPLETED_DATE, $s.getText(), true );
                          hasStatusCompletedOp = true;
                          
                          addRtmToken( OP_COMPLETED_BEFORE, $s.getText() );
                          lexedOperator = false;
                      };

OP_COMPLETED_AFTER : 'completedafter:' ( s=STRING | s=Q_STRING )
                     {
                        ensureOperator();
                        
                        differsTimeParam( Tasks.COMPLETED_DATE, $s.getText(), false );
                        hasStatusCompletedOp = true;
                        
                        addRtmToken( OP_COMPLETED_AFTER, $s.getText() );
                        lexedOperator = false;
                     };

OP_COMPLETED_WITHIN : 'completedwithin:' s=Q_STRING
                      {
                         ensureOperator();
                         
                         inTimeParamRange( Tasks.COMPLETED_DATE, $s.getText(), true );
                         hasStatusCompletedOp = true;
                         
                         addRtmToken( OP_COMPLETED_WITHIN, $s.getText() );
                         lexedOperator = false;
                      };

OP_ADDED     : 'added:' ( s=STRING | s=Q_STRING )
               {
                  ensureOperator();
                  
                  equalsTimeParam( Tasks.ADDED_DATE, $s.getText() );
                  
                  addRtmToken( OP_ADDED, $s.getText() );
                  lexedOperator = false;
               };

OP_ADDED_BEFORE : 'addedbefore:' ( s=STRING | s=Q_STRING )
                  {
                     ensureOperator();
                     
                     differsTimeParam( Tasks.ADDED_DATE, $s.getText(), true );
                     
                     addRtmToken( OP_ADDED_BEFORE, $s.getText() );
                     lexedOperator = false;
                  };

OP_ADDED_AFTER : 'addedafter:' ( s=STRING | s=Q_STRING )
                 {
                    ensureOperator();
                    
                    differsTimeParam( Tasks.ADDED_DATE, $s.getText(), false );
                    
                    addRtmToken( OP_ADDED_AFTER, $s.getText() );
                    lexedOperator = false;
                 };

OP_ADDED_WITHIN : 'addedwithin:' s=Q_STRING
                  {
                     ensureOperator();
                     
                     inTimeParamRange( Tasks.ADDED_DATE, $s.getText(), true );
                     
                     addRtmToken( OP_ADDED_WITHIN, $s.getText() );
                     lexedOperator = false;
                  };

OP_TIME_ESTIMATE : 'timeestimate:' s=Q_STRING
                   {
                      ensureOperator();
                      
                      result.append( "(" );
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
                      {
                         result.append( estimatedMillis );
                         result.append( ")" );
                      }
                         
                      addRtmToken( OP_TIME_ESTIMATE, $s.getText() );
                      lexedOperator = false;
                   };

OP_POSTPONED : 'postponed:'
               {
                  ensureOperator();
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
                  lexedOperator = false;
               };

OP_IS_SHARED : 'isshared:'
               {
                  ensureOperator();
               }
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
               )
               {
                  lexedOperator = false;
               };

OP_SHARED_WITH : 'sharedwith:' ( s=STRING | s=Q_STRING )
                 {
                    ensureOperator();
                    
                    result.append( "(" );
                    result.append( Tasks.PARTICIPANT_FULLNAMES );
                    containsStringParam( $s.getText() );
                    
                    result.append( " OR " );
                    result.append( Tasks.PARTICIPANT_USERNAMES );
                    containsStringParam( $s.getText() );
                    result.append( ")" );
                    
                    addRtmToken( OP_SHARED_WITH, $s.getText() );
                    lexedOperator = false;
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

PRIO_HIGH   : '1';

PRIO_MED    : '2';

PRIO_LOW	   : '3';

PRIO_NONE	: 'n' | 'N';	

L_PARENTH   : '('
               {
                  ensureOperator();
                  result.append( "( " );
               };

R_PARENTH   : ')'
               {
                  result.append( " )" );
               };

AND         : 'and'
              {
                 result.append( " AND " );
                 opNot = false;
                 lexedOperator = true;
              };

OR          : 'or'
              {
                 result.append( " OR " );
                 opNot = false;
                 lexedOperator = true;
              };

NOT         : 'not'
              {
                 ensureOperator();
              }
              {
                 result.append( " NOT " );
                 opNot = true;
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
