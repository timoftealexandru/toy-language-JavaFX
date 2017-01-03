package utils;
import java.util.*;
public class Output<E> implements IOutput<E> {
    private ArrayList<E> outs;

    public Output(){
        outs=new ArrayList<>();
    }
    public void add(E elem){
        outs.add(elem);
    }
    public int size(){
        return outs.size();
    }
    public Iterable<E> getAll(){
        return outs;
    };
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        string.append("Output = {");

        if (!outs.isEmpty()) string.append("\n");

        for (E output : outs) {
            string.append("   " + output + "\n");
        }

        string.append("}");
        return string.toString();
    }
}
