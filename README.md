#ANTLR for Java
In this Project I am going to checkout ANTLR for JAVA. I don't know where this Project is going or what I am going to do, other than getting in touch with ANTLR. But I will define the goals if I work on this Project.

***The goal(s):***

1. Automated Tests hinzufügen
2. Create the grammar for simple calculations (+,-,*,/) and a Visitor for the Abstract Parse Tree of the calculations

***By the way:
<br />This Project is inspired by [Let's build a Compiler](https://www.youtube.com/playlist?list=PLOfFbVTfT2vbJ9qiw_6fWwBAmJAYV4iUm) from [Yankee's Coding workshops](https://www.youtube.com/user/yankeecoding) on [Youtube](http://youtube.com).***

##Used Technologies
- [eclipse](https://eclipse.org)
- [antlr.jar](http://www.antlr.org/download.html)
- [jasmin](http://jasmin.sourceforge.net)
- [junit](http://junit.org)
- [junit-dataProvider](https://github.com/TNG/junit-dataprovider)

##What is ANTLR??
If you don't know what ANTLR is click [here](http://www.antlr.org). 

There is also a Link to download the actual [antlr.jar](http://www.antlr.org/download.html). In my case this is version 4.5.

There is also a small beginner [Tutorial](https://theantlrguy.atlassian.net/wiki/display/ANTLR4/Getting+Started+with+ANTLR+v4) on the Website.

##What is Jasmin??
If you don't know what Jasmin is click [here](http://jasmin.sourceforge.net)

There is also a Link to download the actual [jasmin.jar](http://sourceforge.net/projects/jasmin/files/). In my case this is version 2.4.

##What does the junit-dataProvider??
If you don't know what the junit-dataProvider is click [here](https://github.com/TNG/junit-dataprovider).

It is a very cool Tool to add Parameters to a @Test Method.

##Setup
As ou can see there are two Projects. The Parser and the Compiler. 

The Compiler has in its references the Parser project.

	Right click on Compiler > Properties > Project References > select Parser
	
In the Project Parser in the subfolder lib is the Antlr .jar which is needed in both Project, so you have to configure the Build Path in Both Projects tho use the Antlr .jar. 

	Right click on Parser > Properties > Java Build Path > select the Tab Libraries > add JARs... > select from the lib folder the antlr.jar > then goto the Tab Order and Export and select the antlr.jar
	
Now the antlr.jar can be used in the Project Compiler.

To use the jasmin.jar do the last step again, but for the jasmin.jar

**Your now good to go!**

