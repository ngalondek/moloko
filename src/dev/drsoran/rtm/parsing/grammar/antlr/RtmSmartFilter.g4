grammar RtmSmartFilter;

/** Rules **/

parseFilter
    : expression+                       #FilterExpression
    | EOF                               #EmptyExpression
    ;
    
expression
    : operator                          #OperatorExpression
    | expression AND? expression        #LogicAndExpression
    | expression OR expression          #LogicOrExpression
    | NOT expression                    #NegatedExpression
    | L_PARENTH expression R_PARENTH    #ParenthExpression
    ;
    
operator
    : stringOrQuotedStringOperator
    | booleanOperator             
    | priorityOperator
    | statusOperator
    | timeEstimateOperator
    | postponedOperator 
    ;

stringOrQuotedStringOperator
    : OP_LIST               s=(STRING | Q_STRING) #List
    | OP_TAG                s=(STRING | Q_STRING) #Tag
    | OP_TAG_CONTAINS       s=(STRING | Q_STRING) #TagContains
    | OP_LOCATION           s=(STRING | Q_STRING) #Location
    | OP_NAME               s=(STRING | Q_STRING) #Name
    | OP_NOTE_CONTAINS      s=(STRING | Q_STRING) #NoteContains
    | OP_DUE                s=(STRING | Q_STRING) #Due
    | OP_DUE_AFTER          s=(STRING | Q_STRING) #DueAfter
    | OP_DUE_BEFORE         s=(STRING | Q_STRING) #DueBefore
    | OP_DUE_WITHIN         s=Q_STRING            #DueWithin
    | OP_COMPLETED          s=(STRING | Q_STRING) #Completed
    | OP_COMPLETED_AFTER    s=(STRING | Q_STRING) #CompletedAfter
    | OP_COMPLETED_BEFORE   s=(STRING | Q_STRING) #CompletedBefore
    | OP_COMPLETED_WITHIN   s=Q_STRING            #CompletedWithin
    | OP_ADDED              s=(STRING | Q_STRING) #Added
    | OP_ADDED_AFTER        s=(STRING | Q_STRING) #AddedAfter
    | OP_ADDED_BEFORE       s=(STRING | Q_STRING) #AddedBefore
    | OP_ADDED_WITHIN       s=Q_STRING            #AddedWithin
    | OP_SHARED_WITH        s=(STRING | Q_STRING) #SharedWith
    ;

booleanOperator
    : OP_IS_TAGGED      b=(TRUE | FALSE) #IsTagged
    | OP_IS_LOCATED     b=(TRUE | FALSE) #IsLocated
    | OP_IS_REPEATING   b=(TRUE | FALSE) #IsRepeating
    | OP_HAS_NOTES      b=(TRUE | FALSE) #HasNotes
    | OP_IS_SHARED      b=(TRUE | FALSE) #IsShared
    ;

priorityOperator
    : OP_PRIORITY p=STRING                             #Priority
    ;

statusOperator
    : OP_STATUS COMPLETED                              #StatusCompleted
    | OP_STATUS INCOMPLETE                             #StatusIncomplete
    ;

timeEstimateOperator
    : OP_TIME_ESTIMATE RELATION_PARAM                  #TimeEstimate
    ;

postponedOperator
    : OP_POSTPONED (r=RELATION_PARAM | s=STRING)       #Postponed
    ;

/** Operators **/

OP_LIST         : 'list:';

OP_PRIORITY     : 'priority:';

OP_STATUS       : 'status:';

OP_TAG          : 'tag:';

OP_TAG_CONTAINS : 'tagcontains:';

OP_IS_TAGGED    : 'istagged:';

OP_LOCATION     : 'location:';

// OP_LOCATED_WITHIN

OP_IS_LOCATED   : 'islocated:';

OP_IS_REPEATING : 'isrepeating:';

OP_NAME         : 'name:';

OP_NOTE_CONTAINS : 'notecontains:';

OP_HAS_NOTES    : 'hasnotes:';

OP_DUE          : 'due:';

OP_DUE_AFTER    : 'dueafter:';

OP_DUE_BEFORE   : 'duebefore:';

OP_DUE_WITHIN   : 'duewithin:';

OP_COMPLETED    : 'completed:';
               
OP_COMPLETED_AFTER  : 'completedafter:';

OP_COMPLETED_BEFORE : 'completedbefore:';

OP_COMPLETED_WITHIN : 'completedwithin:';

OP_ADDED            : 'added:';

OP_ADDED_AFTER      : 'addedafter:';

OP_ADDED_BEFORE     : 'addedbefore:';
                  
OP_ADDED_WITHIN     : 'addedwithin:';

OP_TIME_ESTIMATE    : 'timeestimate:';

OP_POSTPONED        : 'postponed:';

OP_IS_SHARED        : 'isshared:';

OP_SHARED_WITH      : 'sharedwith:';

// OP_IS_RECEIVED

// OP_TO

// OP_FROM

// OP_INCLUDE_ARCHIVED


/** other tokens **/

RELATION_PARAM     : ( QUOTE RELATION STRING QUOTE
                      |      RELATION STRING);


COMPLETED   : 'completed';

INCOMPLETE  : 'incomplete';

TRUE        : 'true';

FALSE       : 'false';

L_PARENTH   : '(';

R_PARENTH   : ')';

AND         : 'and';

OR          : 'or';

NOT         : 'not';
                 
QUOTE       : ('"' | '\'');

Q_STRING    : QUOTE .*? QUOTE;

STRING      : ~(':' | ' ' | '(' | ')' | '"' | '\'')+;

fragment
RELATION    : '<' | '>' | '=';

WS          : [ \t\r\n]+ -> skip;
