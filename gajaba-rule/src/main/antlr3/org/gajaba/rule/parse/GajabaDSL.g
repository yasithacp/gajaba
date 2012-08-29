grammar GajabaDSL;

options { output=AST; }

tokens {
	DOC='doc';
	INPUT_VAR='input';
	STATE_VAR='state';
	STRING='string';
	REGEX = 'regex';
}

@header {
package org.gajaba.rule.parse;
}

@lexer::header {
package org.gajaba.rule.parse;
}



dsl_doc         :   (dsl_rule ';')+ -> ^('doc' dsl_rule+);

dsl_rule        :   leftvar=dsl_exp dsl_op rightvar=dsl_exp -> ^(dsl_op $leftvar $rightvar);

dsl_exp         :	dsl_var |  dsl_str_exp ;

dsl_str_exp 	:	STRING_CONST   -> ^('string' STRING_CONST  );

dsl_var         :   dsl_var_input | dsl_var_state | dsl_var_input_regex;

dsl_var_input   :   '@' ID -> ^('input' ID);

dsl_var_input_regex :	dsl_var_input '[' STRING_CONST  ']'  -> ^('regex' STRING_CONST dsl_var_input) ;

dsl_var_state   :   '#' dsl_exp -> ^('state' dsl_exp);

dsl_op	        :   OP;

OP              :   '>' | '=';

ID              :   ('a'..'z'|'A'..'Z')+ ;

COMMENT	        :	'//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;} ;


STRING_CONST
@init{StringBuilder lBuf = new StringBuilder();}
    :
           '"'
           ( escaped=ESC {lBuf.append(getText());} |
             normal=~('"'|'\\'|'\n'|'\r')     {lBuf.appendCodePoint(normal);} )*
           '"'
           {setText(lBuf.toString());}
    ;

fragment
ESC
    :   '\\'
        (   'n'    {setText("\n");}
        |   'r'    {setText("\r");}
        |   't'    {setText("\t");}
        |   'b'    {setText("\b");}
        |   'f'    {setText("\f");}
        |   '"'    {setText("\"");}
        |   '\''   {setText("\'");}
        |   '/'    {setText("/");}
        |   '\\'   {setText("\\");}

        )
    ;

