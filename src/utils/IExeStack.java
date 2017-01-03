package utils;
import model.*;
import java.io.Serializable;

public interface IExeStack<E> extends Serializable{
    void push(E elem);
    E pop();
    boolean isEmpty();
    E top();

    Iterable<E> getAll();
}
