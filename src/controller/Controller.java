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

import utils.Tuple;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import model.*;
import repo.IRepository;
import services.PrgStateService;
import utils.Observable;

public class Controller implements utils.Observer<PrgState>{
    @FXML
    private Label prgStatesCnt;
    @FXML
    private TableView<Map.Entry<Integer, Integer>> heapTableView;
    @FXML
    private ListView<String> outListView;
    @FXML
    private TableView<Tuple<Integer, String>> fileTableView;
    @FXML
    private ListView<PrgState> prgStateListView;
    @FXML
    private Label prgIdLabel;
    @FXML
    private ListView exeStackListView;

    private IRepository repo;
    ObservableList<PrgState> prgStateModel;
    ObservableList<String> outListModel;
    ObservableList<Map.Entry<Integer, Integer>> heapTableModel;
    ObservableList<Tuple<Integer, String>> fileTableModel;
    private ExecutorService executor;
    private PrgStateService prgStateService;

    public Controller(){

    }

    @FXML
    private void initialize() {
    }

    public void setService(PrgStateService prgStateService) {
        this.prgStateService = prgStateService;
        this.repo = this.prgStateService.getRepo();
        //prg state model;
        this.prgStateModel = FXCollections.observableArrayList();
        this.prgStateListView.setItems(this.prgStateModel);
        this.prgStateListView.setCellFactory(new Callback<ListView<PrgState>, ListCell<PrgState>>() {
            @Override
            public ListCell<PrgState> call(ListView<PrgState> param) {
                ListCell<PrgState> listCell = new ListCell<PrgState>() {
                    @Override
                    protected void updateItem(PrgState e, boolean empty) {
                        super.updateItem(e, empty);
                        if(e == null || empty)
                            setText("");
                        else
                            setText(String.valueOf(e.getId()));
                    }
                };
                return listCell;
            }
        });
        // heapTableView
        this.heapTableModel = FXCollections.observableArrayList();
        TableColumn<Map.Entry<Integer, Integer>, String> first = new TableColumn<>("Address");
        TableColumn<Map.Entry<Integer, Integer>, String> second = new TableColumn<>("Value");
        first.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Integer, Integer>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<Integer, Integer>, String> param) {
                return new SimpleStringProperty(String.valueOf(param.getValue().getKey()));
            }
        });
        second.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Map.Entry<Integer, Integer>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Map.Entry<Integer, Integer>, String> param) {
                return new SimpleStringProperty(String.valueOf(param.getValue().getValue()));
            }
        });
        this.heapTableView.getColumns().setAll(first, second);
        this.heapTableView.setItems(this.heapTableModel);

        ///fileTableView
        this.fileTableModel = FXCollections.observableArrayList();
        TableColumn<Tuple<Integer, String>, String> fd = new TableColumn<>("File descriptor");
        TableColumn<Tuple<Integer, String>, String> fn = new TableColumn<>("File name");
        fd.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tuple<Integer, String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Tuple<Integer, String>, String> param) {
                return new SimpleStringProperty(String.valueOf(param.getValue().getFirst()));
            }
        });
        fn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Tuple<Integer, String>, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Tuple<Integer, String>, String> param) {
                return new SimpleStringProperty(String.valueOf(param.getValue().getSecond()));
            }
        });
        this.fileTableView.getColumns().setAll(fd, fn);
        this.fileTableView.setItems(this.fileTableModel);

        // outListView
        this.outListModel = FXCollections.observableArrayList();
        this.outListView.setItems(this.outListModel);

        this.update(this.prgStateService);
    }


    public List<PrgState> removeCompletedPrgState(List<PrgState> l){
        return l.stream()
                .filter(p->p.isNotCompleted())
                .collect(Collectors.toList());
    }

    public void setMain(PrgState state) {
        this.repo.getPrgStates().clear();
        this.repo.getPrgStates().add(state);
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
        list.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        // IV. save the current programs in the repository
        repo.setPrgStates(list);
        this.prgStateService.notifyObservers();
    }


    public void allStep(){
        executor = Executors.newFixedThreadPool(2);
        while(true){
            List<PrgState> list=removeCompletedPrgState(this.prgStateService.getAll());
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

    @FXML
    public void allStepsGUI() throws InterruptedException{
        executor = Executors.newFixedThreadPool(2);
        while(true) {
            this.prgStateService.notifyObservers();
            List<PrgState> prgList = removeCompletedPrgState(this.prgStateService.getAll());
            if(prgList.size() == 0) {
                System.out.println("FINISHED");
                break;
            }
            oneStepForAll(prgList);
            System.out.println("ONE STEP");
            break;
        }
        executor.shutdownNow();
        /*
        List<PrgState> prgList = rep.getPrgList();
        while(!crt.getExeStack().isEmpty()) {
            //this.rep.serialize();
            oneStep(crt);
            crt.getHeap().setMap(this.conservativeGarbageCollector(crt.getSymTable().values(), crt.getHeap().getMap()));
            //this.rep.deserialize();
            try {
                rep.logPrgStateExec();
            } catch (IOException e) {
                System.out.println("Cannot log program state to file. Exiting...");
                return ;
            }
        }
        */
    }

    @Override
    public void update(Observable<PrgState> observable) {
        List<PrgState> prgStates = this.prgStateService.getAll();
        this.prgStatesCnt.setText(String.valueOf(prgStates.size()));
        this.prgStateModel.setAll(prgStates);
        this.outListModel.setAll(this.prgStateService.getOutList());
        this.heapTableModel.setAll(this.prgStateService.getHeapList());
        this.fileTableModel.setAll(prgStates.get(0).getFileTable().keys()
                .stream()
                .map(k -> new Tuple<Integer, String>(k, prgStates.get(0).getFileTable().getValue(k).getFileName()))
                .collect(Collectors.toList())
        );
        this.fileTableModel.stream().map(e->String.valueOf(e.getFirst()) + e.getSecond()).forEach(System.out::println);
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

