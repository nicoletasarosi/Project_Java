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

public class DataController {

    @FXML
    Button adaugaBut;

    @FXML
    TextField ziField;

    @FXML
    TextField lunaField;

    @FXML
    TextField anField;

    private Stage self;
    private Stage pagPrinc;
    private TemaController temaController;
    private ServiceNota serviceNota;

    private StructuraAnUniversitar structuraAn;

    public DataController(){}

    @FXML
    public void initialize() {
    }

    public void set(Stage self, Stage pagPrinc, TemaController temaController, ServiceNota serviceNota){
        this.self=self;
        this.pagPrinc=pagPrinc;
        this.temaController=temaController;
        this.serviceNota=serviceNota;
    }

    public StructuraAnUniversitar getStructuraAn(){
        return structuraAn;
    }

    @FXML
    public void onEnter(javafx.event.ActionEvent actionEvent){
        String zi=ziField.getText();
        String luna=lunaField.getText();
        String an=anField.getText();
        String data=an+"-"+luna+"-"+zi;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date myDate = format.parse(data);
            structuraAn = new StructuraAnUniversitar("1", "a", "b", myDate);
            temaController.setStructuraAnUniversitar(structuraAn);
            serviceNota.addFilePrivateFile(structuraAn);
            self.close();
            pagPrinc.show();
        }catch (ParseException ex){
            Alert alerta=new Alert(Alert.AlertType.ERROR);
            alerta.setContentText(ex.getMessage());
            alerta.show();
        }
    }

    @FXML
    public void adauga(javafx.event.ActionEvent actionEvent) {
        String zi=ziField.getText();
        String luna=lunaField.getText();
        String an=anField.getText();
        String data=an+"-"+luna+"-"+zi;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date myDate = format.parse(data);
            structuraAn = new StructuraAnUniversitar("1", "a", "b", myDate);
            temaController.setStructuraAnUniversitar(structuraAn);
            self.close();
            pagPrinc.show();
        }catch (ParseException ex){
            Alert alerta=new Alert(Alert.AlertType.ERROR);
            alerta.setContentText(ex.getMessage());
            alerta.show();
        }
    }
}

