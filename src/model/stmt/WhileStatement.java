package model.stmt;

import model.*;
import model.stmt.*;
import model.expr.*;
import utils.*;

public class WhileStatement implements Statement {
    private Expression exp;
    private Statement stmt;
    public WhileStatement(Expression e,Statement s){
        this.exp=e;
        this.stmt=s;
    }
    public PrgState execute(PrgState p){
        IExeStack<Statement> exeStack=p.getExeStack();
        if(exp.evaluate(p.getSymbolTable(),p.getHeap())!=0){
            exeStack.push(this);
            exeStack.push(stmt);
        }
        return null;
    }
    public String toString(){
        return "While("+this.exp.toString()+")"+this.stmt.toString();
    }
}
