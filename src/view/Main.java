package view;

import model.expr.*;
import model.stmt.*;
import utils.*;
import model.*;
import repo.*;
import controller.*;
import view.*;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.PrgStateService;
import sun.security.pkcs11.Secmod;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.*;


public class Main extends Application {
    public static IRepository getNewRepository(Statement prg) {
        PrgState first = new PrgState(new ExeStack<Statement>(), new SymbolTable<String, Integer>(), new Output<Integer>(),new FileTable<Integer, FileData>(),new Heap<>() , prg);
        Repository repo = new Repository("./logs/log.txt");
        repo.addPrgState(first);


        return repo;
    }
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        /*
        *   Lab2Ex1:
        *   v = 2;
        *   print (v)
        *
        * */
        Statement s1= new CompStatement(new AssignmentStatement("v",new ConstExpr(2)), new PrintStatement(new
                VarExpr("v")));
        /*
        *   Lab2Ex2:
        *   a = 2 + 3 * 5;
        *   b = a + 1;
        *   print (b)
        *
        * */
        Statement s2 = new CompStatement(new AssignmentStatement("a", new ArithmeticExpr('+',new ConstExpr(2),new
                ArithmeticExpr('*',new ConstExpr(3), new ConstExpr(5)))),
                new CompStatement(new AssignmentStatement("b",new ArithmeticExpr('+',new VarExpr("a"), new
                        ConstExpr(1))), new PrintStatement(new VarExpr("b"))));
        /*
        *   Lab2Ex3:
        *   a = 2 - 2;
        *   If a then v = 2 else v = 3;
        *   print (v)
        *
        * */
        Statement s3 = new CompStatement(new AssignmentStatement("a", new ArithmeticExpr('-',new ConstExpr(2), new
                ConstExpr(2))),
                new CompStatement(new IfStatement(new VarExpr("a"),new AssignmentStatement("v",new ConstExpr(2)), new
                        AssignmentStatement("v", new ConstExpr(3))), new PrintStatement(new VarExpr("v"))));


        /*
        *   Lab5Ex1
        *   openRFile (var_f, "test.in");
        *   readFile (var_f, var_c); print (var_c);
        *   If var_c then readFile (var_f, var_c); print (var_c) else print (0);
        *   closeRFile (var_f)
        *
        * */
        Statement s4 = new CompStatement(
                new OpenFileStatement("var_f", "test.in"),
                new CompStatement(
                        new ReadFileStatement(new VarExpr("var_f"), "var_c"),
                        new CompStatement(
                                new PrintStatement(new VarExpr("var_c")),
                                new CompStatement(
                                        new IfStatement(
                                                new VarExpr("var_c"),
                                                new CompStatement(
                                                        new ReadFileStatement(new VarExpr("var_f"), "var_c"),
                                                        new PrintStatement(new VarExpr("var_c"))
                                                ),
                                                new PrintStatement(new ConstExpr(0))
                                        ),
                                        new CloseReadFileStatement(new VarExpr("var_f"))
                                )
                        )
                )
        );

        /*
        *   Lab5Ex2
        *   openRFile (var_f, "test.in");
        *   readFile (var_f + 2, var_c); print (var_c);
        *   If var_c then readFile (var_f, var_c); print (var_c) else print (0);
        *   closeRFile (var_f)
        * */

        Statement s5 = new CompStatement(
                new OpenFileStatement("var_f", "test.in"),
                new CompStatement(
                        new ReadFileStatement(new ArithmeticExpr('+', new VarExpr("var_f"), new ConstExpr(2)), "var_c"),
                        new CompStatement(
                                new PrintStatement(new VarExpr("var_c")),
                                new CompStatement(
                                        new IfStatement(
                                                new VarExpr("var_c"),
                                                new CompStatement(
                                                        new ReadFileStatement(new VarExpr("var_f"), "var_c"),
                                                        new PrintStatement(new VarExpr("var_c"))
                                                ),
                                                new PrintStatement(new ConstExpr(0))
                                        ),
                                        new CloseReadFileStatement(new VarExpr("var_f"))
                                )
                        )
                )
        );

        /**
         *v=10;new(v,20);new(a,22);wH(a,30);print(a);print(rH(a));a=0
         *
         * */
        Statement s6 =
                new CompStatement(
                        new AssignmentStatement("v", new ConstExpr(10)),
                        new CompStatement(
                                new NewStatement("v", new ConstExpr(20)),
                                new CompStatement(
                                        new NewStatement("a", new ConstExpr(22)),
                                        new CompStatement(
                                                new WriteHeapStatement("a", new ConstExpr(30)),
                                                new CompStatement(
                                                        new PrintStatement(new VarExpr("a")),
                                                        new CompStatement(
                                                                new PrintStatement(new ReadHeapExpr("a")),
                                                                new AssignmentStatement("a", new ConstExpr(0)))))
                                )
                        )
                );

