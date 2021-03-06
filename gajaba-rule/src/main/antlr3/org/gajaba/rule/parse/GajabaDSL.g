grammar GajabaDSL;

options { output=AST; }

tokens {
	DOC='doc';
	SET='set';
	INPUT_VAR='input';
	STATE_VAR='state';
	STRING='string';
	REGEX = 'regex';
	FUNCTION='func';
}

@header {
package org.gajaba.rule.parse;
}

@lexer::header {
package org.gajaba.rule.parse;
}



dsl_doc         :   ( '(' dsl_set ')' )* -> ^('doc' dsl_set*) ;

dsl_set	        :   (dsl_rule ';')+ -> ^('set' dsl_rule+);

dsl_rule        :   (dsl_exp dsl_op) => dsl_op_rule | dsl_statement_rule;

dsl_op_rule     :   leftvar=dsl_exp dsl_op rightvar=dsl_exp -> ^(dsl_op $leftvar $rightvar);

dsl_statement_rule : dsl_var_state | dsl_var_state_func;

dsl_exp         :	dsl_var |  dsl_str_exp ;

dsl_str_exp 	:	STRING_CONST   -> ^('string' STRING_CONST  );

dsl_var         :   dsl_var_input | dsl_var_state | dsl_var_input_regex ;

dsl_var_state_func : dsl_func_name  '(' dsl_var_state ')' ->  ^('func' dsl_func_name dsl_var_state);

dsl_func_name   : 'min' | 'max';

dsl_var_input   :   '@' ID -> ^('input' ID);

dsl_var_input_regex :	dsl_var_input '[' STRING_CONST ',' INT  ']'  -> ^('regex' STRING_CONST INT dsl_var_input) ;

dsl_var_state   :   '#' dsl_exp -> ^('state' dsl_exp);

dsl_op	        :   OP;

OP              :   '>' | '=';

ID              :   ('a'..'z'|'A'..'Z')+ ;

COMMENT	        :	'//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;} ;

INT 	:('0'..'1')+;

STRING_CONST
@init{StringBuilder lBuf = new StringBuilder();}
    :
           '\''
           ( escaped=ESC {lBuf.append(getText());} |
             normal=~('\''|'\\'|'\n'|'\r')     {lBuf.appendCodePoint(normal);} )*
           '\''
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





