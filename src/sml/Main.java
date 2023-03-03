package sml;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class Main {
	/**
	 * Initialises the system and executes the program.
	 *
	 * @param args name of the file containing the program text.
	 */
	public static void main(String... args) {
		// FYI: arg used = "resources/sml/factorial.sml"
		if (args.length != 1) {
			System.err.println("Incorrect number of arguments - Machine <file> - required");
			System.exit(-1);
		}
		try {
			System.setProperty("fileName", args[0]);
			var factory = new ClassPathXmlApplicationContext("beans.xml");
			Machine m = (Machine) factory.getBean("machine");
			Translator t = (Translator) factory.getBean("translator");
			t.readAndTranslate(m.getLabels(), m.getProgram());

			System.out.println("Here is the program; it has " + m.getProgram().size() + " instructions.");
			System.out.println(m);

			System.out.println("Beginning program execution.");
			m.execute();
			System.out.println("Ending program execution.");

			System.out.println("Values of registers at program termination:" + m.getRegisters() + ".");
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error reading the program from " + args[0]);
		}
	}
}
