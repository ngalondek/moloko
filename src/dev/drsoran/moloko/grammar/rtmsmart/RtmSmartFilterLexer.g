lexer grammar RtmSmartFilterLexer;

options
{
   language=Java;
}

@header
{
   package dev.drsoran.moloko.grammar.rtmsmart;
   
   import org.antlr.runtime.RecognitionException;
   import dev.drsoran.moloko.grammar.GrammarException;
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

   private IRtmSmartFilterEvaluator evaluator;

   // STATUS VARIABLES   

   private boolean hasStatusCompletedOp;

   private boolean error;

   private RecognitionException innerException;


   public void setEvaluator(IRtmSmartFilterEvaluator evaluator)
   {
      this.evaluator = evaluator;
   }



   public void startEvaluation() throws GrammarException
   {
      Token currentToken = null;
      
      if ( !error )
      {
         hasStatusCompletedOp = false;
         
         for ( currentToken = nextToken(); !error
            && currentToken != Token.EOF_TOKEN; currentToken = nextToken() )
         {
         }
      }
      
      if ( error )
      {
         final String exceptionMessage = String.format( "Failed to evaluate RTM smart filter. Last token was '\%s':\%d.",
                                                        currentToken,
                                                        getCharPositionInLine() );
         
         if ( innerException != null )
         {
            throw new GrammarException( exceptionMessage, innerException );
         }
         else
         {
            throw new GrammarException( exceptionMessage );
         }
      }
   }



   @Override
   public void reset()
   {
      super.reset();
		
      hasStatusCompletedOp = false;      
      error = false;
      innerException = null;
   }



   @Override
   public void reportError( RecognitionException e )
   {
      error = true;
      innerException = e;
                  
      super.reportError( e );
   }



   public boolean hasStatusCompletedOperator()
   {
      return hasStatusCompletedOp;
   }
}


/** Operators **/

OP_LIST     :  'list:' ( s=STRING | s=Q_STRING )
               {
                  if (evaluator != null)
                  {
                     error = evaluator.evalList( $s.getText() );
                  }
               };

OP_PRIORITY :  'priority:' ( p=PRIO_HIGH | p=PRIO_MED | p=PRIO_LOW | p=PRIO_NONE )
               {
                  if (evaluator != null)
                  {
                     error = evaluator.evalPriority( $p.getText() );
                  }
               };

OP_STATUS   :  'status:'
               (
                  COMPLETED
                  {
                     if (evaluator != null)
                     {
                        error = evaluator.evalStatus( true );
                     }
                     
                     hasStatusCompletedOp = true;
                  }
                  |
                  INCOMPLETE
                  {
                     if (evaluator != null)
                     {
                        error = evaluator.evalStatus( false );
                     }
                  }
               );

OP_TAG      : 'tag:' ( s=STRING | s=Q_STRING )
              {                     
                 if (evaluator != null)
                 {
                    error = evaluator.evalTag( $s.getText() );
                 }                   
              };

OP_TAG_CONTAINS : 'tagcontains:' ( s=STRING | s=Q_STRING )
                  {
                    if (evaluator != null)
                    {
                       error = evaluator.evalTagContains( $s.getText() );
                    }
                  };

OP_IS_TAGGED    : 'istagged:'
                  (
                     TRUE
                     {
                        if (evaluator != null)
                        {
                           error = evaluator.evalIsTagged( true );
                        }
                     }
                     |
                     FALSE
                     {
                        if (evaluator != null)
                        {
                           error = evaluator.evalIsTagged( false );
                        }
                     }
                  );

OP_LOCATION : 'location:' ( s=STRING | s=Q_STRING )
               {
                  if (evaluator != null)
                  {
                     error = evaluator.evalLocation( $s.getText() );
                  }
               };

// OP_LOCATED_WITHIN

OP_ISLOCATED : 'islocated:'
               (
                  TRUE
                  {
                     if (evaluator != null)
                     {
                        error = evaluator.evalIsLocated( true );
                     }
                  }
                  |
                  FALSE
                  {
                     if (evaluator != null)
                     {
                        error = evaluator.evalIsLocated( false );
                     }
                  }
               );

OP_IS_REPEATING : 'isrepeating:'
                  (
                     TRUE
                     {
                        if (evaluator != null)
                        {
                           error = evaluator.evalIsRepeating( true );
                        }
                     }
                     |
                     FALSE
                     {
                        if (evaluator != null)
                        {
                           error = evaluator.evalIsRepeating( false );
                        }
                     }
                  );

OP_NAME      : 'name:' ( s=STRING | s=Q_STRING )
               {          
                  if (evaluator != null)
                  {
                     error = evaluator.evalTaskName( $s.getText() );
                  }
               };

OP_NOTE_CONTAINS : 'notecontains:' ( s=STRING | s=Q_STRING )
                   {    
                      if (evaluator != null)
                      {
                         error = evaluator.evalNoteContains( $s.getText() );
                      }
                   };

