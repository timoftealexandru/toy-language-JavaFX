package model.stmt;
import model.expr.Expression;
import utils.*;
import model.*;

public class WriteHeapStatement implements Statement {
    private String varName;
    private Expression expression;
    public WriteHeapStatement(String vName, Expression expr){
        this.varName=vName;
        this.expression=expr;
    }
    public PrgState execute(PrgState p) {
        int expResult = expression.evaluate(p.getSymbolTable(), p.getHeap());
        int heapAddress = p.getSymbolTable().getValue(varName);

        if (p.getHeap().contains(heapAddress)) {
            p.getHeap().setValue(heapAddress, expResult);
        } else {
            throw new RuntimeException("Invalid heap memory address ! ");
        }
        return null;
    }
    public String toString(){
        return "wH("+varName+","+expression.toString()+")";
    }
}
