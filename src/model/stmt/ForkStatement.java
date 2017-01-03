package model.stmt;
import model.stmt.*;
import model.*;
import utils.*;
/**
 * Created by Nicu on 12/7/2016.
 */
public class ForkStatement implements Statement{
    private Statement stmt;
    public ForkStatement(Statement s){
        this.stmt=s;
    }
    public PrgState execute(PrgState p){

        ISymbolTable st=p.getSymbolTable();
        ISymbolTable newSt=st.clone();
        PrgState newPs=new PrgState(new ExeStack<Statement>(), newSt, p.getOutput() ,p.getFileTable(),p.getHeap(),stmt);
        return newPs;
    }
    public String toString(){
		return "fork("+stmt.toString()+ ")";
    }
}
