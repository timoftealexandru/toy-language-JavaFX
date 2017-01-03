package model.expr;

import utils.*;
import model.*;

public class ReadHeapExpr implements Expression {
    private String varName;
    public ReadHeapExpr(String vName){
        this.varName=vName;
    }
    public int evaluate(ISymbolTable<String,Integer> symTable,IHeap<Integer> heap){
        int  heapAddress;
        if(symTable.contains(varName)){
            heapAddress=symTable.getValue(varName);
        }else{
            throw new RuntimeException("no such variable in SymbolTable !");
        }
        int heapContent;
        if(heap.contains(heapAddress)){
            heapContent= heap.getValue(heapAddress);
        }
        else{
            throw new RuntimeException("no such variable in Heap !");
        }
        return heapContent;
    }
    public String toString(){
        return "rH("+varName+")";
    }
}
