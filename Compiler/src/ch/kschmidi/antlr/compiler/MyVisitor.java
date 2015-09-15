package ch.kschmidi.antlr.compiler;

import java.util.HashMap;

import ch.kschmidi.antlr.parser.DemoBaseVisitor;
import ch.kschmidi.antlr.parser.DemoParser.BracketsContext;
import ch.kschmidi.antlr.parser.DemoParser.DivisionContext;
import ch.kschmidi.antlr.parser.DemoParser.MinusContext;
import ch.kschmidi.antlr.parser.DemoParser.MultiplicationContext;
import ch.kschmidi.antlr.parser.DemoParser.NumberContext;
import ch.kschmidi.antlr.parser.DemoParser.PlusContext;
import ch.kschmidi.antlr.parser.DemoParser.VarAssignContext;
import ch.kschmidi.antlr.parser.DemoParser.VarDeclContext;
import ch.kschmidi.antlr.parser.DemoParser.VarInitContext;
import ch.kschmidi.antlr.parser.DemoParser.VariableContext;

public class MyVisitor extends DemoBaseVisitor<String> {
	private HashMap<String, Integer> varTable = new HashMap<>();
	
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
	public String visitVarDecl(VarDeclContext ctx) {
		if (varTable.containsKey(ctx.varName.getText())){
			throw new AssertionError("Variable" + ctx.varName.getText() + "already exists");
		}
		varTable.put(ctx.varName.getText(), varTable.size());
		return "";
	}
	
	@Override
	public String visitVarAssign(VarAssignContext ctx) {
		if (!varTable.containsKey(ctx.varName.getText())){
			throw new AssertionError("Variable" + ctx.varName.getText() + "doesn't exists");
		}
		return visit(ctx.expr) + "\n" + "istore " + varTable.get(ctx.varName).intValue();
	}
	
	@Override
	public String visitVarInit(VarInitContext ctx) {
		return "";
	}

	@Override
	public String visitNumber(NumberContext ctx) {
		return "ldc " + ctx.number.getText();
	}
	
	@Override
	public String visitVariable(VariableContext ctx) {
		return "iload " + varTable.get(ctx.varName).intValue();
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
