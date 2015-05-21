#ANTLR for Java
In this Project I am going to checkout ANTLR for JAVA. I don't know where this Project is going or what I am going to do, other than getting in touch with ANTLR. But I will define the goals if I work on this Project.

***The goal(s):***

1. Add Automated Tests
2. Create the grammar for simple calculations (+,-,*,/) and a Visitor for the Abstract Parse Tree of the calculations

***By the way:
<br />This Project is inspired by [Let's build a Compiler](https://www.youtube.com/playlist?list=PLOfFbVTfT2vbJ9qiw_6fWwBAmJAYV4iUm) from [Yankee's Coding workshops](https://www.youtube.com/user/yankeecoding) on [Youtube](http://youtube.com).***

##Used Technologies
- [eclipse](https://eclipse.org)
- [antlr.jar](http://www.antlr.org/download.html)
- [jasmin](http://jasmin.sourceforge.net)
- [junit](http://junit.org)
- [junit-dataProvider](https://github.com/TNG/junit-dataprovider)
- [TM Terminal Plugin for Eclipse](http://marketplace.eclipse.org/content/tm-terminal)

##What is ANTLR??
If you don't know what ANTLR is click [here](http://www.antlr.org). 

There is also a Link to download the actual [antlr.jar](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22org.antlr%22). In my case this is version 4.5.

There is also a small beginner [Tutorial](https://theantlrguy.atlassian.net/wiki/display/ANTLR4/Getting+Started+with+ANTLR+v4) on the Website.

##What is Jasmin??
If you don't know what Jasmin is click [here](http://jasmin.sourceforge.net)

There is also a Link to download the actual [jasmin.jar](http://search.maven.org/#search%7Cga%7C1%7Cjasmin). In my case this is version 2.4.

##What does the junit-dataProvider??
If you don't know what the junit-dataProvider is click [here](https://github.com/TNG/junit-dataprovider).

It is a very cool Tool to add Parameters to a @Test Method.

##TM Terminal Plugin
If you don't know what the TM Terminal Plugin is click [here](http://marketplace.eclipse.org/content/tm-terminal).

It allows you to use the terminal in eclipse, which makes it easier to run terminal commands. 

To use all commands you have to set the Path. I created a script which echos me my path and reset the eclipse terminal path with my path

My script:
	
	#!/bin/bash

	echo $PATH:~/bin:/usr/local/sbin
	
Maybe you need to add more useful paths to it separated with ":".

Then you can run this command to set the path in the eclipse terminal:
	
	PATH=$(your_script_name)

##Setup
As ou can see there are two Projects. The Parser and the Compiler. 

The Compiler has in its references the Parser project.

	Right click on Compiler > Properties > Project References > select Parser
	
In the Project Parser in the subfolder lib is the Antlr .jar which is needed in both Project, so you have to configure the Build Path in Both Projects tho use the Antlr .jar. 

	Right click on Parser > Properties > Java Build Path > select the Tab Libraries > add JARs... > select from the lib folder the antlr.jar > then goto the Tab Order and Export and select the antlr.jar
	
Now the antlr.jar can be used in the Project Compiler.

To use the jasmin.jar do the last step again, but for the jasmin.jar and without the Order and Export part.

**Your now good to go!**

##First steps
###Grammar
At first we start with creating a grammar for our language. I called it Demo.g4 (it's in the gammar folder). Of course you can name it what ever you want as long it has the .g4 postfix. 

At the beginning it may looks like this:

	grammar Demo;

	addition: left=addition '+' right=NUMBER #Plus
			| number=NUMBER #Number
			;

	NUMBER: [0-9]+;

At first we define that this is going to be the grammar of Demo (or whatever you named it). Then it is followed with rules. The left=addition means just that addition is labeled left in the Visitor as same as for #Plus, which is going to be more readable in the Visitor and explained later. 

###Generate the Parser from the grammar
Now we can generate, with the help of the antlr.jar, the Parser Project.

Open the terminal change to the directory of the grammar and use the following command:

	java -jar ../lib/antlr-4.5-complete.jar -package ch.kschmidi.antlr.parser -o ../src/ch/kschmidi/antlr/parser -no-listener -visitor Demo.g4
	
This will generate the Parser.

***Note: the path may differ from your projects***

###Create the Visitor for the Compiler
The Visitor helps us to generate the jasmin file by visiting the abstract syntax tree. By every leaf or node it visits it returns a String, wich is added to the jasmin file.

In my case it looks like this:

	public class MyVisitor extends DemoBaseVisitor<String> {

		@Override
		public String visitPlus(PlusContext ctx) {
			return visitChildren(ctx) + "\nldc " + ctx.right.getText() + "\n" + "iadd";
		}

		@Override
		public String visitNumber(NumberContext ctx) {
			return "ldc " + ctx.number.getText();
		}

		@Override
		protected String aggregateResult(String aggregate, String nextResult) {
			if (aggregate == null) {
				return nextResult;
			}
			if (nextResult == null) {
				return aggregate;
			}
			return aggregate + "\n" + nextResult;
		}
		
	}

###Get it all together
Now we got all. We just have to get it all to work with eachother, wich is done in the main. 

	ANTLRInputStream input = new ANTLRFileStream("resources/code.demo");
	DemoLexer lexer = new DemoLexer(input);
	CommonTokenStream tokens = new CommonTokenStream(lexer);
	DemoParser parser = new DemoParser(tokens);
		
	ParseTree tree = parser.addition();
	new MyVisitor().visit(tree)
	
Now the Visitor is used to create the jasmin file, which need a bit more than just the information in the abstract parse tree.

This could be done like this:

	createJasminFile(new MyVisitor().visit(tree));
	
Where the createJasminFile method looks like this:
	
	private static String createJasminFile(String instructions) {
		return ".class public HelloWorld\n"
				+ ".super java/lang/Object\n"
				+ "\n"
				+ ".method public static main([Ljava/lang/String;)V\n"
				+ "   .limit stack 100\n"
				+ "   .limit locals 100\n"
				+ "\n"
				+ "   getstatic java/lang/System/out Ljava/io/PrintStream;\n"
				+ instructions + "\n"
				+ "   invokevirtual java/io/PrintStream/println(I)V\n"
				+ "return\n"
				+ "\n"
				+ ".end method";
	}

***Done!***
	
##Further steps
Now we can improve the grammar and do the first steps again to improve our language.

#Now have fun!
