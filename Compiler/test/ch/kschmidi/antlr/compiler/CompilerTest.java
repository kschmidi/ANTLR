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
				{ "1+2", "3" }, 
				{ "20+22", "42" }, 
				{ "1+2+3", "6" }
			   };
		//@formatter:on
	}

	@Test
	@UseDataProvider("provide_code_expectedText")
	public void test(String code, String expectedText) throws IOException,
			Exception {
		// execution
		String actualOutput = compileAndRun(code);

		// evaluation
		System.out.print("Expected:");
		System.out.print(expectedText + "\t");
		System.out.println(expectedText.length());
		System.out.print("Actual:");
		System.out.println(actualOutput+ "\t");
		System.out.println(expectedText.length());
		System.out.println("Comparision: " + expectedText.compareTo(actualOutput));
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
		Process process = Runtime.getRuntime().exec(new String[] { "java", "-cp", dir.toString(), className });
		try (InputStream in = process.getInputStream()) {
			return new Scanner(in).useDelimiter("\\A").next();
		}
	}

}
