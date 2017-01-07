package utils;

/**
 * Created by Nicu on 1/7/2017.
 */
public interface Observer <T> {
    void update(Observable<T> observable);
}