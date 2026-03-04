package com.example.model;

import javax.persistence.*;

@Entity
@Table(name = "utilisateurs")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUtilisateur;

    @Column(name = "nom", nullable = false)
    private String nomUtilisateur;

    @Column(name = "prenom", nullable = false)
    private String prenomUtilisateur;

    @Column(name = "email", nullable = false, unique = true)
    private String adresseEmail;

    // Constructeur vide obligatoire pour JPA
    public Utilisateur() {}

    // Constructeur avec paramètres
    public Utilisateur(String nomUtilisateur, String prenomUtilisateur, String adresseEmail) {
        this.nomUtilisateur = nomUtilisateur;
        this.prenomUtilisateur = prenomUtilisateur;
        this.adresseEmail = adresseEmail;
    }

    // Accesseurs
    public Long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(Long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getNomUtilisateur() {
        return nomUtilisateur;
    }

    public void setNomUtilisateur(String nomUtilisateur) {
        this.nomUtilisateur = nomUtilisateur;
    }

    public String getPrenomUtilisateur() {
        return prenomUtilisateur;
    }

    public void setPrenomUtilisateur(String prenomUtilisateur) {
        this.prenomUtilisateur = prenomUtilisateur;
    }

    public String getAdresseEmail() {
        return adresseEmail;
    }

    public void setAdresseEmail(String adresseEmail) {
        this.adresseEmail = adresseEmail;
    }

    @Override
    public String toString() {
        return "Utilisateur [id=" + idUtilisateur +
                ", nom=" + nomUtilisateur +
                ", prenom=" + prenomUtilisateur +
                ", email=" + adresseEmail + "]";
    }
}