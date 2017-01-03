package model.stmt;
import model.PrgState;
import model.expr.Expression;
import utils.*;
import java.io.*;

public class CloseReadFileStatement implements Statement {
    private Expression fileDescriptor;
    public CloseReadFileStatement(Expression e){
        this.fileDescriptor=e;
    }

    public PrgState execute(PrgState p){
        try {
            int descriptor = fileDescriptor.evaluate(p.getSymbolTable(),p.getHeap());
            IFileTable<Integer, FileData> fileTable = p.getFileTable();
            BufferedReader bufferedReader = fileTable.getValue(descriptor).getReader();

            bufferedReader.close();
            fileTable.delete(descriptor);

            return null;

        } catch (IOException e) {
            throw new InterpreterException("error: file could not be closed");
        }
    }
    public String toString(){
		return "CloseRFile("+fileDescriptor.toString()+")";
    }
}
