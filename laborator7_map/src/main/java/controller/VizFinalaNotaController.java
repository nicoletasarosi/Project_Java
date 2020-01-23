package controller;

import domain.StructuraAnUniversitar;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.ServiceNota;
import service.StudentService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VizFinalaNotaController {

    @FXML
    Button okBut;

    @FXML
    Button cancelBut;


    @FXML
    Label numeVal;
    @FXML
    Label prenumeVal;
    @FXML
    Label labVal;
    @FXML
    Label dataVal;
    @FXML
    Label penalizareVal;
    @FXML
    Label motivareVal;
    @FXML
    Label feedbackVal;
    @FXML
    Label profesorVal;
    private static boolean val=false;


    private Stage self;

    public VizFinalaNotaController(){}

    @FXML
    public void initialize() {
    }

    public void set(Stage self,String nume,String prenume,String lab, String data, String penalizare, String motivare,String feedback,String profesor){
        this.self=self;
        numeVal.setText(nume);
        prenumeVal.setText(prenume);
        labVal.setText(lab);
        dataVal.setText(data);
        penalizareVal.setText(penalizare);
        motivareVal.setText(motivare);
        feedbackVal.setText(feedback);
        profesorVal.setText(profesor);
    }

    public static boolean getVal(){
        return val;
    }

    @FXML
    public void ok(javafx.event.ActionEvent actionEvent){
        val=true;
        self.close();
    }
    @FXML
    public void cancel(javafx.event.ActionEvent actionEvent){
        val=false;
        self.close();
    }
}

