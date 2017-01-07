package utils;
import java.io.Serializable;

public interface IOutput<E> extends Serializable{
    void add(E value);
    int size();
    public E get(int index);
    Iterable<E> getAll();
}
