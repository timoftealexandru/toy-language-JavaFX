package model.expr;
import utils.*;

public class ConstExpr implements Expression {
    private int value;
    public ConstExpr(int value){
        this.value=value;
    }
    public int evaluate(ISymbolTable s,IHeap h){
        return value;
    }
    public String toString(){
        return "Const("+value+")";
    }
}
