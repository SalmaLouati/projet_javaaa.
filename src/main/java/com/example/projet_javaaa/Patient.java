package com.example.projet_javaaa;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Patient {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "salma";

    private int id;
    private String nom;
    private String prenom;
    private String email;
    private LocalDate dateNaissance;
    private String historique;

    public Patient() {
    }

    public Patient(int id, String nom, String prenom, String email, LocalDate dateNaissance, String historique) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.dateNaissance = dateNaissance;
        this.historique = historique;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getHistorique() {
        return historique;
    }

    public void setHistorique(String historique) {
        this.historique = historique;
    }

    // Ajouter un patient à la base de données
    public static void ajouter(String prenom, String nom, String email, LocalDate dateNaissance, String historique) {
        // Valider les entrées (ajoutez de la logique de validation si nécessaire)
        if (prenom.isEmpty() || nom.isEmpty() || email.isEmpty() || dateNaissance == null || historique.isEmpty()) {
            System.err.println("Erreur de validation : certains champs sont vides.");
            return;
        }

        Date dateNaissanceSQL = Date.valueOf(dateNaissance);

        String sql = "INSERT INTO patients (prenom, nom, email, datenaiss, historique) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, prenom);
            statement.setString(2, nom);
            statement.setString(3, email);
            statement.setDate(4, dateNaissanceSQL);
            statement.setString(5, historique);

            statement.executeUpdate();

            System.out.println("Patient ajouté avec succès!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'ajout du patient : " + e.getMessage());
        }
    }

    public static ArrayList<Patient> recupererTous() {
        ArrayList<Patient> patients = new ArrayList<>();
        String sql = "SELECT * FROM patients";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String prenom = resultSet.getString("prenom");
                String nom = resultSet.getString("nom");
                String email = resultSet.getString("email");
                LocalDate dateNaissance = resultSet.getDate("datenaiss").toLocalDate();
                String historique = resultSet.getString("historique");

                Patient patient = new Patient(id, nom, prenom, email, dateNaissance, historique);
                patients.add(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la récupération des patients : " + e.getMessage());
        }

        return patients;
    }

    public static void mettreAJour(int id, String prenom, String nom, String email, LocalDate dateNaissance, String historique) {
        if (prenom.isEmpty() || nom.isEmpty() || email.isEmpty() || dateNaissance == null || historique.isEmpty()) {
            System.err.println("Erreur de validation : certains champs sont vides.");
            return;
        }

        Date dateNaissanceSQL = Date.valueOf(dateNaissance);

        String sql = "UPDATE patients SET prenom=?, nom=?, email=?, datenaiss=?, historique=? WHERE id=?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, prenom);
            statement.setString(2, nom);
            statement.setString(3, email);
            statement.setDate(4, dateNaissanceSQL);
            statement.setString(5, historique);
            statement.setInt(6, id);

            statement.executeUpdate();

            System.out.println("Patient mis à jour avec succès!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la mise à jour du patient : " + e.getMessage());
        }
    }
    public static void supprimer(String prenom, String nom) {
        String sql = "DELETE FROM patients WHERE prenom = ? AND nom = ?";

        try (
                Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                PreparedStatement statement = connection.prepareStatement(sql);
        ) {
            statement.setString(1, prenom);
            statement.setString(2, nom);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Patient supprimé avec succès !");
            } else {
                System.out.println("Aucun patient trouvé avec ce nom et prénom.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la suppression du patient : " + e.getMessage());
        }
    }
}


