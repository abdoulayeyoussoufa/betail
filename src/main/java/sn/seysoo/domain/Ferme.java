package sn.seysoo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Ferme.
 */

@Document(collection = "ferme")
public class Ferme implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 30)
    @Field("nom")
    private String nom;

    @NotNull
    @Size(max = 15)
    @Field("superficie")
    private String superficie;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Ferme nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSuperficie() {
        return superficie;
    }

    public Ferme superficie(String superficie) {
        this.superficie = superficie;
        return this;
    }

    public void setSuperficie(String superficie) {
        this.superficie = superficie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ferme ferme = (Ferme) o;
        if (ferme.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, ferme.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Ferme{" +
            "id=" + id +
            ", nom='" + nom + "'" +
            ", superficie='" + superficie + "'" +
            '}';
    }
}
