grammar GajabaDSL;

options { output=AST; }

tokens {
	DOC='doc';
	INPUT_VAR='input';
	STATE_VAR='state';
	STRING='string';
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

dsl_str_exp 	:	STRING_CONSTANT -> ^('string' STRING_CONSTANT);

dsl_var         :   dsl_var_input | dsl_var_state;

dsl_var_input   :   '@' ID -> ^('input' ID);

dsl_var_state   :   '#' dsl_exp -> ^('state' dsl_exp);

dsl_op	        :   OP;

OP              :   '>' | '=';

ID              :   ('a'..'z'|'A'..'Z')+ ;

STRING_CONSTANT :   '"' ~('"')* '"';

COMMENT	        :	'//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;} ;




