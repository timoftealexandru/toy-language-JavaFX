package model;
import model.stmt.IfStatement;
import model.stmt.Statement;
import utils.*;

import java.io.Serializable;

public class PrgState implements Serializable{
    private IExeStack<Statement> stack;
    private ISymbolTable<String, Integer> symTable;
    private IOutput<Integer> out;
    private Statement stmt;
    private IFileTable<Integer,FileData> fTable;
    private IHeap<Integer> heap;
    public int id;

    public PrgState(IExeStack<Statement> st,ISymbolTable<String,Integer> sy,IOutput<Integer> o,IFileTable<Integer,FileData> ft, IHeap<Integer> h,Statement stm){
        stack=st;
        symTable=sy;
        out=o;
        fTable=ft;
        heap=h;
        stmt=stm;
        stack.push(stmt);
        this.id=generator.generatePrgStateId();
        System.out.println(this.id);
    }

    public boolean isNotCompleted(){
        return !stack.isEmpty();
    }

    public PrgState oneStep() {
        try {
            Statement currentStatement = this.stack.pop();
            return currentStatement.execute(this);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //getter,setters

    public int getId() {return id;}
    public IFileTable<Integer,FileData> getFileTable(){return fTable; }
    public IOutput<Integer> getOutput(){return out;}
    public IExeStack<Statement> getExeStack(){
        return stack;
    }
    public IHeap<Integer> getHeap(){
        return heap;
    }
    public ISymbolTable<String,Integer> getSymbolTable(){
        return symTable;
    }

    @Override
    public String toString() {
        return  id+"\n"
                +stack.toString() + "\n"
                + symTable.toString() + "\n"
                + fTable.toString() + "\n"
                + out.toString() + "\n"
                + heap.toString();
    }

}
