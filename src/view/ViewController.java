package view;

/**
 * Created by Nicu on 1/7/2017.
 */

import controller.Controller;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import model.stmt.Statement;

/**
 * Created by cosmin on 1/2/17.
 */
public class ViewController {
    @FXML
    private Label prgStatesCnt;
    @FXML
    private TableView heapTableView;
    @FXML
    private ListView outListView;
    @FXML
    private TableView fileTableView;
    @FXML
    private ListView prgStateListView;
    @FXML
    private Label prgIdLabel;
    @FXML
    private ListView exeStackListView;
    private Statement stmt;
    @FXML
    private void initialize() {
    }
    public void bindProperties() {
    }
    public void setData(Controller ctrl, Statement stmt) {
    }
}