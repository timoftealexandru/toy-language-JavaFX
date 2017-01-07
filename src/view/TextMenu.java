package view;
import java.io.*;
import java.util.*;
public class TextMenu {
    private Map<String, Command> commands;

    public TextMenu(Map<String, Command> cmds) {
        this.commands = cmds;
    }
    public void addCommand(Command c){
        commands.put(c.getKey(),c);
    }
    private void printMenu(){
        for(Command com : commands.values()){
            String line=String.format("%4s : %s", com.getKey(), com.getDescription());
            System.out.println(line);
        }
    }
    public List<String> getCommandList() {
        List<String> l = new ArrayList<String>();
        for(Command cmd: this.commands.values())
            l.add(cmd.getDescription());
        return l;
    }
    public void show(){
        Scanner scanner=new Scanner(System.in);
        while(true){
            printMenu();
            System.out.printf("Input the option: ");
            String key=scanner.nextLine();
            Command com=commands.get(key);
            if (com==null){
                System.out.println("Invalid Option");
                continue;
            }
            com.execute();
        }
    }
}