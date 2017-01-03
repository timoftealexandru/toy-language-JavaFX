package utils;

/**
 * Created by Nicu on 12/7/2016.
 */
public class generator{
    public static int fileTableId=0,heapId=0,PrgStateId=1;
    public static int generatePrgStateId(){
        return PrgStateId++;
    }
}