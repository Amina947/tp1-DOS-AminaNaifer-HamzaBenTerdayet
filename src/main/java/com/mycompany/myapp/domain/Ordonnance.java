package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Ordonnance.
 */
@Entity
@Table(name = "ordonnance")
public class Ordonnance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "medicament")
    private String medicament;

    @OneToOne(mappedBy = "ordonnance")
    @JsonIgnore
    private Patient patient;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumero() {
        return numero;
    }

    public Ordonnance numero(Integer numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getMedicament() {
        return medicament;
    }

    public Ordonnance medicament(String medicament) {
        this.medicament = medicament;
        return this;
    }

    public void setMedicament(String medicament) {
        this.medicament = medicament;
    }

    public Patient getPatient() {
        return patient;
    }

    public Ordonnance patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ordonnance)) {
            return false;
        }
        return id != null && id.equals(((Ordonnance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ordonnance{" +
            "id=" + getId() +
            ", numero=" + getNumero() +
            ", medicament='" + getMedicament() + "'" +
            "}";
    }
}
