package utils;
import java.util.*;
import java.lang.*;

public class FileTable<E,F> implements IFileTable<E,F>{
    private HashMap<E,F> map;
    public FileTable(){
        map=new HashMap<E, F>();
    }

    @Override
    public void add(E key, F value){
        map.putIfAbsent(key,value);
    }

    @Override
    public boolean contains(E key){
        return map.containsKey(key);
    }

    @Override
    public void setValue(E key,F value){
        map.put(key,value);
    }

    @Override
    public F getValue(E key){
        F value=map.get(key);
        if(value==null){
            throw new RuntimeException("no such key");
        }
        return value;
    }

    @Override
    public Iterable<Map.Entry<E,F>> getAll(){
        return map.entrySet();
    }

    @Override
    public void delete(E key){
        map.remove(key);
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("FileTable = [");

        if (!map.isEmpty()) string.append("\n");

        for (E key : map.keySet()) {
            string.append("   " + key + " - " + map.get(key) + "\n");
        }

        string.append("]");

        return string.toString();
    }
}
