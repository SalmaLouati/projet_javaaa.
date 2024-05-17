package com.example.projet_javaaa;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;


import java.io.IOException;
import java.time.LocalDate;

public class ModifierPatientController {

    @FXML
    private TextField patientIdField;

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

    private Patient patient;

    public void donnees(Patient patient) {
        this.patient = patient;
        patientIdField.setText(Integer.toString(patient.getId()));
        firstNameField.setText(patient.getPrenom());
        lastNameField.setText(patient.getNom());
        emailField.setText(patient.getEmail());
        dateOfBirthPicker.setValue(patient.getDateNaissance());
        medicalHistoryTextArea.setText(patient.getHistorique());
    }

    public void enregistrer(ActionEvent actionEvent) throws IOException {
        String prenom = firstNameField.getText();
        String nom = lastNameField.getText();
        String email = emailField.getText();
        LocalDate dateNaissance = dateOfBirthPicker.getValue();
        String historique = medicalHistoryTextArea.getText();

        if (prenom.isEmpty() || nom.isEmpty() || email.isEmpty() || dateNaissance == null || historique.isEmpty()) {
            showAlert("Tous les champs sont obligatoires!");
            return;
        }

        Patient.mettreAJour(patient.getId(), prenom, nom, email, dateNaissance, historique);

        showAlert("Patient mis à jour avec succès!");
        ((Stage) saveButton.getScene().getWindow()).close();

    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Modification du patient");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public void quitter(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

        stage.close();
    }
}
