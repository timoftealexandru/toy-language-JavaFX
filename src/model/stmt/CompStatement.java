package model.stmt;
import model.PrgState;
import utils.*;
public class CompStatement implements Statement {
    private Statement first;
    private Statement second;

    public CompStatement(Statement f,Statement s){
        first=f;
        second=s;
    }
    public PrgState execute(PrgState p){
        IExeStack exe = p.getExeStack();
        exe.push(second);
        exe.push(first);
        return null;
    }
    public String toString(){
        return ""+first.toString()+"; "+second.toString()+" ";
    }
}
