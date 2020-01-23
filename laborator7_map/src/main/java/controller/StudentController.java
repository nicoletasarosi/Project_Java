package controller;

import domain.Student;
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
import service.StudentService;

import java.util.List;
import java.util.function.Predicate;


public class StudentController {
    @FXML
    TableView<Student> tabel;
    @FXML
    TextField numeField;
    @FXML
    TextField prenumeField;
    @FXML
    TextField emailField;
    @FXML
    TextField grupaField;
    @FXML
    TextField cadruDidacticField;
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
    TableColumn<Student,String> nume;
    @FXML
    private
    TableColumn<Student,String> prenume;
    @FXML
    private
    TableColumn<Student,Integer> grupa;
    @FXML
    private
    TableColumn<Student, String> email;
    @FXML
    private
    TableColumn<Student,String> cadruDidactic;
    private StudentService service;
    private int idSelect;
    private Stage stagePagPrinc;
    private Stage stageStudenti;
    private Stage stageTeme;
    private Stage stageNote;
    private Alert eroare;

    public StudentController(){}

    @FXML public void initialize(){
        eroare=new Alert(Alert.AlertType.ERROR);
        eroare.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        //id=new TableColumn<>("id");
        nume=new TableColumn<>("nume");
        prenume=new TableColumn<>("prenume");
        grupa=new TableColumn<>("grupa");
        email = new TableColumn<>("email");
        cadruDidactic=new TableColumn<>("cadruDidactic");
        //tabel.getColumns().add(id);
        tabel.getColumns().add(nume);
        tabel.getColumns().add(prenume);
        tabel.getColumns().add(grupa);
        tabel.getColumns().add(email);
        tabel.getColumns().add(cadruDidactic);
        // id.prefWidthProperty().bind(tabel.widthProperty().multiply(1));
        tabel.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        adaugaBut.setText("Adauga");
        stergeBut.setText("Sterge");
        updateBut.setText("Update");
        clearBut.setText("Clear");

        tabel.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {
            @Override
            public void changed(ObservableValue<? extends Student> observable, Student oldValue, Student newValue) {
                if(newValue!=null) {
                    idSelect = newValue.getId();
                    numeField.setText(newValue.getNume());
                    prenumeField.setText(newValue.getPrenume());
                    grupaField.setText(Integer.toString(newValue.getGrupa()));
                    emailField.setText(newValue.getEmail());
                    cadruDidacticField.setText(newValue.getCadruDidacticIndrumatorLab());
                }
            }
        });
    }

    public void set(StudentService service,Stage stagePagPrinc,Stage stageStudenti,Stage stageTeme,Stage stageNote){
        this.service=service;
        this.stagePagPrinc=stagePagPrinc;
        this.stageStudenti=stageStudenti;
        this.stageNote=stageNote;
        this.stageTeme=stageTeme;
        //id.setCellValueFactory(new PropertyValueFactory<Student, Integer>("id"));
        nume.setCellValueFactory(new PropertyValueFactory<Student,String>("nume"));
        prenume.setCellValueFactory(new PropertyValueFactory<Student,String>("prenume"));
        grupa.setCellValueFactory(new PropertyValueFactory<Student,Integer>("grupa"));
        email.setCellValueFactory(new PropertyValueFactory<Student,String>("email"));
        cadruDidactic.setCellValueFactory(new PropertyValueFactory<Student,String>("cadruDidacticIndrumatorLab"));
        //clear();
        List<Student> list=service.getAll();
        ObservableList<Student> students= FXCollections.observableArrayList(list);
        tabel.setItems(students);
    }

    @FXML
    public void clear(){
        idSelect = -1;
        List<Student> list=service.getAll();
        ObservableList<Student> students= FXCollections.observableArrayList(list);
        tabel.setItems(students);
        tabel.refresh();
        tabel.getSelectionModel().select(null);

        numeField.clear();
        prenumeField.clear();
        grupaField.clear();
        emailField.clear();
        cadruDidacticField.clear();
    }

    @FXML public void adauga(javafx.event.ActionEvent actionEvent) {
        try {
            String nume = numeField.getText();
            String prenume = prenumeField.getText();
            int grupa = Integer.parseInt(grupaField.getText());
            String profesor = cadruDidacticField.getText();
            String email = emailField.getText();
            service.add(nume, prenume, grupa,email, profesor);
            clear();
        }catch (NumberFormatException| ValidationException ex){
            if(ex instanceof  NumberFormatException) {
                eroare.setContentText("Number format exception "+((NumberFormatException) ex).getMessage());
            }
            else {
                eroare.setContentText(((ValidationException) ex).getMessage());
            }
            eroare.show();
        }

    }

    @FXML public void sterge(javafx.event.ActionEvent actionEvent){
        //tabel.getSelectionModel().select(null);
        service.delete(idSelect);
        clear();
    }

    @FXML public void update(javafx.event.ActionEvent actionEvent){
        try {
            String nume = numeField.getText();
            String prenume = prenumeField.getText();
            int grupa = Integer.parseInt(grupaField.getText());
            String email = emailField.getText();
            String profesor = cadruDidacticField.getText();
            // tabel.getSelectionModel().select(null);
            service.update(idSelect,nume, prenume, grupa,email, profesor);
            clear();
        }catch (NumberFormatException|ValidationException ex){
            if(ex instanceof  NumberFormatException) {
                eroare.setContentText("Number format exception "+((NumberFormatException) ex).getMessage());
            }
            else {
                eroare.setContentText(((ValidationException) ex).getMessage());
            }
            eroare.show();
        }

    }

    @FXML public void acasaMen(){
        stagePagPrinc.show();
        stageStudenti.close();
        stageNote.close();
        stageTeme.close();
    }

    @FXML public void temeMen(){
        stageTeme.show();
        //stageStudenti.close();
    }

    @FXML public void noteMen(){
        stageNote.show();
        //stageStudenti.close();
    }
}
/*
xmlns="http://javafx.com/javafx"
            xmlns:fx="http://javafx.com/fxml"
            fx:controller="controller.StudentController"
            prefHeight="400.0" prefWidth="600.0">
 */