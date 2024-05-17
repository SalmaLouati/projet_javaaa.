package com.example.projet_javaaa;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthentificationController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    public void login(ActionEvent actionEvent) throws IOException {

        String nom = usernameField.getText();
        String mdp = passwordField.getText();

        if (nom.isEmpty() || mdp.isEmpty()){
            showAlert("Tous les champs sont obligatoires!");
            return;
        }
        Authentification authentification = new Authentification();
        authentification.login(nom,mdp);

    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Authentification");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
