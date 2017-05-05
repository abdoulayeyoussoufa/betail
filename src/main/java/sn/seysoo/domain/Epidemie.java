package sn.seysoo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Epidemie.
 */

@Document(collection = "epidemie")
public class Epidemie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Size(max = 30)
    @Field("nom")
    private String nom;

    @Field("date")
    private LocalDate date;

    @Size(max = 256)
    @Field("desciption")
    private String desciption;
    
    @DBRef
    Veterinaire veterinaire;

    public Veterinaire getVeterinaire() {
		return veterinaire;
	}

	public void setVeterinaire(Veterinaire veterinaire) {
		this.veterinaire = veterinaire;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Epidemie nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDate() {
        return date;
    }

    public Epidemie date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDesciption() {
        return desciption;
    }

    public Epidemie desciption(String desciption) {
        this.desciption = desciption;
        return this;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Epidemie epidemie = (Epidemie) o;
        if (epidemie.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, epidemie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Epidemie{" +
            "id=" + id +
            ", nom='" + nom + "'" +
            ", date='" + date + "'" +
            ", desciption='" + desciption + "'" +
            '}';
    }
}
