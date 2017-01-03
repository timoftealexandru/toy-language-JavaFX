package utils;
import java.lang.*;
import java.util.*;

public class Heap<F> implements IHeap<F> {
    private Map<Integer,F> heap;
    private int memory;

    public Heap(){
        this.heap=new HashMap<>();
    }

    public Integer add(F content){
        int newAddress=HeapMemoryGenerator.generateAddress();
        ++ this.memory;
        heap.put(memory,content);
        return memory;
    }
    public Iterable<Map.Entry<Integer,F>> getAll(){
        return heap.entrySet();
    }

    @Override
    public boolean contains(F key){
        return heap.containsKey(key);
    }

    @Override
    public void setValue(Integer key,F value){
        heap.put(key,value);
    }


    @Override
    public F getValue(Integer key){
        F value=heap.get(key);
        if(value==null){
            throw new RuntimeException("no such key");
        }
        return value;
    }

    @Override
    public void remove(Integer key){
        heap.remove(key);
    }

    @Override
    public void setMap(Map<Integer, F> map) {
        this.heap = map;
    }

    @Override
    public Map<Integer, F> getMap() {
        return this.heap;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("Heap = {");

        if (!heap.isEmpty()) string.append("\n");

        for (Integer key : heap.keySet()) {
            string.append("   " + key + " - " + heap.get(key) + "\n");
        }

        string.append("}");
        return string.toString();
    }
}
