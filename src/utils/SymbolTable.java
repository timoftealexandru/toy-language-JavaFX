package utils;
import java.util.*;
import java.lang.*;
import java.util.stream.Collectors;

public class SymbolTable<E,F> implements ISymbolTable<E,F>{
    private HashMap<E,F> map;
    public SymbolTable(){
        map=new HashMap<>();
    }

    @Override
    public void add(E key, F value){
        map.put(key,value);
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
    public ISymbolTable clone(){
        ISymbolTable newSt=new SymbolTable();
        for(Map.Entry<E,F> entry: getAll()){
            newSt.add(entry.getKey(),entry.getValue());
        }
        return newSt;
    }

    @Override
    public Collection<F> values(){
        return this.map.values();
    }

    @Override
    public Iterable<Map.Entry<E,F>> getAll(){
        return map.entrySet();
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("SymbolTable = {");

        if (!map.isEmpty()) string.append("\n");

        for (E key : map.keySet()) {
            string.append("   " + key + " <- " + map.get(key) + "\n");
        }

        string.append("}");
        return string.toString();
    }
}
