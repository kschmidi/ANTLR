#ANTLR for Java
In this Project I am going to checkout ANTLR for JAVA. I don't know where this Project is going or what I am going to do, other than getting in touch with ANTLR. But I will define the goals if I work on this Project..

***The goal(s):***

1. Create the grammar for simple calculations (+,-,*,/) and a Visitor for the Abstract Parse Tree of the calculations

***By the way:
<br />This Project is inspired by [Let's build a Compiler](http://25.io/smaller/) from [Yankees Code Academy](http://25.io/smaller/) on [Youtube](http://25.io/smaller/).***)

##What is ANTLR
If you don't know what ANTLR is click [here](http://25.io/smaller/). 

There is also a Link to download the actual [antlr.jar](http://25.io/smaller/). In my case this is version 4.5.

There is also a [Tutorial](http://25.io/smaller/) on the Website.

##Setup
As ou can see there are two Projects the Parser and the Compiler. 

The Compiler has in its references the Parser project.

	Right click on Compiler > Properties > Project References > select Parser
	
In the Project Parser in the subfolder lib is the Antlr .jar which is needed in both Project, so you have to configure the Build Path in Both Projects tho use the Antlr .jar. 

	Right click on Parser > Properties > Java Build Path > select the Tab Libraries > add JARs... > select from the lib folder the antlr.jar > then goto the Tab Order and Export and select the antlr.jar
	
Now the antlr.jar can be used in the Project Compiler.

**Your now good to go!**

