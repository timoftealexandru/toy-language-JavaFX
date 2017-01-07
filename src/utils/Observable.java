package utils;

/**
 * Created by Nicu on 1/7/2017.
 */
public interface Observable<T> {
    void addObserver(Observer<T> o);
    void removeObserver(Observer<T> o);
    void notifyObservers();
}