package sn.seysoo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AvisPerte.
 */

@Document(collection = "avis_perte")
public class AvisPerte implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 30)
    @Field("animal_id")
    private String animalId;

    @NotNull
    @Size(max = 30)
    @Field("nom_animalper")
    private String nomAnimalper;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnimalId() {
        return animalId;
    }

    public AvisPerte animalId(String animalId) {
        this.animalId = animalId;
        return this;
    }

    public void setAnimalId(String animalId) {
        this.animalId = animalId;
    }

    public String getNomAnimalper() {
        return nomAnimalper;
    }

    public AvisPerte nomAnimalper(String nomAnimalper) {
        this.nomAnimalper = nomAnimalper;
        return this;
    }

    public void setNomAnimalper(String nomAnimalper) {
        this.nomAnimalper = nomAnimalper;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AvisPerte avisPerte = (AvisPerte) o;
        if (avisPerte.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, avisPerte.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AvisPerte{" +
            "id=" + id +
            ", animalId='" + animalId + "'" +
            ", nomAnimalper='" + nomAnimalper + "'" +
            '}';
    }
}
