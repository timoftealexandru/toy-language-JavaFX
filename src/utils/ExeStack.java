package utils;

import java.util.*;
public class ExeStack<E> implements IExeStack<E> {
    private Deque<E> d;
    public ExeStack(){
        this.d=new ArrayDeque<E>();
    }
    public Iterable<E> getAll(){
        return d;
    }
    public void push(E elem){
        d.push(elem);
    }
    public E pop(){
        return d.pop();
    }
    public boolean isEmpty(){
        return d.isEmpty();
    }
    public E top(){
        return d.peek();
    }
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("ExeStack = {");

        if (!d.isEmpty()) {
            string.append("\n");
        }

        for (E statement : d) {
            string.append("   " + statement.toString() + "\n");
        }

        string.append("}");
        return string.toString();
    }
}
