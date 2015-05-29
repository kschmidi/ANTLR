package ch.kschmidi.antlr.compiler;

import ch.kschmidi.antlr.parser.DemoBaseVisitor;
import ch.kschmidi.antlr.parser.DemoParser.BracketsContext;
import ch.kschmidi.antlr.parser.DemoParser.DivisionContext;
import ch.kschmidi.antlr.parser.DemoParser.MinusContext;
import ch.kschmidi.antlr.parser.DemoParser.MultiplicationContext;
import ch.kschmidi.antlr.parser.DemoParser.NumberContext;
import ch.kschmidi.antlr.parser.DemoParser.PlusContext;

public class MyVisitor extends DemoBaseVisitor<String> {
	
	@Override
	public String visitBrackets(BracketsContext ctx) {
		return visitChildren(ctx);
	}
	
	@Override
	public String visitDivision(DivisionContext ctx) {
		return visitChildren(ctx) + "\n" + "idiv";
	}
	
	@Override
	public String visitMultiplication(MultiplicationContext ctx) {
		return visitChildren(ctx) + "\n" + "imul";
	}
	
	@Override
	public String visitMinus(MinusContext ctx) {
		return visitChildren(ctx) + "\n" + "isub";
	}

	@Override
	public String visitPlus(PlusContext ctx) {
		return visitChildren(ctx) + "\n" + "iadd";
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
