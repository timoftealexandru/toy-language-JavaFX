package utils;

public class HeapMemoryGenerator {
    private static int address=0;
    public static int generateAddress(){
        return ++address;
    }
}
