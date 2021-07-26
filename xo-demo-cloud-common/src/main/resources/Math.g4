grammar Math;

@header{package com.xo.web.antlr.config;}

//Parser（语法） 规则（即 non-terminal）以小写字母开始

//以规则prog作为根规则，prog由多条语句stat组成
prog : stat+;

//而语句stat可以是：表达式语句exprState或者：赋值语句assignState；
stat: expr NEWLINE          # printExpr //exprState
    | ID '=' expr NEWLINE   # assign    //assignState
    | NEWLINE               # blank
    ;
//依次向下，到最后一层语法规则表达式expr,表达式可以是由表达式组成的加减乘除运算，或者是整数INT、变量ID，
// 注意expr规则使用了递归的表达，即在expr规则的定义中引用了expr，这也是ANTLR v4的一个特点
expr:  expr op=('*'|'/') expr   # MulDiv
| expr op=('+'|'-') expr        # AddSub
| INT                           # int
| ID                            # id
| '(' expr ')'                  # parens
;

//Lexer(词法) 规则（即 terminal）以大写字母开始;// assigns token name to '*' used above in grammar
MUL : '*' ;
DIV : '/' ;
ADD : '+' ;
SUB : '-' ;
ID : [a-zA-Z]+ ;
INT : [0-9]+ ;
NEWLINE:'\r'? '\n' ;
WS : [ \t]+ -> skip;
/**
 标识符
 符号(Token)名大写开头
 解析规则(Parser rule)名小写开头,后面可以跟字母、数字、下划线
 ID, LPAREN, RIGHT_CURLY // token names
 expr, simpleDeclarator, d2, header_file // rule names
*/
