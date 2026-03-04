package com.example.model;

import javax.persistence.*;

@Entity
@Table(name = "salles")
public class Salle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSalle;

    @Column(name = "nom", nullable = false)
    private String nomSalle;

    @Column(name = "capacite", nullable = false)
    private Integer nombrePlaces;

    @Column(name = "description", length = 500)
    private String details;

    // Constructeur vide nécessaire pour JPA
    public Salle() {
    }

    // Constructeur avec paramètres
    public Salle(String nomSalle, Integer nombrePlaces) {
        this.nomSalle = nomSalle;
        this.nombrePlaces = nombrePlaces;
    }

    // Méthodes getters et setters
    public Long getIdSalle() {
        return idSalle;
    }

    public void setIdSalle(Long idSalle) {
        this.idSalle = idSalle;
    }

    public String getNomSalle() {
        return nomSalle;
    }

    public void setNomSalle(String nomSalle) {
        this.nomSalle = nomSalle;
    }

    public Integer getNombrePlaces() {
        return nombrePlaces;
    }

    public void setNombrePlaces(Integer nombrePlaces) {
        this.nombrePlaces = nombrePlaces;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Salle [id=" + idSalle +
                ", nom=" + nomSalle +
                ", capacite=" + nombrePlaces +
                ", description=" + details + "]";
    }
}