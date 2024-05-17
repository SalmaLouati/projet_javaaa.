package com.example.projet_javaaa;

import java.sql.*;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class InscriptionMed {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "salma";

    public static void createMedTable(Connection connection) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS medecin (\n" +
                "    id SERIAL PRIMARY KEY,\n" +
                "    prenom VARCHAR(255) NOT NULL,\n" +
                "    nom VARCHAR(255) NOT NULL,\n" +
                "    email VARCHAR(255) UNIQUE NOT NULL,\n" +
                "    motdepasse VARCHAR(255) NOT NULL,\n" +
                "    confirmer VARCHAR(255) NOT NULL\n" +
                ");";

        Statement statement = connection.createStatement();
        statement.execute(sql);
        statement.close();
    }

    public void inscription(String prenom, String nom, String email, String password, String confirmPassword) {
        if (prenom.isEmpty() || nom.isEmpty() || email.isEmpty() || password.isEmpty()) {
            System.err.println("Erreur de validation : certains champs sont vides.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            createMedTable(connection);

            if (isEmailExistant(connection, email)) {
                System.out.println("Erreur : L'adresse email existe déjà.");
                showAlertAndRedirect("Erreur : L'adresse email existe déjà.", true);
                return;
            }

            String sql = "INSERT INTO medecin (prenom, nom, email, motdepasse, confirmer) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, prenom);
                statement.setString(2, nom);
                statement.setString(3, email);
                statement.setString(4, password);
                statement.setString(5, confirmPassword);
                statement.executeUpdate();

                showAlertAndRedirect("Médecin inscrit avec succès!", false);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'inscription du médecin : " + e.getMessage());
        }
    }

    private boolean isEmailExistant(Connection connection, String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM medecin WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }

    private void showAlertAndRedirect(String message, boolean showError) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Inscription de médecin");
        alert.setHeaderText(showError ? "Erreur" : "Succès");
        alert.setContentText(message);

        if (showError) {
            alert.getButtonTypes().clear();
            alert.getButtonTypes().add(ButtonType.OK);
            alert.setOnCloseRequest(event -> {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("inscription.fxml"));
                try {
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("afficherPatients.fxml"));
            try {
                Parent root = loader.load();
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        alert.showAndWait();
    }
}
