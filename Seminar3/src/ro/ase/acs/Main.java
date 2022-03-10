package ro.ase.acs;

import java.lang.reflect.InvocationTargetException;

import ro.ase.acs.contracts.Operation;

public class Main {

	public static void main(String[] args)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {
		Operation operation = (Operation) Class.forName("ro.ase.acs.SqlOperation").getDeclaredConstructor()
				.newInstance();

		Orchestrator orchestrator = new Orchestrator(operation);
		orchestrator.execute();

	}

}
