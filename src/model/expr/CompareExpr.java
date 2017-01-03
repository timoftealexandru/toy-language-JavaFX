package model.expr;

import utils.IHeap;
import utils.ISymbolTable;
import utils.InterpreterException;

public class CompareExpr implements Expression {
    private String type;
    private Expression exp1, exp2;
    public CompareExpr(String type, Expression e1, Expression e2) {
        this.type = type;
        this.exp1 = e1;
        this.exp2 = e2;
    }

    @Override
    public int evaluate(ISymbolTable<String, Integer> symTable, IHeap<Integer> heap) {
        int val1 = this.exp1.evaluate(symTable, heap);
        int val2 = this.exp2.evaluate(symTable, heap);
        switch (this.type) {
            case "<":
                return val1 < val2 ? 1 : 0;
            case "<=":
                return val1 <= val2 ? 1 : 0;
            case "==":
                return val1 == val2 ? 1 : 0;
            case "!=":
                return val1 != val2 ? 1 : 0;
            case ">":
                return val1 > val2 ? 1 : 0;
            case ">=":
                return val1 >= val2 ? 1 : 0;
            default:
                throw new InterpreterException("Unknown comparison exception: " + this.type + "\n" + "At: " + this.toString());
        }
    }

    @Override
    public String toString() {
        return "CompExp (" + this.type + ", " + this.exp1.toString() + ", " + this.exp1.toString();
    }
}
