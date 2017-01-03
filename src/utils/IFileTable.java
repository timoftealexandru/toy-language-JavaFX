package utils;
import java.io.Serializable;
import java.util.*;

public interface IFileTable<E,F> extends Serializable{
    public void add(E key, F value);
    public boolean contains(E key);
    public void setValue(E key,F value);
    public F getValue(E key);
    public void delete(E key);
    Iterable<Map.Entry<E,F>> getAll();
}
