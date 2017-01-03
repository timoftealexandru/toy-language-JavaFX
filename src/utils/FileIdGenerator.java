package utils;

public class FileIdGenerator{
    private static int counter=0;
    public static int generateId(){
        return ++counter;
    }
}
