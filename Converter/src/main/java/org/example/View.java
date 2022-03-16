package org.example;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import io.reactivex.schedulers.Schedulers;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class View {
    private static boolean  fullbo=false;
    @FXML
    public Label label;
    @FXML
    public Label updatetime;
    @FXML
    public Button bal;
    @FXML
    public Button full;
    @FXML
    private ChoiceBox<String> chois1;
    @FXML
    ChoiceBox<String> chois2;
    @FXML
    TextField col;
    @FXML
    public LineChart<?, ?> graphic;
    @FXML
    public NumberAxis yaxis;
    @FXML
    public CategoryAxis xaxis;
    @FXML
    public ListView list;
    @FXML
    public ChoiceBox listfrom;
    @FXML
    public ChoiceBox listto;
    @FXML
    public Button listadd;
    @FXML
    public Button listdel;
    public static ViewModel view=new ViewModel();


    @FXML
    void initialize(){
        //observables for buttons
        Observable<ActionEvent> fullbtn = JavaFxObservable.actionEventsOf(full);
        Observable<ActionEvent> bttnEvents = JavaFxObservable.actionEventsOf(bal);
        Observable<ActionEvent> chois1btn = JavaFxObservable.actionEventsOf(chois1);
        Observable<ActionEvent> chois2btn = JavaFxObservable.actionEventsOf(chois2);
        Observable<ActionEvent> listdelbtn = JavaFxObservable.actionEventsOf(listdel);
        Observable<ActionEvent> addEvents = JavaFxObservable.actionEventsOf(listadd);
        //initialize forms
        getDate();
        coutries();
        col.setText(String.valueOf(view.index));
        list.getItems().setAll(view.list);
        chois1.setValue(view.cursfrom);
        chois2.setValue(view.cursto);
        getcurs();
        setGraphic();
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();
        //buttons events
        bttnEvents.subscribeOn(Schedulers.newThread())
                .subscribe(actionEvent -> {
                    getcurs();
                    setGraphic();
                });

        fullbtn.subscribeOn(Schedulers.newThread())
                .subscribe(actionEvent -> {
                    switchToSecondary();
                });

        chois1btn.subscribeOn(Schedulers.newThread())
                .subscribe(actionEvent -> {
                    getcurs();
                     setGraphic();
                });

        chois2btn.subscribeOn(Schedulers.newThread())
                .subscribe(actionEvent -> {
            getcurs();
            setGraphic();
        });

        addEvents.subscribeOn(Schedulers.newThread())
                .subscribe(actionEvent -> {
                    listshow();
                });

        listdelbtn.subscribeOn(Schedulers.newThread())
                .subscribe(actionEvent -> {
                listedit();
        });
    }
    public void setGraphic(){//set values to line rate
        String from=chois1.getValue();
        String to=chois2.getValue();
        graphic.getData().clear();
        graphic.getData().add(view.Series(from, to));
    }
    public void coutries(){//output list of countries to choiceboxes
        chois1.setItems(view.Countries());
        chois2.setItems(view.Countries());
        listfrom.setItems(view.Countries());
        listto.setItems(view.Countries());
    }
    public void listshow(){//output list
        String from=listfrom.getValue().toString();
        String to=listto.getValue().toString();
       // System.out.println("WAIT!!!!!!!!!"+from+" "+to);
        view.addList(from, to);
        list.getItems().clear();
        list.getItems().addAll(view.list);
    }
    private void listedit(){//add new elemnt to list
        String time =list.getSelectionModel().getSelectedItem().toString().replace(" ", "").substring(0, 3);
        System.out.println(time);
        view.editlist(time);
        list.getItems().clear();
        list.getItems().addAll(view.list);
    }
    private void updateAll(){//Inf update
        getDate();
        getcurs();
        list.getItems().clear();
        view.updateList();
        list.getItems().setAll(view.list);
        //LineRate
        graphic.getData().clear();
        graphic.getData().add(view.linerateUpdate());
    }
    private void getcurs(){//Curs output
        String from=chois1.getValue();
        String to=chois2.getValue();
       // System.out.println("suma: "+suma);
        String vrt= col.getText();
        String suma=view.curs(from,  to, vrt);
        label.setText(suma);

    }
    Timeline fiveSecondsWonder = new Timeline(//timer for updating
            new KeyFrame(Duration.seconds(60),
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            //System.out.println("60 seconds left");
                            updateAll();
                        }
                    }));
    private void getDate(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        //System.out.println(formatter.format(date));
        updatetime.setText("Updated at: "+formatter.format(date));
    }
    private void switchToSecondary() throws IOException {//switch scene
        if(fullbo==false){
            App.setRoot("secondary");

            fullbo=true;}
        else{App.setRoot("primary");
            fullbo=false;}
    }
}
