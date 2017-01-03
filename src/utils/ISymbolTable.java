package utils;
import java.util.*;
import java.io.Serializable;

public interface ISymbolTable<E,F> extends Serializable{
    public void add(E key, F value);
    public boolean contains(E key);
    public void setValue(E key,F value);
    public F getValue(E key);
    public Collection<F> values();
    ISymbolTable clone();
    Iterable<Map.Entry<E,F>> getAll();
}
