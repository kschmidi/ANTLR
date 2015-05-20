package ch.kschmidi.antlr.compiler;

import ch.kschmidi.antlr.parser.DemoBaseVisitor;
import ch.kschmidi.antlr.parser.DemoParser.NumberContext;
import ch.kschmidi.antlr.parser.DemoParser.PlusContext;

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
