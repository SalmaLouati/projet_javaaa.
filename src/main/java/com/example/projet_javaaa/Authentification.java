package com.example.projet_javaaa;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class Authentification {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "salma";

    public void login(String email, String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT motdepasse FROM medecin WHERE email = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, email);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String motDePasseBase = resultSet.getString("motdepasse");
                        if (motDePasseBase.equals(password)) {
                            showAlert("Authentification réussie!", true);
                        } else {
                            showAlert("Mot de passe incorrect!", false);
                        }
                    } else {
                        showRegistrationAlert();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'authentification : " + e.getMessage());
        }
    }

    private void showAlert(String message, boolean success) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(success ? "Succès" : "Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);

        if (!success) {
            alert.showAndWait();
            return;
        }

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("afficherPatients.fxml"));
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showRegistrationAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Inscription");
        alert.setHeaderText("Adresse e-mail non enregistrée");
        alert.setContentText("L'adresse e-mail saisie n'est pas enregistrée.\nVoulez-vous vous inscrire maintenant?");

        ButtonType buttonTypeOui = new ButtonType("Oui");
        ButtonType buttonTypeNon = new ButtonType("Non");
        alert.getButtonTypes().setAll(buttonTypeOui, buttonTypeNon);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == buttonTypeOui) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("inscription.fxml"));
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
