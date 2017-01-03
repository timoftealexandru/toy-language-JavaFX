package utils;
import java.io.*;

public class FileData{
    private String fName;
    private BufferedReader bufferedReader;

    public FileData(String name, BufferedReader b){
        this.fName=name;
        bufferedReader=b;
    }
    //getters+setters

    public BufferedReader getReader(){
        return bufferedReader;
    }
    public String getFileName(){
        return fName;
    }
    public String toString(){
        return fName;
    }
}