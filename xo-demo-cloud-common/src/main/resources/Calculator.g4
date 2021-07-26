// file: Calculator.g4
grammar Calculator;
@header{package com.xo.common.antlr.config.calculator;}


line : expr EOF ;
expr : '(' expr ')'             # parenExpr
     | expr ('*'|'/') expr      # multOrDiv
     | expr ('+'|'-') expr      # addOrSubstract
     | FLOAT                    # float
     ;

WS : [ \t\n\r]+ -> skip;
FLOAT : DIGIT+ '.' DIGIT* EXPONET?
      | '.' DIGIT+ EXPONET?
      | DIGIT+ EXPONET?
      ;

fragment DIGIT : '0'..'9' ;
fragment EXPONET : ('e'|'E') ('+'|'-')? DIGIT+ ;
