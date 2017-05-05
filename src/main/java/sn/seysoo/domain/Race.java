package sn.seysoo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Race.
 */

@Document(collection = "race")
public class Race implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 30)
    @Field("nom")
    private String nom;

    @NotNull
    @Field("effectif")
    private Integer effectif;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Race nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getEffectif() {
        return effectif;
    }

    public Race effectif(Integer effectif) {
        this.effectif = effectif;
        return this;
    }

    public void setEffectif(Integer effectif) {
        this.effectif = effectif;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Race race = (Race) o;
        if (race.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, race.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Race{" +
            "id=" + id +
            ", nom='" + nom + "'" +
            ", effectif='" + effectif + "'" +
            '}';
    }
}
