package sn.seysoo.domain;

import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A Maladie.
 */

@Document(collection = "maladie")
public class Maladie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 30)
    @Field("nom")
    private String nom;

    @Size(max = 20)
    @Field("type")
    private String type;

    @Size(max = 256)
    @Field("description")
    private String description;
    
    
    private List<Animal> animals;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Maladie nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public Maladie type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public Maladie description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Maladie maladie = (Maladie) o;
        if (maladie.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, maladie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Maladie{" +
            "id=" + id +
            ", nom='" + nom + "'" +
            ", type='" + type + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
