package model.stmt;
import java.io.*;
import java.util.*;

import model.PrgState;
import utils.*;
public class OpenFileStatement implements Statement {
    private String fName;
    private String varName;
    public OpenFileStatement(String vName,String fName){
        this.fName=fName;
        this.varName=vName;
    }
    public PrgState execute(PrgState p){
        if(isOpen(fName, p.getFileTable())){
            throw new InterpreterException("File is already open "+ this.fName);
        }
        try{
            BufferedReader br=new BufferedReader(new FileReader (fName));
            FileData fd=new FileData(fName,br);
            int id=FileIdGenerator.generateId();
            p.getFileTable().add(id,fd);
            p.getSymbolTable().add(varName,id);
        }
        catch(IOException e){
            throw new InterpreterException("Error while reading from file: "+e,e);
        }
        return null;
    }
    private boolean isOpen(String fileName,IFileTable<Integer,FileData> fileTable){
        for(Map.Entry<Integer,FileData> it: fileTable.getAll()){
            if(fileName.equals(it.getValue().getFileName()))
                return true;
        }
        return false;
    }
    public String toString(){
        return "OpenFile("+fName+" "+varName+")";
    }
    
}
