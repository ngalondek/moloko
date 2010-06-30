grammar RtmSmartFilter;

options
{
   language=java;
   output=AST;
}


/* Parser rules */

smartfilter
	: andExpression ( OR andExpression )* EOF
	| '(' smartfilter ')'
	;
	
andExpression
	: unaryExpression ( AND unaryExpression )*
	;

unaryExpression
	: NOT? expression
	;
	
expression
	: operator
	;
	
operator
	: op_list
	| op_priority
   | op_status
	;

op_list
	: OP_LIST
	;

op_priority
	: OP_PRIORITY
	;
	
op_status
	: OP_STATUS
	;
	
	
/* Lexer rules */

OP_LIST : 'list:' ( STRING | Q_STRING );

OP_PRIORITY : 'priority:' ( PRIORITY1 | PRIORITY2 | PRIORITY3 | PRIORITYN );

	PRIORITY1 : '1' | 'high';
	
	PRIORITY2 : '2' | 'normal';
	
	PRIORITY3 : '3' | 'low';
	
	PRIORITYN : 'none';

OP_STATUS : 'status:' ( STATUS_COMPLETED | STATUS_INCOMPLETE );

	STATUS_COMPLETED  : 'completed';
	
	STATUS_INCOMPLETE : 'incomplete';
	
AND   : 'AND';

OR    : 'OR';

NOT 	: 'NOT';

TRUE  : 'true';

FALSE	: 'false';



INT :	'0'..'9'+;

FLOAT
    :   ('0'..'9')+ '.' ('0'..'9')* EXPONENT?
    |   '.' ('0'..'9')+ EXPONENT?
    |   ('0'..'9')+ EXPONENT
    ;

WS  :   ( ' '
		  | '\t'
        | '\r'
        | '\n'
        ) {$channel=HIDDEN;}
    ;

Q_STRING : '"' ~('"')* '"';

STRING : ~('"' | ' ' | '(' | ')')+;

fragment
EXPONENT : ('e'|'E') ('+'|'-')? ('0'..'9')+ ;
