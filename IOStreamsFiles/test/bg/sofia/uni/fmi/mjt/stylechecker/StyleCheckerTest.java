package bg.sofia.uni.fmi.mjt.stylechecker;

import static org.junit.Assert.*;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import org.junit.BeforeClass;
import org.junit.Test;

public class StyleCheckerTest {

	private static StyleChecker checker;

	@BeforeClass
	public static void setUp() {
		checker = new StyleChecker();
	}

	@Test
	public void testWildcards() {
		Reader input = new StringReader("import java.util.*;");
		Writer output = new StringWriter();

		checker.checkStyle(input, output);
		String actual = output.toString();

		assertEquals("// FIXME Wildcards are not allowed in import statements" + System.lineSeparator()
				+ "import java.util.*;", actual.strip());
	}

	@Test
	public void importStatementsWithoutWildcardsAreAllowed() {
		Reader input = new StringReader("import java.io.BufferedReader;");
		Writer output = new StringWriter();

		checker.checkStyle(input, output);
		String actual = output.toString();

		assertEquals("import java.io.BufferedReader;", actual.strip());
	}

	@Test
	public void testBrackets() {
		String text = "public static void main(String... args)" + System.lineSeparator() + "{" + System.lineSeparator()
				+ System.lineSeparator() + "}";
		Reader input = new StringReader(text);
		Writer output = new StringWriter();

		checker.checkStyle(input, output);
		String actual = output.toString();

		assertEquals(
				"public static void main(String... args)" + System.lineSeparator()
						+ "// FIXME Opening brackets should be placed on the same line as the declaration"
						+ System.lineSeparator() + "{" + System.lineSeparator() + System.lineSeparator() + "}",
				actual.strip());
	}

	@Test
	public void testBracketsWhenCorrect() {
		String text = "public static void main(String... args)" + " {" + System.lineSeparator() + System.lineSeparator()
				+ "}";
		Reader input = new StringReader(text);
		Writer output = new StringWriter();

		checker.checkStyle(input, output);
		String actual = output.toString();

		assertEquals(text, actual.strip());
	}

	@Test
	public void testStatements() {
		String text = "doSomething(); counter++; doSomethingElse(counter);";

		Reader input = new StringReader(text);
		Writer output = new StringWriter();

		checker.checkStyle(input, output);
		String actual = output.toString();

		assertEquals("// FIXME Only one statement per line is allowed" + System.lineSeparator() + text, actual.strip());
	}

	@Test
	public void testStatementsWhenMultipleSemicolons() {
		String text = "doSomething();;;;;;;;";

		Reader input = new StringReader(text);
		Writer output = new StringWriter();

		checker.checkStyle(input, output);
		String actual = output.toString();

		assertEquals(text, actual.strip());
	}

	@Test
	public void testLength() {
		String text = "Hey, it's Hannah, Hannah Baker. That's right."
				+ " Don't adjust your... whatever device you're listening to this on." + " It's me, live and in stereo";

		Reader input = new StringReader(text);
		Writer output = new StringWriter();

		checker.checkStyle(input, output);
		String actual = output.toString();

		assertEquals("// FIXME Length of line should not exceed 100 characters" + System.lineSeparator() + text,
				actual.strip());
	}

	@Test
	public void testLengthWhenThereIsImport() {
		String text = "import moderntavatechnologies.iostreamsfile.test.bg.sofia.uni."
				+ "fmi.mjt.stylechecker.stylecheckertest.java.testlengthwhenthereis";

		Reader input = new StringReader(text);
		Writer output = new StringWriter();

		checker.checkStyle(input, output);
		String actual = output.toString();

		final int maxLength = 100;
		assertTrue(text.replace("import ", "").length() > maxLength);
		assertEquals(text, actual.strip());
	}

	@Test
	public void testPackage() {
		String text = "package gov.nasa.deepSpace;";
		Reader input = new StringReader(text);
		Writer output = new StringWriter();

		checker.checkStyle(input, output);
		String actual = output.toString();

		assertEquals("// FIXME Package name should not contain upper-case letters or underscores"
				+ System.lineSeparator() + text, actual.strip());
	}

	@Test
	public void testLengthAndMultipleStatements() {
		String text = "sayHello();sayHello();sayHello();sayHello();sayHello();sayHello();sayHello();"
				+ "sayHello();sayHello();sayHello();sayHello();" + System.lineSeparator();

		Reader input = new StringReader(text);
		Writer output = new StringWriter();

		checker.checkStyle(input, output);
		String actual = output.toString();

		final int maxLength = 100;
		assertTrue(text.replace("import ", "").length() > maxLength);
		assertEquals("// FIXME Only one statement per line is allowed" + System.lineSeparator()
				+ "// FIXME Length of line should not exceed 100 characters" + System.lineSeparator()
				+ "sayHello();sayHello();sayHello();sayHello();sayHello();sayHello();"
				+ "sayHello();sayHello();sayHello();sayHello();sayHello();", actual.strip());
	}

	
}
