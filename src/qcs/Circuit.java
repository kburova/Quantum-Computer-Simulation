/****************************************************
 Circuit class

 Consists of maximum of 2 registers so far, X and/or Y

 ***************************************************/
package qcs;

public class Circuit {

    private Register x;
    private Register y;
    private int numberOfGates; // equals to number of steps

    //init both registers and start with 0 gates
    public Circuit(int qubitsInX, int qubitsInY){
         x = new Register("X", qubitsInX);
         y = new Register("Y", qubitsInY);
         numberOfGates = 0;
    }

    //Implement 1 step forward through "music" cord
    public void stepForward(){

    }

    //Implement 1 step back through "music" cord
    public void stepBack(){

    }

    //Return to the beginning of circuit, restart computation
    public void restart(){

    }

    //run through all steps
    public void runAll(){

    }

    //add gate
    public void addGate(Register name, int targetQubit, int ControlQubit ){

    }

    //remove gate
    public void removeGate(){

    }

    //TODO: think if possible, to be able to delete from the middle
    //select gate
    public void selectGate(){

    }

    final public int getNumberOfOperations(){
        return numberOfGates;
    }
    
}
