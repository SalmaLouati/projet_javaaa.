package com.example.projet_javaaa;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AjoutPatientController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private DatePicker dateOfBirthPicker;

    @FXML
    private TextArea medicalHistoryTextArea;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    public void ajouter(ActionEvent actionEvent) {
        String prenom = firstNameField.getText();
        String nom = lastNameField.getText();
        String email = emailField.getText();
        LocalDate datenaiss = dateOfBirthPicker.getValue();
        String historique = medicalHistoryTextArea.getText();

        if (prenom.isEmpty() || nom.isEmpty() || email.isEmpty() || datenaiss == null || historique.isEmpty()) {
            showAlert("Tous les champs sont obligatoires!");
            return;
        }

        Patient ajoutPatient = new Patient();

        Patient.ajouter(prenom, nom, email, datenaiss, historique);

        showAlert("Patient ajouté avec succès!");
    }

    public void quitter(ActionEvent actionEvent) {
        System.out.println("Sakkarna l caissa!");
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

        stage.close();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ajout Patient");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
