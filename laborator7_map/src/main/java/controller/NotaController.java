package controller;

import domain.Nota;
import domain.Student;
import domain.Tema;
import domain.validate.ValidationException;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.graalvm.compiler.core.common.type.ArithmeticOpTable;
import service.ServiceNota;
import service.StudentService;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import controller.observ.Observer;
import java.util.Properties;
import java.util.function.Predicate;


public class NotaController implements Observer {
    @FXML
    TableView<Nota> tabel;
    @FXML
    ComboBox<Student> comboBoxStudent;
    @FXML
    ComboBox<Tema> comboBoxTema;
    @FXML
    TextField studentField;
    @FXML
    TextField ziField;
    @FXML
    TextField lunaField;
    @FXML
    TextField anField;
    @FXML
    TextField penalizareField;
    @FXML
    TextField motivareField;
    @FXML
    TextArea feedbackField;
    @FXML
    TextField profesorField;
    @FXML
    Button adaugaBut;
    @FXML
    Button stergeBut;
    @FXML
    Button updateBut;
    @FXML
    Button clearBut;
    @FXML
    ComboBox<String> rapoarteCombo;
    @FXML
    TextArea rapoarteArea;
    //@FXML
    //private
    //TableColumn<Student,Integer> id;
    @FXML
    private
    TableColumn<Nota, String> nume;
    @FXML
    private
    TableColumn<Nota, String> prenume;
    @FXML
    private
    TableColumn<Nota, String> descriere;
    @FXML
    private
    TableColumn<Nota, String> data;
    @FXML
    private
    TableColumn<Nota,String> profesor;
    //@FXML
    //private TableColumn<Nota,String> feedback;
    @FXML
    private TableColumn<Nota,Float> penalizari;
    @FXML
    private TableColumn<Nota,Float> nota;
    private ServiceNota service;
    private int idSelect;
    private int idSelectStudent;
    private int idSelectTema;
    private Stage stagePagPrinc;
    private Stage stageStudenti;
    private Stage stageTeme;
    private Stage stageNote;
    private Stage stageVizFinalaNota;
    private VizFinalaNotaController vizFinalaNotaController;
    private Alert eroare;

    public NotaController(){}

