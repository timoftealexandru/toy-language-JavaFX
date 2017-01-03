package utils;
import java.io.Serializable;

public interface IOutput<E> extends Serializable{
    void add(E value);
    int size();
    Iterable<E> getAll();
}
