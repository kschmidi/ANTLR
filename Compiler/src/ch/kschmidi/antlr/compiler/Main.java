package ch.kschmidi.antlr.compiler;

import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import ch.kschmidi.antlr.parser.DemoLexer;
import ch.kschmidi.antlr.parser.DemoParser;

public class Main {

	public static void main(String[] args) throws Exception {
		ANTLRInputStream input = new ANTLRFileStream("resources/code.demo");
		DemoLexer lexer = new DemoLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		DemoParser parser = new DemoParser(tokens);
		
		ParseTree tree = parser.addition();
		new MyVisitor().visit(tree);
	}

}
