package model.stmt;
import model.PrgState;
import utils.*;
import model.expr.*;

public class NewStatement implements Statement{
    private String varName;
    private Expression expression;
    public NewStatement(String varName, Expression expr){
        this.varName=varName;
        this.expression=expr;
    }
    public PrgState execute(PrgState p){
        int result=expression.evaluate(p.getSymbolTable(),p.getHeap());
        int addr = p.getHeap().add(result);
        p.getSymbolTable().add(varName,addr);
        return null;
    }
    public String toString(){
        return "New("+varName+"," + expression.toString()+")";
    }
}
