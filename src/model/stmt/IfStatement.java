package model.stmt;

import model.PrgState;
import model.expr.Expression;

public class IfStatement implements Statement {
    Expression exp;
    Statement thenS;
    Statement elseS;
    public IfStatement(Expression e, Statement s1,Statement s2){
        exp=e;
        thenS=s1;
        elseS=s2;
    }
    public PrgState execute(PrgState state){
        if(exp.evaluate(state.getSymbolTable(),state.getHeap())==0){
            state.getExeStack().push(elseS);
        }else{
            state.getExeStack().push(thenS);
        }
        return null;
    }
    public String toString(){
        return "IF("+ exp.toString()+") THEN(" +thenS.toString()+")ELSE("+elseS.toString()+")";

    }
}