OP_HAS_NOTES : 'hasnotes:'
               (
                  TRUE
                  {
                     if (evaluator != null)
                     {
                        error = evaluator.evalHasNotes( true );
                     }
                  }
                  |
                  FALSE
                  {
                     if (evaluator != null)
                     {
                        error = evaluator.evalHasNotes( false );
                     }
                  }
               );

OP_DUE      :  'due:' ( s=STRING | s=Q_STRING )
               {     
                  if (evaluator != null)
                  {
                     error = evaluator.evalDue( $s.getText() );
                  }
               };

OP_DUE_AFTER : 'dueafter:' ( s=STRING | s=Q_STRING )
               {
                  if (evaluator != null)
                  {
                     error = evaluator.evalDueAfter( $s.getText() );
                  }
               };

OP_DUE_BEFORE : 'duebefore:' ( s=STRING | s=Q_STRING )
                {
                  if (evaluator != null)
                  {
                     error = evaluator.evalDueBefore( $s.getText() );
                  }
                };

OP_DUE_WITHIN : 'duewithin:' s=Q_STRING
                {
                   if (evaluator != null)
                   {
                      error = evaluator.evalDueWithIn( $s.getText() );
                   }
                };

OP_COMPLETED : 'completed:' ( s=STRING | s=Q_STRING )
               {  
                  if (evaluator != null)
                  {
                     error = evaluator.evalCompleted( $s.getText() );
                  }
                           
                  hasStatusCompletedOp = true;
               };
               
OP_COMPLETED_AFTER : 'completedafter:' ( s=STRING | s=Q_STRING )
                     {
                        if (evaluator != null)
                        {
                           error = evaluator.evalCompletedAfter( $s.getText() );
                        }
                        
                        hasStatusCompletedOp = true;
                     };

OP_COMPLETED_BEFORE : 'completedbefore:' ( s=STRING | s=Q_STRING )
                      {
                          if (evaluator != null)
                          {
                             error = evaluator.evalCompletedBefore( $s.getText() );
                          }
                          
                          hasStatusCompletedOp = true;
                      };

OP_COMPLETED_WITHIN : 'completedwithin:' s=Q_STRING
                      {
                         if (evaluator != null)
                         {
                            error = evaluator.evalCompletedWithIn( $s.getText() );
                         }
                          
                         hasStatusCompletedOp = true;
                      };

OP_ADDED     : 'added:' ( s=STRING | s=Q_STRING )
               {
                  if (evaluator != null)
                  {
                     error = evaluator.evalAdded( $s.getText() );
                  }
               };

OP_ADDED_AFTER : 'addedafter:' ( s=STRING | s=Q_STRING )
                 {
                    if (evaluator != null)
                    {
                       error = evaluator.evalAddedAfter( $s.getText() );
                    }
                 };

OP_ADDED_BEFORE : 'addedbefore:' ( s=STRING | s=Q_STRING )
                  {
                     if (evaluator != null)
                     {
                        error = evaluator.evalAddedBefore( $s.getText() );
                     }
                  };

OP_ADDED_WITHIN : 'addedwithin:' s=Q_STRING
                  {
                     if (evaluator != null)
                     {
                        error = evaluator.evalAddedWithIn( $s.getText() );
                     }
                  };

OP_TIME_ESTIMATE : 'timeestimate:' s=Q_STRING
                   {    
                      if (evaluator != null)
                      {
                         error = evaluator.evalTimeEstimate( $s.getText() );
                      }
                   };

OP_POSTPONED : 'postponed:' s=STRING | s=Q_STRING
               {
                  if (evaluator != null)
                  {
                     error = evaluator.evalPostponed( $s.getText() );
                  }
               };

OP_IS_SHARED : 'isshared:'
               (
                  TRUE
                  {       
                     if (evaluator != null)
                     {
                        error = evaluator.evalIsShared( true );
                     }              
                  }
                  |
                  FALSE
                  {
                     if (evaluator != null)
                     {
                        error = evaluator.evalIsShared( false );
                     }
                  }
               );

OP_SHARED_WITH : 'sharedwith:' ( s=STRING | s=Q_STRING )
                 {
                    if (evaluator != null)
                    {
                       error = evaluator.evalSharedWith( $s.getText() );
                    }
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
                  if (evaluator != null)
                  {
                     error = evaluator.evalLeftParenthesis();
                  }
               };

R_PARENTH   : ')'
               {
                  if (evaluator != null)
                  {
                     error = evaluator.evalRightParenthesis();
                  }
               };

AND         : 'and'
              {
                 if (evaluator != null)
                 {
                    error = evaluator.evalAnd();
                 }
              };

OR          : 'or'
              {
                 if (evaluator != null)
                 {
                    error = evaluator.evalOr();
                 }
              };

NOT         : 'not'
              {          
                 if (evaluator != null)
                 {
                    error = evaluator.evalNot();
                 }
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
