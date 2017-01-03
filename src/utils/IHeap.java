package utils;
import java.io.Serializable;
import java.util.*;

public interface IHeap<F> extends Serializable {
    public Integer add(F content);
    public boolean contains(F key);
    public void setValue(Integer key,F value);
    public F getValue(Integer key);
    public void remove(Integer key);
    public Map<Integer, F> getMap();
    public void setMap(Map<Integer, F> map);
    public Iterable<Map.Entry<Integer,F>> getAll();
}
