grammar GajabaDSL;

options { output=AST; }

tokens {
	DOC='doc';
	RULE='rule';
}

@header {
package org.gajaba.rule.parse;
}

@lexer::header {
package org.gajaba.rule.parse;
}

dsl_doc	:	(dsl_rule ';')+ -> ^('doc' dsl_rule+);

dsl_rule	:	leftvar=dsl_var dsl_op rightvar=dsl_var -> ^(dsl_op $leftvar $rightvar);

dsl_var	:	ID;

dsl_op	:	OP;

OP	:	'>' | '=';

ID	:	 ('a'..'z'|'A'..'Z')+ ;





