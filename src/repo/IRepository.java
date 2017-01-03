package repo;
import model.*;
import java.io.Serializable;
import java.util.*;

public interface IRepository extends Serializable {
    void logPrgStateExec(PrgState p);
    public void addPrgState(PrgState p);
    public List<PrgState> getPrgStates();
    public void setPrgStates(List<PrgState> l);
    void serialize(PrgState p,String fName);
    PrgState deserialize(String fName);
}
