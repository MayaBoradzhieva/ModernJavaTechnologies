package bg.sofia.uni.fmi.mjt.stylechecker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;

/**
 * Checks adherence to Java style guidelines.
 */
public class StyleChecker {

	final static int MAX_LENGTH = 100;

	boolean hasUpperCaseLetter(String string) {
		return string.matches(".*[A-Z].*");
	}

	boolean containsMoreThanOneStatement(String string) {

		if (string.contains(";") && !string.contains(";;")) {
			int count = string.length() - string.replaceAll(";", "").length();
			return count > 1;
		}

		return false;

	}

	public void checkStyle(Reader source, Writer output) {

		try (BufferedReader bufferedReader = new BufferedReader(source);
				PrintWriter printWriter = new PrintWriter(output, true);) {

			String line;
			while ((line = bufferedReader.readLine()) != null) {

				String newLine = line.strip();

				if (newLine.startsWith("{")) {
					printWriter
							.println("// FIXME Opening brackets should be placed on the same line as the declaration");
				}

				if (line.contains(".*;")) {
					printWriter.println("// FIXME Wildcards are not allowed in import statements");
				}

				if (containsMoreThanOneStatement(line)) {
					printWriter.println("// FIXME Only one statement per line is allowed");

				}

				if (line.length() > MAX_LENGTH && !line.contains("import")) {
					printWriter.println("// FIXME Length of line should not exceed 100 characters");
				}

				if (line.contains("package") && (line.contains("_") || hasUpperCaseLetter(line))) {
					printWriter.println("// FIXME Package name should not contain upper-case letters or underscores");

				}

				printWriter.println(line);

			}
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}