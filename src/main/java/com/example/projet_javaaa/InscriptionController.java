package com.example.projet_javaaa;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class InscriptionController {

    @FXML
    private TextField prenomField;

    @FXML
    private TextField nomField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField mdpField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Label errorMessageLabel;

    @FXML
    public void inscription(ActionEvent actionEvent) throws IOException {
        String prenom = prenomField.getText().trim();
        String nom = nomField.getText().trim();
        String email = emailField.getText().trim();
        String password = mdpField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validate input
        if (prenom.isEmpty() || nom.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            errorMessageLabel.setText("Veuillez remplir tous les champs.");
            errorMessageLabel.setVisible(true);
            return;
        }

        if (!password.equals(confirmPassword)) {
            errorMessageLabel.setText("Les mots de passe ne correspondent pas.");
            errorMessageLabel.setVisible(true);
            return;
        }

        // Clear error message
        errorMessageLabel.setVisible(false);

        InscriptionMed inscriptionMed = new InscriptionMed();
        inscriptionMed.inscription(prenom, nom, email, password, confirmPassword);
    }
}
