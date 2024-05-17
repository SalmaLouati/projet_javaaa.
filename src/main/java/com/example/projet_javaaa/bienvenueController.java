package com.example.projet_javaaa;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class bienvenueController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button inscriptionButton;

    @FXML
    private Button authButton;

    public void inscription(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("inscription.fxml"));
        Parent root = loader.load();
        Stage registrationStage = new Stage();
        Scene scene = new Scene(root);
        registrationStage.setScene(scene);
        registrationStage.show();
    }

    public void authentification(ActionEvent actionEvent) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("authentification.fxml"));
        Parent root = loader.load();
        Stage loginStage = new Stage();
        Scene scene = new Scene(root);
        loginStage.setScene(scene);
        loginStage.show();
    }

}
