package ch.kschmidi.antlr.compiler;

import static org.junit.Assert.assertEquals;
import jasmin.ClassFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;

@RunWith(DataProviderRunner.class)
public class CompilerTest {

	private Path tempDir;

	@Before
	public void createTempDir() throws IOException {
		tempDir = Files.createTempDirectory("compilerTest");
	}

	@After
	public void deleteTempDir() {
		deleteRecursive(tempDir.toFile());
	}

	private void deleteRecursive(File file) {
		if (file.isDirectory()) {
			for (File child : file.listFiles()) {
				deleteRecursive(child);
			}
		}
		if (!file.delete()) {
			throw new Error("Could not delete file <" + file.getName() + ">");
		}
	}

	@DataProvider
	public static Object[][] provide_code_expectedText() {
		//@formatter:off
		return new Object[][] { 
				// Additions
				{ "1+2", "3" + System.lineSeparator() }, 
				{ "20+22", "42" + System.lineSeparator() }, 
				{ "1+2+3", "6" + System.lineSeparator() },
				
				// Subtractions
				{ "5-1", "4" + System.lineSeparator() },
				{ "42-5", "37" + System.lineSeparator() },
				{ "42-16-5", "21" + System.lineSeparator() },
				
				// Mix of subtractions and additions
				{"9+5-3", "11" + System.lineSeparator()},
				{"7-4+8", "11" + System.lineSeparator()},
				{"42+56-9", "89" + System.lineSeparator()},
				
				// Multiplications
				{"3*2", "6" + System.lineSeparator()},
				{"12*34", "408" + System.lineSeparator()},
				{"1*4*6", "24" + System.lineSeparator()},
				
				// Mix of multiplications and subtractions and additions
				{"1+2*5", "11" + System.lineSeparator()},
				{"1+2*5-11", "0" + System.lineSeparator()},
				{"15*1-15/3+4", "14" + System.lineSeparator()},
				{"20/4-3*2+36/6", "5" + System.lineSeparator()}
			   };
		//@formatter:on
	}

	@Test
	@UseDataProvider("provide_code_expectedText")
	public void test(String code, String expectedText) throws IOException, Exception {
		// execution
		String actualOutput = compileAndRun(code);

		// evaluation
		assertEquals(expectedText, actualOutput);
	}

	private String compileAndRun(String code) throws IOException, Exception {
		code = Main.compile(new ANTLRInputStream(code));
		ClassFile classFile = new ClassFile();
		classFile.readJasmin(new StringReader(code), "", false);
		Path outputPath = tempDir.resolve(classFile.getClassName() + ".class");
		classFile.write(Files.newOutputStream(outputPath));
		return runJavaClass(tempDir, classFile.getClassName());
	}

	private String runJavaClass(Path dir, String className) throws IOException {
		Process process = Runtime.getRuntime().exec(
				new String[] { "java", "-cp", dir.toString(), className });
		try (InputStream in = process.getInputStream()) {
			return new Scanner(in).useDelimiter("\\A").next();
		}
	}

}
