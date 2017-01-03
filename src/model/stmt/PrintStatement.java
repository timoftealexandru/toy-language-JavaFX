package model.stmt;

import model.PrgState;
import model.expr.Expression;

public class PrintStatement implements Statement {
    Expression exp;
    public PrintStatement(Expression e){
        exp=e;
    }
    public PrgState execute(PrgState p){
        int res=exp.evaluate(p.getSymbolTable(),p.getHeap());
        p.getOutput().add(res);
        return null;
    }
    public String toString(){
        return "Print(" +exp.toString()+")";
    }

}