        Statement s7 = new CompStatement(
                new AssignmentStatement("v", new ConstExpr(0)),
                new WhileStatement(new CompareExpr("<=", new VarExpr("v"), new ConstExpr(10)),
                        new CompStatement(
                                new PrintStatement(new VarExpr("v")),
                                new AssignmentStatement("v", new ArithmeticExpr('+', new VarExpr("v"), new ConstExpr(1)))
                        ))
        );


        /**
         * Example: v=6; (while (v-4) print(v);v=v-1);print(v)
         */
        Statement  s8 = new CompStatement(
                new AssignmentStatement("v", new ConstExpr(6)),
                new CompStatement(
                        new WhileStatement(new ArithmeticExpr('-', new VarExpr("v"), new ConstExpr(4)),
                                new CompStatement(
                                        new PrintStatement(new VarExpr("v")),
                                        new AssignmentStatement("v", new ArithmeticExpr('-', new VarExpr("v"), new ConstExpr(1)))
                                )),
                        new PrintStatement(new VarExpr("v")))
        );

        /*

        v=10;new(a,22);
        fork(wH(a,30);v=32;print(v);print(rH(a)));
        print(v);print(rH(a))
         */
        Statement s9;
        Statement st1 = new CompStatement(
                new AssignmentStatement("v", new ConstExpr(10)),
                new NewStatement("a", new ConstExpr(22))
        );
        Statement st2 = new ForkStatement(
                new CompStatement(
                        new WriteHeapStatement("a", new ConstExpr(30)),
                        new CompStatement(
                                new AssignmentStatement("v", new ConstExpr(32)),
                                new CompStatement(
                                        new PrintStatement(new VarExpr("v")),
                                        new PrintStatement(new ReadHeapExpr("a"))
                                )
                        )
                )
        );
        Statement st3 = new CompStatement(
                new PrintStatement(new VarExpr("v")),
                new PrintStatement(new ReadHeapExpr("a"))
        );
        s9 = new CompStatement(
                st1, new CompStatement(
                st2, st3
        ));


        List<Statement> menu = new ArrayList<Statement>();
        menu.add(s1);
        menu.add(s2);
        menu.add(s3);
        menu.add(s4);
        menu.add(s5);
        menu.add(s6);
        menu.add(s7);
        menu.add(s8);
        menu.add(s9);

        VBox root = new VBox(5);
        root.getChildren().add(new Label("Plase choose a program: "));

        /// create the listview
        ObservableList<Statement> observableStmtList = FXCollections.observableArrayList(menu);
        ListView<Statement> programList = new ListView<Statement>(observableStmtList);
        programList.setCellFactory(new Callback<ListView<Statement>, ListCell<Statement>>() {
            @Override
            public ListCell<Statement> call(ListView<Statement> param) {
                ListCell<Statement> listCell = new ListCell<Statement>() {
                    @Override
                    protected void updateItem(Statement e, boolean empty) {
                        super.updateItem(e, empty);
                        if(e == null || empty)
                            setText("");
                        else
                            setText(e.toString());
                    }
                };
                return listCell;
            }
        });
        root.getChildren().add(programList);

        Scene scene = new Scene(root, 100, 100);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Examples");
        primaryStage.show();

        observableStmtList.add(new PrintStatement(new ConstExpr(1)));

        programList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Statement>() {
            @Override
            public void changed(ObservableValue<? extends Statement> observable, Statement oldValue, Statement newValue) {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Main.class.getResource("RunProgram.fxml"));
                    VBox root = (VBox) loader.load();

                    PrgStateService prgStateService = new PrgStateService(getNewRepository(newValue));
                    Controller ctrl = loader.getController();
                    ctrl.setService(prgStateService);
                    prgStateService.addObserver(ctrl);

                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("Run example dialog");
                    dialogStage.initModality(Modality.APPLICATION_MODAL);
                    dialogStage.setScene(new Scene(root));
                    dialogStage.show();
                } catch(IOException e) {
                    e.printStackTrace();
                }
                /*
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Run example dialog");
                dialogStage.initModality(Modality.APPLICATION_MODAL);
                dialogStage.setScene(new Scene(new Label(newValue.getDescription())));
                dialogStage.show();
                */
            }
        });
    }
}

