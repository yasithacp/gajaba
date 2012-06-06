grammar GajabaDSL;

options { output=AST; }

tokens {
	DOC='doc';
	INPUT_VAR='input';
	STATE_VAR='state';
}

@header {
package org.gajaba.rule.parse;
}

@lexer::header {
package org.gajaba.rule.parse;
}

dsl_doc       :   (dsl_rule ';')+ -> ^('doc' dsl_rule+);

dsl_rule      :   leftvar=dsl_var dsl_op rightvar=dsl_var -> ^(dsl_op $leftvar $rightvar);

dsl_var       :   dsl_var_input | dsl_var_state;

dsl_var_input :   '@' ID -> ^('input' ID);

dsl_var_state :   ID -> ^('state' ID);

dsl_op	      :   OP;

OP            :   '>' | '=';

ID            :   ('a'..'z'|'A'..'Z')+ ;




