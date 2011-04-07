grammar RtmSmartAdd;

@option
{
   language=Java;
}

@header
{
   package dev.drsoran.moloko.grammar;
}

@lexer::header
{
   package dev.drsoran.moloko.grammar;
}

@members
{
   static final int SUGG_DUE           = 0;
   static final int SUGG_ESTIMATE      = 1;
   static final int SUGG_LIST_AND_TAGS = 2;
   static final int SUGG_LOCATION      = 3;
   static final int SUGG_PRIORITY      = 4;
   static final int SUGG_REPEAT        = 5;
   
   static final int HAS_DUE            = 1 << 0;
   static final int HAS_ESTIMATE       = 1 << 1;
   static final int HAS_LIST_AND_TAGS  = 1 << 2;
   static final int HAS_LOCATION       = 1 << 3;
   static final int HAS_PRIORITY       = 1 << 4;
   static final int HAS_REPEAT         = 1 << 5;
}

// RULES

parseSmartAdd
   : name=STRING
   (
        (
           OP_DUE_DATE     due=STRING
           {
           }
        )
      | (
           OP_ESTIMATE     est=STRING
           {
           }
        )
      | (
           OP_LIST_OR_TAGS tag=STRING
           {
           }
        )
      | (
           OP_LOCATION     loc=STRING
           {
           }
        )
      | (
           OP_PRIORITY     pri=STRING
           {
           }
        )
      | (
           OP_REPEAT       rep=STRING
           {
           }
        )
   )*
   ;


// TOKENS

OP_DUE_DATE     : '^';

OP_PRIORITY     : '!';

OP_LIST_OR_TAGS : '#';

OP_LOCATION     : '@';

OP_REPEAT       : '*';

OP_ESTIMATE     : '=';

STRING          : ~(OP_DUE_DATE | OP_PRIORITY | OP_LIST_OR_TAGS | OP_REPEAT | OP_ESTIMATE)+;
