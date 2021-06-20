grammar Rows;

@header { package com.xo.common.antlr.config.rows;}

@parser::members {    // add members to generated RowsParser
    int col;
    public RowsParser(TokenStream input, int col) {    // custom constructor
        this(input);
        this.col = col;
    }
}

file: (row NL)+ ;

row //匹配任意字符，不包含(\t\r\n)

locals [int i=0]
    : ( STUFF
        {
        $i++;
        if ( $i == col ) System.out.println($STUFF.text);
        }
      )+
    ;
TAB  :  '\t' -> skip ;    // match but don't pass to the parser
NL   :  '\r'? '\n' ;      // match and pass to the parser
STUFF:  ~[\t\r\n]+ ;      // match any chars except tab, newline