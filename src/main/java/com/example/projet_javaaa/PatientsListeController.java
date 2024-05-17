package com.example.projet_javaaa;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

public class PatientsListeController {

    @FXML
    private TableView<Patient> patientTableView;

    @FXML
    private TableColumn<Patient, Integer> patientIdColumn;

    @FXML
    private TableColumn<Patient, String> firstNameColumn;

    @FXML
    private TableColumn<Patient, String> lastNameColumn;

    @FXML
    private TableColumn<Patient, String> emailColumn;

    @FXML
    private TableColumn<Patient, LocalDate> datenaiss;

    @FXML
    private TableColumn<Patient, String> historique;

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private void initialize() {
        patientIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        datenaiss.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));
        historique.setCellValueFactory(new PropertyValueFactory<>("historique"));

        chargerPatients();
    }
        private void chargerPatients() {
            ArrayList<Patient> patients = Patient.recupererTous();

            System.out.println("Nombre de patients récupérés depuis la base de données : " + patients.size());

            for (Patient patient : patients) {
                System.out.println("Patient récupéré : " + patient.getId() + ", " + patient.getPrenom() + " " + patient.getNom());
            }

            // Ajouter tous les patients à la TableView
            patientTableView.getItems().addAll(patients);
        }



    @FXML
    private void AjoutPatient() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ajoutPatient.fxml"));
        Parent root = loader.load();
        Stage registrationStage = new Stage();
        Scene scene = new Scene(root);
        registrationStage.setScene(scene);
        registrationStage.show();
    }


    @FXML
    private void ModifPatient() {
        Patient selectedPatient = patientTableView.getSelectionModel().getSelectedItem();

        if (selectedPatient != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modifierPatients.fxml"));
            Parent root;
            try {
                root = loader.load();
                ModifierPatientController controller = loader.getController();

                controller.donnees(selectedPatient);

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Erreur lors du chargement de la page de modification.");
            }
        } else {
            showAlert("Veuillez sélectionner un patient à modifier.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("patient modifié");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
    @FXML
    private void SupprPatient() {
        Patient selectedPatient = patientTableView.getSelectionModel().getSelectedItem();

        if (selectedPatient != null) {
            Patient.supprimer(selectedPatient.getPrenom(), selectedPatient.getNom());

            showAlert("Patient supprimé, Le patient a été supprimé avec succès.");
        } else {
            showAlert("Aucun patient sélectionne, Veuillez sélectionner un patient à supprimer.");
        }
    }


}