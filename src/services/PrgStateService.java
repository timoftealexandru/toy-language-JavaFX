package services;

import model.PrgState;
import utils.Tuple;
import repo.IRepository;
import utils.Observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PrgStateService implements utils.Observable<PrgState> {
    protected List<Observer<PrgState>> observers = new ArrayList<Observer<PrgState>>();
    private IRepository repo;

    public PrgStateService(IRepository repo) {
        this.repo = repo;
    }

    public IRepository getRepo() {
        return this.repo;
    }

    public List<PrgState> getAll() {
        List<PrgState> mList = new ArrayList<PrgState>();
        mList.addAll(this.repo.getPrgStates());
        return mList;
    }

    public List<String> getOutList() {
        List<String> mList = new ArrayList<String>();
        for(int i = 0; i < this.repo.getPrgStates().get(0).getOutput().size(); ++ i)
            mList.add(String.valueOf(this.repo.getPrgStates().get(0).getOutput().get(i)));
        return mList;
    }

    public List<Tuple<Integer, String>> getFileList() {
        List<Tuple<Integer, String>> mList = new ArrayList<>();
        for(Integer x : this.repo.getPrgStates().get(0).getFileTable().keys())
            mList.add(new Tuple(x, this.repo.getPrgStates().get(0).getFileTable().getValue(x).getFileName()));
        return mList;
    }

    public List<Map.Entry<Integer, Integer>> getHeapList() {
        System.out.println(repo.getPrgStates().get(0).getHeap().getMap().entrySet());
        return new ArrayList<Map.Entry<Integer, Integer>>(repo.getPrgStates().get(0).getHeap().getMap().entrySet());
    }

    @Override
    public void addObserver(Observer<PrgState> o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer<PrgState> o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for(Observer o : observers)
            o.update(this);
    }
}