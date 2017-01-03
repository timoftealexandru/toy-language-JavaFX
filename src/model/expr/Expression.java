package model.expr;
import utils.*;
import java.io.Serializable;

public interface Expression extends Serializable{
    public int evaluate(ISymbolTable<String,Integer> s,IHeap<Integer> heap);
}
