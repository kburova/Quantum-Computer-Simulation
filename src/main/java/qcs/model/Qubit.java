/****************************************************
 Single Qubit class

 Qubit can be written as superposition α|0> + β|1>,
 where α and β are some values such that
 |α|^2+|β|^2 = 1

 Created by: Ksenia Burova
            Parker Diamond
            Nick Kelley
            Chris Martin

 Date: 03/18/2017
 ***************************************************/

package qcs.model;

public class Qubit {

    private int id;
    private String register;
    private double alpha;
    private double betta;
    private int value;

    // initialize qubit to 0 when register is created
    // superposition = 0, if α = 1 and β = 0
    public Qubit(int newId, String registerName){
        alpha = 1;
        betta = 0;
        id = newId;
        register = registerName;
        value = 0;
    }

//    public void setId(int newId){
//        id = newId;
//    }
//
//    public void setRegister(String registerName){
//        register = registerName;
//    }

    public void setAlpha(double a){
        alpha = a;
    }

    public void setBetta(double b){
        betta = b;
    }

    final public int getId(){
        return id;
    }

    final public double getAlpha(){
        return alpha;
    }
    final public double getBetta(){
        return betta;
    }

    final public String getRegister(){
        return register;
    }

    //calculate superposition to determine color of qubit
    public void calculateState(){
        //TODO: claculations here
    }

    public int getValue(){
        return value;
    }
}
