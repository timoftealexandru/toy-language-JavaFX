package model.expr;
import model.expr.Expression;
import utils.*;
public class VarExpr implements Expression {
    private String name;
    public VarExpr(String name){
        this.name=name;
    }

    public int evaluate(ISymbolTable<String,Integer> symTable, IHeap<Integer> heap){
        if(symTable.contains(name)){
            return symTable.getValue(name);
        }else{
            throw new RuntimeException("no such variable !");
        }
    }
    public String toString(){
        return "Var("+name+")";
    }
}
