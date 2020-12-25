package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Patient.
 */
@Entity
@Table(name = "patient")
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "tel")
    private Long tel;

    @ManyToOne
    @JsonIgnoreProperties(value = "patients", allowSetters = true)
    private Medecin medecin;

    @OneToOne
    @JoinColumn(unique = true)
    private Ordonnance ordonnance;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Patient nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Long getTel() {
        return tel;
    }

    public Patient tel(Long tel) {
        this.tel = tel;
        return this;
    }

    public void setTel(Long tel) {
        this.tel = tel;
    }

    public Medecin getMedecin() {
        return medecin;
    }

    public Patient medecin(Medecin medecin) {
        this.medecin = medecin;
        return this;
    }

    public void setMedecin(Medecin medecin) {
        this.medecin = medecin;
    }

    public Ordonnance getOrdonnance() {
        return ordonnance;
    }

    public Patient ordonnance(Ordonnance ordonnance) {
        this.ordonnance = ordonnance;
        return this;
    }

    public void setOrdonnance(Ordonnance ordonnance) {
        this.ordonnance = ordonnance;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Patient)) {
            return false;
        }
        return id != null && id.equals(((Patient) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Patient{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", tel=" + getTel() +
            "}";
    }
}
