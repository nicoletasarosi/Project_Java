package controller;

import domain.StructuraAnUniversitar;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.awt.*;

public class PagPrincController {
    @FXML
    Button student;
    @FXML
    Button tema;
    @FXML
    Button nota;
    @FXML
    Button addDate;
    @FXML
    TextField zi;
    @FXML
    TextField luna;
    @FXML
    TextField an;

    private StructuraAnUniversitar structuraAnUniversitar;

    private Stage studentStage;
    private Stage temaStage;
    private Stage notaStage;
    private Stage pagPrinc;



    @FXML public void initialize(){
    }

    public void set(Stage studentStage,Stage temaStage,Stage notaStage,Stage pagPrinc){
        this.studentStage=studentStage;
        this.temaStage=temaStage;
        this.notaStage=notaStage;
        this.pagPrinc=pagPrinc;
    }

    @FXML public void studentPage(){
        studentStage.show();
        pagPrinc.close();
    }

    @FXML public void temaPage(){
        temaStage.show();
        pagPrinc.close();
    }

    @FXML public void notaPage(){
        notaStage.show();
        pagPrinc.close();
    }

    public StructuraAnUniversitar getStructuraAnUniversitar(){
        return structuraAnUniversitar;
    }
}
