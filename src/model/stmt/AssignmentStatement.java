package model.stmt;
import model.expr.Expression;
import model.PrgState;
import utils.*;
public class AssignmentStatement implements Statement {
    public String var;
    public Expression expr;
    public AssignmentStatement(String v, Expression e){
        var=v;
        expr=e;
    }
    public PrgState execute(PrgState p) {
        ISymbolTable t= p.getSymbolTable();
        int result = expr.evaluate(t,p.getHeap());
        t.add(var,result);
        return null;
    }
    public String toString(){
        return ""+var+"="+expr.toString()+"";
    }
}
