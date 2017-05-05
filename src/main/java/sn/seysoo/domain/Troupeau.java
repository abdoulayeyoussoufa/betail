package sn.seysoo.domain;

import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A Troupeau.
 */

@Document(collection = "troupeau")
public class Troupeau implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Size(max = 30)
    @Field("categorie")
    private String categorie;

    @NotNull
    @Field("effectif")
    private Integer effectif;
    
    @DBRef
    @JsonManagedReference
    List<Animal> animaux;
    
    @DBRef
    List<Race> races;

    public String getId() {
        return id;
    }

    public List<Animal> getAnimaux() {
		return animaux;
	}

	public void setAnimaux(List<Animal> animaux) {
		this.animaux = animaux;
	}

	public void setId(String id) {
        this.id = id;
    }

    public String getCategorie() {
        return categorie;
    }

    public Troupeau categorie(String categorie) {
        this.categorie = categorie;
        return this;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public Integer getEffectif() {
        return effectif;
    }

    public Troupeau effectif(Integer effectif) {
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
        Troupeau troupeau = (Troupeau) o;
        if (troupeau.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, troupeau.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Troupeau{" +
            "id=" + id +
            ", categorie='" + categorie + "'" +
            ", effectif='" + effectif + "'" +
            '}';
    }
}
