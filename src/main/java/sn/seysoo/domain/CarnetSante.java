package sn.seysoo.domain;

import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A CarnetSante.
 */

@Document(collection = "carnet_sante")
public class CarnetSante implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("dat_vaccination")
    private ZonedDateTime datVaccination;

    @Size(max = 30)
    @Field("nom")
    private String nom;

    public String getId() {
        return id;
    }
    
    @DBRef
    @JsonBackReference
    Animal animal;

    public void setId(String id) {
        this.id = id;
    }

    public ZonedDateTime getDatVaccination() {
        return datVaccination;
    }

    public CarnetSante datVaccination(ZonedDateTime datVaccination) {
        this.datVaccination = datVaccination;
        return this;
    }

    public void setDatVaccination(ZonedDateTime datVaccination) {
        this.datVaccination = datVaccination;
    }

    public String getNom() {
        return nom;
    }

    public CarnetSante nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarnetSante carnetSante = (CarnetSante) o;
        if (carnetSante.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, carnetSante.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CarnetSante{" +
            "id=" + id +
            ", datVaccination='" + datVaccination + "'" +
            ", nom='" + nom + "'" +
            '}';
    }
}
