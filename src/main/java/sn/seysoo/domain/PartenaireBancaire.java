package sn.seysoo.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A PartenaireBancaire.
 */

@Document(collection = "partenaire_bancaire")
public class PartenaireBancaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 30)
    @Field("nom_reseau")
    private String nomReseau;
    
    @DBRef
    List<Eleveur> eleveurs;

    public List<Eleveur> getEleveurs() {
		return eleveurs;
	}



    public void setEleveurs(List<Eleveur> eleveurs) {
		this.eleveurs = eleveurs;
	}



	public String getId() {
        return id;
    }
    
    

    public void setId(String id) {
        this.id = id;
    }

    public String getNomReseau() {
        return nomReseau;
    }

    public PartenaireBancaire nomReseau(String nomReseau) {
        this.nomReseau = nomReseau;
        return this;
    }

    public void setNomReseau(String nomReseau) {
        this.nomReseau = nomReseau;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PartenaireBancaire partenaireBancaire = (PartenaireBancaire) o;
        if (partenaireBancaire.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, partenaireBancaire.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PartenaireBancaire{" +
            "id=" + id +
            ", nomReseau='" + nomReseau + "'" +
            '}';
    }
}
