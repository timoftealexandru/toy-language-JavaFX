package model.stmt;
import model.PrgState;
import model.expr.ConstExpr;
import model.expr.Expression;
import utils.*;
import java.lang.*;
import java.io.*;

public class ReadFileStatement implements Statement {
        private String variable;
        private Expression fileDescriptor;
        public ReadFileStatement(Expression e, String n){
            fileDescriptor=e;
            variable=n;
        }
        public PrgState execute(PrgState p){
            try {
                int descriptor = fileDescriptor.evaluate(p.getSymbolTable(),p.getHeap());
                IFileTable<Integer, FileData> fileTable = p.getFileTable();
                BufferedReader bufferedReader = fileTable.getValue(descriptor).getReader();

                String line = bufferedReader.readLine();
                ConstExpr value;

                if (line == null) {
                    value = new ConstExpr(0);
                } else {
                    value = new ConstExpr(Integer.parseInt(line));
                }

                Statement assignment = new AssignmentStatement(variable, value);

                assignment.execute(p);
                return null;
            } catch (IOException e) {
                throw new InterpreterException("error: file could not be read");
            }
        }
        public String toString(){
		    return "ReadFileStatement("+fileDescriptor.toString()+" "+ variable+")";
        }


}
