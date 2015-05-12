package ch.kschmidi.antlr.compiler;

import ch.kschmidi.antlr.parser.DemoBaseVisitor;
import ch.kschmidi.antlr.parser.DemoParser.AdditionContext;

public class MyVisitor extends DemoBaseVisitor<String>{
	
	@Override
	public String visitAddition(AdditionContext ctx) {
		visitChildren(ctx);
		
		if (ctx.getChildCount() == 1){
			System.out.println(ctx.getChild(0));
		}else{
			System.out.println(ctx.getChild(2));
			System.out.println("addition");
		}
		
		return null;
	}
	
}
