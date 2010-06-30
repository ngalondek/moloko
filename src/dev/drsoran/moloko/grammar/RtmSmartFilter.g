grammar RtmSmartFilter;

options
{
   language=java;
}

/* Parser rules */

smartfilter
	: operator ( OP_LOG_BIN operator )*
	;
	
operator
	: OP_LIST
	| OP_PRIORITY ( PRIORITY1 | PRIORITY2 | PRIORITY3 | PRIORITYN )
	| OP_STATUS ( STATUS_COMPLETED | STATUS_INCOMPLETE )
	;



/* Lexer rules */

OP_LIST 		: 'list:' ( STRING | Q_STRING );

OP_PRIORITY : 'priority:';

	PRIORITY1 : '1' | 'high';
	
	PRIORITY2 : '2' | 'normal';
	
	PRIORITY3 : '3' | 'low';
	
	PRIORITYN : 'none';

OP_STATUS 	: 'status:';

	STATUS_COMPLETED  : 'completed';
	
	STATUS_INCOMPLETE : 'incomplete';
	
OP_LOG_BIN	: AND | OR;

fragment
AND 			: 'AND';

fragment
OR 			: 'OR';

NOT 			: 'NOT';

TRUE 			: 'true';

FALSE 		: 'false';



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

STRING : ~('"')+;

fragment
EXPONENT : ('e'|'E') ('+'|'-')? ('0'..'9')+ ;
