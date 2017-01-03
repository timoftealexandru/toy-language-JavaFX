package controller;
import model.*;
import model.stmt.Statement;
import repo.*;
import utils.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.concurrent.*;
import java.lang.*;
import java.util.concurrent.Callable;

public class Controller {
    private IRepository repo;
    private ExecutorService executor;

    public Controller(IRepository repo){
        this.repo=repo;
    }

    public List<PrgState> removeCompletedPrgState(List<PrgState> l){
        return l.stream()
                .filter(p->p.isNotCompleted())
                .collect(Collectors.toList());
    }
    private void oneStepForAll(List<PrgState> list) throws InterruptedException {
        // I. Log the ProgramStates before the execution
        list.forEach(p -> repo.logPrgStateExec(p));

        // II. RUN concurrently one step for each of the existing ProgramStates
        // ------------------------------
        // 1. prepare the list of Callable
        List<Callable<PrgState>> callList = list.stream()
                .map((PrgState p) -> (Callable<PrgState>)(() -> p.oneStep()))
                .collect(Collectors.toList());

        /*
        List<Callable<ProgramState>> callList = list.stream()
                .map((ProgramState p) -> new Callable<ProgramState> () {
                    public ProgramState call() throws Exception {
                        return p.oneStep();
                    }
                })
                .collect(Collectors.toList());
         */

        // 2. start the execution of the Callable's (it returns the list of new created threads)
        List<PrgState> newProgramList =
                executor.invokeAll(callList).stream()
                        .map(future -> { try {
                            return future.get();
                        }
                        catch (Exception e) {
                            System.err.println("There is an exception in Controller::oneStepForAllPrograms");
                            e.printStackTrace();
                        }
                            return null;
                        })
                        .filter(p -> p != null)
                        .collect(Collectors.toList());

        // 3. add the new created threads to the list of existing threads
        list.addAll(newProgramList);

        // III. Log the ProgramStates after the execution
        list.forEach(p -> repo.logPrgStateExec(p));

        // IV. save the current programs in the repository
        repo.setPrgStates(list);
    }


    public void allStep(){
        executor = Executors.newFixedThreadPool(2);
        while(true){
            List<PrgState> list=removeCompletedPrgState(repo.getPrgStates());
            if(list.size()==0)
                break;
            try {
                oneStepForAll(list);
            }
            catch (InterruptedException e) {
                System.err.println("The execution Thread has been interrupted.");
                e.printStackTrace();
            }
        }
        executor.shutdownNow();
    }

    public void executeOneStep(PrgState state){
        IExeStack<Statement> stack=state.getExeStack();
        if(!stack.isEmpty()){
            Statement stmt=stack.pop();
            stmt.execute(state);
        }
    }

    private Map<Integer, Integer> conservativeGarbageCollector(Collection<Integer> symTableValues, Map<Integer, Integer> heap) {
        return heap.entrySet()
                .stream()
                .filter(e->symTableValues.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void serialize(String fName){
        this.repo.serialize(this.repo.getPrgStates().get(0),fName);
    }
    public PrgState deserialize(String fName){
        return this.repo.deserialize(fName);
    }
}

    /*
    public void executeAll()  {
        PrgState prg = repo.getCurrentProgramState();
        while(!prg.getExeStack().isEmpty()) {
            executeOneStep(prg);
            prg.getHeap().setMap(this.conservativeGarbageCollector(prg.getSymbolTable().values(), prg.getHeap().getMap()));
            try {
                repo.logPrgStateExec();
            } catch (InterpreterException e) {
                System.out.println("Cannot log program state to file !"+e);
                return ;
            }
        }
    }
    */


    /*
    public void oneStepForAll(List<PrgState> list){
        list.forEach(prg->this.repo.logPrgStateExec(prg));


        List<Callable<PrgState>> callables= list
                .stream()
                .map((PrgState prg)->(Callable<PrgState>) (()->prg.oneStep()))
                .collect(Collectors.toList());

        try {
            List<PrgState> newPrg = executor
                    .invokeAll(callables)
                    .stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException er) {
                            er.printStackTrace();
                        } catch (ExecutionException err) {
                            err.printStackTrace();
                        }
                        return null;
                    })
                    .filter(p -> p != null)
                    .collect(Collectors.toList());

            list.addAll(newPrg);
            repo.setPrgStates(list);
        }
        catch(InterruptedException e){
            e.printStackTrace();
        }

    }
    */