    @FXML public void initialize(){
        eroare=new Alert(Alert.AlertType.ERROR);
        eroare.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        //id=new TableColumn<>("id");
        nume=new TableColumn<>("numeS");
        prenume=new TableColumn<>("prenumeS");
        descriere=new TableColumn<>("tema");
        data=new TableColumn<>("data");
        profesor=new TableColumn<>("profesor");
        //feedback=new TableColumn<>("feedback");
        penalizari=new TableColumn<>("penalizari");
        nota=new TableColumn<>("nota");
        //tabel.getColumns().add(id);
        tabel.getColumns().add(nume);
        tabel.getColumns().add(prenume);
        tabel.getColumns().add(descriere);
        tabel.getColumns().add(data);
        tabel.getColumns().add(profesor);
        // tabel.getColumns().add(feedback);
        tabel.getColumns().add(penalizari);
        tabel.getColumns().add(nota);
        // id.prefWidthProperty().bind(tabel.widthProperty().multiply(1));
        tabel.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        adaugaBut.setText("Adauga");
        stergeBut.setText("Sterge");
        updateBut.setText("Update");
        clearBut.setText("Clear");
        ObservableList<String> valRap=FXCollections.observableArrayList("Nota lab fiecare student","Cea mai grea tema","Studentii care pot intra in examen","Studentii cu temele predate la timp");
        rapoarteCombo.setItems(valRap);
        rapoarteCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null) {
                if (newValue.equals("Nota lab fiecare student")) {
                    rapoarteArea.setText(service.mediePonderata());
                }
                if (newValue.equals("Cea mai grea tema")) {
                    //parje denisa lab2 8p
                    rapoarteArea.setText(service.temaCeaMaiGrea().toString());
                }
                if (newValue.equals("Studentii care pot intra in examen")) {
                    //pop radu lab3 5p
                    rapoarteArea.setText(service.studentiExamen());
                }
                if (newValue.equals("Studentii cu temele predate la timp")) {
                    //prundus vlad lab1 30.10 0p
                    rapoarteArea.setText(service.studentiLaTimp());
                }
            }
        });
        comboBoxStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null) {
                idSelectStudent = newValue.getId();
            }
        });
        comboBoxTema.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null) {
                idSelectTema = newValue.getId();
            }
        });
        tabel.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Nota>() {
            @Override
            public void changed(ObservableValue<? extends Nota> observable, Nota oldValue, Nota newValue) {
                if(newValue!=null) {
                    idSelect = newValue.getId();
                    comboBoxStudent.setValue(service.getStudent(newValue.getStudentID()));
                    comboBoxTema.setValue(service.getTema(newValue.getTemaID()));
                    Date data=newValue.getDataPredare();
                    SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
                    String[] d=dateFormat.format(data).split("-");
                    ziField.setText(d[2]);
                    lunaField.setText(d[1]);
                    anField.setText(d[0]);
                    penalizareField.setText(Float.toString(newValue.getPenalizari()));
                    motivareField.setText(Integer.toString(0));
                    feedbackField.setText(newValue.getFeedback());
                    profesorField.setText(newValue.getProfesor());
                }
            }
        });

    }

    public void set(ServiceNota service, Stage stagePagPrinc, Stage stageStudenti, Stage stageTeme, Stage stageNote,Stage stageVizFinalaNota,VizFinalaNotaController vizFinalaNotaController){
        this.service=service;
        this.stagePagPrinc=stagePagPrinc;
        this.stageStudenti=stageStudenti;
        this.stageTeme=stageTeme;
        this.stageNote=stageNote;
        this.stageVizFinalaNota=stageVizFinalaNota;
        this.vizFinalaNotaController=vizFinalaNotaController;
        //id.setCellValueFactory(new PropertyValueFactory<Student, Integer>("id"));
        nume.setCellValueFactory(param -> new SimpleStringProperty(service.getStudent(param.getValue().getStudentID()).getNume()));
        prenume.setCellValueFactory(param -> new SimpleStringProperty(service.getStudent(param.getValue().getStudentID()).getPrenume()));
        descriere.setCellValueFactory(param -> new SimpleStringProperty(service.getTema(param.getValue().getStudentID()).getDescriere()));
        data.setCellValueFactory(param -> {
            Date data=param.getValue().getDataPredare();
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
            String d=dateFormat.format(data);
            return new SimpleStringProperty(d);
        });
        profesor.setCellValueFactory(new PropertyValueFactory<Nota,String>("profesor"));
        //feedback.setCellValueFactory(new PropertyValueFactory<Nota,String>("feedback"));
        penalizari.setCellValueFactory(new PropertyValueFactory<Nota,Float>("penalizari"));
        nota.setCellValueFactory(param -> {
            float notaMax=service.notaMaxStudent(param.getValue().getTemaID(),param.getValue().getDataPredare());
            float nota=notaMax-param.getValue().getPenalizari();
            return new SimpleFloatProperty(nota).asObject();
        });
        //clear();

        update();


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


    public void update(){
        List<Nota> list=service.getAll();
        ObservableList<Nota> note= FXCollections.observableArrayList(list);
        tabel.setItems(note);
        List<Student> listStud=service.getAllStudent();
        ObservableList<Student> students= FXCollections.observableArrayList(listStud);
        //comboBoxStudent.setItems(students);
        List<Tema> listTem=service.getAllTema();
        ObservableList<Tema> teme= FXCollections.observableArrayList(listTem);
        comboBoxTema.setItems(teme);

        FilteredList<Student> fList=new FilteredList<>(students,x->true);
        studentField.textProperty().addListener((obs,oldValue,newValue)->{
            fList.setPredicate(student-> {
                if (newValue == null || newValue.isEmpty()) {
                    comboBoxStudent.hide();
                    return true;
                }
                comboBoxStudent.hide();
                //comboBoxStudent.setVisibleRowCount(fList.size());
                comboBoxStudent.show();
                return student.toString().toLowerCase().contains(newValue.toLowerCase());
            });
        });
        comboBoxStudent.setItems(fList);
    }

    @FXML
    public void clear(){
        List<Nota> list=service.getAll();
        ObservableList<Nota> note= FXCollections.observableArrayList(list);
        tabel.setItems(note);
        tabel.refresh();
        tabel.getSelectionModel().select(null);

        studentField.clear();
        ziField.clear();
        lunaField.clear();
        anField.clear();
        penalizareField.clear();
        motivareField.clear();
        profesorField.clear();
        feedbackField.clear();

        comboBoxStudent.getSelectionModel().select(null);
        comboBoxTema.getSelectionModel().select(null);

    }

    @FXML public void adauga(javafx.event.ActionEvent actionEvent) {
        try {
            int zi = Integer.parseInt(ziField.getText());
            int luna = Integer.parseInt(lunaField.getText());
            int an = Integer.parseInt(anField.getText());
            String data = an + "-" + luna + "-" + zi;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date myDate = format.parse(data);
            float penalizare = Float.parseFloat(penalizareField.getText());
            int motivare = Integer.parseInt(motivareField.getText());
            String feedback = feedbackField.getText();
            String profesor = profesorField.getText();



            int n = service.notaMaxStudent(idSelectTema, myDate);

            if (n != 10) {
                int dim = 10 - n;
                feedback += " NOTA A FOST DIMINUATĂ CU " + dim +
                        " PUNCTE DATORITA INTARZIERILOR" + " " +"cu penalizare" + penalizare + "si a avut" + motivare + "saptamani motivate";
            }

            vizFinalaNotaController.set(stageVizFinalaNota, service.getStudent(idSelectStudent).getNume(), service.getStudent(idSelectStudent).getPrenume(), service.getTema(idSelectTema).getDescriere(), data, Float.toString(penalizare), Integer.toString(motivare), feedback, profesor);
            stageVizFinalaNota.showAndWait();
            boolean ok = VizFinalaNotaController.getVal();
            if (ok) {
                int[] verificare ={0};
                service.add(idSelectStudent, idSelectTema, myDate, profesor, feedback, penalizare - motivare,verificare);
                clear();
                if(verificare[0]==1){
                    Alert naelert=new Alert(Alert.AlertType.INFORMATION);
                    naelert.setContentText("Ati intarziat adaugarea notei!");
                    naelert.show();
                }
            }
        } catch (Exception ex) {
            if (ex instanceof NumberFormatException) {
                eroare.setContentText("Number format exception " + ((NumberFormatException) ex).getMessage());
            } else {
                eroare.setContentText(ex.getMessage());
            }
            eroare.show();
        }
        // clear();
    }

    @FXML public void sterge(javafx.event.ActionEvent actionEvent){
        //tabel.getSelectionModel().select(null);
        service.delete(idSelect);
        clear();
    }

    @FXML public void update(javafx.event.ActionEvent actionEvent){
        try {
            int zi = Integer.parseInt(ziField.getText());
            int luna = Integer.parseInt(lunaField.getText());
            int an = Integer.parseInt(anField.getText());
            String data = an + "-" + luna + "-" + zi;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date myDate = format.parse(data);
            float penalizare = Float.parseFloat(penalizareField.getText());
            int motivare = Integer.parseInt(motivareField.getText());
            String feedback = feedbackField.getText();
            String profesor = profesorField.getText();

            int n = service.notaMaxStudent(idSelectTema, myDate);
            if (n != 10) {
                int dim = 10 - n;
                feedback += " NOTA A FOST DIMINUATĂ CU " + dim +
                        " PUNCTE DATORITA INTARZIERILOR";
            }

            vizFinalaNotaController.set(stageVizFinalaNota, service.getStudent(idSelectStudent).getNume(), service.getStudent(idSelectStudent).getPrenume(), service.getTema(idSelectTema).getDescriere(), data, Float.toString(penalizare), Integer.toString(motivare), feedback, profesor);
            stageVizFinalaNota.showAndWait();
            boolean ok = VizFinalaNotaController.getVal();
            if (ok) {
                service.update(idSelect,idSelectStudent, idSelectTema, myDate, profesor, feedback, penalizare - motivare);
                clear();
            }
        } catch (Exception ex) {
            if (ex instanceof NumberFormatException) {
                eroare.setContentText("Number format exception " + ((NumberFormatException) ex).getMessage());
            } else {
                eroare.setContentText(ex.getMessage());
            }
            eroare.show();
        }
    }

    @FXML public void acasaMen(){
        stagePagPrinc.show();
        stageNote.close();
    }

    @FXML public void studentiMen(){
        stageStudenti.show();
        //stageNote.close();
    }

    @FXML public void temeMen(){
        stageTeme.show();
        //stageNote.close();
    }
}
/*
xmlns="http://javafx.com/javafx"
            xmlns:fx="http://javafx.com/fxml"
            fx:controller="controller.StudentController"
            prefHeight="400.0" prefWidth="600.0">
 */