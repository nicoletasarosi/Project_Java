package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import javafx.scene.control.Label;

public class ErrorController {

    @FXML
    Label eroare;

    public ErrorController(){}

    @FXML
    public void initialize() {
    }

    public void setEroare(String er) {
        eroare.setText(er);
    }
}
