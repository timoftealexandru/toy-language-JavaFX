package model.stmt;
import java.io.Serializable;
import model.PrgState;

public interface Statement extends Serializable{
    PrgState execute(PrgState p);
    String toString();
}
