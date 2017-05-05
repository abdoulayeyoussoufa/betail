package sn.seysoo.domain;

import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonIdentityReference;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A FicheAnimal.
 */

@Document(collection = "fiche_animal")
public class FicheAnimal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("date_dec")
    private ZonedDateTime dateDec;

    @NotNull
    @Field("date_nais")
    private ZonedDateTime dateNais;

    @NotNull
    @Size(max = 30)
    @Field("lieu_nais")
    private String lieuNais;
    
    @DBRef
    @JsonIdentityReference
    Animal animal;

    @DBRef
    @JsonManagedReference
    FicheTracabilite ficheTracabilite;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ZonedDateTime getDateDec() {
        return dateDec;
    }

    public FicheAnimal dateDec(ZonedDateTime dateDec) {
        this.dateDec = dateDec;
        return this;
    }

    public void setDateDec(ZonedDateTime dateDec) {
        this.dateDec = dateDec;
    }

    public ZonedDateTime getDateNais() {
        return dateNais;
    }

    public FicheAnimal dateNais(ZonedDateTime dateNais) {
        this.dateNais = dateNais;
        return this;
    }

    public void setDateNais(ZonedDateTime dateNais) {
        this.dateNais = dateNais;
    }

    public String getLieuNais() {
        return lieuNais;
    }

    public FicheAnimal lieuNais(String lieuNais) {
        this.lieuNais = lieuNais;
        return this;
    }

    public void setLieuNais(String lieuNais) {
        this.lieuNais = lieuNais;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FicheAnimal ficheAnimal = (FicheAnimal) o;
        if (ficheAnimal.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, ficheAnimal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FicheAnimal{" +
            "id=" + id +
            ", dateDec='" + dateDec + "'" +
            ", dateNais='" + dateNais + "'" +
            ", lieuNais='" + lieuNais + "'" +
            '}';
    }
}
