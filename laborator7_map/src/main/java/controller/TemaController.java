package controller;

import domain.StructuraAnUniversitar;
import domain.Student;
import domain.Tema;
import domain.validate.ValidationException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import service.StudentService;
import service.ServiceTema;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;


public class TemaController {
    @FXML
    TableView<Tema> tabel;
    @FXML
    TextField descriereField;
    @FXML
    TextField deadlineWeekField;
    @FXML
    Button adaugaBut;
    @FXML
    Button stergeBut;
    @FXML
    Button updateBut;
    @FXML
    Button clearBut;
    //@FXML
    //private
    //TableColumn<Student,Integer> id;
    @FXML
    private
    TableColumn<Tema,String> descriere;
    @FXML
    private
    TableColumn<Tema,Integer> startWeek;
    @FXML
    private
    TableColumn<Tema,Integer> deadlineWeek;
    private int idSelect;
    private Stage stagePagPrinc;
    private Stage stageStudenti;
    private Stage stageTeme;
    private Stage stageNote;
    private ServiceTema service;
    private StructuraAnUniversitar structuraAnUniversitar;
    private Alert eroare;

    public TemaController(){}

    @FXML public void initialize(){
        //id=new TableColumn<>("id");
        eroare=new Alert(Alert.AlertType.ERROR);
        eroare.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        descriere=new TableColumn<>("descriere");
        startWeek=new TableColumn<>("startWeek");
        deadlineWeek=new TableColumn<>("deadlineWeek");
        //tabel.getColumns().add(id);
        tabel.getColumns().add(descriere);
        tabel.getColumns().add(startWeek);
        tabel.getColumns().add(deadlineWeek);
        // id.prefWidthProperty().bind(tabel.widthProperty().multiply(1));
        tabel.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        adaugaBut.setText("Adauga");
        stergeBut.setText("Sterge");
        updateBut.setText("Update");
        clearBut.setText("Clear");

        tabel.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tema>() {
            @Override
            public void changed(ObservableValue<? extends Tema> observable, Tema oldValue, Tema newValue) {
                if(newValue!=null) {
                    idSelect = newValue.getId();
                    descriereField.setText(newValue.getDescriere());
                    deadlineWeekField.setText(Integer.toString(newValue.getDeadlineWeek()));
                }
            }
        });
    }

    public void set(ServiceTema service,Stage stagePagPrinc,Stage stageStudenti,Stage stageTeme,Stage stageNote){
        this.service=service;
        this.stagePagPrinc=stagePagPrinc;
        this.stageStudenti=stageStudenti;
        this.stageTeme=stageTeme;
        this.stageNote=stageNote;
        //id.setCellValueFactory(new PropertyValueFactory<Student, Integer>("id"));
        descriere.setCellValueFactory(new PropertyValueFactory<Tema,String>("descriere"));
        startWeek.setCellValueFactory(new PropertyValueFactory<Tema,Integer>("startWeek"));
        deadlineWeek.setCellValueFactory(new PropertyValueFactory<Tema,Integer>("deadlineWeek"));
        //clear();
        List<Tema> list=service.getAll();
        ObservableList<Tema> teme= FXCollections.observableArrayList(list);
        tabel.setItems(teme);
        /*
        FilteredList<Student> fList=new FilteredList<>(students,x->true);
        filtrareField.textProperty().addListener((obs,oldValue,newValue)->{
            fList.setPredicate(student->{
                if(newValue==null||newValue.isEmpty())
                    return true;
                return Integer.toString(student.getGrupa()).startsWith(newValue);
            });
        });
        tabel.setItems(fList);
        */
    }

    public void setStructuraAnUniversitar(StructuraAnUniversitar structuraAnUniversitar){
        this.structuraAnUniversitar=structuraAnUniversitar;
    }

    @FXML
    public void clear(){
        List<Tema> list=service.getAll();
        ObservableList<Tema> teme= FXCollections.observableArrayList(list);
        tabel.setItems(teme);
        tabel.refresh();
        tabel.getSelectionModel().select(null);

        descriereField.clear();
        deadlineWeekField.clear();
    }

    @FXML public void adauga(javafx.event.ActionEvent actionEvent) {
        try {
            String descriere = descriereField.getText();
            int deadlineWeek = Integer.parseInt(deadlineWeekField.getText());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String data = dateFormat.format(date);
            Date df = dateFormat.parse(data);
            service.add(descriere, deadlineWeek, df, structuraAnUniversitar);
        } catch (Exception ex) {
            if(ex instanceof  NumberFormatException) {
                eroare.setContentText("Number format exception "+((NumberFormatException) ex).getMessage());
            }
            else {
                eroare.setContentText(ex.getMessage());
            }
            eroare.show();
        }
        clear();
    }

    @FXML public void sterge(javafx.event.ActionEvent actionEvent){
        //tabel.getSelectionModel().select(null);
        service.delete(idSelect);
        clear();
    }

    @FXML public void update(javafx.event.ActionEvent actionEvent) {
        try {
            String descriere = descriereField.getText();
            int deadlineWeek = Integer.parseInt(deadlineWeekField.getText());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String data = dateFormat.format(date);
            Date df = dateFormat.parse(data);
            service.add(descriere, deadlineWeek, df, structuraAnUniversitar);
        } catch (Exception ex) {
            if(ex instanceof  NumberFormatException) {
                eroare.setContentText("Number format exception "+((NumberFormatException) ex).getMessage());
            }
            else {
                eroare.setContentText(ex.getMessage());
            }
            eroare.show();
        }
        clear();
    }

    @FXML public void acasaMen(){
        stagePagPrinc.show();
        stageTeme.close();
    }

    @FXML public void studentiMen(){
        stageStudenti.show();
        //stageTeme.close();
    }

    @FXML public void noteMen(){
        stageNote.show();
        //stageTeme.close();
    }
}
/*
xmlns="http://javafx.com/javafx"
            xmlns:fx="http://javafx.com/fxml"
            fx:controller="controller.StudentController"
            prefHeight="400.0" prefWidth="600.0">
 */