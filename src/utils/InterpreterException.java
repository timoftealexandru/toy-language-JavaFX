package utils;

public class InterpreterException extends RuntimeException {
    public InterpreterException(String s){
        super(s);
    }
    public InterpreterException(String s, Throwable t){
       super(s,t);
    }
}
