grammar RtmSmartFilter;

options
{
   language=java;
}

/* Parser rules */

smartfilter
	: operator ( operator )*
	;
	
operator
	: OP_LIST STRING
	| OP_PRIORITY ( PRIORITY1 | PRIORITY2 | PRIORITY3 | PRIORITYN )
	| OP_STATUS	  ( STATUS_COMPLETED | STATUS_INCOMPLETE )
	;



/* Lexer rules */

OP_LIST 		: 'list:';

OP_PRIORITY : 'priority:';

	PRIORITY1 : '1';
	
	PRIORITY2 : '2';
	
	PRIORITY3 : '3';
	
	PRIORITYN : 'none';

OP_STATUS 	: 'status:';

	STATUS_COMPLETED  : 'completed';
	
	STATUS_INCOMPLETE : 'incomplete';

AND 			: 'AND';

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

WS  :   ( '\t'
        | '\r'
        | '\n'
        ) {$channel=HIDDEN;}
    ;

STRING
    :  '"' ~('\\'|'"')* '"'
    |  ('a'..'z'| 'A'..'Z' | '0'..'9')+
    ;

fragment
EXPONENT : ('e'|'E') ('+'|'-')? ('0'..'9')+ ;

