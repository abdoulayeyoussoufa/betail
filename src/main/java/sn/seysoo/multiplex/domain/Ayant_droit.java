package sn.seysoo.multiplex.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Ayant_droit.
 */
@Entity
@Table(name = "ayant_droit")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ayant_droit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @NotNull
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotNull
    @Column(name = "sexe", nullable = false)
    private String sexe;

    @Column(name = "lien_parent")
    private String lien_parent;

    @Column(name = "date_naissance")
    private LocalDate date_naissance;

    @ManyToOne
    private Assure ayant_assure;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Ayant_droit nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Ayant_droit prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSexe() {
        return sexe;
    }

    public Ayant_droit sexe(String sexe) {
        this.sexe = sexe;
        return this;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getLien_parent() {
        return lien_parent;
    }

    public Ayant_droit lien_parent(String lien_parent) {
        this.lien_parent = lien_parent;
        return this;
    }

    public void setLien_parent(String lien_parent) {
        this.lien_parent = lien_parent;
    }

    public LocalDate getDate_naissance() {
        return date_naissance;
    }

    public Ayant_droit date_naissance(LocalDate date_naissance) {
        this.date_naissance = date_naissance;
        return this;
    }

    public void setDate_naissance(LocalDate date_naissance) {
        this.date_naissance = date_naissance;
    }

    public Assure getAyant_assure() {
        return ayant_assure;
    }

    public Ayant_droit ayant_assure(Assure assure) {
        this.ayant_assure = assure;
        return this;
    }

    public void setAyant_assure(Assure assure) {
        this.ayant_assure = assure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ayant_droit ayant_droit = (Ayant_droit) o;
        if(ayant_droit.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, ayant_droit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Ayant_droit{" +
            "id=" + id +
            ", nom='" + nom + "'" +
            ", prenom='" + prenom + "'" +
            ", sexe='" + sexe + "'" +
            ", lien_parent='" + lien_parent + "'" +
            ", date_naissance='" + date_naissance + "'" +
            '}';
    }
}
