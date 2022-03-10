package ro.ase.acs;

import ro.ase.acs.contracts.Operation;

public class Orchestrator {
	
	    private Operation operation;
	    public Orchestrator(Operation operation){
	        this.operation=operation;
	    }
	    public void execute(){
	        operation.create();
	        operation.insert();
	        operation.read();
	    }
}
