grammar Demo;

program: expression+ ;

expression: '(' expression ')'					 #Brackets
		  | left=expression '/' right=expression #Division
		  | left=expression '*' right=expression #Multiplication
		  | left=expression '-' right=expression #Minus
		  | left=expression '+' right=expression #Plus
		  | number=NUMBER #Number
		  ;

NUMBER: [0-9]+;

WHITESPACE: [ \t\n\r]+ -> skip;
