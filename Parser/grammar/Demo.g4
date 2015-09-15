grammar Demo;

program: expression+ 
	   | varDecl ';'
	   | varAssign ';'
	   | varInit ';'
	   ;

expression: '(' expression ')'					 #Brackets
		  | left=expression '/' right=expression #Division
		  | left=expression '*' right=expression #Multiplication
		  | left=expression '-' right=expression #Minus
		  | left=expression '+' right=expression #Plus
		  | number=NUMBER #Number
		  | varName=IDENTIFIER #Variable
		  ;

varDecl: 'int' varName=IDENTIFIER ;
varAssign: varName=IDENTIFIER '=' expr=expression ;
varInit: 'int' varName=IDENTIFIER '=' expr=expression ;

NUMBER: [0-9]+ ;
IDENTIFIER: [a-z][a-zA-Z0-9]* ;
WHITESPACE: [ \t\n\r]+ -> skip ;
